public class CounterMain {

    public static void main(String[] args) {
        Countdown countdown = new Countdown();
        CountdownThread ct1 = new CountdownThread(countdown);
        ct1.setName("Thread 1");
        CountdownThread ct2 = new CountdownThread(countdown);
        ct1.setName("Thread 2");

        ct1.start();
        ct2.start();
    }
}

class Countdown {

    //  On the heap!
    private int i;

//    "synchronised" prevents thread interference when necessary. Can only be used by one thread at a time.
    public /*synchronized*/ void doCountdown () {
        String colour;

        switch(Thread.currentThread().getName()) {
            case "Thread 1":
                colour = ThreadColour.ANSI_CYAN;
                break;
            case "Thread 2":
                colour = ThreadColour.ANSI_PURPLE;
                break;
            default:
                colour = ThreadColour.ANSI_GREEN;
        }

        /* Multiple suspension points are possible within for loop, affecting how thread instances
        share and individually update and utilise resources such as instance variables used
        by both threads, e.g. int i
        */
        synchronized (this) {
            for (i = 10; i > 0; i--) {
                System.out.println(colour + Thread.currentThread().getName() + ": i = " + i);
            }
        }
    }
}

class CountdownThread extends  Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        threadCountdown.doCountdown();
    }
}