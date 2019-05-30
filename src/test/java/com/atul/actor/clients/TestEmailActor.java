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
		if(!ActorSystem.getInstance().isRegistered(emailActor.getName())) {
			ActorSystem.getInstance().registerActor(emailActor);
		}
		
		boolean status = ActorSystem.getInstance().sendMessage(emailActor.getName(), "Email Body");
		assertEquals(true, status);
	}
	
	
	@Test
	public void testSendMailActorSendEvetsSSequence() throws InterruptedException {
		SendMailActor emailActor = new SendMailActor("atulsm@gmail.com");
		if(!ActorSystem.getInstance().isRegistered(emailActor.getName())) {
			ActorSystem.getInstance().registerActor(emailActor);
		}
		
		for(int i=0;i<5;i++) {
			boolean status = ActorSystem.getInstance().sendMessage(emailActor.getName(), "EmailBody " + i);
			assertEquals(true, status);
		}	
	}

	
}
