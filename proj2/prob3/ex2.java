
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Ex2_Thread extends Thread {
	ReadWriteLock readWriteLock;
	
	  public Ex2_Thread(String name, ReadWriteLock l) {
		  super(name);
		  this.readWriteLock = l;
		  start();
	  }
	  
	  public void run() {
	      try {
	    	  readWriteLock.readLock().lock();
	    	  System.out.println(getName()+" is reading");
	    	  sleep((int)(Math.random() * 3000));
	    	  readWriteLock.readLock().unlock();
	    	  
	    	  readWriteLock.writeLock().lock();
	    	  System.out.println(getName()+" is writing");
	    	  sleep((int)(Math.random() * 3000));
	    	  System.out.println(getName()+" just finished writing!");
	    	  readWriteLock.writeLock().unlock();
	      } catch (InterruptedException e) {
	    	  e.printStackTrace();
	      }
	  }
}

public class ex2 {
	public static void main(String[] args) {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
				
		for(int i = 1; i <= 5; i++) {
			Ex2_Thread t = new Ex2_Thread("Thread "+i , readWriteLock);
		}
 	}
}