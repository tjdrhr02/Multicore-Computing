import java.util.concurrent.*;

class CounterLock {
	private Semaphore places = new Semaphore(7);
	
	public void inc() {
		try {
			places.acquire();
		}catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	
	public void dec() {
		places.release();
	}
	
}

class Car extends Thread {
  private CounterLock myCounter;
	
  public Car(String name, CounterLock c) {
	  super(name);
	  this.myCounter = c;
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
      
      tryingEnter();
	  myCounter.inc();
      justEntered();
	
      try {
        sleep((int)(Math.random() * 20000)); // stay within the parking garage
      } catch (InterruptedException e) {}
      
	  aboutToLeave();
	  myCounter.dec();
      Left();
    }
  }
}





public class ParkingSemaphore {
  public static void main(String[] args){
    CounterLock c_lock = new CounterLock();
    
    for (int i=1; i<= 10; i++) {
      Car c = new Car("Car "+i, c_lock);
    }
  }
}