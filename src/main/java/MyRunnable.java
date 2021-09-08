public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(ThreadColour.ANSI_RED + "Hello from my runnable impl");
    }
}
