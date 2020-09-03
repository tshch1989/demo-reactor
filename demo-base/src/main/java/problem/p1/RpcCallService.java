package problem.p1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.util.Map;

public class RpcCallService {

    @SneakyThrows
    public <T> Mono<T> call(RpcContext context, String uri, Map<String, Object> params, TypeReference<Resp<T>> typeReference){
        Resp<HelloResp> objectResp = new Resp<HelloResp>();
        objectResp.setCode("200");
        HelloResp data = new HelloResp();
        data.setMsg("hello " + context.getContext());
        objectResp.setData(data);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(objectResp);
        Resp<T> tResp = mapper.readValue(json, typeReference);
        return Mono.just(tResp.getData());
    }

}
