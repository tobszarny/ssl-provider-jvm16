//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package javax.net.ssl;

import java.util.Arrays;

public abstract class SNIServerName {
    private final int type;
    private final byte[] encoded;
    private static final char[] HEXES = "0123456789ABCDEF".toCharArray();

    protected SNIServerName(int var1, byte[] var2) {
        if(var1 < 0) {
            throw new IllegalArgumentException("Server name type cannot be less than zero");
        } else if(var1 > 255) {
            throw new IllegalArgumentException("Server name type cannot be greater than 255");
        } else {
            this.type = var1;
            if(var2 == null) {
                throw new NullPointerException("Server name encoded value cannot be null");
            } else {
                this.encoded = (byte[])var2.clone();
            }
        }
    }

    public final int getType() {
        return this.type;
    }

    public final byte[] getEncoded() {
        return (byte[])this.encoded.clone();
    }

    public boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(this.getClass() != var1.getClass()) {
            return false;
        } else {
            SNIServerName var2 = (SNIServerName)var1;
            return this.type == var2.type && Arrays.equals(this.encoded, var2.encoded);
        }
    }

    public int hashCode() {
        byte var1 = 17;
        int var2 = 31 * var1 + this.type;
        var2 = 31 * var2 + Arrays.hashCode(this.encoded);
        return var2;
    }

    public String toString() {
        return this.type == 0?"type=host_name (0), value=" + toHexString(this.encoded):"type=(" + this.type + "), value=" + toHexString(this.encoded);
    }

    private static String toHexString(byte[] var0) {
        if(var0.length == 0) {
            return "(empty)";
        } else {
            StringBuilder var1 = new StringBuilder(var0.length * 3 - 1);
            boolean var2 = true;
            byte[] var3 = var0;
            int var4 = var0.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                byte var6 = var3[var5];
                if(var2) {
                    var2 = false;
                } else {
                    var1.append(':');
                }

                int var7 = var6 & 255;
                var1.append(HEXES[var7 >>> 4]);
                var1.append(HEXES[var7 & 15]);
            }

            return var1.toString();
        }
    }
}
