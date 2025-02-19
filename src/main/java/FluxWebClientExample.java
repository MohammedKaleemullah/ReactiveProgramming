import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class FluxWebClientExample {
    private static final Logger logger = LoggerFactory.getLogger(FluxWebClientExample.class);

    public static void main(String[] args) {
        WebClient webClient = WebClient.create("https://fakestoreapi.com");

        Flux<String> products = Flux.merge(
                webClient.get()
                        .uri("/products/5")
                        .retrieve()
                        .bodyToMono(String.class)
                        .doOnError(e -> logger.error("Error fetching product 1", e))
                        .log(),

                webClient.get()
                        .uri("/products/6")
                        .retrieve()
                        .bodyToMono(String.class)
                        .doOnError(e -> logger.error("Error fetching product 2", e))
                        .log()
        );

        products
                .doOnNext(product -> logger.info("Received product: {}", product))
                .doOnError(error -> logger.error("Error fetching products", error))
                .doFinally(signal -> logger.info("All tasks completed!"))
                .subscribe();

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            logger.error("Thread interrupted", e);
        }
    }
}
