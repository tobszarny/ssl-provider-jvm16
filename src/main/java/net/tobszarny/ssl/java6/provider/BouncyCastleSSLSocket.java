package net.tobszarny.ssl.java6.provider;

import org.bouncycastle.crypto.tls.*;

import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class BouncyCastleSSLSocket extends SSLSocket {

    private final String host;
    private final TlsClientProtocol tlsClientProtocol;

    private java.security.cert.Certificate[] peertCerts;

    public BouncyCastleSSLSocket(final String host, final TlsClientProtocol tlsClientProtocol) {
        super();
        this.host = host;
        this.tlsClientProtocol = tlsClientProtocol;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return tlsClientProtocol.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return tlsClientProtocol.getOutputStream();
    }

    @Override
    public synchronized void close() throws IOException {
        tlsClientProtocol.close();
    }

    @Override
    public void addHandshakeCompletedListener(HandshakeCompletedListener arg0) {

    }

    @Override
    public boolean getEnableSessionCreation() {
        return false;
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return null;
    }

    @Override
    public String[] getEnabledProtocols() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getNeedClientAuth() {
        return false;
    }

    @Override
    public SSLSession getSession() {
        return new BouncyCastleSSLSession(peertCerts);
    }


    @Override
    public String[] getSupportedProtocols() {
        return null;
    }

    @Override
    public boolean getUseClientMode() {
        return false;
    }

    @Override
    public boolean getWantClientAuth() {

        return false;
    }

    @Override
    public void removeHandshakeCompletedListener(HandshakeCompletedListener arg0) {

    }

    @Override
    public void setEnableSessionCreation(boolean arg0) {


    }

    @Override
    public void setEnabledCipherSuites(String[] arg0) {

    }

    @Override
    public void setEnabledProtocols(String[] arg0) {


    }

    @Override
    public void setNeedClientAuth(boolean arg0) {

    }

    @Override
    public void setUseClientMode(boolean arg0) {

    }

    @Override
    public void setWantClientAuth(boolean arg0) {

    }

    @Override
    public String[] getSupportedCipherSuites() {
        return null;
    }

    @Override
    public void startHandshake() throws IOException {
        tlsClientProtocol.connect(new DefaultTlsClient() {
            @Override
            public Hashtable<Integer, byte[]> getClientExtensions() throws IOException {
                Hashtable<Integer, byte[]> clientExtensions = super.getClientExtensions();
                if (clientExtensions == null) {
                    clientExtensions = new Hashtable<Integer, byte[]>();
                }

                //Add host_name
                byte[] host_name = host.getBytes();

                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final DataOutputStream dos = new DataOutputStream(baos);
                dos.writeShort(host_name.length + 3); // entry size
                dos.writeByte(0); // name type = hostname
                dos.writeShort(host_name.length);
                dos.write(host_name);
                dos.close();
                clientExtensions.put(ExtensionType.server_name, baos.toByteArray());
                return clientExtensions;
            }

            @Override
            public TlsAuthentication getAuthentication()
                    throws IOException {
                return new TlsAuthentication() {


                    @Override
                    public void notifyServerCertificate(Certificate serverCertificate) throws IOException {

                        try {
                            CertificateFactory cf = CertificateFactory.getInstance("X.509");
                            List<java.security.cert.Certificate> certs = new LinkedList<java.security.cert.Certificate>();
                            for (org.bouncycastle.asn1.x509.Certificate c : serverCertificate.getCertificateList()) {
                                certs.add(cf.generateCertificate(new ByteArrayInputStream(c.getEncoded())));
                            }
                            peertCerts = certs.toArray(new java.security.cert.Certificate[0]);
                        } catch (CertificateException e) {
                            System.out.println("Failed to cache server certs" + e);
                            throw new IOException(e);
                        }

                    }

                    @Override
                    public TlsCredentials getClientCredentials(CertificateRequest arg0)
                            throws IOException {
                        return null;
                    }

                };

            }

        });


    }
}


