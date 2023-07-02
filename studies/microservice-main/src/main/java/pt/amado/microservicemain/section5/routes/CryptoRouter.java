package pt.amado.microservicemain.section5.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "5",
        matchIfMissing = true
)
public class CryptoRouter extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        from("timer:crypto-route?period=5000")
                .routeId("Crypto-route")
                .transform().constant("Default secret value encrypted.")
                .marshal(createEncryptor())
                .log("Message sent from crypto-route")
                .to("activemq:crypto-route-secret-message");

    }

    private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        ClassLoader classLoader = getClass().getClassLoader();
        keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
        Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

        CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
        return sharedKeyCrypto;
    }
}
