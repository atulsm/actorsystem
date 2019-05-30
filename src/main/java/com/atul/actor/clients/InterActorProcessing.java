package com.atul.actor.clients;

import com.atul.actor.base.Actor;
import com.atul.actor.base.ActorSystem;

/**
 * One actors creating another actor.
 * After sending email, calls for print actor
 * @author SAtul
 *
 */
public final class InterActorProcessing implements Actor {

	private final String email;
	
	public InterActorProcessing(String email) {
		this.email = email;
		ActorSystem.getInstance().registerActor(new PrintActor());
	}
	
	@Override
	public String getName() {
		return "sendMail";
	}

	@Override
	public String execute(String message) {
		System.out.println("Sending email to " + email);
		ActorSystem.getInstance().sendMessage(PrintActor.NAME, "Email sending successful");
		return null;
	}

}
