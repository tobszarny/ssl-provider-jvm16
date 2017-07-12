package net.tobszarny.ssl.java6.provider;

import com.sun.net.ssl.internal.ssl.SSLContextImpl;

import javax.net.ssl.SSLSocketFactory;

public class BouncyCastleSSLContextImpl extends SSLContextImpl {

    @Override
    protected SSLSocketFactory engineGetSocketFactory() {
        return new TSLSocketConnectionFactory();
    }
}
