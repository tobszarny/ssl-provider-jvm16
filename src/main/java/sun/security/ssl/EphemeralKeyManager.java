//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.security.ssl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

final class EphemeralKeyManager {
    private static final int INDEX_RSA512 = 0;
    private static final int INDEX_RSA1024 = 1;
    private final EphemeralKeyManager.EphemeralKeyPair[] keys = new EphemeralKeyManager.EphemeralKeyPair[]{new EphemeralKeyManager.EphemeralKeyPair((KeyPair)null), new EphemeralKeyManager.EphemeralKeyPair((KeyPair)null)};

    EphemeralKeyManager() {
    }

    KeyPair getRSAKeyPair(boolean var1, SecureRandom var2) {
        short var3;
        byte var4;
        if(var1) {
            var3 = 512;
            var4 = 0;
        } else {
            var3 = 1024;
            var4 = 1;
        }

        EphemeralKeyManager.EphemeralKeyPair[] var5 = this.keys;
        synchronized(this.keys) {
            KeyPair var6 = this.keys[var4].getKeyPair();
            if(var6 == null) {
                try {
                    KeyPairGenerator var7 = JsseJce.getKeyPairGenerator("RSA");
                    var7.initialize(var3, var2);
                    this.keys[var4] = new EphemeralKeyManager.EphemeralKeyPair(var7.genKeyPair());
                    var6 = this.keys[var4].getKeyPair();
                } catch (Exception var9) {
                    ;
                }
            }

            return var6;
        }
    }

    private static class EphemeralKeyPair {
        private static final int MAX_USE = 200;
        private static final long USE_INTERVAL = 3600000L;
        private KeyPair keyPair;
        private int uses;
        private long expirationTime;

        private EphemeralKeyPair(KeyPair var1) {
            this.keyPair = var1;
            this.expirationTime = System.currentTimeMillis() + 3600000L;
        }

        private boolean isValid() {
            return this.keyPair != null && this.uses < 200 && System.currentTimeMillis() < this.expirationTime;
        }

        private KeyPair getKeyPair() {
            if(!this.isValid()) {
                this.keyPair = null;
                return null;
            } else {
                ++this.uses;
                return this.keyPair;
            }
        }
    }
}
