package loop.server;

import loop.common.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

}

