package problem.p1;

import com.fasterxml.jackson.core.type.TypeReference;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloInvocation implements InvocationHandler {

    private final RpcCallService rpcCallService;

    public HelloInvocation(RpcCallService rpcCallService){
        this.rpcCallService = rpcCallService;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return Mono.subscriberContext().map(context->{//这个地方返回的样式是Mono<Mono<HelloResp>>
            Object context1 = context.get("context");
            return rpcCallService.call((RpcContext) context1, null, null, new TypeReference<Resp<HelloResp>>() {
            });
        }).flatMap(helloRespMono -> {//解除一层封装
            return helloRespMono;
        });
    }
}
