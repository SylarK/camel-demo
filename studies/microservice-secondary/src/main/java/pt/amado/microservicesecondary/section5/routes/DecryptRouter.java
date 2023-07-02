package pt.amado.microservicesecondary.section5.routes;

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
public class DecryptRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:crypto-route-secret-message")
                .routeId("Crypto-route-receiver")
                .unmarshal(createEncryptor())
                .to("log:crypto-route-message-received");
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
