import java.util.concurrent.CountDownLatch;

public class PingPongManager {

	private static final int MAX_HITS_IN_GAME = 10;

	private Thread[] threads = new Thread[2];

	private static CountDownLatch totalHitsLeft = new CountDownLatch(MAX_HITS_IN_GAME);

	public static void main(String[] args) {

		PingPongManager manager = new PingPongManager();
		
		manager.startGame();
		manager.waitAndFinishGame();

	}

	private PingPongManager() {
		threads[0] = new Thread(new PingPongPlayer("Ping!"));
		threads[1] = new Thread(new PingPongPlayer("Pong!"));
	}

	private void startGame() {
		System.out.println("Ready… Set… Go!\n");
		for (Thread thread : threads)
			thread.start();

	}

	private void waitAndFinishGame(){
		try {
			for (Thread thread : threads) {
				thread.join();

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("\nDone!");
	}

	private class PingPongPlayer implements Runnable {

		private String hitSound;

		public PingPongPlayer(String sound) {
			this.hitSound = sound;
		}

		private boolean moreHitsInGame() {
			return totalHitsLeft.getCount() > 0;
		}
		
		@Override
		public void run() {
			while( moreHitsInGame() ){
				
				hitAndPrint();
			}
		}

		private synchronized void hitAndPrint() {
			totalHitsLeft.countDown();
			System.out.println(hitSound);
		}
		
		public String getHitSound() {
			return hitSound;
		}
		
		@Override
		public String toString(){
			return "PingPongPlayer - Hit sound: " + hitSound;
			
		}
	}

}
