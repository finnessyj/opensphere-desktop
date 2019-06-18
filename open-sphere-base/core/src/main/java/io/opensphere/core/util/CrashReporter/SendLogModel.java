package io.opensphere.core.util.CrashReporter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

public class SendLogModel
{

    private URL myURL;

    private String myEncodedPass;

    public void initializeUserPass(String username, String password)
    {

        String originalInput = (username + ":" + password);
        String encodedUserPass = Base64.getEncoder().encodeToString(originalInput.getBytes());
        setMyEncodedPass(encodedUserPass);
    }

    /**
     * @return URL the current website address
     */
    public URL getMyUrl()
    {
        return myURL;
    }

    /**
     * @param theURL the url for the JIRA Host Server
     */
    public URL setMyUrl(String string)
    {
        try
        {
            myURL = new URL(string);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return myURL;
    }

    public Map<String, String> getPostHeaders()
    {
        Map<String, String> Headers = Map.ofEntries(Map.entry("Content-Type", "application/json"),
                Map.entry("Authorization", "Basic " + myEncodedPass));
        return Headers;
    }

    public Map<String, String> getFileUploadHeaders()
    {

        Map<String, String> Headers = Map.ofEntries(Map.entry("X-Atlassian-Token", "no-check"),
                Map.entry("Authorization", "Basic " + myEncodedPass));
        return Headers;
    }

    /**
     * Gets the value of the {@link #myEncodedPass} field.
     *
     * @return the value stored in the {@link #myEncodedPass} field.
     */
    public String getMyEncodedPass()
    {
        return myEncodedPass;
    }

    /**
     * Sets the value of the {@link #myEncodedPass} field.
     *
     * @param myEncodedPass the value to store in the {@link #myEncodedPass}
     *            field.
     */
    public void setMyEncodedPass(String myEncodedPass)
    {
        this.myEncodedPass = myEncodedPass;
    }

}
