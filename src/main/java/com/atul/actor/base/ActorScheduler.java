package com.atul.actor.base;

public interface ActorScheduler {
	
	public void init();
	public void shutdown();
	public int execute();
}
