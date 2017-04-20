//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sun.net.ssl.internal.ssl;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

public class SSLContextImpl extends SSLContextSpi {
    private static final Debug debug = Debug.getInstance("ssl");
    private final EphemeralKeyManager ephemeralKeyManager;
    private final SSLSessionContextImpl clientCache;
    private final SSLSessionContextImpl serverCache;
    private boolean isInitialized;
    private X509ExtendedKeyManager keyManager;
    private X509TrustManager trustManager;
    private SecureRandom secureRandom;

    public SSLContextImpl() {
        this((SSLContextImpl)null);
    }

    SSLContextImpl(SSLContextImpl var1) {
        if(var1 == null) {
            this.ephemeralKeyManager = new EphemeralKeyManager();
            this.clientCache = new SSLSessionContextImpl();
            this.serverCache = new SSLSessionContextImpl();
        } else {
            this.ephemeralKeyManager = var1.ephemeralKeyManager;
            this.clientCache = var1.clientCache;
            this.serverCache = var1.serverCache;
        }

    }

    protected void engineInit(KeyManager[] var1, TrustManager[] var2, SecureRandom var3) throws KeyManagementException {
        this.isInitialized = false;
        this.keyManager = this.chooseKeyManager(var1);
        if(var2 == null) {
            try {
                TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                var4.init((KeyStore)null);
                var2 = var4.getTrustManagers();
            } catch (Exception var5) {
                ;
            }
        }

        this.trustManager = this.chooseTrustManager(var2);
        if(var3 == null) {
            this.secureRandom = JsseJce.getSecureRandom();
        } else {
            if(Provider.isFIPS() && var3.getProvider() != Provider.cryptoProvider) {
                throw new KeyManagementException("FIPS mode: SecureRandom must be from provider " + Provider.cryptoProvider.getName());
            }

            this.secureRandom = var3;
        }

        if(debug != null && Debug.isOn("sslctx")) {
            System.out.println("trigger seeding of SecureRandom");
        }

        this.secureRandom.nextInt();
        if(debug != null && Debug.isOn("sslctx")) {
            System.out.println("done seeding SecureRandom");
        }

        this.isInitialized = true;
    }

    private X509TrustManager chooseTrustManager(TrustManager[] var1) throws KeyManagementException {
        for(int var2 = 0; var1 != null && var2 < var1.length; ++var2) {
            if(var1[var2] instanceof X509TrustManager) {
                if(Provider.isFIPS() && !(var1[var2] instanceof X509TrustManagerImpl)) {
                    throw new KeyManagementException("FIPS mode: only SunJSSE TrustManagers may be used");
                }

                return (X509TrustManager)var1[var2];
            }
        }

        return DummyX509TrustManager.INSTANCE;
    }

    private X509ExtendedKeyManager chooseKeyManager(KeyManager[] var1) throws KeyManagementException {
        for(int var2 = 0; var1 != null && var2 < var1.length; ++var2) {
            KeyManager var3 = var1[var2];
            if(var3 instanceof X509KeyManager) {
                if(Provider.isFIPS()) {
                    if(!(var3 instanceof X509KeyManagerImpl) && !(var3 instanceof SunX509KeyManagerImpl)) {
                        throw new KeyManagementException("FIPS mode: only SunJSSE KeyManagers may be used");
                    }

                    return (X509ExtendedKeyManager)var3;
                }

                if(var3 instanceof X509ExtendedKeyManager) {
                    return (X509ExtendedKeyManager)var3;
                }

                if(debug != null && Debug.isOn("sslctx")) {
                    System.out.println("X509KeyManager passed to SSLContext.init():  need an X509ExtendedKeyManager for SSLEngine use");
                }

                return new AbstractWrapper((X509KeyManager)var3);
            }
        }

        return DummyX509KeyManager.INSTANCE;
    }

    protected SSLSocketFactory engineGetSocketFactory() {
        if(!this.isInitialized) {
            throw new IllegalStateException("SSLContextImpl is not initialized");
        } else {
            return new SSLSocketFactoryImpl(this);
        }
    }

    protected SSLServerSocketFactory engineGetServerSocketFactory() {
        if(!this.isInitialized) {
            throw new IllegalStateException("SSLContext is not initialized");
        } else {
            return new SSLServerSocketFactoryImpl(this);
        }
    }

    protected SSLEngine engineCreateSSLEngine() {
        if(!this.isInitialized) {
            throw new IllegalStateException("SSLContextImpl is not initialized");
        } else {
            return new SSLEngineImpl(this);
        }
    }

    protected SSLEngine engineCreateSSLEngine(String var1, int var2) {
        if(!this.isInitialized) {
            throw new IllegalStateException("SSLContextImpl is not initialized");
        } else {
            return new SSLEngineImpl(this, var1, var2);
        }
    }

    protected SSLSessionContext engineGetClientSessionContext() {
        return this.clientCache;
    }

    protected SSLSessionContext engineGetServerSessionContext() {
        return this.serverCache;
    }

    SecureRandom getSecureRandom() {
        return this.secureRandom;
    }

    X509ExtendedKeyManager getX509KeyManager() {
        return this.keyManager;
    }

    X509TrustManager getX509TrustManager() {
        return this.trustManager;
    }

    EphemeralKeyManager getEphemeralKeyManager() {
        return this.ephemeralKeyManager;
    }
}
