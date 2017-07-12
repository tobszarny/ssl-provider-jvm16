import net.tobszarny.ssl.java6.provider.BouncyCastleSSLProvider;
import net.tobszarny.ssl.java6.provider.ProvidersUtil;

import javax.net.ssl.KeyManagerFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {

//        ProvidersUtil.insertProviderAfterProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider(),
//                "BC-JSSE", "SUN");
//        ProvidersUtil.insertProviderAfterProvider(new BouncyCastleSSLProvider(), "SUN");
        for (Provider provider : Security.getProviders()) {
            System.out.println(provider.getName());
        }

//        final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        System.out.println(String.format("##Found %s", kmf.getAlgorithm()));
    }
}
