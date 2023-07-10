
public class pc_static_cyclic {
	private static int NUM_END = 200000;
	private static int NUM_THREADS = 32;
	private static final int TASK_SIZE = 10;
	
	public static void main(String[] args) {
		if (args.length==2) {
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
		
		PC_thread_cyclic[] ts = new PC_thread_cyclic[NUM_THREADS];
		for (int i = 0; i < NUM_THREADS; i++) {
			ts[i] = new PC_thread_cyclic(i, TASK_SIZE, NUM_END, NUM_THREADS);
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

class PC_thread_cyclic extends Thread {
	private int index;
	private int size_of_task;
	private int end_num;
	private int num_of_thread;
	private int counter = 0;
	
	public PC_thread_cyclic(int i, int size, int end, int threads) {
		index = i;
		size_of_task = size;
		end_num = end;
		num_of_thread = threads;
	}
	
	public void run() {
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; (i*size_of_task*num_of_thread) < end_num; i++) {
			for (int j = 0; j < size_of_task; j++) {
				int check_num = (i*size_of_task*num_of_thread) + size_of_task*index + j;
				if (check_num < end_num) {
					if (isPrime(check_num)) counter++;
				}
				else {
					break;
				}
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