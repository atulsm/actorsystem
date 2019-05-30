package com.atul.actor.clients;

import org.junit.Test;

import com.atul.actor.base.ActorSystem;

import junit.framework.TestCase;

/**
 * Test cases for print Actor
 * @author SAtul
 *
 */
public class TestMultipleActors extends TestCase {
	
	@Test
	public void testPrintActorSendEvents() throws InterruptedException {
		PrintActor printActor = new PrintActor();
		if(!ActorSystem.getInstance().isRegistered(printActor.getName())) {
			ActorSystem.getInstance().registerActor(printActor);
		}
		
		boolean status = ActorSystem.getInstance().sendMessage(printActor.getName(), "Testing message printing");
		assertEquals(true, status);
		
		SendMailActor emailActor = new SendMailActor("atulsm@gmail.com");
		if(!ActorSystem.getInstance().isRegistered(emailActor.getName())) {
			ActorSystem.getInstance().registerActor(emailActor);
		}
		
		status = ActorSystem.getInstance().sendMessage(emailActor.getName(), "Email Body");
		assertEquals(true, status);		
	}
	
}
