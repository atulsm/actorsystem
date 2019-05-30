package com.atul.actor.clients;

import com.atul.actor.base.Actor;

public final class SendMailActor implements Actor {

	private final String email;
	
	public SendMailActor(String email) {
		this.email = email;
	}
	
	@Override
	public String getName() {
		return "sendMail";
	}

	@Override
	public String execute(String message) {
		System.out.println("Sending email to " + email);
		return null;
	}

}
