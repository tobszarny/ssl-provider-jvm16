//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package javax.net.ssl;

import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SSLParameters8 {
    private String[] cipherSuites;
    private String[] protocols;
    private boolean wantClientAuth;
    private boolean needClientAuth;
    private String identificationAlgorithm;
    private AlgorithmConstraints algorithmConstraints;
    private Map<Integer, SNIServerName> sniNames = null;
    private Map<Integer, SNIMatcher> sniMatchers = null;
    private boolean preferLocalCipherSuites;

    public SSLParameters8() {
    }

    public SSLParameters8(String[] var1) {
        this.setCipherSuites(var1);
    }

    public SSLParameters8(String[] var1, String[] var2) {
        this.setCipherSuites(var1);
        this.setProtocols(var2);
    }

    private static String[] clone(String[] var0) {
        return var0 == null?null:(String[])var0.clone();
    }

    public String[] getCipherSuites() {
        return clone(this.cipherSuites);
    }

    public void setCipherSuites(String[] var1) {
        this.cipherSuites = clone(var1);
    }

    public String[] getProtocols() {
        return clone(this.protocols);
    }

    public void setProtocols(String[] var1) {
        this.protocols = clone(var1);
    }

    public boolean getWantClientAuth() {
        return this.wantClientAuth;
    }

    public void setWantClientAuth(boolean var1) {
        this.wantClientAuth = var1;
        this.needClientAuth = false;
    }

    public boolean getNeedClientAuth() {
        return this.needClientAuth;
    }

    public void setNeedClientAuth(boolean var1) {
        this.wantClientAuth = false;
        this.needClientAuth = var1;
    }

    public AlgorithmConstraints getAlgorithmConstraints() {
        return this.algorithmConstraints;
    }

    public void setAlgorithmConstraints(AlgorithmConstraints var1) {
        this.algorithmConstraints = var1;
    }

    public String getEndpointIdentificationAlgorithm() {
        return this.identificationAlgorithm;
    }

    public void setEndpointIdentificationAlgorithm(String var1) {
        this.identificationAlgorithm = var1;
    }

    public final void setServerNames(List<SNIServerName> var1) {
        if(var1 != null) {
            if(!var1.isEmpty()) {
                this.sniNames = new LinkedHashMap(var1.size());
                Iterator var2 = var1.iterator();

                while(var2.hasNext()) {
                    SNIServerName var3 = (SNIServerName)var2.next();
                    if(this.sniNames.put(Integer.valueOf(var3.getType()), var3) != null) {
                        throw new IllegalArgumentException("Duplicated server name of type " + var3.getType());
                    }
                }
            } else {
                this.sniNames = Collections.emptyMap();
            }
        } else {
            this.sniNames = null;
        }

    }

    public final List<SNIServerName> getServerNames() {
        return this.sniNames != null?(!this.sniNames.isEmpty()?Collections.unmodifiableList(new ArrayList(this.sniNames.values())):Collections.emptyList()):null;
    }

    public final void setSNIMatchers(Collection<SNIMatcher> var1) {
        if(var1 != null) {
            if(!var1.isEmpty()) {
                this.sniMatchers = new HashMap(var1.size());
                Iterator var2 = var1.iterator();

                while(var2.hasNext()) {
                    SNIMatcher var3 = (SNIMatcher)var2.next();
                    if(this.sniMatchers.put(Integer.valueOf(var3.getType()), var3) != null) {
                        throw new IllegalArgumentException("Duplicated server name of type " + var3.getType());
                    }
                }
            } else {
                this.sniMatchers = Collections.emptyMap();
            }
        } else {
            this.sniMatchers = null;
        }

    }

    public final Collection<SNIMatcher> getSNIMatchers() {
        return this.sniMatchers != null?(!this.sniMatchers.isEmpty()?Collections.unmodifiableList(new ArrayList(this.sniMatchers.values())):Collections.emptyList()):null;
    }

    public final void setUseCipherSuitesOrder(boolean var1) {
        this.preferLocalCipherSuites = var1;
    }

    public final boolean getUseCipherSuitesOrder() {
        return this.preferLocalCipherSuites;
    }
}
