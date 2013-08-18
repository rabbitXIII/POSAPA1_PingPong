import java.util.concurrent.CountDownLatch;

public class PingPongManager {

	private static final int MAX_HITS_IN_GAME = 10;

	private Thread[] threads = new Thread[2];

	private static CountDownLatch totalHits = new CountDownLatch(MAX_HITS_IN_GAME);

	public static void main(String[] args) {

		PingPongManager manager = new PingPongManager();
		
		manager.startGame();
		manager.waitAndFinishGame();

	}

	public PingPongManager() {
		threads[0] = new Thread(new PingPongPlayer("Ping!"));
		threads[1] = new Thread(new PingPongPlayer("Pong!"));
	}

	public void startGame() {
		System.out.println("Ready… Set… Go!\n");
		for (Thread thread : threads)
			thread.start();

	}

	public void waitAndFinishGame(){
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

	public class PingPongPlayer implements Runnable {

		private String sound;

		public PingPongPlayer(String sound) {
			this.sound = sound;
		}

		private boolean moreHitsInGame() {
			return totalHits.getCount() > 0;
		}
		
		@Override
		public void run() {
			while(moreHitsInGame()){
				hitAndPrint();
			}
		}

		private synchronized void hitAndPrint() {
			totalHits.countDown();
			System.out.println(sound);
		}
	}

}
