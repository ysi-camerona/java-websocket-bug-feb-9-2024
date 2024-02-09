import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;

public class DefaultClient extends WebSocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultClient.class);

    public DefaultClient(URI serverUri, Duration connectTimeout) {
        super(serverUri, new Draft_6455(), new HashMap<>(), (int) connectTimeout.toMillis());
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOGGER.info("onConnect called");
    }

    @Override
    public void onMessage(String message) {
        LOGGER.info("onMessage called");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LOGGER.info("onClose called");
    }

    @Override
    public void onError(Exception ex) {
        LOGGER.info("onError called");
    }

}
