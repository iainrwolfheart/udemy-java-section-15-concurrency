public class AnotherThread extends Thread {

    @Override
    public void run() {
        System.out.println(ThreadColour.ANSI_BLUE + "Hello from " + currentThread().getName());

        int sleepTime = 5000;
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ie) {
            System.out.println(ThreadColour.ANSI_BLUE + "A different thread woke me up");
            return;
        }

        System.out.println(sleepTime / 1000 + " seconds have passed, I'm awake");
    }
}
