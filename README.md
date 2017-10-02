# ssl-provider-jvm16

Bouncy Castle based SSL provider for JVM 1.6. Mainly to fill the gaps for SSL connectivity with SQLServer over JDBC.

** ABSOLETE IF YOU CAN UPGRADE BOUNCY CASTLE TO VERSION 1.58+ **

Solves problems with encoding strength, which is below SQLServer required on JVM 1.6.
Solves problem with message error "Unsupported curveId: XX"

Edit _jdk1.6.0_XX\jre\lib\security\java.security_

`security.provider.1=sun.security.provider.Sun`
**`security.provider.2=net.tobszarny.ssl.java6.provider.BouncyCastleSSLProvider
security.provider.3=org.bouncycastle.jce.provider.BouncyCastleProvider`**
`security.provider.4=sun.security.rsa.SunRsaSign
security.provider.5=com.sun.net.ssl.internal.ssl.Provider
security.provider.6=com.sun.crypto.provider.SunJCE
security.provider.7=sun.security.jgss.SunProvider
security.provider.8=com.sun.security.sasl.Provider
security.provider.9=org.jcp.xml.dsig.internal.dom.XMLDSigRI
security.provider.10=sun.security.smartcardio.SunPCSC
security.provider.11=sun.security.mscapi.SunMSCAPI`

Add
_bcprov-jdk15on-1.52.jar_
_ssl-provider-jvm16-0.2.jar_

to _jdk1.6.0_XX\jre\lib\ext_
