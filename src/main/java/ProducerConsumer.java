import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
    public static final String eof = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<String>();
        ReentrantLock bufferLock = new ReentrantLock();
        MyProducer producer = new MyProducer(buffer, ThreadColour.ANSI_GREEN, bufferLock);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColour.ANSI_PURPLE, bufferLock);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColour.ANSI_CYAN, bufferLock);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements Runnable {
    private List<String> buffer;
    private String colour;
    private ReentrantLock bufferLock;

    public MyProducer(List<String> buffer, String colour, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.colour = colour;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1","2","3","4","5"};

        for (String num: nums) {
            try {
                System.out.println(colour + "Adding..." + num);
                bufferLock.lock();
                try {
                    buffer.add(num);
                } finally {
                    bufferLock.unlock();
                }
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ie) {
                System.out.println("Producer was interrupted");
            }
        }
        System.out.println(colour + "Adding EOF and exiting...");
        bufferLock.lock();
        try {
            buffer.add("EOF");
        } finally {
            bufferLock.unlock();
        }
    }
}

class MyConsumer implements Runnable {

    private List<String> buffer;
    private String colour;
    private ReentrantLock bufferLock;


    public MyConsumer(List<String> buffer, String colour, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.colour = colour;
        this.bufferLock = bufferLock;

    }

    @Override
    public void run() {
        int counter = 0;

        while(true) {
            if (bufferLock.tryLock()) {
                try {
                    if(buffer.isEmpty()) {
                        continue;
                    }
                    System.out.println(colour + "The counter = " + counter);
                    counter = 0;

                    if (buffer.get(0).equals(ProducerConsumer.eof)) {
                        System.out.println(colour + "Exiting");
                        break;
                    } else {
                        System.out.println(colour + "Removed " + buffer.remove(0));
                    }
                } finally {
                    bufferLock.unlock();
                }
            } else {
                counter++;
            }
        }
    }
}