package io.opensphere.core.util.CrashReporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import io.opensphere.core.util.lang.StringUtilities;

public class LogManager
{
    private SendLogModel mySendLogModel;

    public LogManager(SendLogModel theSendModel)
    {
        mySendLogModel = theSendModel;
    }

    public List<File> getLogs()
    {
        final StringBuilder logPath = new StringBuilder();
        logPath.append(StringUtilities.expandProperties(System.getProperty("opensphere.path.runtime"), System.getProperties()));
        logPath.append(StringUtilities.FILE_SEP);
        logPath.append("logs");

        final List<File> logs_unsort = new ArrayList<File>();
        for (File file : new File(logPath.toString()).listFiles())
        {
            if (file.isFile())
            {
                logs_unsort.add(file);
            }
        }

        return logs_unsort;
    }

    public InputStream getDatatoPost(JSONObject theJson)
    {
        String projectName = new String("AC");
        String summOfBug = new String("sent with stringsender");

        StringBuilder eat = new StringBuilder();

        if (theJson == null)
        {
            // For creating Task
            eat.append("{\"fields\":{\"project\":{");
            eat.append("\"key\":" + "\"" + projectName + "\"" + "},\"summary\": \" " + summOfBug + " \",");
            eat.append("\"issuetype\": {\"name\":\"Task\"},");
             eat.append("\"customfield_10209\": \"phone number\",");
            eat.append("\"description\": \"64444\"}}");
        }
        else
        {
            // For creating Sub-Task on top of the most recent issue**
            eat = new StringBuilder();
            String id = new String("\"" + theJson.get("id") + "\"");
            summOfBug = new String("\"This is the subtask\"");

            eat.append("{\"fields\": {\"project\": {\"key\":" + "\"" + projectName + "\"" + "}, \"parent\": { " + "\"id\":" + id
                    + "}, \"summary\":" + summOfBug
                    + ",\"description\": \"some description\", \"issuetype\": {\"name\": \"Sub-task\" }}}");
            System.out.println(eat.toString());
        }
        System.out.println(eat);
        InputStream postData = new ByteArrayInputStream(eat.toString().getBytes());
        return postData;
    }

}
