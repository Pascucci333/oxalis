/*
 * Copyright 2010-2017 Norwegian Agency for Public Management and eGovernment (Difi)
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.peppol.util;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import eu.peppol.identifier.AccessPointIdentifier;
import eu.peppol.security.KeystoreLoader;
import eu.peppol.security.KeystoreManager;
import eu.peppol.security.KeystoreManagerImpl;
import eu.peppol.security.PeppolKeystoreLoader;
import no.difi.oxalis.commons.security.CertificateUtils;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * @author steinar
 *         Date: 09.12.2015
 *         Time: 15.02
 * @author erlend
 */
public class OxalisKeystoreModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(KeystoreLoader.class).to(PeppolKeystoreLoader.class).in(Singleton.class);
        bind(KeystoreManager.class).to(KeystoreManagerImpl.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    protected AccessPointIdentifier provideOurAccessPointIdentifier(X509Certificate certificate) {
        return new AccessPointIdentifier(CertificateUtils.extractCommonName(certificate));
    }

    @Provides
    @Singleton
    protected X509Certificate provideCertificate(KeystoreManager keystoreManager) {
        return keystoreManager.getOurCertificate();
    }

    @Provides
    @Singleton
    protected PrivateKey providePrivateKey(KeystoreManager keystoreManager) {
        return keystoreManager.getOurPrivateKey();
    }

    @Provides
    @Singleton
    protected KeyStore.PrivateKeyEntry providePrivateKeyEntry(KeystoreManager keystoreManager) {
        return new KeyStore.PrivateKeyEntry(
                keystoreManager.getOurPrivateKey(),
                new Certificate[]{keystoreManager.getOurCertificate()}
        );
    }
}
