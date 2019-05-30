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
		if(!ActorSystem.INSTANCE.isRegistered(printActor.getName())) {
			ActorSystem.INSTANCE.registerActor(printActor);
		}
		
		boolean status = ActorSystem.INSTANCE.sendMessage(printActor.getName(), "Testing message printing");
		assertEquals(true, status);
		
		SendMailActor emailActor = new SendMailActor("atulsm@gmail.com");
		if(!ActorSystem.INSTANCE.isRegistered(emailActor.getName())) {
			ActorSystem.INSTANCE.registerActor(emailActor);
		}
		
		status = ActorSystem.INSTANCE.sendMessage(emailActor.getName(), "Email Body");
		assertEquals(true, status);		
	}
	
}
