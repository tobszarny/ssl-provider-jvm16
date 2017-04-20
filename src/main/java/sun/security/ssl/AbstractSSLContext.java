package sun.security.ssl;

import javax.net.ssl.SSLParameters8;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSSLContext extends SSLContextImpl {
        private static final SSLParameters8 defaultServerSSLParams;
        private static final SSLParameters8 supportedSSLParams = new SSLParameters8();

        private AbstractSSLContext() {
        }

        SSLParameters8 getDefaultServerSSLParams() {
            return defaultServerSSLParams;
        }

        SSLParameters8 getSupportedSSLParams() {
            return supportedSSLParams;
        }

        static List<String> getAvailableProtocols(ProtocolVersion[] var0) {
            Object var1 = Collections.emptyList();
            if(var0 != null && var0.length != 0) {
                var1 = new ArrayList(var0.length);
                ProtocolVersion[] var2 = var0;
                int var3 = var0.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    ProtocolVersion var5 = var2[var4];
                    if(ProtocolVersion.availableProtocols.contains(var5)) {
                        ((List)var1).add(var5.name);
                    }
                }
            }

            return (List)var1;
        }

        static {
            ProtocolVersion[] var0;
            if(SunJSSE.isFIPS()) {
                supportedSSLParams.setProtocols(new String[]{ProtocolVersion.TLS10.name, ProtocolVersion.TLS11.name, ProtocolVersion.TLS12.name});
                var0 = new ProtocolVersion[]{ProtocolVersion.TLS10, ProtocolVersion.TLS11, ProtocolVersion.TLS12};
            } else {
                supportedSSLParams.setProtocols(new String[]{ProtocolVersion.SSL20Hello.name, ProtocolVersion.SSL30.name, ProtocolVersion.TLS10.name, ProtocolVersion.TLS11.name, ProtocolVersion.TLS12.name});
                var0 = new ProtocolVersion[]{ProtocolVersion.SSL20Hello, ProtocolVersion.SSL30, ProtocolVersion.TLS10, ProtocolVersion.TLS11, ProtocolVersion.TLS12};
            }

            defaultServerSSLParams = new SSLParameters8();
            defaultServerSSLParams.setProtocols((String[])getAvailableProtocols(var0).toArray(new String[0]));
        }
    }