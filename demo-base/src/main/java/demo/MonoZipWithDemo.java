package demo;

import reactor.core.publisher.Mono;

public class MonoZipWithDemo {
    public static void main(String[] args) throws InterruptedException {
        //第一个流程返回空,如果不适用defaultIfEmpty或者switchIfEmpty,流程将直接结束,不再执行后续操作
        Mono.empty().zipWith(Mono.just("bbb")).doOnNext(objects -> {
            System.out.println(objects.getT1());
            System.out.println(objects.getT2());
        }).block();

        Mono.just("aaa").zipWith(Mono.justOrEmpty(Mono.empty())).doOnNext(objects -> {
            System.out.println(objects.getT1());
            System.out.println(objects.getT2());
        }).block();

    }
}
