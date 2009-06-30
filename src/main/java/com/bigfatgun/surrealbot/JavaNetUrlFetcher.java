package com.bigfatgun.surrealbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class JavaNetUrlFetcher implements UrlFetcher {
	public String fetch(final URL url) {
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			final StringBuilder contents = new StringBuilder();

			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				contents.append(line);
			}
			reader.close();
			return contents.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
