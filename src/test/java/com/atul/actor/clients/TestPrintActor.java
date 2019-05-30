package com.atul.actor.clients;

import org.junit.Test;

import com.atul.actor.base.ActorSystem;

import junit.framework.TestCase;

/**
 * Test cases for print Actor
 * @author SAtul
 *
 */
public class TestPrintActor extends TestCase {
	
	@Test
	public void testPrintActorSendEvents() throws InterruptedException {
		PrintActor printActor = new PrintActor();
		if(!ActorSystem.getInstance().isRegistered(printActor.getName())) {
			ActorSystem.getInstance().registerActor(printActor);
		}
		
		boolean status = ActorSystem.getInstance().sendMessage(printActor.getName(), "Testing message printing");
		assertEquals(true, status);		
	}
	
	
	@Test
	public void testPrintActorSendEvetsSequence() throws InterruptedException {
		PrintActor printActor = new PrintActor();
		if(!ActorSystem.getInstance().isRegistered(printActor.getName())) {
			ActorSystem.getInstance().registerActor(printActor);
		}
		
		for(int i=0;i<5;i++) {
			boolean status = ActorSystem.getInstance().sendMessage(printActor.getName(), "Testing message printing " + i);
			assertEquals(true, status);
		}
		
	}

	
}
