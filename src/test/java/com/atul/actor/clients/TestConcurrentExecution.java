package com.atul.actor.clients;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.atul.actor.base.ActorSystem;

import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * Test cases for print Actor
 * @author SAtul
 *
 */
public class TestConcurrentExecution extends TestCase {
		
	@Test
	public void testPrintActorSendEvetsSequence() throws InterruptedException {
		PrintActor printActor = new PrintActor();
		if(!ActorSystem.getInstance().isRegistered(printActor.getName())) {
			ActorSystem.getInstance().registerActor(printActor);
		}
		
		AtomicInteger counter = new AtomicInteger(0);
		Worker[] workers = new Worker[5];
		ExecutorService exec = Executors.newFixedThreadPool(10);

		for(int i=0;i<5;i++) {
			workers[i] = new Worker(printActor, counter, 100);
			exec.execute(workers[i]);
		}
		
		exec.awaitTermination(5, TimeUnit.SECONDS);
	}

	public static final class Worker implements Runnable {
		final PrintActor printActor;
		final AtomicInteger counter;
		final int limit;
		
		public  Worker(PrintActor printActor, AtomicInteger counter, int limit) {
			this.printActor = printActor;
			this.counter = counter;
			this.limit = limit;
		}
		
		@Override
		public void run() {
			for(int i=0;i<limit;i++) {
				ActorSystem.getInstance().sendMessage(printActor.getName(), "Testing message printing " + counter.incrementAndGet());
			}
		}
	}


	
}
