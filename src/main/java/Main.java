public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(ThreadColour.ANSI_PURPLE + "Hello from the main thread");

        Thread anotherThread = new AnotherThread();
        anotherThread.setName("== Another Thread ==");
        anotherThread.start();
//        TimeUnit.SECONDS.sleep(1);

//        Thread execution order cannot be guaranteed, it's down to the system running the code...
        new Thread() {
            public void run() {
                System.out.println(ThreadColour.ANSI_GREEN + "Hello from anonymous class thread");
            }
        }.start();

        Thread myRunnableThread = new Thread(new MyRunnable());
//        anotherThread.interrupt();
        myRunnableThread.start();

        Thread myAnonymousRunnableThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ThreadColour.ANSI_RESET + "Hello from anonymous runnable");
                try {
                    anotherThread.join(2000); // Overloaded method allows for a timeout in case joining thread doesn't complete/terminate in good time
                    System.out.println(ThreadColour.ANSI_RED + "AnotherThread terminated or timed out, I'm running again");
                } catch (InterruptedException ie) {
                    System.out.println(ThreadColour.ANSI_RED + "I couyldn't wait after all, I was interrupted");
                }
            }
        });

        myAnonymousRunnableThread.start();
        System.out.println(ThreadColour.ANSI_PURPLE + "Hello again from MAIN");

//    You can't use the same instance of a thread more than once, obvs
//        anotherThread.start();

    }
}
