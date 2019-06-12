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
public class SendLogController

{
	/**
	 * Used to log messages.
	 */

	private final Toolbox myToolbox;

	private final ResponseValues myResponseValues = new ResponseValues();

	private static final Logger LOGGER = Logger.getLogger(SendLogController.class);

	private URL myURL;

	/**
	 * Constructs a new controller.
	 *
	 * @param toolbox The system toolbox.
	 * @throws MalformedURLException
	 */
	public SendLogController(Toolbox toolbox) {
		myToolbox = toolbox;
		try {
			this.setMyUrl("http://localhost:8080/rest/api/2/issue/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void ConnectToServer() {
		ThreadUtilities.runBackground(() -> {
			try {
				System.out.println("Connecting to Server");
				this.setMyUrl("http://localhost:8080");
				System.out.println(myResponseValues.toString());
				System.out.println("Connected to Server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void postBug() {
		Map<String, String> Headers = Map.ofEntries(Map.entry("Content-Type", "application/json"),
				Map.entry("Authorization", "Basic QWRtaW4xOkJvdWxkZXIyMCE="));
		ThreadUtilities.runBackground(() -> {
			try {
				String string = new String(
						"{\n\"fields\":{\n\"project\": \n\n{\n    \"key\":\"BUGS\"\n},\n\"summary\":\"Send From MIST\",\n\"issuetype\": {\n    \"name\": \"Task\"\n    }\n}\n}");
				InputStream postData = new ByteArrayInputStream(string.getBytes());
				InputStream theStream2 = myToolbox.getServerProviderRegistry().getProvider(HttpServer.class)
						.getServer(myURL).sendPost(myURL, postData, Headers, myResponseValues, ContentType.JSON);
				System.out.println(new StreamReader(theStream2).readStreamIntoString(StringUtilities.DEFAULT_CHARSET));

			} catch (IOException | URISyntaxException e) {
			}
			System.out.println("Finishing postman");

		});
	}

	public void uploadfiles() {
		System.out.println("uploading files");

		try {
			this.setMyUrl("http://localhost:8080/rest/api/2/issue/BUGS-8/attachments");
			System.out.println("URL SET:  " + this.getMyUrl());
		} catch (MalformedURLException e1) {
		}

		Map<String, String> Headers = Map.ofEntries(
				Map.entry("X-Atlassian-Token", "no-check"),
				Map.entry("Authorization", "Basic QWRtaW4xOkJvdWxkZXIyMCE="),
				Map.entry("Cache-Control", "no-cache"),
				Map.entry("Host", "localhost:8080"));

		ThreadUtilities.runBackground(() -> {

			System.out.println("Begin Stream");

			File theFile = new File("/home/crombiek/Desktop/call.png");
			try {
				myToolbox.getServerProviderRegistry().getProvider(HttpServer.class).getServer(myURL).postFile(myURL,
						theFile, myResponseValues, Headers);
			} catch (IOException | URISyntaxException e) {
			}
			System.out.println(myResponseValues.toString());
			System.out.println("Begin Stream");

		});
		System.out.println("Final Ending");
	}

	public URL getMyUrl() {
		return myURL;
	}

	public URL setMyUrl(String string) throws MalformedURLException {
		myURL = new URL(string);
		return myURL;
	}
}