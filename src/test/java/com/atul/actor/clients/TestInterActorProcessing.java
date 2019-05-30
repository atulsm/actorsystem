package com.atul.actor.clients;

import org.junit.Test;

import com.atul.actor.base.ActorSystem;

import junit.framework.TestCase;

/**
 * Test cases for print Actor
 * @author SAtul
 *
 */
public class TestInterActorProcessing extends TestCase {
	
	@Test
	public void testSendMailActorSendEvents() throws InterruptedException {
		InterActorProcessing interActor = new InterActorProcessing("atulsm@gmail.com");
		if(!ActorSystem.INSTANCE.isRegistered(interActor.getName())) {
			ActorSystem.INSTANCE.registerActor(interActor);
		}
		
		boolean status = ActorSystem.INSTANCE.sendMessage(interActor.getName(), "Email Body");
		assertEquals(true, status);		
	}
	
}
