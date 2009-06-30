package com.bigfatgun.surrealbot;

import com.google.inject.Singleton;
import com.google.wave.api.ProfileServlet;

@Singleton
public final class SurrealProfileServlet extends ProfileServlet {

	@Override
	public String getRobotName() {
		return "Surrealist Compliment Robot";
	}

	@Override
	public String getRobotAvatarUrl() {
		return "http://surrealbot.appspot.com/surrealbot.png";
	}

	@Override
	public String getRobotProfilePageUrl() {
		return "http://surrealbot.appspot.com/";
	}
}
