package com.bigfatgun.surrealbot;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SurrealTestServlet extends HttpServlet {

	private final ComplimentServer server;

	private final ComplimentFormatter formatter;

	@Inject
	public SurrealTestServlet(final ComplimentServer cServer, final ComplimentFormatter cFormatter) {
		super();
		this.server = cServer;
		this.formatter = cFormatter;
	}

	@Override
	protected void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
		httpServletResponse.getWriter().println(formatter.format(httpServletRequest.getRemoteHost(), server.fetchCompliment()));
	}
}
