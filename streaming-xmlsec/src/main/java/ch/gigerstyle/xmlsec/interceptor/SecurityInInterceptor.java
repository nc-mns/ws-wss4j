/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.gigerstyle.xmlsec.interceptor;

import ch.gigerstyle.xmlsec.XMLSec;
import ch.gigerstyle.xmlsec.ext.Constants;
import ch.gigerstyle.xmlsec.ext.InboundXMLSec;
import ch.gigerstyle.xmlsec.ext.SecurityProperties;
import ch.gigerstyle.xmlsec.ext.XMLSecurityException;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInInterceptor;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class SecurityInInterceptor extends AbstractSoapInterceptor {

    private static final Set<QName> HEADERS = new HashSet<QName>();

    static {
        HEADERS.add(Constants.TAG_wsse_Security);
        HEADERS.add(Constants.TAG_xenc_EncryptedData);
    }

    private InboundXMLSec inboundXMLSec;

    public SecurityInInterceptor(String p, SecurityProperties securityProperties) throws Exception {
        super(p);
        getAfter().add(StaxInInterceptor.class.getName());

        inboundXMLSec = XMLSec.getInboundXMLSec(securityProperties);
    }

    public void handleMessage(SoapMessage soapMessage) throws Fault {

        XMLStreamReader originalXmlStreamReader = soapMessage.getContent(XMLStreamReader.class);
        XMLStreamReader newXmlStreamReader = null;

        try {
            newXmlStreamReader = inboundXMLSec.processInMessage(originalXmlStreamReader);
            soapMessage.setContent(XMLStreamReader.class, newXmlStreamReader);
            //todo correct faults per WSS-spec
        } catch (XMLSecurityException e) {
            throw new SoapFault("Invalid security", soapMessage.getVersion().getSender());
        } catch (XMLStreamException e) {
            throw new SoapFault("Invalid security", soapMessage.getVersion().getReceiver());
        }
    }

    public Set<QName> getUnderstoodHeaders() {
        return HEADERS;
    }
}
