//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.security.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

public class ECUtil {
    public static ECPoint decodePoint(byte[] var0, EllipticCurve var1) throws IOException {
        if(var0.length != 0 && var0[0] == 4) {
            int var2 = (var0.length - 1) / 2;
            if(var2 != var1.getField().getFieldSize() + 7 >> 3) {
                throw new IOException("Point does not match field size");
            } else {
                byte[] var3 = Arrays.copyOfRange(var0, 1, 1 + var2);
                byte[] var4 = Arrays.copyOfRange(var0, var2 + 1, var2 + 1 + var2);
                return new ECPoint(new BigInteger(1, var3), new BigInteger(1, var4));
            }
        } else {
            throw new IOException("Only uncompressed point format supported");
        }
    }

    public static byte[] encodePoint(ECPoint var0, EllipticCurve var1) {
        int var2 = var1.getField().getFieldSize() + 7 >> 3;
        byte[] var3 = trimZeroes(var0.getAffineX().toByteArray());
        byte[] var4 = trimZeroes(var0.getAffineY().toByteArray());
        if(var3.length <= var2 && var4.length <= var2) {
            byte[] var5 = new byte[1 + (var2 << 1)];
            var5[0] = 4;
            System.arraycopy(var3, 0, var5, var2 - var3.length + 1, var3.length);
            System.arraycopy(var4, 0, var5, var5.length - var4.length, var4.length);
            return var5;
        } else {
            throw new RuntimeException("Point coordinates do not match field size");
        }
    }

    public static byte[] trimZeroes(byte[] var0) {
        int var1;
        for(var1 = 0; var1 < var0.length - 1 && var0[var1] == 0; ++var1) {
            ;
        }

        return var1 == 0?var0:Arrays.copyOfRange(var0, var1, var0.length);
    }

    private static AlgorithmParameters getECParameters(Provider var0) {
        try {
            return var0 != null?AlgorithmParameters.getInstance("EC", var0):AlgorithmParameters.getInstance("EC");
        } catch (NoSuchAlgorithmException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static byte[] encodeECParameterSpec(Provider var0, ECParameterSpec var1) {
        AlgorithmParameters var2 = getECParameters(var0);

        try {
            var2.init(var1);
        } catch (InvalidParameterSpecException var5) {
            throw new RuntimeException("Not a known named curve: " + var1);
        }

        try {
            return var2.getEncoded();
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static ECParameterSpec getECParameterSpec(Provider var0, ECParameterSpec var1) {
        AlgorithmParameters var2 = getECParameters(var0);

        try {
            var2.init(var1);
            return (ECParameterSpec)var2.getParameterSpec(ECParameterSpec.class);
        } catch (InvalidParameterSpecException var4) {
            return null;
        }
    }

    public static ECParameterSpec getECParameterSpec(Provider var0, byte[] var1) throws IOException {
        AlgorithmParameters var2 = getECParameters(var0);
        var2.init(var1);

        try {
            return (ECParameterSpec)var2.getParameterSpec(ECParameterSpec.class);
        } catch (InvalidParameterSpecException var4) {
            return null;
        }
    }

    public static ECParameterSpec getECParameterSpec(Provider var0, String var1) {
        AlgorithmParameters var2 = getECParameters(var0);

        try {
            var2.init(new ECGenParameterSpec(var1));
            return (ECParameterSpec)var2.getParameterSpec(ECParameterSpec.class);
        } catch (InvalidParameterSpecException var4) {
            return null;
        }
    }

    public static ECParameterSpec getECParameterSpec(Provider var0, int var1) {
        AlgorithmParameters var2 = getECParameters(var0);

        try {
            var2.init(new ECKeySizeParameterSpec(var1));
            return (ECParameterSpec)var2.getParameterSpec(ECParameterSpec.class);
        } catch (InvalidParameterSpecException var4) {
            return null;
        }
    }

    public static String getCurveName(Provider var0, ECParameterSpec var1) {
        AlgorithmParameters var3 = getECParameters(var0);

        ECGenParameterSpec var2;
        try {
            var3.init(var1);
            var2 = (ECGenParameterSpec)var3.getParameterSpec(ECGenParameterSpec.class);
        } catch (InvalidParameterSpecException var5) {
            return null;
        }

        return var2 == null?null:var2.getName();
    }

    private ECUtil() {
    }
}
