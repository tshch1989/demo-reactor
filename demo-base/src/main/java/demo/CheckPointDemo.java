package demo;

import reactor.core.publisher.Flux;

public class CheckPointDemo {
    public static void main(String[] args) {
        aa().checkpoint("my-check-point").subscribe();
    }

    public static Flux<Integer> aa(){
        return Flux.just("111","222")
                .map(s -> "a"+s)
                .map(Integer::parseInt);
    }

}
