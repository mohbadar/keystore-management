package de.adorsys.keymanagement.generator;

import de.adorsys.keymanagement.api.generator.SigningKeyGenerator;
import de.adorsys.keymanagement.api.types.template.generated.Signing;
import de.adorsys.keymanagement.api.types.template.provided.ProvidedKeyPair;
import de.adorsys.keymanagement.deprecated.generator.KeyPairData;
import de.adorsys.keymanagement.deprecated.generator.KeyPairGeneratorImpl;
import de.adorsys.keymanagement.deprecated.generator.V3CertificateUtils;
import de.adorsys.keymanagement.deprecated.types.keystore.ReadKeyPassword;
import lombok.SneakyThrows;
import org.bouncycastle.cert.X509CertificateHolder;

import javax.inject.Inject;
import java.security.KeyPair;

public class DefaultSigningKeyGeneratorImpl implements SigningKeyGenerator {

    @Inject
    public DefaultSigningKeyGeneratorImpl() {
    }

    @Override
    public KeyPair generatePair(Signing fromTemplate) {
        return generateSigning(fromTemplate).getKeyPair().getKeyPair();
    }

    @Override
    public ProvidedKeyPair generate(Signing fromTemplate) {
        KeyPairData data = generateSigning(fromTemplate);
        X509CertificateHolder subjectCert = data.getKeyPair().getSubjectCert();
        return ProvidedKeyPair.builder()
                .keyTemplate(fromTemplate)
                .pair(data.getKeyPair().getKeyPair())
                .certificate(V3CertificateUtils.getX509JavaCertificate(subjectCert))
                .build();
    }

    @SneakyThrows
    private KeyPairData generateSigning(Signing signing) {
        return new KeyPairGeneratorImpl(signing.getAlgo(), signing.getSize(), signing.getSigAlgo(), "PAIR")
                .generateSignatureKey("STUB",
                        new ReadKeyPassword("STUB") // FIXME unneeded
                );
    }
}