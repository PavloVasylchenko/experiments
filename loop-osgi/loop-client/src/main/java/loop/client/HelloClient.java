package loop.client;

import org.osgi.util.tracker.ServiceTracker;

import loop.common.HelloService;

public class HelloClient {

    private final ServiceTracker helloServiceTracker;

    public HelloClient(ServiceTracker helloServiceTracker) {
        this.helloServiceTracker = helloServiceTracker;
    }

    public String hello(String name) {
        HelloService helloService = (HelloService) helloServiceTracker.getService();
        if (helloService == null) {
            return "Null";
        } else {
            return helloService.hello(name);
        }
    }

}

