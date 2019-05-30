package com.atul.actor.clients;

import com.atul.actor.base.Actor;

public class PrintActor implements Actor {
	
	public static final String NAME = "print";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String execute(String message) {
		System.out.println(message);
		return null;
	}

}
