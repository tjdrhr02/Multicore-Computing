import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Ex4_Thread extends Thread {
	  CyclicBarrier Barrier;
	  public Ex4_Thread(String name, CyclicBarrier b) {
		  super(name);
		  this.Barrier = b;
		  start();
	  }
	  
	  public void run() {
	      try {
	    	  sleep((int)(Math.random() * 5000));
	    	  System.out.println(getName()+" is waiting");
	    	  Barrier.await();
	      } catch (InterruptedException | BrokenBarrierException e) {} finally {
	    	  System.out.println(getName()+" just finished!" );
	      }
	  }
}

public class ex4 {
	public static void main(String[] args) {
		CyclicBarrier newBarrier = new CyclicBarrier(5);
		for (int i=1; i<= 5; i++) {
	    	Ex4_Thread t = new Ex4_Thread("Thread "+ i, newBarrier);
	    }
	}
}