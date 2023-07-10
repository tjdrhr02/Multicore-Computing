import java.util.*;

public class MatmultD {
	private static Scanner sc = new Scanner(System.in);
	public static void main(String [] args) {
		int thread_no = 0;
		if (args.length == 1) thread_no = Integer.valueOf(args[0]);
		else thread_no = 32;
		
		int a[][] = readMatrix();
		int b[][] = readMatrix();
		
		Matrix c = new Matrix(a.length, b[0].length);
		
		long startTime = System.currentTimeMillis();
		
		MatmultD_thread[] ts = new MatmultD_thread[thread_no];
		for (int i = 0; i < thread_no; i++) {
			ts[i] = new MatmultD_thread(i, thread_no, a, b, c);
			ts[i].start();
		}
		for (int i = 0; i < thread_no; i++) {
			try {
				ts[i].join();
			} catch (InterruptedException e) {}
		}
		
		long endTime = System.currentTimeMillis();
		
		printMatrix(c.getMatrix());
		
		System.out.printf("[thread_no] : %2d, [Time] : %4d ms\n", thread_no, endTime-startTime);
	}
	
	public static int[][] readMatrix() {
		int rows = sc.nextInt();
		int cols = sc.nextInt();
		int[][] result = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result[i][j]= sc.nextInt();
			}
		}
		return result;
	}
	
	public static void printMatrix(int[][] mat) {
		System.out.println("Matrix[" + mat.length + "] [" + mat[0].length + "]");
		int rows = mat.length;
		int columns = mat[0].length;
		int sum = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.printf("4%d ", mat[i][j]);
				sum = sum + mat[i][j];
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Matrix Sum = " + sum + "\n");
	}
}


class Matrix {
	private int[][] mat;
	
	public Matrix(int row, int col) {
		mat = new int[row][col];
	}
	
	public int[][] getMatrix() {
		return mat;
	}
	
	public synchronized void setMatrix(int row, int col, int result) {
		mat[row][col] = result; 
	}
}


class MatmultD_thread extends Thread {
	private int index;
	private int num_of_thread;
	private int a[][];
	private int b[][];
	private Matrix c;
	
	public MatmultD_thread(int i, int threads, int a[][], int b[][], Matrix c) {
		index = i;
		num_of_thread = threads;
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public void run() {
		long startTime = System.currentTimeMillis();
		
		int n = a[0].length;
		int m = a.length;
		int p = b[0].length;
		
		for (int i = index; i < (m*p); i += num_of_thread) {
			int col = i % m;
			int row = (i - col) / m;
			
			int result = 0;
			for (int k = 0; k < n; k++) {
				result = result + a[row][k]*b[k][col]; 
			}
			
			c.setMatrix(row, col, result);
		}
		
		long endTime = System.currentTimeMillis();
		System.out.printf("[thread_no] : %2d, [Time] : %4d ms\n", index, endTime-startTime);
	}
}


	