package loop.server;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import loop.common.HelloService;

public class App implements BundleActivator {

    private ServiceRegistration registration;

    @Override
    public void start(BundleContext context) throws Exception {
        registration = context.registerService(HelloService.class, new HelloServiceImpl(), new Hashtable<>());
        System.out.println("Registered");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (registration != null) {
            registration.unregister();
        }
        System.out.println("Unregistered");
    }
}
