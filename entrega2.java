import java.util.Scanner;
import java.util.Arrays;
public class CC_02_Carrera {
	private static class objThread extends Thread {
		private static int n = 0;
		private int delayTime;
		private int code;
		private int dif;

		public objThread(int code, int sec, int dif) {
			super();
			this.code = code;
			this.delayTime = sec;
			this.dif = dif;
		}

		public void run() {
			try {
				Thread.sleep(this.delayTime);
				n += dif;
				n -= dif;
				System.out.println(
						"Thread " + this.code + ": It has been executed after " + this.delayTime + " miliseconds.");
			} catch (InterruptedException e) {
				System.err.println("Thread " + this.code + ": has been interrupted.");
			}
		}
	}

	@SuppressWarnings("resource")
	private static int getNThreads() {
		System.out.print("Enter the number of threads: ");
		Scanner sc = new Scanner(System.in);
		try {
			int nThreads = sc.nextInt();
			if (nThreads < 0) {
				System.out.println("Enter a positive number.");// Negative number
				return 0;
			} else {
				System.out.println("The program will create " + nThreads + (nThreads == 1 ? " Thread" : " Threads"));
				return nThreads;
			}
		} catch (Exception e) {
			System.err.println("Please enter a number.");// NaN
		}
		return 0;
	}

	private static objThread[] startThreads(int nThreads) {
		objThread[] arrayThreads = new objThread[nThreads];
		for (int i = 0; i < arrayThreads.length; i++) {
			int delayTime = (int) (Math.random() * 100);
			int dif = (int)(Math.random() * 10);
			objThread thread = new objThread(i + 1, delayTime, dif);// id, number between 0-100 == 0s - 1s,dif
			thread.start();
			arrayThreads[i] = thread;
		}
		return arrayThreads;
	}

	private static void joinThreads(objThread[] arrayThreads) {
		try {
			for (int i = 0; i < arrayThreads.length; i++) {
				arrayThreads[i].join();
			}
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		int nThreads = getNThreads();
		objThread[] arrayThreads = startThreads(nThreads);
		joinThreads(arrayThreads);
		System.out.println("---------------------");
		System.out.println("All Threads executed.");
		System.out.println("The final value of n is: " + objThread.n);
	}
}