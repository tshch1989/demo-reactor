package demo;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

public class FluxDemo {
    public static void main(String[] args) throws InterruptedException {
        Scheduler parallel = Schedulers.newParallel("parallel-my");
        AtomicInteger count = new AtomicInteger(1);
        for (int i = 0; i < 10; i++) {
            Mono.defer(()->{
                BlockCall blockCall = new BlockCall();
                String s = blockCall.remoteCall();
                return Mono.just(s);
            }).subscribeOn(parallel)
                    .onErrorResume(throwable -> Mono.empty())
                    .flatMap(s -> {
                        System.out.println(s);
                        int andIncrement = count.getAndIncrement();
                        return Mono.just("修改了" + andIncrement);
                    }).doOnNext(System.out::println)
                    .subscribe();
        }
        while (count.get() < 10) {
            Thread.sleep(1000L);
            System.out.println("run ...");
        }
        parallel.dispose();
    }

    private static class BlockCall{
        public String remoteCall() {
            String name = Thread.currentThread().getName();
            System.out.println(name+"=========remoteCall start");
            try {
                Thread.sleep(2*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+"==========remoteCall end");
            return "remote: hello block.";
        }
    }

}
