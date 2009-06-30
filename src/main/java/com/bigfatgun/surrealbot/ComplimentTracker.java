package com.bigfatgun.surrealbot;

public interface ComplimentTracker {

	void increment(String participant);

	void increment(Iterable<? extends CharSequence> participants);
}
