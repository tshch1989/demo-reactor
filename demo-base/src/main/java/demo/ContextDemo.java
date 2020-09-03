package demo;

import reactor.core.publisher.Mono;

public class ContextDemo {
    public static void main(String[] args) {
        String key = "message";
        Mono<String> r =
                Mono.just("Hello")
                        .flatMap( s -> Mono.subscriberContext()
                                .map( ctx -> {
                                    String s1 = s + " " + ctx.get(key);
                                    System.out.println(s1);
                                    return s1;
                                })
                        )
                        .flatMap( s -> Mono.subscriberContext()
                                .map( ctx -> {
                                    String s1 = s + " " + ctx.get(key);
                                    System.out.println(s1);
                                    return s1;
                                })
                        )
                        .subscriberContext(ctx -> ctx.put(key, "World"));
        r.subscribe();
    }
}
