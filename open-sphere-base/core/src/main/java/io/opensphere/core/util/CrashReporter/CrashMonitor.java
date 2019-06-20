package io.opensphere.core.util.CrashReporter;

public class CrashMonitor
{

    private SendLogModel mySendLogModel;

    public CrashMonitor(SendLogModel theSendModel)
    {
        mySendLogModel = theSendModel;
    }

    public void ThreadMonitor() {
        //Monitor for potential memory issues//
        //mySendLogModel.getToolbox().getSystemToolbox().getMemoryManager().getMemoryStatus()
    }
}
