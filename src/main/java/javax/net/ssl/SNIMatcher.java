//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package javax.net.ssl;

public abstract class SNIMatcher {
    private final int type;

    protected SNIMatcher(int var1) {
        if(var1 < 0) {
            throw new IllegalArgumentException("Server name type cannot be less than zero");
        } else if(var1 > 255) {
            throw new IllegalArgumentException("Server name type cannot be greater than 255");
        } else {
            this.type = var1;
        }
    }

    public final int getType() {
        return this.type;
    }

    public abstract boolean matches(SNIServerName var1);
}
