package net.tobszarny.ssl.java6.provider;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

public class ProvidersUtil {
    public static void insertProviderAfterProvider(final Provider providerToInsert, String providerNameToInsertAfter,
                                                   String... orOtherProviderNamesToInsertAfter) {
        final int index = getProviderIndex(providerNameToInsertAfter);
        if (index > 0) {
            AccessController.doPrivileged(
                    new PrivilegedAction() {
                        public Void run() {
                            Security.insertProviderAt(providerToInsert, index + 1);
                            return null;
                        }
                    }
            );
        } else {
            if (orOtherProviderNamesToInsertAfter != null && orOtherProviderNamesToInsertAfter.length > 0) {
                insertProviderAfterProvider(providerToInsert, orOtherProviderNamesToInsertAfter[0],
                        Arrays.copyOfRange(orOtherProviderNamesToInsertAfter, 1,
                                orOtherProviderNamesToInsertAfter.length));
            }
        }
    }

    private static int getProviderIndex(final String provider) {
        return (Integer) AccessController.doPrivileged(
                new PrivilegedAction() {
                    public Object run() {
                        final Provider[] providers = Security.getProviders();
                        for (int i = 1; i <= providers.length; i++) {
                            Provider availableProvider = providers[i - 1];
                            if (provider.equals(availableProvider.getName())) {
                                return i;
                            }
                        }
                        return 0;
                    }
                });
    }
}
