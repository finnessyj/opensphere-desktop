package io.opensphere.core.util.CrashReporter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.opensphere.core.Toolbox;
import io.opensphere.core.server.ContentType;
import io.opensphere.core.server.HttpServer;
import io.opensphere.core.server.ResponseValues;
import io.opensphere.core.util.lang.NamedThreadFactory;

/**
 * Sends crash log files to the server. -eventually integrate to send to JIRA
 */
public class SendLogControllerImpl implements SendLogController

{
    /**
     * Used to log messages.
     */

    private final ResponseValues myResponseValues = new ResponseValues();

    private static final Logger myLOGGER = Logger.getLogger(SendLogControllerImpl.class);

    private Future<?> theTracker;

    private JSONObject myResponseJSON;

    private Toolbox myToolbox;

    private SendLogModel myModel = new SendLogModel();

    /**
     * Constructs a new controller.
     *
     * @param toolbox The system toolbox.
     * @throws MalformedURLException
     */
    public SendLogControllerImpl(Toolbox toolbox)
    {
        myToolbox = toolbox;
        // HIGHSIDE: This will need to be linked to local users password
        // --> Recommend adding in a Map<String,String> to the function call of
        // SendLogControllerImpl
        // to get this information
        myModel.initializeUserPass("Admin1", "Boulder20!");
    }

    public boolean ConnectToServer()
    {
        myModel.setUrl("https://localhost:8443");

        ExecutorService test = Executors.newCachedThreadPool(new NamedThreadFactory("IO-Worker"));
        Future<?> theTracker = test.submit(() ->
        {
            try
            {
                myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myModel.getUrl())
                        .sendGet(myModel.getUrl(), myResponseValues);
            }
            catch (IOException | URISyntaxException e)
            {
                e.printStackTrace();
            }
        });
        test.shutdown();
        return connectionNotify(theTracker);

    }

    public boolean connectionNotify(Future<?> theTask)
    {
        Integer[] SuccessCodes = { 200, 201, 202 };
        Integer[] FailCodes = { 400, 401, 403, 404, 405, 406, 407, 408, 409, 410, 504 };
        boolean status = false;
        try
        {
            if (theTask.get() == null)
            {
                if (Arrays.asList(SuccessCodes).contains(myResponseValues.getResponseCode()))
                {
                    status = true;
                    myLOGGER.info("Successfully Connected to the JIRA Server");
                }
                if (Arrays.asList(FailCodes).contains(myResponseValues.getResponseCode()))
                {
                    status = false;
                    myLOGGER.info("Failed to connect to the JIRA Server, code: " + myResponseValues.getResponseCode());
                }

                if (!(Arrays.asList(FailCodes).contains(myResponseValues.getResponseCode()))
                        && !(Arrays.asList(SuccessCodes).contains(myResponseValues.getResponseCode())))
                {
                    status = false;
                    myLOGGER.info("Unknown JIRA exemeption, code: " + myResponseValues.getResponseCode());
                }

                return status;
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        return status;
    }

    public JSONObject postBug()
    {
        myModel.setUrl("https://localhost:8443/rest/api/2/issue");
        ExecutorService test = Executors.newCachedThreadPool(new NamedThreadFactory("IO-Worker"));
        Future<?> theTracker = test.submit(() ->
        {
            try
            {

                InputStream theStream = myToolbox.getServerProviderRegistry().getProvider(HttpServer.class)
                        .getServer(myModel.getUrl()).sendPost(myModel.getUrl(), myModel.getLogManager().getDatatoPost(null),
                                myModel.getPostHeaders(), myResponseValues, ContentType.JSON);
                myResponseJSON = (JSONObject)new JSONParser().parse(new InputStreamReader(theStream, "UTF-8"));

                // Adds subtask to above task
//                myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myModel.getMyUrl()).sendPost(
//                        myModel.getMyUrl(), myModel.getMyLogManager().getDatatoPost(myResponseJSON), myModel.getPostHeaders(),
//                        myResponseValues, ContentType.JSON);
            }
            catch (IOException | URISyntaxException | ParseException e)
            {
                e.printStackTrace();
            }
        });
        test.shutdown();
        connectionNotify(theTracker);
        checkIssueStatus();
        return myResponseJSON;
    }

    public void uploadfiles()
    {
        myModel.getLogManager().getLogs();

        // For uploading to the sub-task.
        // myModel.setMyUrl(myResponseJSON.get("self")+"/attachments");
        myModel.setUrl("https://localhost:8443/rest/api/2/issue/BUGS/attachments");
        ExecutorService test = Executors.newCachedThreadPool(new NamedThreadFactory("IO-Worker"));
        for (int idx = 0; idx < 2; idx++)
        {
            if ((idx >= 0) && (idx < myModel.getLogManager().getLogs().size()))
            {
                System.out.println(myModel.getLogManager().getLogs().get(idx).getName());
                File theFile = myModel.getLogManager().getLogs().get(idx);
                theTracker = test.submit(() ->
                {
                    try
                    {
                        InputStream theStream = myToolbox.getServerProviderRegistry().getProvider(HttpServer.class)
                                .getServer(myModel.getUrl())
                                .postJIRAFile(myModel.getUrl(), theFile, myResponseValues, myModel.getFileUploadHeaders());
                        myResponseJSON = (JSONObject)new JSONParser().parse(new InputStreamReader(theStream, "UTF-8"));
                    }
                    catch (IOException | URISyntaxException | ParseException e)
                    {
                        e.printStackTrace();
                    }
                });
            }
        }
        test.shutdown();
        connectionNotify(theTracker);
    }

    public void checkIssueStatus()
    {
        myModel.setUrl("https://localhost:8443/rest/api/2/issue/AC-21");

        ExecutorService test = Executors.newCachedThreadPool(new NamedThreadFactory("IO-Worker"));
        Future<?> theTracker = test.submit(() ->
        {
            try
            {
                InputStream postData = myToolbox.getServerProviderRegistry().getProvider(HttpServer.class)
                        .getServer(myModel.getUrl()).sendGet(myModel.getUrl(), myModel.getFileUploadHeaders(), myResponseValues);
                myResponseJSON = (JSONObject)new JSONParser().parse(new InputStreamReader(postData, "UTF-8"));

                JSONObject theTest = (JSONObject)myResponseJSON.get("fields");
                JSONObject theTest2 = (JSONObject)theTest.get("status");
                System.out.println(theTest2.get("name").toString());

            }
            catch (IOException | URISyntaxException | ParseException e)
            {
                e.printStackTrace();
            }
        });
        test.shutdown();
        connectionNotify(theTracker);
    }
}
