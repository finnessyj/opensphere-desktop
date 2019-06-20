package io.opensphere.core.util.CrashReporter;

import org.json.simple.JSONObject;

public interface SendLogController {

	public boolean ConnectToServer();

	public JSONObject postBug();

	public void uploadfiles();
	

	public void checkIssueStatus();

}
