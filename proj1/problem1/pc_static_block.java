
public class pc_static_block {
	private static int NUM_END = 200000;
	private static int NUM_THREADS = 32;
	
	public static void main(String[] args) {
		if (args.length == 2) {
			NUM_THREADS = Integer.parseInt(args[0]);
			NUM_END = Integer.parseInt(args[1]);
		}
		
		int counter;
		
		long startTime = System.currentTimeMillis();
		
		counter = cnt();
		
		long endTime = System.currentTimeMillis();
		long timeDiff = endTime - startTime;
		System.out.println("Program Execution Time: " + timeDiff + "ms");
		System.out.println("1..." + (NUM_END-1) + " prime# counter=" + counter);
	}
	
	private static int cnt() {
		int counter = 0;
		
		PC_thread_block[] ts = new PC_thread_block[NUM_THREADS];
		for (int i = 0; i < NUM_THREADS; i++) {
			ts[i] = new PC_thread_block(i, NUM_END, NUM_THREADS);
			ts[i].start();
		}
		for (int i = 0; i < NUM_THREADS; i++) {
			try {
				ts[i].join();
				counter = counter + ts[i].getCounter();
			} catch (InterruptedException e) {}
		}
		
		return counter;
	}
	
}

class PC_thread_block extends Thread {
	private int index;
	private int end_num;
	private int thread_num;
	private int counter = 0;
	
	public PC_thread_block(int i, int end, int num_thread) {
		index = i;
		end_num = end;
		thread_num = num_thread;
	}
	
	public void run() {
		long startTime = System.currentTimeMillis();
		
		int start_i = index * (int) (end_num / thread_num);
		int end_i = start_i + (int) (end_num / thread_num);
		if (index != thread_num - 1) {
			for (int i = start_i; i < end_i; i++) {
				if (isPrime(i)) counter++;
 			}
		}
		else {
			for (int i = start_i; i < end_num; i++) {
				if (isPrime(i)) counter++;
 			}
		}
		
		long endTime = System.currentTimeMillis();
		long timeDiff = endTime - startTime;
		System.out.println("Thread " + index + " Execution Time: " + timeDiff + "ms");
	}
	
	private static boolean isPrime(int x) {
		if (x <= 1) return false;
		for (int i = 2; i < x; i++) {
			if (x%i == 0) return false;
		}
		return true;
	}
	
	public int getCounter() {
		return counter;
	}
	
	
}
