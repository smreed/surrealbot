package com.bigfatgun.surrealbot;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.wave.api.AbstractRobotServlet;
import com.google.wave.api.Blip;
import com.google.wave.api.Event;
import com.google.wave.api.RobotMessageBundle;
import com.google.wave.api.Wavelet;

@Singleton
public class SurrealJsonRpc extends AbstractRobotServlet {

	private final ComplimentServer server;

	private final ComplimentFormatter formatter;

	private final ComplimentTracker tracker;

	private Function<String,String> complimenter;

	@Inject
	public SurrealJsonRpc(final ComplimentServer cServer, final ComplimentFormatter cFormatter, final ComplimentTracker cTracker) {
		super();
		this.server = cServer;
		this.formatter = cFormatter;
		this.tracker = cTracker;
		this.complimenter = new Function<String, String>() {
			@Override
			public String apply(@Nullable final String participant) {
				if (getRobotAddress().equals(participant)) {
					return null;
				} else {
					String compliment = null;
					for (int i = 0; compliment == null && i < 3; i++) {
						compliment = server.fetchCompliment();
					}
					return formatter.format(participant, compliment);
				}
			}
		};
	}

	@Override
	protected String getRobotAddress() {
		return "surrealbot@appspot.com";
	}

	@Override
	public void processEvents(final RobotMessageBundle bundle) {
		final Wavelet wavelet = bundle.getWavelet();

		if (bundle.wasSelfAdded()) {
			complimentParticipants(wavelet, ImmutableSet.copyOf(wavelet.getParticipants()));
		} else {
			for (final Event event : bundle.getParticipantsChangedEvents()) {
				complimentParticipants(wavelet, ImmutableSet.copyOf(event.getAddedParticipants()));
			}
		}
	}

	private void complimentParticipants(final Wavelet wavelet, final ImmutableSet<String> participants) {
		for (final String compliment : Iterables.transform(participants, complimenter)) {
			if (compliment != null) {
				final Blip blip = wavelet.appendBlip();
				blip.getDocument().append(compliment);
			}
		}
		tracker.increment(participants);
	}
}
