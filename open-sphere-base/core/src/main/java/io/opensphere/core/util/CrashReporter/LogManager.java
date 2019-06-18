package io.opensphere.core.util.CrashReporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.opensphere.core.util.lang.StringUtilities;

public class LogManager
{
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

}
