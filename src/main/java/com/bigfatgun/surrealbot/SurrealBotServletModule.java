package com.bigfatgun.surrealbot;

import java.util.logging.Logger;

import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.ServletModule;

public class SurrealBotServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		serve("/_wave/robot/jsonrpc").with(SurrealJsonRpc.class);
		serve("/test").with(SurrealTestServlet.class);
		serve("/_wave/robot/profile").with(SurrealProfileServlet.class);

		final Logger logger = Logger.getLogger("com.bigfatgun.surrealbot");
		bindInterceptor(Matchers.subclassesOf(SurrealJsonRpc.class), Matchers.any(), new MethodPerfInterceptor(logger));
	}
}
