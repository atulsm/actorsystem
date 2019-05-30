package com.atul.actor.base;

/**
 * The ator interface which clients have to consume
 * @author SAtul
 *
 */
public interface Actor {
	/**
	 * Name of actor
	 * @return
	 */
	public String getName();
	
	/**
	 * Executes one message. The message will be pushed from the actor system and could be executed in a caller
	 * @param message
	 * @return
	 */
	public String execute(String message);
	
}
