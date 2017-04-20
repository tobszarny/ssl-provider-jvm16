//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.security.ssl;

import java.security.AlgorithmParameters;
import java.security.CryptoPrimitive;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public final class ProtocolVersion implements Comparable<ProtocolVersion> {
    static final int LIMIT_MAX_VALUE = 65535;
    static final int LIMIT_MIN_VALUE = 0;
    static final ProtocolVersion NONE = new ProtocolVersion(-1, "NONE");
    static final ProtocolVersion SSL20Hello = new ProtocolVersion(2, "SSLv2Hello");
    static final ProtocolVersion SSL30 = new ProtocolVersion(768, "SSLv3");
    static final ProtocolVersion TLS10 = new ProtocolVersion(769, "TLSv1");
    static final ProtocolVersion TLS11 = new ProtocolVersion(770, "TLSv1.1");
    static final ProtocolVersion TLS12 = new ProtocolVersion(771, "TLSv1.2");
    private static final boolean FIPS = SunJSSE.isFIPS();
    static final ProtocolVersion MIN;
    static final ProtocolVersion MAX;
    static final ProtocolVersion DEFAULT;
    static final ProtocolVersion DEFAULT_HELLO;
    static final Set<ProtocolVersion> availableProtocols;
    public final int v;
    public final byte major;
    public final byte minor;
    final String name;

    private ProtocolVersion(int var1, String var2) {
        this.v = var1;
        this.name = var2;
        this.major = (byte)(var1 >>> 8);
        this.minor = (byte)(var1 & 255);
    }

    private static ProtocolVersion valueOf(int var0) {
        if(var0 == SSL30.v) {
            return SSL30;
        } else if(var0 == TLS10.v) {
            return TLS10;
        } else if(var0 == TLS11.v) {
            return TLS11;
        } else if(var0 == TLS12.v) {
            return TLS12;
        } else if(var0 == SSL20Hello.v) {
            return SSL20Hello;
        } else {
            int var1 = var0 >>> 8 & 255;
            int var2 = var0 & 255;
            return new ProtocolVersion(var0, "Unknown-" + var1 + "." + var2);
        }
    }

    public static ProtocolVersion valueOf(int var0, int var1) {
        return valueOf((var0 & 255) << 8 | var1 & 255);
    }

    static ProtocolVersion valueOf(String var0) {
        if(var0 == null) {
            throw new IllegalArgumentException("Protocol cannot be null");
        } else if(!FIPS || !var0.equals(SSL30.name) && !var0.equals(SSL20Hello.name)) {
            if(var0.equals(SSL30.name)) {
                return SSL30;
            } else if(var0.equals(TLS10.name)) {
                return TLS10;
            } else if(var0.equals(TLS11.name)) {
                return TLS11;
            } else if(var0.equals(TLS12.name)) {
                return TLS12;
            } else if(var0.equals(SSL20Hello.name)) {
                return SSL20Hello;
            } else {
                throw new IllegalArgumentException(var0);
            }
        } else {
            throw new IllegalArgumentException("Only TLS 1.0 or later allowed in FIPS mode");
        }
    }

    public String toString() {
        return this.name;
    }

    public int compareTo(ProtocolVersion var1) {
        return this.v - var1.v;
    }

    static {
        MIN = FIPS?TLS10:SSL30;
        MAX = TLS12;
        DEFAULT = TLS12;
        DEFAULT_HELLO = FIPS?TLS10:SSL30;
        HashSet var0 = new HashSet(5);
        ProtocolVersion[] var1 = new ProtocolVersion[]{SSL20Hello, SSL30, TLS10, TLS11, TLS12};
        ProtocolVersion[] var2 = var1;
        int var3 = var1.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            ProtocolVersion var5 = var2[var4];
            if(SSLAlgorithmConstraints.DEFAULT_SSL_ONLY.permits(EnumSet.of(CryptoPrimitive.KEY_AGREEMENT), var5.name, (AlgorithmParameters)null)) {
                var0.add(var5);
            }
        }

        availableProtocols = Collections.unmodifiableSet(var0);
    }
}
