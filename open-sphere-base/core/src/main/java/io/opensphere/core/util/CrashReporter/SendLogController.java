package io.opensphere.core.util.CrashReporter;

import java.net.MalformedURLException;
import java.net.URL;

public interface SendLogController {

	public boolean ConnectToServer();

	public void postBug();

	public void uploadfiles();

	public void initializeServer();

}
