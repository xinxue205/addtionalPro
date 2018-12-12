package gc;
import java.util.ArrayList;
import java.util.List;

public class GCTest2 {
  static final int ENOUGH_PIGS = 50;

  public static void main(String[] args) throws InterruptedException {
	    new PigEater().start();
	    new PigEater().start();
	    new PigEater().start();
	    new PigEater().start();
  }

  static class PigEater extends Thread {
	  static volatile List pigs = new ArrayList();

    @Override
    public void run() {
      while (true) {
        pigs.add(new byte[4 * 1024 * 1024]); //32MB per pig
        if (pigs.size() > ENOUGH_PIGS) {
            takeANap(4000);
            pigs = new ArrayList();
            takeANap(500);
        }
        takeANap(100);
      }
    }
  }

  static void takeANap(int ms) {
    try {
      Thread.sleep(ms);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

