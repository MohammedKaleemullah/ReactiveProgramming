import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class MonoWebClientExample {
    private static final Logger logger = LoggerFactory.getLogger(MonoWebClientExample.class);

    public static void main(String[] args) {
        WebClient webClient = WebClient.create("https://fakestoreapi.com");

        Mono<String> product1 = webClient.get()
                .uri("/products/5")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> logger.error("Error fetching product 1", e)).log();

        Mono<String> product2 = webClient.get()
                .uri("/products/6")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> logger.error("Error fetching product 2", e)).log();



        Mono.zip(product1, product2)
                .doOnSuccess(result -> logger.info("Product 1: {}\nProduct 2: {}", result.getT1(), result.getT2()))
                .doOnError(error -> logger.error("Error fetching products", error))
                .doFinally(signal -> logger.info("All tasks completed!"))
                .subscribe();



        try {
            System.out.println("Main Thread is running 1");
            Thread.sleep(2500);
            System.out.println("Main Thread is running 2");
            Thread.sleep(2500);
            System.out.println("Main Thread is running 3");
            Thread.sleep(2500);
            System.out.println("Main Thread is running 4");
            Thread.sleep(2500);
            System.out.println("Main Thread is running 5");
            Thread.sleep(2500);
            System.out.println("Main Thread is running 6");
        } catch (InterruptedException e) {
            logger.error("Thread interrupted", e);
        }
        Mono.just(product1).subscribe(System.out::println);
        Mono.just(product2).subscribe(System.out::println);
    }
}
