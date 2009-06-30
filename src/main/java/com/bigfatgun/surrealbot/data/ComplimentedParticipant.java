package com.bigfatgun.surrealbot.data;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ComplimentedParticipant {

	@PrimaryKey
   @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Date firstCompliment;

	@Persistent
	private String participant;

	@Persistent
	private int compliments;

	public ComplimentedParticipant(final String p) {
		firstCompliment = new Date();
		compliments = 0;
		participant = p;
	}

	public Long getId() {
		return id;
	}

	public Date getFirstCompliment() {
		return firstCompliment;
	}

	public String getParticipant() {
		return participant;
	}

	public int getCompliments() {
		return compliments;
	}

	public ComplimentedParticipant incrementCompliments() {
		compliments++;
		return this;
	}
}
