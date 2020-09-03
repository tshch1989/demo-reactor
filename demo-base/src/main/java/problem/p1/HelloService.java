package problem.p1;

import reactor.core.publisher.Mono;

public interface HelloService {
    //原接口需要整改为返回Mono类型,以能够加入到组装流程
    Mono<HelloResp> hello(HelloReq helloReq);
}
