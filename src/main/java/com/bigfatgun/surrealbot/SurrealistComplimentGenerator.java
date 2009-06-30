package com.bigfatgun.surrealbot;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class SurrealistComplimentGenerator implements ComplimentServer {

	private final URL url;

	private final UrlFetcher fetcher;

	private final Pattern p;

	@Inject
	public SurrealistComplimentGenerator(final UrlFetcher urlFetcher, final URL theUrl, @Named("regex") final String regex) {
		this.fetcher = urlFetcher;
		this.url = theUrl;
		this.p = Pattern.compile(regex);
	}

	public String fetchCompliment() {
		final String contents = fetcher.fetch(url);
		if (contents == null) {
			return null;
		}
		final Matcher matcher = p.matcher(contents);
		if (!matcher.matches() || matcher.groupCount() != 1) {
			return null;
		}
		return matcher.group(1);
	}
}
