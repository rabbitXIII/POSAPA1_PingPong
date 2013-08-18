import java.util.concurrent.CountDownLatch;



public class PingPongManager {
	
	private final int MAX_HITS_IN_GAME = 10;
	
	private Thread[] threads = new Thread[2];
	
    private CountDownLatch startSignal = new CountDownLatch(1);
    
    private CountDownLatch totalHits = new CountDownLatch(MAX_HITS_IN_GAME);
	
	
	public static void main(String[] args) {

		PingPongManager game = new PingPongManager();

	}
	
	public PingPongManager(){
		threads[0] = new Thread(new PingPongPlayer("Ping!"));
		threads[1] = new Thread(new PingPongPlayer("Pong!"));	
	}
	
	public void startGame(){
		System.out.println("Ready… Set… Go!\n");
		for (Thread thread: threads)
			thread.start();
		
	}
	
	public void waitForGame() throws InterruptedException{
		for (Thread thread : threads) {
			thread.join();

		}
		
		
		System.out.println("\nDone!");
	}
	
	public class PingPongPlayer implements Runnable {
		
		private String sound;
		private CountDownLatch startSignal;
		
		public PingPongPlayer(String sound){
			this.sound = sound;
		}
		
		@Override
		public void run(){
			hitAndPrint();
		}

		private synchronized void hitAndPrint() {
			System.out.println(sound);
		}
	}

}

