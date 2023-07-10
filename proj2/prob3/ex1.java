
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Ex1_Thread extends Thread {
	  private BlockingQueue queue;
	  public Ex1_Thread(String name, BlockingQueue q) {
		  super(name);
		  this.queue = q;
		  start();
	  }

	  public void run() {
	      try {
	    	  System.out.println(getName()+": I'm trying to enter");
	    	  queue.put(getName());
	    	  System.out.println(getName()+": I just entered");
	      } catch (InterruptedException e) {}
	    
	      try {
	    	  sleep((int)(Math.random() * 5000));
	      } catch (InterruptedException e) {}
	      
	      try {
	    	  System.out.println(getName()+": I'm waiting to leave");
	    	  queue.take();
	    	  System.out.println(getName()+": Bye-bye!");
	      } catch (InterruptedException e) {}
	
	  }
}

public class ex1 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlockingQueue queue = new ArrayBlockingQueue<String>(3);
	    for (int i = 1; i <= 5; i++) {
	    	Ex1_Thread t = new Ex1_Thread("Thread "+i, queue);
	    }
	}
}