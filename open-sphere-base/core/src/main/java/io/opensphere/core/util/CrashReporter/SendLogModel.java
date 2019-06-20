package io.opensphere.core.util.CrashReporter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Map;


public class SendLogModel
{

    private URL myURL = null;

    private String myEncodedPass = null;

    private LogManager myLogManager = new LogManager();

    private CrashMonitor myCrashMonitor = new CrashMonitor();

    public SendLogModel()
    {
    }

    public void initializeUserPass(String username, String password)
    {

        String originalInput = (username + ":" + password);
        String encodedUserPass = Base64.getEncoder().encodeToString(originalInput.getBytes());
        setEncodedPass(encodedUserPass);
    }

    /**
     * @return URL the current website address
     */
    public URL getUrl()
    {
        return myURL;
    }

    /**
     * @param theURL the url for the JIRA Host Server
     */
    public URL setUrl(String string)
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
    public String getEncodedPass()
    {
        return myEncodedPass;
    }

    /**
     * Sets the value of the {@link #myEncodedPass} field.
     *
     * @param myEncodedPass the value to store in the {@link #myEncodedPass}
     *            field.
     */
    public void setEncodedPass(String myEncodedPass)
    {
        this.myEncodedPass = myEncodedPass;
    }

    /**
     * Gets the value of the {@link #myLogManager} field.
     *
     * @return the value stored in the {@link #myLogManager} field.
     */
    public LogManager getLogManager()
    {
        return myLogManager;
    }

    /**
     * Sets the value of the {@link #myLogManager} field.
     *
     * @param myLogManager the value to store in the {@link #myLogManager}
     *            field.
     */
    public void setLogManager(LogManager myLogManager)
    {
        this.myLogManager = myLogManager;
    }

    /**
     * Gets the value of the {@link #myCrashMonitor} field.
     *
     * @return the value stored in the {@link #myCrashMonitor} field.
     */
    public CrashMonitor getCrashMonitor()
    {
        return myCrashMonitor;
    }

    /**
     * Sets the value of the {@link #myCrashMonitor} field.
     *
     * @param myCrashMonitor the value to store in the {@link #myCrashMonitor}
     *            field.
     */
    public void setCrashMonitor(CrashMonitor myCrashMonitor)
    {
        this.myCrashMonitor = myCrashMonitor;
    }
}
