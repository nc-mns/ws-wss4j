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
package ch.gigerstyle.xmlsec.ext;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;

/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class Utils {

    private Utils() {
    }

    public static String dropReferenceMarker(String reference) {
        if (reference.startsWith("#")) {
            return reference.substring(1);
        }
        return reference;
    }

    public static String getXMLEventAsString(XMLEvent xmlEvent) {
        int eventType = xmlEvent.getEventType();

        switch (eventType) {
            case XMLEvent.START_ELEMENT:
                return "START_ELEMENT";
            case XMLEvent.END_ELEMENT:
                return "END_ELEMENT";
            case XMLEvent.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";
            case XMLEvent.CHARACTERS:
                return "CHARACTERS";
            case XMLEvent.COMMENT:
                return "COMMENT";
            case XMLEvent.START_DOCUMENT:
                return "START_DOCUMENT";
            case XMLEvent.END_DOCUMENT:
                return "END_DOCUMENT";
            case XMLEvent.ATTRIBUTE:
                return "ATTRIBUTE";
            case XMLEvent.DTD:
                return "DTD";
            case XMLEvent.NAMESPACE:
                return "NAMESPACE";
            default:
                throw new IllegalArgumentException("Illegal XMLEvent received: " + eventType);
        }
    }

    public static void doCallback(CallbackHandler callbackHandler, Callback callback) throws XMLSecurityException {
        try {
            Callback[] callbacks = new Callback[]{callback};
            callbackHandler.handle(callbacks);
        } catch (IOException e) {
            throw new XMLSecurityException(e);
        } catch (UnsupportedCallbackException e) {
            throw new XMLSecurityException(e);
        }
    }
}
