package com.bigfatgun.surrealbot;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SimpleComplimentFormatterTest {

	private SimpleComplimentFormatter obj;

	@Before
	public void setup() {
		obj = new SimpleComplimentFormatter();
	}

	@Test
	public void convertBrTagsToNewlines() {
		assertEquals(String.format("Hello p!%n%none\ntwo\nthree\nfour\nfive"), obj.format("p", "one<bR>two<BR>three<br>four<Br>five"));
	}

	@Test
	public void stripOtherTags() {
		assertEquals(String.format("Hello !%n%n"), obj.format("", "<html><script></script>"));
	}
}
