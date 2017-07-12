package net.tobszarny.ssl.java6.provider;

import org.bouncycastle.crypto.tls.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class BouncyCastleDefaultTlsClient extends DefaultTlsClient {

    private String host;
    private java.security.cert.Certificate[] peertCerts;

    public BouncyCastleDefaultTlsClient(final String host, java.security.cert.Certificate[] peertCerts) {
        this.host = host;
        this.peertCerts = peertCerts;
    }

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


}
