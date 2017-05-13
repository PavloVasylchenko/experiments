package loop.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import loop.common.HelloService;

public class App implements BundleActivator {

    private ServiceTracker serviceTracker;
    private LoopRequestSender srv = null;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Started");
        serviceTracker = new ServiceTracker(context, HelloService.class.getName(), null);
        serviceTracker.open();
        srv = new LoopRequestSender(serviceTracker);
        Thread thread = new Thread(srv);
        thread.start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping");
        srv.stop();
        System.out.println("Stoped");
        serviceTracker.close();
    }
}
