package problem.p1;

import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.lang.reflect.Proxy;

public class HelloProblemMain {
    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        HelloService helloService = (HelloService) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[] { HelloService.class }, new HelloInvocation(new RpcCallService()));
        RpcContext rpcContext = new RpcContext();
        rpcContext.setContext("哈哈哈哈哈哈");
        Mono.defer(() -> {
            HelloReq helloReq = new HelloReq();
            helloReq.setMsg("world.");
            return helloService.hello(helloReq);
        }).subscriberContext(Context.of("context", rpcContext))
               .doOnNext(helloResp -> {
                   System.out.println(helloResp);
               })
                .subscribe();
    }
}
