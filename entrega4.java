package entrega4;

import es.upm.babel.cclib.Semaphore;

public class CC_04_MutexSem {
   private static int N_THREADS = 2;
   private static int N_PASOS = 1000000;
   
   static Semaphore s = new Semaphore (1);

   static class Shared {
      private volatile int n;
      public Shared() {
         this.n = 0;
      }
      public int valueN() {
         return this.n;
      }
      public void inc () {
         this.n++;
      }
      public void dec () {
         this.n--;
      }
   }	

   static class Incrementador extends Thread {
      private Shared cont;
      public Incrementador (Shared c) {
         this.cont = c;
      }
      public void run() {
         for (int i = 0; i < N_PASOS; i++) {
        	s.await();
        	this.cont.inc();
     		s.signal();
         }
      }
   }
   
   static class Decrementador extends Thread {
      private Shared cont;
      public Decrementador (Shared c) {
         this.cont = c;
      }
      public void run() {
         for (int i = 0; i < N_PASOS; i++) {
        	s.await();
         	this.cont.dec();
      		s.signal();
         }
      }
   }
   
   public static void main(String args[]) {
	   Shared count = new Shared();
      
      Incrementador[] tInc =
         new Incrementador[N_THREADS];
      Decrementador[] tDec =
         new Decrementador[N_THREADS];
      
      for (int i = 0; i < N_THREADS; i++) {
         tInc[i] = new Incrementador(count);
         tDec[i] = new Decrementador(count);
      }
      
      for (int i = 0; i < N_THREADS; i++) {
         tInc[i].start();
         tDec[i].start();
      }
      
      try {
         for (int i = 0; i < N_THREADS; i++) {
            tInc[i].join();
            tDec[i].join();
         }
      } catch (Exception ex) {}
      
      System.out.printf("El valor final del contador es: %d\n",
    		  			count.valueN());
      System.exit (0);
   }
}
