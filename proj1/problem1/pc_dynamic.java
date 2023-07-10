
public class pc_dynamic {
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
		SharedNum sharedNum = new SharedNum();
		
		PC_thread_dynamic[] ts = new PC_thread_dynamic[NUM_THREADS];
		for (int i = 0; i < NUM_THREADS; i++) {
			ts[i] = new PC_thread_dynamic(i, NUM_END, TASK_SIZE, sharedNum);
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

class PC_thread_dynamic extends Thread {
	private int index;
	private int end_num;
	private int size_of_task;
	private SharedNum sharedNum;
	private int counter = 0;
	
	public PC_thread_dynamic(int i, int end, int size, SharedNum num) {
		index = i;
		end_num = end;
		size_of_task =size;
		sharedNum = num;
	}
	
	public void run() {
		long startTime = System.currentTimeMillis();
		
		int i = sharedNum.getNext();
		while (i < end_num/size_of_task) {
			int start = i*size_of_task;
			
			for (int j = 0; j < size_of_task; j++) {
				if (isPrime(start + j)) counter++;
			}
			
			i = sharedNum.getNext();
			
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


class SharedNum {
	private int num = -1;
	
	public synchronized int getNext() {
		num = num + 1;
		
		return num;
	}
}
