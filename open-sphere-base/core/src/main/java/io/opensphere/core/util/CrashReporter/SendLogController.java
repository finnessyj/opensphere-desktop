package io.opensphere.core.util.CrashReporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import io.opensphere.core.Toolbox;
import io.opensphere.core.server.ContentType;
import io.opensphere.core.server.HttpServer;
import io.opensphere.core.server.ResponseValues;
import io.opensphere.core.util.io.StreamReader;
import io.opensphere.core.util.lang.StringUtilities;
import io.opensphere.core.util.lang.ThreadUtilities;

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
				// System.out.println(new
				// StreamReader(theStream).readStreamIntoString(StringUtilities.DEFAULT_CHARSET));
				System.out.println(myResponseValues.toString());
				System.out.println("Connected to Server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void Postman() {
		System.out.println("connecting to postman");
		ThreadUtilities.runBackground(() -> {
			try {
				this.setMyUrl("https://postman-echo.com/post");
				String string = new String("{\n\"foo1\" : \"bar1\"\n}");
				InputStream postData = new ByteArrayInputStream(string.getBytes());
				InputStream theStream2 = myToolbox.getServerProviderRegistry().getProvider(HttpServer.class)
						.getServer(myURL).sendPost(myURL, postData, myResponseValues, ContentType.JSON);

				System.out.println(new StreamReader(theStream2).readStreamIntoString(StringUtilities.DEFAULT_CHARSET));
				// System.out.println(myResponseValues.getHeader());
			} catch (IOException | URISyntaxException e) {
			}
			System.out.println("Finishing postman");
			// System.out.println(myResponseValues.toString());

		});
	}

	public void postB() {
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

	public URL getMyUrl() {
		return myURL;
	}

	public URL setMyUrl(String string) throws MalformedURLException {
		myURL = new URL(string);
		return myURL;
	}

}