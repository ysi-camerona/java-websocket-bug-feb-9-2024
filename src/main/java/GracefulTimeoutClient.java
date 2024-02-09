import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;

import javax.net.ssl.SSLParameters;

class GracefulTimeoutClient extends WebSocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GracefulTimeoutClient.class);
    private final Duration connectTimeout;

    public GracefulTimeoutClient(URI serverUri, Duration connectTimeout) {
        super(serverUri, new Draft_6455(), new HashMap<>(), (int) connectTimeout.toMillis());
        this.connectTimeout = connectTimeout;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOGGER.info("onConnect called");
        setSocketTimeout(Duration.ZERO);
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

    @Override
    protected void onSetSSLParameters(SSLParameters sslParameters) {
        super.onSetSSLParameters(sslParameters);
        setSocketTimeout(connectTimeout);
    }

    private void setSocketTimeout(Duration timeout) {
        try {
            getSocket().setSoTimeout((int) timeout.toMillis());
            LOGGER.info("Set socket timeout to {} ms", timeout.toMillis());
        } catch (SocketException e) {
            LOGGER.warn("Failed to set socket timeout!");
        }
    }
}
