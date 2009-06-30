package com.bigfatgun.surrealbot;

public class SimpleComplimentFormatter implements ComplimentFormatter {
	public String format(final String participant, final String rawCompliment) {
		final String brLess = rawCompliment.replaceAll("<[bB][rR]>", "\n");
		final String tagLess = brLess.replaceAll("<\\/?[\\w\\s]+>", "");
		return String.format("Hello %s!%n%n%s", participant, tagLess);
	}
}
