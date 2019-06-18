package io.opensphere.core.util.CrashReporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;

import io.opensphere.core.Toolbox;
import io.opensphere.core.server.ContentType;
import io.opensphere.core.server.HttpServer;
import io.opensphere.core.server.ResponseValues;
import io.opensphere.core.util.io.StreamReader;
import io.opensphere.core.util.lang.NamedThreadFactory;
import io.opensphere.core.util.lang.StringUtilities;
import io.opensphere.core.util.lang.ThreadUtilities;

/**
 * Sends crash log files to the server. -eventually integrate to send to JIRA
 */
public class SendLogControllerImpl implements SendLogController

{
    /**
     * Used to log messages.
     */

    private final Toolbox myToolbox;

    private final ResponseValues myResponseValues = new ResponseValues();

    private static final Logger myLOGGER = Logger.getLogger(SendLogControllerImpl.class);

    private URL myURL;

    private final SendLogModel myModel = new SendLogModel();

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
        // --> Reccommend adding in a Map<String,String> to the function call of
        // SendLogControllerImpl
        // to get this information
        myModel.initializeUserPass("Admin1", "Boulder20!");
    }

    public boolean ConnectToServer()
    {

        myModel.setMyUrl("https://localhost:8443");
        ExecutorService test = Executors.newCachedThreadPool(new NamedThreadFactory("IO-Worker"));
        Future<?> theTracker = test.submit(() ->
        {

            try
            {
                myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myModel.getMyUrl())
                        .sendGet(myModel.getMyUrl(), myResponseValues);
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
                return status;
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        return status;
    }

    public void postBug()
    {
        myModel.setMyUrl("https://localhost:8443/rest/api/2/issue");
        ExecutorService test = Executors.newCachedThreadPool(new NamedThreadFactory("IO-Worker"));
        Future<?> theTracker = test.submit(() ->
        {
            try
            {
                String string = new String(
                        "{\n\"fields\":{\n\"project\": \n\n{\n    \"key\":\"BUGS\"\n},\n\"summary\":\"Send From MIST with SSL\",\n\"issuetype\": "
                                + "{\n    \"name\": \"Task\"\n    }\n}\n}");
                InputStream postData = new ByteArrayInputStream(string.getBytes());
                InputStream theStream2 = myToolbox.getServerProviderRegistry().getProvider(HttpServer.class)
                        .getServer(myModel.getMyUrl())
                        .sendPost(myModel.getMyUrl(), postData, myModel.getPostHeaders(), myResponseValues, ContentType.JSON);
                System.out.println(new StreamReader(theStream2).readStreamIntoString(StringUtilities.DEFAULT_CHARSET));

            }
            catch (IOException | URISyntaxException e)
            {
            }
        });
        test.shutdown();
        connectionNotify(theTracker);
    }

    public void uploadfiles()
    {

        myModel.setMyUrl("https://localhost:8443/rest/api/2/issue/NEW-1/attachments");

        ExecutorService test = Executors.newCachedThreadPool(new NamedThreadFactory("IO-Worker"));
        Future<?> theTracker = test.submit(() ->
        {

            File theFile = new File("/home/crombiek/Desktop/it.JSON");
            try
            {
                myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myModel.getMyUrl())
                        .postJIRAFile(myModel.getMyUrl(), theFile, myResponseValues, myModel.getFileUploadHeaders());
            }
            catch (IOException | URISyntaxException e)
            {
            }

        });
        test.shutdown();
        connectionNotify(theTracker);
    }

    public void initializeServer()
    {
        myModel.setMyUrl("https://localhost:8443/rest/api/2/issue");

        ExecutorService test = Executors.newCachedThreadPool(new NamedThreadFactory("IO-Worker"));
        Future<?> theTracker = test.submit(() ->
        {
            try
            {
                String string = new String(
                        "{\n\"fields\":{\n\"project\": \n\n{\n    \"key\":\"BUGS\"\n},\n\"summary\":\"Send From MIST with SSL\",\n\"issuetype\": "
                                + "{\n    \"name\": \"Task\"\n    }\n}\n}");
                InputStream postData = new ByteArrayInputStream(string.getBytes());
                myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myModel.getMyUrl())
                        .sendPost(myModel.getMyUrl(), postData, myModel.getPostHeaders(), myResponseValues, ContentType.JSON);

            }
            catch (IOException | URISyntaxException e)
            {
            }
        });
        test.shutdown();
        connectionNotify(theTracker);
    }

}