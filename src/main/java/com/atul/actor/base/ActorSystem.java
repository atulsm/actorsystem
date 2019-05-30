package com.atul.actor.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The base system for the actor framework
 * @author SAtul
 *
 */
public final class ActorSystem {	
	private static final int INDIVIDUAL_QUEUE_CAPACITY = 10;
	
	private static Map<String, ActorContext> actorQueue = new HashMap<String, ActorContext>();

	private AtomicBoolean isShuttingDown = new AtomicBoolean(false);
	private final ActorScheduler scheduler;
	
	public static ActorSystem instance;
    public static synchronized ActorSystem getInstance() {
    	return getInstance(new RoundRobinExecutor());
    }
    
    public static synchronized ActorSystem getInstance(ActorScheduler scheduler) {
        if (instance == null) {
            instance = new ActorSystem(scheduler);
        }
        return instance;
    }
    
	private ActorSystem(ActorScheduler scheduler) {
		this.scheduler = scheduler;
		scheduler.init();
	}
	
	/**
	 * Register a new Actor
	 * @param actor
	 * @return true if success/false if already registered
	 */
	public boolean registerActor(Actor actor) {
		if(actor==null) {
			return false;
		}
		
		if(!actorQueue.containsKey(actor.getName())) {
			System.out.println("Registering actor for first time :" + actor.getName());
			BlockingQueue<String> queue = new ArrayBlockingQueue<String>(INDIVIDUAL_QUEUE_CAPACITY);
			actorQueue.put(actor.getName(), new ActorContext(actor, queue));
			return true;
		}
		
		System.out.println("Actor already registered : " + actor.getName());
		return false;
	}
	
	public boolean isRegistered(String name) {
		return actorQueue.containsKey(name);
	}
	
	/**
	 * Shuting down. After shutdown nothing can be send
	 * @return
	 */
	public boolean shutdown() {
		System.out.println("Received message to shutdown");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		isShuttingDown.set(true);
		scheduler.shutdown();
		
		System.out.println("Shutting down");
		return true;
	}
	
	/**
	 * Lookup registered Actor.
	 * Send to the respective actor Queue
	 * return success if Queue is free
	 * 
	 * @param actorName
	 * @param payload
	 * @return
	 */
	public boolean sendMessage(String actorName, String payload) {
		if(isShuttingDown.get()) {
			System.out.println("System has already shutdown. Cannot accept new events");
			return false;
		}
		
		if(!actorQueue.containsKey(actorName)) {
			System.out.println("This actor is not registered yet : " + actorName);
			return false;
		}
		
		BlockingQueue<String> queue = actorQueue.get(actorName).getQueue();
		boolean status = queue.offer(payload);
		
		if(status==false) {
			System.out.println("Actor queue is too busy to accept new payload :" + actorName);
			return false;
		}else {
			return true;
		}
		
	}
	
	/**
	 * Return an existing registered Actor
	 * @param name
	 * @return
	 */
	public Actor getActor(String name) {
		
		return null;
	}
	
	private static final class ActorContext {
		private final Actor actor;
		private final BlockingQueue<String> queue;
		
		public ActorContext(Actor actor, BlockingQueue<String> queue) {
			this.actor = actor;
			this.queue = queue;
		}

		public Actor getActor() {
			return actor;
		}

		public BlockingQueue<String> getQueue() {
			return queue;
		}
		
	}
	
	/**
	 * Takes actors from the queue and execute one by one
	 * @author SAtul
	 *
	 */
	private static final class RoundRobinExecutor implements ActorScheduler {
		private AtomicInteger execCounts = new AtomicInteger(0);
		private ExecutorService rrExecutor = Executors.newFixedThreadPool(10);
		private Thread scheduleRunner;
		
		public void init() {
			scheduleRunner = new Thread() {
				@Override
				public void run() {
					while(true) {
						int exec = execute();
						if(exec == 0) {
							try {
								//Nothing was executed in the last step. Wait sometime
								Thread.sleep(100);
							} catch (InterruptedException e) {
								//Nothing to do since its shuting down
							}
						}
					}
				}
			};
			scheduleRunner.start();
		}
		
		public void shutdown() {
			scheduleRunner.interrupt();
			rrExecutor.shutdown();
		}
		
		public int execute() {
			execCounts.set(0);
			
			for(Entry<String, ActorContext> entry : actorQueue.entrySet()) {
				String name = entry.getKey();
				ActorContext context = entry.getValue();
				
				//read one message
				String message = context.getQueue().poll();
				if(message==null) {
					continue;
				}else {
					System.out.print("Processing message for actor (" + name + "): ");
					rrExecutor.execute(() -> context.getActor().execute(message));
					execCounts.incrementAndGet();
				}
			}
			return execCounts.get();
		}
	}
}
