package loop.client;

import java.util.concurrent.TimeUnit;

import org.osgi.util.tracker.ServiceTracker;

public class LoopRequestSender implements Runnable {

    private HelloClient client;
    private volatile boolean stop = false;

    public LoopRequestSender(ServiceTracker serviceTracker) {
        this.client = new HelloClient(serviceTracker);
    }

    @Override
    public void run() {
        int i = 0;
        while (!stop) {
            i++;
            System.out.println(client.hello("John") + " " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.stop = true;
    }
}
