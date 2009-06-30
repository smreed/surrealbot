package com.bigfatgun.surrealbot.data;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.bigfatgun.surrealbot.ComplimentTracker;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class PersistentComplimentTracker implements ComplimentTracker {

	private final Provider<PersistenceManager> pmf;

	@Inject
	public PersistentComplimentTracker(final Provider<PersistenceManager> persistence) {
		pmf = persistence;
	}

	@Override
	public void increment(final String participant) {
		increment(ImmutableSet.of(participant));
	}

	@Override
	public void increment(final Iterable<? extends CharSequence> participants) {
		final PersistenceManager pm = pmf.get();
		final Query query = pm.newQuery(ComplimentedParticipant.class);
		query.setFilter("participant == partParam");
		query.declareParameters("String partParam");

		try {
			for (final CharSequence participant : participants) {
				findOrCreate(pm, query, participant).incrementCompliments();
			}
		} finally {
			query.closeAll();
			pm.close();
		}
	}

	private ComplimentedParticipant findOrCreate(final PersistenceManager pm, final Query query, final CharSequence participant) {
		@SuppressWarnings({"unchecked"})
		final ImmutableList<ComplimentedParticipant> list = ImmutableList.copyOf(
				  (Iterable<? extends ComplimentedParticipant>) query.execute(participant)
				  );

		System.out.println("Query returned " + list);

		if (list.isEmpty()) {
			final ComplimentedParticipant complimentedParticipant = new ComplimentedParticipant(participant.toString());
			pm.makePersistent(complimentedParticipant);
			return complimentedParticipant;
		} else {
			return list.get(0);
		}
	}
}
