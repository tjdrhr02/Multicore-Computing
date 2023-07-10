import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Car extends Thread {
  private BlockingQueue queue;
	
  public Car(String name, BlockingQueue q) {
	  super(name);
	  this.queue = q;
	  start();
  }
  

  private void tryingEnter()
  {
      System.out.println(getName()+": trying to enter");
  }


  private void justEntered()
  {
      System.out.println(getName()+": just entered");

  }

  private void aboutToLeave()
  {
      System.out.println(getName()+":                                     about to leave");
  }

  private void Left()
  {
      System.out.println(getName()+":                                     have been left");
  }

  public void run() {
    while (true) {
      try {
        sleep((int)(Math.random() * 10000)); // drive before parking
      } catch (InterruptedException e) {}
      
      try {
    	  tryingEnter();
    	  queue.put(getName());
	      justEntered();
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      try {
        sleep((int)(Math.random() * 20000)); // stay within the parking garage
      } catch (InterruptedException e) {}
      
      try {
    	  aboutToLeave();
    	  queue.take();
	      Left();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    }
  }
}


public class ParkingBlockingQueue {
  public static void main(String[] args){
    BlockingQueue queue = new ArrayBlockingQueue<String>(7);
    
    
    for (int i=1; i<= 10; i++) {
      Car c = new Car("Car "+i, queue);
    }
  }
}