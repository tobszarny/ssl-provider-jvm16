//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.security.ssl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.ProviderException;
import java.security.Security;

public abstract class SunJSSE extends Provider {
    private static final long serialVersionUID = 3231825739635378733L;
    private static String info = "Sun JSSE provider(PKCS12, SunX509/PKIX key/trust factories, SSLv3/TLSv1/TLSv1.1/TLSv1.2)";
    private static String fipsInfo = "Sun JSSE provider (FIPS mode, crypto provider ";
    private static Boolean fips;
    static Provider cryptoProvider;

    protected static synchronized boolean isFIPS() {
        if (fips == null) {
            fips = Boolean.valueOf(false);
        }

        return fips.booleanValue();
    }

    private static synchronized void ensureFIPS(Provider var0) {
        if (fips == null) {
            fips = Boolean.valueOf(true);
            cryptoProvider = var0;
        } else {
            if (!fips.booleanValue()) {
                throw new ProviderException("SunJSSE already initialized in non-FIPS mode");
            }

            if (cryptoProvider != var0) {
                throw new ProviderException("SunJSSE already initialized with FIPS crypto provider " + cryptoProvider);
            }
        }

    }

    protected SunJSSE() {
        super("SunJSSE", 1.8D, info);
        this.subclassCheck();
        if (Boolean.TRUE.equals(fips)) {
            throw new ProviderException("SunJSSE is already initialized in FIPS mode");
        } else {
            this.registerAlgorithms(false);
        }
    }

    protected SunJSSE(Provider var1) {
        this((Provider) checkNull(var1), var1.getName());
    }

    protected SunJSSE(String var1) {
        this((Provider) null, (String) checkNull(var1));
    }

    private static <T> T checkNull(T var0) {
        if (var0 == null) {
            throw new ProviderException("cryptoProvider must not be null");
        } else {
            return var0;
        }
    }

    private SunJSSE(Provider var1, String var2) {
        super("SunJSSE", 1.8D, fipsInfo + var2 + ")");
        this.subclassCheck();
        if (var1 == null) {
            var1 = Security.getProvider(var2);
            if (var1 == null) {
                throw new ProviderException("Crypto provider not installed: " + var2);
            }
        }

        ensureFIPS(var1);
        this.registerAlgorithms(true);
    }

    private void registerAlgorithms(final boolean var1) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                SunJSSE.this.doRegister(var1);
                return null;
            }
        });
    }

    private void doRegister(boolean var1) {
        if (!var1) {
            this.put("KeyFactory.RSA", "sun.security.rsa.RSAKeyFactory");
            this.put("Alg.Alias.KeyFactory.1.2.840.113549.1.1", "RSA");
            this.put("Alg.Alias.KeyFactory.OID.1.2.840.113549.1.1", "RSA");
            this.put("KeyPairGenerator.RSA", "sun.security.rsa.RSAKeyPairGenerator");
            this.put("Alg.Alias.KeyPairGenerator.1.2.840.113549.1.1", "RSA");
            this.put("Alg.Alias.KeyPairGenerator.OID.1.2.840.113549.1.1", "RSA");
            this.put("Signature.MD2withRSA", "sun.security.rsa.RSASignature$MD2withRSA");
            this.put("Alg.Alias.Signature.1.2.840.113549.1.1.2", "MD2withRSA");
            this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2", "MD2withRSA");
            this.put("Signature.MD5withRSA", "sun.security.rsa.RSASignature$MD5withRSA");
            this.put("Alg.Alias.Signature.1.2.840.113549.1.1.4", "MD5withRSA");
            this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.4", "MD5withRSA");
            this.put("Signature.SHA1withRSA", "sun.security.rsa.RSASignature$SHA1withRSA");
            this.put("Alg.Alias.Signature.1.2.840.113549.1.1.5", "SHA1withRSA");
            this.put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.5", "SHA1withRSA");
            this.put("Alg.Alias.Signature.1.3.14.3.2.29", "SHA1withRSA");
            this.put("Alg.Alias.Signature.OID.1.3.14.3.2.29", "SHA1withRSA");
        }

        this.put("Signature.MD5andSHA1withRSA", "sun.security.ssl.RSASignature");
        this.put("KeyManagerFactory.SunX509", "sun.security.ssl.KeyManagerFactoryImpl$SunX509");
        this.put("KeyManagerFactory.NewSunX509", "sun.security.ssl.KeyManagerFactoryImpl$X509");
        this.put("Alg.Alias.KeyManagerFactory.PKIX", "NewSunX509");
        this.put("TrustManagerFactory.SunX509", "sun.security.ssl.TrustManagerFactoryImpl$SimpleFactory");
        this.put("TrustManagerFactory.PKIX", "sun.security.ssl.TrustManagerFactoryImpl$PKIXFactory");
        this.put("Alg.Alias.TrustManagerFactory.SunPKIX", "PKIX");
        this.put("Alg.Alias.TrustManagerFactory.X509", "PKIX");
        this.put("Alg.Alias.TrustManagerFactory.X.509", "PKIX");
        this.put("SSLContext.TLSv1", "sun.security.ssl.SSLContextImpl$TLS10Context");
        this.put("SSLContext.TLSv1.1", "sun.security.ssl.SSLContextImpl$TLS11Context");
        this.put("SSLContext.TLSv1.2", "sun.security.ssl.SSLContextImpl$TLS12Context");
        this.put("SSLContext.TLS", "sun.security.ssl.SSLContextImpl$TLSContext");
        if (!var1) {
            this.put("Alg.Alias.SSLContext.SSL", "TLS");
            this.put("Alg.Alias.SSLContext.SSLv3", "TLSv1");
        }

        this.put("SSLContext.Default", "sun.security.ssl.SSLContextImpl$DefaultSSLContext");
        this.put("KeyStore.PKCS12", "sun.security.pkcs12.PKCS12KeyStore");
    }

    private void subclassCheck() {
        if (com.sun.net.ssl.internal.ssl.Provider.class.isAssignableFrom(this.getClass())) {
            throw new AssertionError("Illegal subclass: " + this.getClass());
        }
    }

    protected final void finalize() throws Throwable {
        super.finalize();
    }
}
