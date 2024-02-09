import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class WebsocketConnectionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketConnectionTest.class);

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        Duration timeout = Duration.ofSeconds(5);
        URI serverUri = new URI("wss://ws.postman-echo.com/raw");

        connectWithGracefulClient(serverUri, timeout);
        connectWithDefaultClient(serverUri, timeout);
    }

    private static void connectWithGracefulClient(URI serverUri, Duration timeout) throws InterruptedException {
        GracefulTimeoutClient client = new GracefulTimeoutClient(serverUri, timeout);
        LOGGER.info("Graceful client - connecting with {} ms timeout", timeout.toMillis());
        boolean connected = client.connectBlocking();
        LOGGER.info("Graceful client - finished connecting, result: {}", connected);
        client.closeBlocking();
        LOGGER.info("Graceful client - closed connection");
    }

    private static void connectWithDefaultClient(URI serverUri, Duration timeout) throws InterruptedException {
        DefaultClient client = new DefaultClient(serverUri, timeout);
        LOGGER.info("Default client - connecting with {} ms timeout", timeout.toMillis());
        boolean connected = client.connectBlocking();
        LOGGER.info("Default client - finished connecting, result: {}", connected);
        client.closeBlocking();
        LOGGER.info("Default client - closed connection");
    }

}
