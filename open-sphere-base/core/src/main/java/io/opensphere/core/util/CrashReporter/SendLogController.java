package io.opensphere.core.util.CrashReporter;

import java.net.MalformedURLException;
import java.net.URL;

public interface SendLogController {

	public boolean ConnectToServer();

	public void postBug();

	public void uploadfiles();

	public void initializeServer();
	/**
	 * @return URL the current website address
	 */
	public URL getMyUrl();

	/**
	 * @param theURL the url for the JIRA Host Server
	 * @throws MalformedURLException
	 */
	public URL setMyUrl(String theURL) throws MalformedURLException;

}
