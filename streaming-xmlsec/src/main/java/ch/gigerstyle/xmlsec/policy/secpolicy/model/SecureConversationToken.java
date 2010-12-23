/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.gigerstyle.xmlsec.policy.secpolicy.model;

import ch.gigerstyle.xmlsec.policy.secpolicy.SPConstants;
import ch.gigerstyle.xmlsec.securityEvent.SecurityEvent;
import org.apache.axiom.om.OMElement;
import org.apache.neethi.Policy;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Model class of SecureConversationToken assertion
 */

/**
 * class lent from apache rampart
 */
public class SecureConversationToken extends SecurityContextToken {

    private Policy bootstrapPolicy;

    private OMElement issuerEpr;

    public SecureConversationToken(SPConstants spConstants) {
        super(spConstants);
    }

    /**
     * @return Returns the bootstrapPolicy.
     */
    public Policy getBootstrapPolicy() {
        return bootstrapPolicy;
    }

    /**
     * @param bootstrapPolicy The bootstrapPolicy to set.
     */
    public void setBootstrapPolicy(Policy bootstrapPolicy) {
        this.bootstrapPolicy = bootstrapPolicy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.neethi.Assertion#getName()
     */

    public QName getName() {
        return spConstants.getSecureConversationToken();
    }

    public void serialize(XMLStreamWriter writer) throws XMLStreamException {

        String localname = getName().getLocalPart();
        String namespaceURI = getName().getNamespaceURI();
        String prefix;

        String writerPrefix = writer.getPrefix(namespaceURI);

        if (writerPrefix == null) {
            prefix = getName().getPrefix();
            writer.setPrefix(prefix, namespaceURI);
        } else {
            prefix = writerPrefix;
        }

        // <sp:SecureConversationToken>
        writer.writeStartElement(prefix, localname, namespaceURI);

        if (writerPrefix == null) {
            // xmlns:sp=".."
            writer.writeNamespace(prefix, namespaceURI);
        }

        String inclusion = spConstants.getAttributeValueFromInclusion(getInclusion());

        if (inclusion != null) {
            writer.writeAttribute(prefix, namespaceURI, SPConstants.ATTR_INCLUDE_TOKEN, inclusion);
        }

        if (issuerEpr != null) {
            // <sp:Issuer>
            writer.writeStartElement(prefix, SPConstants.ISSUER, namespaceURI);

            issuerEpr.serialize(writer);

            writer.writeEndElement();
        }

        if (isDerivedKeys() || isRequireExternalUriRef()
                || isSc10SecurityContextToken() || (bootstrapPolicy != null)) {

            String wspNamespaceURI = SPConstants.POLICY.getNamespaceURI();

            String wspPrefix;

            String wspWriterPrefix = writer.getPrefix(wspNamespaceURI);

            if (wspWriterPrefix == null) {
                wspPrefix = SPConstants.POLICY.getPrefix();
                writer.setPrefix(wspPrefix, wspNamespaceURI);

            } else {
                wspPrefix = wspWriterPrefix;
            }

            // <wsp:Policy>
            writer.writeStartElement(wspPrefix,
                    SPConstants.POLICY.getLocalPart(), wspNamespaceURI);

            if (wspWriterPrefix == null) {
                // xmlns:wsp=".."
                writer.writeNamespace(wspPrefix, wspNamespaceURI);
            }

            if (isDerivedKeys()) {
                // <sp:RequireDerivedKeys />
                writer.writeEmptyElement(prefix, SPConstants.REQUIRE_DERIVED_KEYS, namespaceURI);
            }

            if (isRequireExternalUriRef()) {
                // <sp:RequireExternalUriReference />
                writer.writeEmptyElement(prefix, SPConstants.REQUIRE_EXTERNAL_URI_REFERNCE, namespaceURI);
            }

            if (isSc10SecurityContextToken()) {
                // <sp:SC10SecurityContextToken />
                writer.writeEmptyElement(prefix, SPConstants.SC10_SECURITY_CONTEXT_TOKEN, namespaceURI);
            }

            if (bootstrapPolicy != null) {
                // <sp:BootstrapPolicy ..>
                writer.writeStartElement(prefix, SPConstants.BOOTSTRAP_POLICY, namespaceURI);
                bootstrapPolicy.serialize(writer);
                writer.writeEndElement();
            }

            // </wsp:Policy>
            writer.writeEndElement();
        }

        // </sp:SecureConversationToken>
        writer.writeEndElement();
    }

    /**
     * @return Returns the issuerEpr.
     */
    public OMElement getIssuerEpr() {
        return issuerEpr;
    }

    /**
     * @param issuerEpr The issuerEpr to set.
     */
    public void setIssuerEpr(OMElement issuerEpr) {
        this.issuerEpr = issuerEpr;
    }

    @Override
    public SecurityEvent.Event[] getResponsibleAssertionEvents() {
        //todo
        return new SecurityEvent.Event[0];
    }

    /*
    @Override
    public void assertPolicy(SecurityEvent securityEvent) {
    }
    */
}
