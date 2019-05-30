package com.atul.actor.clients;

import org.junit.Test;

import com.atul.actor.base.ActorSystem;

import junit.framework.TestCase;

/**
 * Test cases for print Actor
 * @author SAtul
 *
 */
public class TestEmailActor extends TestCase {
	
	@Test
	public void testSendMailActorSendEvents() throws InterruptedException {
		SendMailActor emailActor = new SendMailActor("atulsm@gmail.com");
		if(!ActorSystem.INSTANCE.isRegistered(emailActor.getName())) {
			ActorSystem.INSTANCE.registerActor(emailActor);
		}
		
		boolean status = ActorSystem.INSTANCE.sendMessage(emailActor.getName(), "Email Body");
		assertEquals(true, status);
	}
	
	
	@Test
	public void testSendMailActorSendEvetsSSequence() throws InterruptedException {
		SendMailActor emailActor = new SendMailActor("atulsm@gmail.com");
		if(!ActorSystem.INSTANCE.isRegistered(emailActor.getName())) {
			ActorSystem.INSTANCE.registerActor(emailActor);
		}
		
		for(int i=0;i<5;i++) {
			boolean status = ActorSystem.INSTANCE.sendMessage(emailActor.getName(), "EmailBody " + i);
			assertEquals(true, status);
		}	
	}

	
}
