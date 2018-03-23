import java.util.Scanner;

public class CC_03_MutexEA {

	static boolean isReady[] = new boolean[2];// [0] => inc, [1] => dec
	static int turn;// 0 => inc, 1 => dec

	volatile static int n = 0;
	static int cont = 0;
	static int nIt = 0;

	static class increment extends Thread {
		public void run() {
			for (int i = 0; i < nIt; i++) {
				isReady[0] = true;// it is increment turn
				turn = 1; // give turn to the other
				while (isReady[1] && turn == 1);
				n++;
				cont++;
				isReady[0] = false;
			}
		}
	}

	static class decrement extends Thread {
		public void run() {
			for (int j = 0; j < nIt; j++) {
				isReady[1] = true;// it is decrement turn
				turn = 0; // give turn to the other
				while (isReady[0] && turn == 0);
				n--;
				cont++;
				isReady[1] = false;
			}
		}
	}

	public static void main(String[] args) {
		// set nit => number of loops
		System.out.print("Enter de number of loops: ");
		Scanner in = new Scanner(System.in);
		nIt = in.nextInt();

		// create threads
		increment inc = new increment();
		decrement dec = new decrement();

		// launch
		inc.start();
		dec.start();

		try {
			inc.join();
			dec.join();
		} catch (Exception e) {
			System.err.println("Something went wrong.");
			System.err.println(e);
		}

		System.out.printf("Final value of n: %d\n", n);
	}

}
