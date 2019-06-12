package io.opensphere.core.util.CrashReporter;

import java.io.ByteArrayInputStream;
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
			} catch (IOException | URISyntaxException e) {
			}
			System.out.println("Finishing postman");
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
		Map<String, String> Headers = Map.ofEntries(

				Map.entry("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW"),
				Map.entry("Content-Type", "multipart/form-data"), Map.entry("X-Atlassian-Token", "no-check"),
				Map.entry("Authorization", "Basic QWRtaW4xOkJvdWxkZXIyMCE="),
				Map.entry("User-Agent", "PostmanRuntime/7.13.0"), Map.entry("Accept", "*/*"),
				Map.entry("Cache-Control", "no-cache"),
				Map.entry("Postman-Token", "9427aba6-6abe-44d4-909e-42d42b6d9de7,c0fe93c6-209f-4cf9-9ea2-21d7a63e4894"),
				Map.entry("Host", "localhost:8080"),
				Map.entry("cookie",
						"atlassian.xsrf.token=BLMR-FBOU-G5CJ-Z0PN_12ee1c3f733bd6c3a572881e02e06261471dd763_lin; JSESSIONID=2E923C2310363485CD968BBAA19E95C1"),
				Map.entry("accept-encoding", "gzip, deflate"), Map.entry("content-length", "39792"),
				Map.entry("Connection", "keep-alive"), Map.entry("cache-control", "no-cache"));

		ThreadUtilities.runBackground(() -> {
			try {
				this.setMyUrl("http://localhost:8080/rest/api/2/issue/BUGS-8/attachments");

				String test = new String("{\n\"file\" : \"/home/crombiek/Desktop/call.png\"\n}");

				// FileBody fileBody = new FileBody(fileToUpload);
				// HttpEntity entity = MultipartEntityBuilder.create().addPart("file",
				// fileBody).build();

				try {
					InputStream postData = new ByteArrayInputStream(test.getBytes());
					InputStream theStream2 = myToolbox.getServerProviderRegistry().getProvider(HttpServer.class)
							.getServer(myURL).sendPost(myURL, postData, Headers, myResponseValues, null);

					System.out.println(myResponseValues.toString());
					System.out.println(
							new StreamReader(theStream2).readStreamIntoString(StringUtilities.DEFAULT_CHARSET));

				} catch (UnsupportedOperationException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
			}
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