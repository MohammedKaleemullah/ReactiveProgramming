import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxBasics {
    public static void main(String[] args) {
        //testMono();
        testFlux();
    }

    private static void testFlux() {
        Flux<?> flux = Flux
                .just("Microservices", "Backend")
                .concatWithValues("Reactive", "Kaleemullah")
                .log();
        flux.subscribe(System.out::println);
    }

    private static void testMono() {
        Mono<String> m1 = Mono
                .just("product1")
                .log();
        m1.subscribe(System.out::println);
    }
}
