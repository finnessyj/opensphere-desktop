package io.opensphere.core.util.CrashReporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import io.opensphere.core.Toolbox;
import io.opensphere.core.server.ContentType;
import io.opensphere.core.server.HttpServer;
import io.opensphere.core.server.ResponseValues;
import io.opensphere.core.server.ServerProvider;
import io.opensphere.core.util.io.StreamReader;
import io.opensphere.core.util.lang.StringUtilities;
import io.opensphere.core.util.lang.ThreadUtilities;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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

    private static final Logger LOGGER = Logger.getLogger(SendLogControllerImpl.class);

    private URL myURL;

    /**
     * Constructs a new controller.
     *
     * @param toolbox The system toolbox.
     * @throws MalformedURLException
     */
    public SendLogControllerImpl(Toolbox toolbox)
    {
        myToolbox = toolbox;
        try
        {
            this.setMyUrl("http://localhost:8080/rest/api/2/issue/");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean ConnectToServer()
    {
        boolean status = false;
        ThreadUtilities.runBackground(() ->
        {
            try
            {
                this.setMyUrl("http://localhost:8080");
            }
            catch (MalformedURLException e1)
            {
            }

            try
            {
                myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myURL).sendGet(myURL,
                        myResponseValues);
            }
            catch (IOException | URISyntaxException e)
            {
                e.printStackTrace();
            }
        });
        if (myResponseValues.getResponseCode() == 200)
        {
            status = true;
            System.out.println("Successfully Connected to the Server");
        }
        return status;
    }

    public void postBug()
    {
        Map<String, String> Headers = Map.ofEntries(Map.entry("Content-Type", "application/json"),
                Map.entry("Authorization", "Basic QWRtaW4xOkJvdWxkZXIyMCE="));
        ThreadUtilities.runBackground(() ->
        {
            try
            {
                String string = new String(
                        "{\n\"fields\":{\n\"project\": \n\n{\n    \"key\":\"BUGS\"\n},\n\"summary\":\"Send From MIST\",\n\"issuetype\": "
                                + "{\n    \"name\": \"Task\"\n    }\n}\n}");
                InputStream postData = new ByteArrayInputStream(string.getBytes());
                InputStream theStream2 = myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myURL)
                        .sendPost(myURL, postData, Headers, myResponseValues, ContentType.JSON);
                System.out.println(new StreamReader(theStream2).readStreamIntoString(StringUtilities.DEFAULT_CHARSET));

            }
            catch (IOException | URISyntaxException e)
            {
            }
            System.out.println("Finishing postman");

        });
    }

    public void uploadfiles()
    {

        try
        {
            this.setMyUrl("http://localhost:8080/rest/api/2/issue/NEW-1/attachments");
        }
        catch (MalformedURLException e1)
        {
        }

        Map<String, String> Headers = Map.ofEntries(Map.entry("X-Atlassian-Token", "no-check"),
                Map.entry("Authorization", "Basic QWRtaW4xOkJvdWxkZXIyMCE="));

        ThreadUtilities.runBackground(() ->
        {

            File theFile = new File("/home/crombiek/Desktop/it.JSON");
            try
            {
                myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myURL).postJIRAFile(myURL, theFile,
                        myResponseValues, Headers);
            }
            catch (IOException | URISyntaxException e)
            {
            }
            System.out.println(myResponseValues.toString());

        });
    }

    public URL getMyUrl()
    {
        return myURL;
    }

    public URL setMyUrl(String string) throws MalformedURLException
    {
        myURL = new URL(string);
        return myURL;
    }
}