package net.tobszarny.ssl.java6.provider;

import com.sun.net.ssl.internal.ssl.SSLSocketFactoryImpl;
import org.bouncycastle.crypto.tls.TlsClientProtocol;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * This Class enables TLS V1.2  connection based on BouncyCastle Providers.
 * Just to use:
 * URL myurl = new URL( "http:// ...URL that only Works in TLS 1.2);
 * HttpsURLConnection  con = (HttpsURLConnection )myurl.openConnection();
 * con.setSSLSocketFactory(new TSLSocketConnectionFactory());
 *
 * @author AZIMUTS
 */
public class TSLSocketConnectionFactory extends SSLSocketFactory {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Adding Custom BouncyCastleProvider
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null)
            ProvidersUtil.insertProviderAfterProvider(new BouncyCastleProvider(),
                    BouncyCastleSSLProvider.BOUNCY_CASTLE_JSSE_PROVIDER_NAME, "SUN");
    }

    private SSLSocketFactoryImpl sslSocketFactoryDelegate = null;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //SECURE RANDOM
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private SecureRandom secureRandom = new SecureRandom();

    public TSLSocketConnectionFactory() {
        try {
            sslSocketFactoryDelegate = new SSLSocketFactoryImpl();
        } catch (Exception e) {
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Adding Custom BouncyCastleProvider
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Socket createSocket(Socket socket, final String host, int port, boolean arg3)
            throws IOException {
        if (socket == null) {
            socket = new Socket();
        }
        if (!socket.isConnected()) {
            socket.connect(new InetSocketAddress(host, port));
        }

        final TlsClientProtocol tlsClientProtocol = new TlsClientProtocol(socket.getInputStream(), socket.getOutputStream(), secureRandom);
        return createSSLSocket(host, tlsClientProtocol);


    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SOCKET FACTORY  METHODS
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String[] getDefaultCipherSuites() {
        return sslSocketFactoryDelegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return sslSocketFactoryDelegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return null;
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return null;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost,
                               int localPort) throws IOException, UnknownHostException {
        return null;
    }

    @Override
    public Socket createSocket(InetAddress address, int port,
                               InetAddress localAddress, int localPort) throws IOException {
        return null;
    }

    private SSLSocket createSSLSocket(final String host, final TlsClientProtocol tlsClientProtocol) {
        return new BouncyCastleSSLSocket(host, tlsClientProtocol);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //SOCKET CREATION
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //HANDSHAKE LISTENER
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class TLSHandshakeListener implements HandshakeCompletedListener {
        @Override
        public void handshakeCompleted(HandshakeCompletedEvent event) {

        }
    }
}

