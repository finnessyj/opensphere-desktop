package io.opensphere.core.util.CrashReporter;

public interface SendLogController {

	public boolean ConnectToServer();

	public void postBug();

	public void uploadfiles();

	public void initializeServer();

}
