//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.security.util;

import java.security.spec.AlgorithmParameterSpec;

public class ECKeySizeParameterSpec implements AlgorithmParameterSpec {
    private int keySize;

    public ECKeySizeParameterSpec(int var1) {
        this.keySize = var1;
    }

    public int getKeySize() {
        return this.keySize;
    }
}
