package ssl;

import com.sun.net.ssl.internal.ssl.SSLContextImpl;

import javax.net.ssl.SSLSocketFactory;

public class BcSSLContextImpl extends SSLContextImpl {

    @Override
    protected SSLSocketFactory engineGetSocketFactory() {
        return new TSLSocketConnectionFactory();
    }
}
