package com.atul.actor;

import com.atul.actor.clients.TestEmailActor;
import com.atul.actor.clients.TestInterActorProcessing;
import com.atul.actor.clients.TestMultipleActors;
import com.atul.actor.clients.TestPrintActor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for actors");
		suite.addTest(new TestSuite(TestPrintActor.class));
		suite.addTest(new TestSuite(TestEmailActor.class));
		suite.addTest(new TestSuite(TestMultipleActors.class));
		suite.addTest(new TestSuite(TestInterActorProcessing.class));		
		return suite;
	}
}
