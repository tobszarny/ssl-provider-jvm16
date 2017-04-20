//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.security.ssl.internal.ssl;

import sun.security.ssl.SunJSSE;

public final class Provider extends SunJSSE {
    private static final long serialVersionUID = 3231825739635378733L;

    public Provider() {
    }

    public Provider(java.security.Provider var1) {
        super(var1);
    }

    public Provider(String var1) {
        super(var1);
    }

    public static synchronized boolean isFIPS() {
        return SunJSSE.isFIPS();
    }

    public static synchronized void install() {
    }
}
