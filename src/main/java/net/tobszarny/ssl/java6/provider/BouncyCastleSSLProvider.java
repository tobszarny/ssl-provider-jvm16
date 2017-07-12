package net.tobszarny.ssl.java6.provider;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProviderException;
import java.security.Security;

public final class BouncyCastleSSLProvider extends java.security.Provider {
    public static final String BOUNCY_CASTLE_JSSE_PROVIDER_NAME = "BC-JSSE";
    private static final long serialVersionUID = 3231825739635378733L;
    static java.security.Provider cryptoProvider;
    private static String info = "Bouncy Castle JSSE provider(PKCS12, SunX509 key/trust factories, SSLv3, TLSv1)";
    private static String fipsInfo = "Bouncy Castle JSSE provider (FIPS mode, crypto provider ";
    private static Boolean fips;

    static {
        System.out.println("dupa");
//        if (Security.getProvider(BOUNCY_CASTLE_JSSE_PROVIDER_NAME) == null) {
//            ProvidersUtil.insertProviderAfterProvider(new BouncyCastleSSLProvider(), "SUN");
//        }
//        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
//            ProvidersUtil.insertProviderAfterProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider(),
//                    BOUNCY_CASTLE_JSSE_PROVIDER_NAME, "SUN");
//        }
    }

    public BouncyCastleSSLProvider() {
        super(BOUNCY_CASTLE_JSSE_PROVIDER_NAME, 1.6D, info);
        if (Boolean.TRUE.equals(fips)) {
            throw new ProviderException(BOUNCY_CASTLE_JSSE_PROVIDER_NAME + " is already initialized in FIPS mode");
        } else {
            this.registerAlgorithms(false);
        }
    }

    public BouncyCastleSSLProvider(java.security.Provider var1) {
        this((java.security.Provider) checkNull(var1), var1.getName());
    }

    public BouncyCastleSSLProvider(String var1) {
        this((java.security.Provider) null, (String) checkNull(var1));
    }

    private BouncyCastleSSLProvider(java.security.Provider var1, String var2) {
        super(BOUNCY_CASTLE_JSSE_PROVIDER_NAME, 1.6D, fipsInfo + var2 + ")");
        if (var1 == null) {
            var1 = Security.getProvider(var2);
            if (var1 == null) {
                throw new ProviderException("Crypto provider not installed: " + var2);
            }
        }

        ensureFIPS(var1);
        this.registerAlgorithms(true);
    }

    public static synchronized void install() {
    }

    public static synchronized boolean isFIPS() {
        if (fips == null) {
            fips = Boolean.valueOf(false);
        }

        return fips.booleanValue();
    }

    private static synchronized void ensureFIPS(java.security.Provider var0) {
        if (fips == null) {
            fips = Boolean.valueOf(true);
            cryptoProvider = var0;
        } else {
            if (!fips.booleanValue()) {
                throw new ProviderException(BOUNCY_CASTLE_JSSE_PROVIDER_NAME + " already initialized in non-FIPS mode");
            }

            if (cryptoProvider != var0) {
                throw new ProviderException(BOUNCY_CASTLE_JSSE_PROVIDER_NAME + " already initialized with FIPS crypto provider " + cryptoProvider);
            }
        }

    }

    private static <T> T checkNull(T var0) {
        if (var0 == null) {
            throw new ProviderException("cryptoProvider must not be null");
        } else {
            return var0;
        }
    }

    private void registerAlgorithms(final boolean var1) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                BouncyCastleSSLProvider.this.doRegister(var1);
                return null;
            }
        });
    }

    private void doRegister(boolean var1) {
        if (!var1) {
            this.put("SSLContext.SSL", "net.tobszarny.ssl.java6.provider.BouncyCastleSSLContextImpl");
            this.put("SSLContext.SSLv3", "net.tobszarny.ssl.java6.provider.BouncyCastleSSLContextImpl");
        }

        this.put("SSLContext.TLS", "net.tobszarny.ssl.java6.provider.BouncyCastleSSLContextImpl");
        this.put("SSLContext.TLSv1", "net.tobszarny.ssl.java6.provider.BouncyCastleSSLContextImpl");
        this.put("SSLContext.Default", "net.tobszarny.ssl.java6.provider.BouncyCastleSSLContextImpl");
    }
}
