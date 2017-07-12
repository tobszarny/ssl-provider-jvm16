package net.tobszarny.ssl.java6.provider;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
import java.security.Principal;
import java.security.cert.Certificate;

public class BouncyCastleSSLSession implements SSLSession {

    private Certificate[] peertCerts;

    public BouncyCastleSSLSession(Certificate[] peertCerts) {
        this.peertCerts = peertCerts;
    }

    @Override
    public int getApplicationBufferSize() {
        return 0;
    }

    @Override
    public String getCipherSuite() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCreationTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.security.cert.Certificate[] getLocalCertificates() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Principal getLocalPrincipal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPacketBufferSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public java.security.cert.Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return peertCerts;
    }

    @Override
    public String getPeerHost() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPeerPort() {
        return 0;
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return null;
        //throw new UnsupportedOperationException();

    }

    @Override
    public String getProtocol() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SSLSessionContext getSessionContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getValue(String arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getValueNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalidate() {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putValue(String arg0, Object arg1) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void removeValue(String arg0) {
        throw new UnsupportedOperationException();

    }


}
