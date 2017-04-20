//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.security.ssl;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.Provider.Service;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.security.spec.RSAPublicKeySpec;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import sun.security.jca.ProviderList;
import sun.security.jca.Providers;
import sun.security.util.ECUtil;

final class JsseJce {
    private static final ProviderList fipsProviderList;
    private static Boolean ecAvailable;
    private static final boolean kerberosAvailable;
    static final String CIPHER_RSA_PKCS1 = "RSA/ECB/PKCS1Padding";
    static final String CIPHER_RC4 = "RC4";
    static final String CIPHER_DES = "DES/CBC/NoPadding";
    static final String CIPHER_3DES = "DESede/CBC/NoPadding";
    static final String CIPHER_AES = "AES/CBC/NoPadding";
    static final String CIPHER_AES_GCM = "AES/GCM/NoPadding";
    static final String SIGNATURE_DSA = "DSA";
    static final String SIGNATURE_ECDSA = "SHA1withECDSA";
    static final String SIGNATURE_RAWDSA = "RawDSA";
    static final String SIGNATURE_RAWECDSA = "NONEwithECDSA";
    static final String SIGNATURE_RAWRSA = "NONEwithRSA";
    static final String SIGNATURE_SSLRSA = "MD5andSHA1withRSA";

    private JsseJce() {
    }

    static synchronized boolean isEcAvailable() {
        if(ecAvailable == null) {
            try {
                getSignature("SHA1withECDSA");
                getSignature("NONEwithECDSA");
                getKeyAgreement("ECDH");
                getKeyFactory("EC");
                getKeyPairGenerator("EC");
                ecAvailable = Boolean.valueOf(true);
            } catch (Exception var1) {
                ecAvailable = Boolean.valueOf(false);
            }
        }

        return ecAvailable.booleanValue();
    }

    static synchronized void clearEcAvailable() {
        ecAvailable = null;
    }

    static boolean isKerberosAvailable() {
        return kerberosAvailable;
    }

    static Cipher getCipher(String var0) throws NoSuchAlgorithmException {
        try {
            return SunJSSE.cryptoProvider == null?Cipher.getInstance(var0):Cipher.getInstance(var0, SunJSSE.cryptoProvider);
        } catch (NoSuchPaddingException var2) {
            throw new NoSuchAlgorithmException(var2);
        }
    }

    static Signature getSignature(String var0) throws NoSuchAlgorithmException {
        if(SunJSSE.cryptoProvider == null) {
            return Signature.getInstance(var0);
        } else if(var0 == "MD5andSHA1withRSA" && SunJSSE.cryptoProvider.getService("Signature", var0) == null) {
            try {
                return Signature.getInstance(var0, "SunJSSE");
            } catch (NoSuchProviderException var2) {
                throw new NoSuchAlgorithmException(var2);
            }
        } else {
            return Signature.getInstance(var0, SunJSSE.cryptoProvider);
        }
    }

    static KeyGenerator getKeyGenerator(String var0) throws NoSuchAlgorithmException {
        return SunJSSE.cryptoProvider == null?KeyGenerator.getInstance(var0):KeyGenerator.getInstance(var0, SunJSSE.cryptoProvider);
    }

    static KeyPairGenerator getKeyPairGenerator(String var0) throws NoSuchAlgorithmException {
        return SunJSSE.cryptoProvider == null?KeyPairGenerator.getInstance(var0):KeyPairGenerator.getInstance(var0, SunJSSE.cryptoProvider);
    }

    static KeyAgreement getKeyAgreement(String var0) throws NoSuchAlgorithmException {
        return SunJSSE.cryptoProvider == null?KeyAgreement.getInstance(var0):KeyAgreement.getInstance(var0, SunJSSE.cryptoProvider);
    }

    static Mac getMac(String var0) throws NoSuchAlgorithmException {
        return SunJSSE.cryptoProvider == null?Mac.getInstance(var0):Mac.getInstance(var0, SunJSSE.cryptoProvider);
    }

    static KeyFactory getKeyFactory(String var0) throws NoSuchAlgorithmException {
        return SunJSSE.cryptoProvider == null?KeyFactory.getInstance(var0):KeyFactory.getInstance(var0, SunJSSE.cryptoProvider);
    }

    static SecureRandom getSecureRandom() throws KeyManagementException {
        if(SunJSSE.cryptoProvider == null) {
            return new SecureRandom();
        } else {
            try {
                return SecureRandom.getInstance("PKCS11", SunJSSE.cryptoProvider);
            } catch (NoSuchAlgorithmException var4) {
                Iterator var0 = SunJSSE.cryptoProvider.getServices().iterator();

                while(true) {
                    Service var1;
                    do {
                        if(!var0.hasNext()) {
                            throw new KeyManagementException("FIPS mode: no SecureRandom  implementation found in provider " + SunJSSE.cryptoProvider.getName());
                        }

                        var1 = (Service)var0.next();
                    } while(!var1.getType().equals("SecureRandom"));

                    try {
                        return SecureRandom.getInstance(var1.getAlgorithm(), SunJSSE.cryptoProvider);
                    } catch (NoSuchAlgorithmException var3) {
                        ;
                    }
                }
            }
        }
    }

    static MessageDigest getMD5() {
        return getMessageDigest("MD5");
    }

    static MessageDigest getSHA() {
        return getMessageDigest("SHA");
    }

    static MessageDigest getMessageDigest(String var0) {
        try {
            return SunJSSE.cryptoProvider == null?MessageDigest.getInstance(var0):MessageDigest.getInstance(var0, SunJSSE.cryptoProvider);
        } catch (NoSuchAlgorithmException var2) {
            throw new RuntimeException("Algorithm " + var0 + " not available", var2);
        }
    }

    static int getRSAKeyLength(PublicKey var0) {
        BigInteger var1;
        if(var0 instanceof RSAPublicKey) {
            var1 = ((RSAPublicKey)var0).getModulus();
        } else {
            RSAPublicKeySpec var2 = getRSAPublicKeySpec(var0);
            var1 = var2.getModulus();
        }

        return var1.bitLength();
    }

    static RSAPublicKeySpec getRSAPublicKeySpec(PublicKey var0) {
        if(var0 instanceof RSAPublicKey) {
            RSAPublicKey var3 = (RSAPublicKey)var0;
            return new RSAPublicKeySpec(var3.getModulus(), var3.getPublicExponent());
        } else {
            try {
                KeyFactory var1 = getKeyFactory("RSA");
                return (RSAPublicKeySpec)var1.getKeySpec(var0, RSAPublicKeySpec.class);
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    static ECParameterSpec getECParameterSpec(String var0) {
        return ECUtil.getECParameterSpec(SunJSSE.cryptoProvider, var0);
    }

    static String getNamedCurveOid(ECParameterSpec var0) {
        return ECUtil.getCurveName(SunJSSE.cryptoProvider, var0);
    }

    static ECPoint decodePoint(byte[] var0, EllipticCurve var1) throws IOException {
        return ECUtil.decodePoint(var0, var1);
    }

    static byte[] encodePoint(ECPoint var0, EllipticCurve var1) {
        return ECUtil.encodePoint(var0, var1);
    }

    static Object beginFipsProvider() {
        return fipsProviderList == null?null:Providers.beginThreadProviderList(fipsProviderList);
    }

    static void endFipsProvider(Object var0) {
        if(fipsProviderList != null) {
            Providers.endThreadProviderList((ProviderList)var0);
        }

    }

    static {
        boolean var0;
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                    Class.forName("sun.security.krb5.PrincipalName", true, (ClassLoader)null);
                    return null;
                }
            });
            var0 = true;
        } catch (Exception var2) {
            var0 = false;
        }

        kerberosAvailable = var0;
        if(!SunJSSE.isFIPS()) {
            fipsProviderList = null;
        } else {
            Provider var3 = Security.getProvider("SUN");
            if(var3 == null) {
                throw new RuntimeException("FIPS mode: SUN provider must be installed");
            }

            JsseJce.SunCertificates var1 = new JsseJce.SunCertificates(var3);
            fipsProviderList = ProviderList.newList(new Provider[]{SunJSSE.cryptoProvider, var1});
        }

    }

    private static final class SunCertificates extends Provider {
        private static final long serialVersionUID = -3284138292032213752L;

        SunCertificates(final Provider var1) {
            super("SunCertificates", 1.8D, "SunJSSE internal");
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    Iterator var1x = var1.entrySet().iterator();

                    while(true) {
                        Entry var2;
                        String var3;
                        do {
                            if(!var1x.hasNext()) {
                                return null;
                            }

                            var2 = (Entry)var1x.next();
                            var3 = (String)var2.getKey();
                        } while(!var3.startsWith("CertPathValidator.") && !var3.startsWith("CertPathBuilder.") && !var3.startsWith("CertStore.") && !var3.startsWith("CertificateFactory."));

                        SunCertificates.this.put(var3, var2.getValue());
                    }
                }
            });
        }
    }
}
