package ssl;

import sun.security.ssl.SSLContextImpl;

public static final class TLS12Context extends SSLContextImpl.AbstractSSLContext {
        private static final SSLParameters defaultClientSSLParams;

        public TLS12Context() {
            super(null);
        }

        SSLParameters getDefaultClientSSLParams() {
            return defaultClientSSLParams;
        }

        static {
            ProtocolVersion[] var0;
            if(SunJSSE.isFIPS()) {
                var0 = new ProtocolVersion[]{ProtocolVersion.TLS10, ProtocolVersion.TLS11, ProtocolVersion.TLS12};
            } else {
                var0 = new ProtocolVersion[]{ProtocolVersion.SSL30, ProtocolVersion.TLS10, ProtocolVersion.TLS11, ProtocolVersion.TLS12};
            }

            defaultClientSSLParams = new SSLParameters();
            defaultClientSSLParams.setProtocols((String[])getAvailableProtocols(var0).toArray(new String[0]));
        }
    }