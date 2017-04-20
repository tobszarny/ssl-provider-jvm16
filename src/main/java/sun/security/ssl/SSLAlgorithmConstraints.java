//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.security.ssl;

import java.security.AlgorithmConstraints;
import java.security.AlgorithmParameters;
import java.security.CryptoPrimitive;
import java.security.Key;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import sun.security.util.DisabledAlgorithmConstraints;

final class SSLAlgorithmConstraints implements AlgorithmConstraints {
    private static final AlgorithmConstraints tlsDisabledAlgConstraints = new DisabledAlgorithmConstraints("jdk.tls.disabledAlgorithms", new SSLAlgorithmDecomposer());
    private static final AlgorithmConstraints x509DisabledAlgConstraints = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms", new SSLAlgorithmDecomposer(true));
    private AlgorithmConstraints userAlgConstraints = null;
    private AlgorithmConstraints peerAlgConstraints = null;
    private boolean enabledX509DisabledAlgConstraints = true;
    static final AlgorithmConstraints DEFAULT = new SSLAlgorithmConstraints((AlgorithmConstraints)null);
    static final AlgorithmConstraints DEFAULT_SSL_ONLY = new SSLAlgorithmConstraints((SSLSocket)null, false);

    SSLAlgorithmConstraints(AlgorithmConstraints var1) {
        this.userAlgConstraints = var1;
    }

    SSLAlgorithmConstraints(SSLSocket var1, boolean var2) {
        if(var1 != null) {
            this.userAlgConstraints = var1.getSSLParameters().getAlgorithmConstraints();
        }

        if(!var2) {
            this.enabledX509DisabledAlgConstraints = false;
        }

    }

    SSLAlgorithmConstraints(SSLEngine var1, boolean var2) {
        if(var1 != null) {
            this.userAlgConstraints = var1.getSSLParameters().getAlgorithmConstraints();
        }

        if(!var2) {
            this.enabledX509DisabledAlgConstraints = false;
        }

    }

    SSLAlgorithmConstraints(SSLSocket var1, String[] var2, boolean var3) {
        if(var1 != null) {
            this.userAlgConstraints = var1.getSSLParameters().getAlgorithmConstraints();
            this.peerAlgConstraints = new SSLAlgorithmConstraints.SupportedSignatureAlgorithmConstraints(var2);
        }

        if(!var3) {
            this.enabledX509DisabledAlgConstraints = false;
        }

    }

    SSLAlgorithmConstraints(SSLEngine var1, String[] var2, boolean var3) {
        if(var1 != null) {
            this.userAlgConstraints = var1.getSSLParameters().getAlgorithmConstraints();
            this.peerAlgConstraints = new SSLAlgorithmConstraints.SupportedSignatureAlgorithmConstraints(var2);
        }

        if(!var3) {
            this.enabledX509DisabledAlgConstraints = false;
        }

    }

    public boolean permits(Set<CryptoPrimitive> var1, String var2, AlgorithmParameters var3) {
        boolean var4 = true;
        if(this.peerAlgConstraints != null) {
            var4 = this.peerAlgConstraints.permits(var1, var2, var3);
        }

        if(var4 && this.userAlgConstraints != null) {
            var4 = this.userAlgConstraints.permits(var1, var2, var3);
        }

        if(var4) {
            var4 = tlsDisabledAlgConstraints.permits(var1, var2, var3);
        }

        if(var4 && this.enabledX509DisabledAlgConstraints) {
            var4 = x509DisabledAlgConstraints.permits(var1, var2, var3);
        }

        return var4;
    }

    public boolean permits(Set<CryptoPrimitive> var1, Key var2) {
        boolean var3 = true;
        if(this.peerAlgConstraints != null) {
            var3 = this.peerAlgConstraints.permits(var1, var2);
        }

        if(var3 && this.userAlgConstraints != null) {
            var3 = this.userAlgConstraints.permits(var1, var2);
        }

        if(var3) {
            var3 = tlsDisabledAlgConstraints.permits(var1, var2);
        }

        if(var3 && this.enabledX509DisabledAlgConstraints) {
            var3 = x509DisabledAlgConstraints.permits(var1, var2);
        }

        return var3;
    }

    public boolean permits(Set<CryptoPrimitive> var1, String var2, Key var3, AlgorithmParameters var4) {
        boolean var5 = true;
        if(this.peerAlgConstraints != null) {
            var5 = this.peerAlgConstraints.permits(var1, var2, var3, var4);
        }

        if(var5 && this.userAlgConstraints != null) {
            var5 = this.userAlgConstraints.permits(var1, var2, var3, var4);
        }

        if(var5) {
            var5 = tlsDisabledAlgConstraints.permits(var1, var2, var3, var4);
        }

        if(var5 && this.enabledX509DisabledAlgConstraints) {
            var5 = x509DisabledAlgConstraints.permits(var1, var2, var3, var4);
        }

        return var5;
    }

    private static class SupportedSignatureAlgorithmConstraints implements AlgorithmConstraints {
        private String[] supportedAlgorithms;

        SupportedSignatureAlgorithmConstraints(String[] var1) {
            if(var1 != null) {
                this.supportedAlgorithms = (String[])var1.clone();
            } else {
                this.supportedAlgorithms = null;
            }

        }

        public boolean permits(Set<CryptoPrimitive> var1, String var2, AlgorithmParameters var3) {
            if(var2 != null && var2.length() != 0) {
                if(var1 != null && !var1.isEmpty()) {
                    if(this.supportedAlgorithms != null && this.supportedAlgorithms.length != 0) {
                        int var4 = var2.indexOf("and");
                        if(var4 > 0) {
                            var2 = var2.substring(0, var4);
                        }

                        String[] var5 = this.supportedAlgorithms;
                        int var6 = var5.length;

                        for(int var7 = 0; var7 < var6; ++var7) {
                            String var8 = var5[var7];
                            if(var2.equalsIgnoreCase(var8)) {
                                return true;
                            }
                        }

                        return false;
                    } else {
                        return false;
                    }
                } else {
                    throw new IllegalArgumentException("No cryptographic primitive specified");
                }
            } else {
                throw new IllegalArgumentException("No algorithm name specified");
            }
        }

        public final boolean permits(Set<CryptoPrimitive> var1, Key var2) {
            return true;
        }

        public final boolean permits(Set<CryptoPrimitive> var1, String var2, Key var3, AlgorithmParameters var4) {
            if(var2 != null && var2.length() != 0) {
                return this.permits(var1, var2, var4);
            } else {
                throw new IllegalArgumentException("No algorithm name specified");
            }
        }
    }
}
