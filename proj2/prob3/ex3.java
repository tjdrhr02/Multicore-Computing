
import java.util.concurrent.atomic.AtomicInteger;

public class ex3 {
	 
    private static AtomicInteger counter = new AtomicInteger(0);
 
    static class MyRunnable implements Runnable {
 
        private int now;
        private int prev;
        private int index;
 
        public MyRunnable(int i) {
			this.index = i;
		}

		public void run() {
            now = counter.get();
            System.out.println("Thread " + index + " reads " + now);
            prev = counter.getAndAdd(10);
            System.out.println("Thread " + index + " reads " + prev + " <- getAndAdd(10)"); 
            now = counter.addAndGet(100);
            System.out.println("Thread " + index + " reads " + now + " <- addAndGet(100)");
            counter.set(1000);     
            System.out.println("Thread " + index + " reads " + counter + " <- set(1000)");
          
 
        }
    }
 
    public static void main(String[] args) {
        for (int i = 1; i <= 2; i++) {
        	Thread t = new Thread(new MyRunnable(i));
        	t.start();
	    }
    }
}