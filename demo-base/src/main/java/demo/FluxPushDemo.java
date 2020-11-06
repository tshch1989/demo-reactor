package demo;

import reactor.core.publisher.Flux;

public class FluxPushDemo {
    public static void main(String[] args) {
        //每隔1秒输出当前时间戳
        Flux.push(fluxSink -> {
            new Thread(() -> {
                try {
                    while (true) {
                        fluxSink.next(System.currentTimeMillis());
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    fluxSink.error(e);
                }finally {
                    fluxSink.complete();
                }
            }).start();
        }).doOnNext(System.out::println).blockLast();
    }
}
