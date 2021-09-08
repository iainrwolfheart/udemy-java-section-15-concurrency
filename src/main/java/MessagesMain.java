import java.util.Random;

public class MessagesMain {

    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();

    }
}

class Message {
    private String message;
    private boolean empty = true;

    /*
    Synchronising these methods as we don't want them running concurrently as they rely upon
    one another
     */
    public synchronized String read() {
        while(empty) {
            try {
                wait();
            } catch (InterruptedException ie) {

            }
        }
        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException ie) {

            }
        }
        empty = false;
        notifyAll();
        this.message = message;
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String messages[] = {
                "Humpty dumpty sat on a wall",
                "Humpty Dumpty had a great fall",
                "All the kings' horses and all the kings' men",
                "Couldn't put Humpty together again"
        };

        Random random = new Random();

        for(int i = 0; i < messages.length; i++) {
            message.write(messages[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ie) {

            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    public void run() {
        Random random = new Random();
        for (String latestMessage = message.read();
             !latestMessage.equals("Finished");
             latestMessage = message.read()) {
            System.out.println(latestMessage);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ie) {

            }
        }
    }
}





