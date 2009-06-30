package com.bigfatgun.surrealbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class SurrealBotGuiceServletContextListener extends GuiceServletContextListener {

	protected Injector getInjector() {
		return Guice.createInjector(new SurrealBotServletModule(), new SurrealServerModule());
	}
}
