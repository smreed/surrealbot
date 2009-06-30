package com.bigfatgun.surrealbot;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.bigfatgun.surrealbot.data.PersistentComplimentTracker;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class SurrealServerModule extends AbstractModule {

	protected void configure() {
		bind(ComplimentFormatter.class).to(SimpleComplimentFormatter.class);
		bind(ComplimentServer.class).to(SurrealistComplimentGenerator.class);
		bind(ComplimentTracker.class).to(PersistentComplimentTracker.class);
		bind(UrlFetcher.class).to(JavaNetUrlFetcher.class);
		bindConstant().annotatedWith(Names.named("url")).to("http://www.madsci.org/cgi-bin/cgiwrap/~lynn/jardin/SCG");
		bindConstant().annotatedWith(Names.named("regex")).to(".*<h2>(.+)</h2>.*");
		final Logger logger = Logger.getLogger("com.bigfatgun.surrealbot");
		bindInterceptor(Matchers.subclassesOf(ComplimentServer.class), Matchers.any(), new MethodPerfInterceptor(logger));
		bind(PersistenceManager.class).toProvider(new Provider<PersistenceManager>() {
			private final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");

			@Override
			public PersistenceManager get() {
				return pmf.getPersistenceManager();
			}
		});
	}

	@Provides
	public final URL serverUrl(@Named("url") final String url) throws MalformedURLException {
		return new URL(url);
	}
}
