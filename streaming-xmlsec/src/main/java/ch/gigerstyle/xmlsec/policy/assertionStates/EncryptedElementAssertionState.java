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
package ch.gigerstyle.xmlsec.policy.assertionStates;

import ch.gigerstyle.xmlsec.policy.secpolicy.model.AbstractSecurityAssertion;
import ch.gigerstyle.xmlsec.securityEvent.EncryptedElementSecurityEvent;
import ch.gigerstyle.xmlsec.securityEvent.SecurityEvent;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class EncryptedElementAssertionState extends AssertionState {

    private List<QName> elements;

    public EncryptedElementAssertionState(AbstractSecurityAssertion assertion, boolean asserted, List<QName> elements) {
        super(assertion, asserted);
        this.elements = elements;
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) {
        //here we add just one AssertionState for all Parts to get a fail-fast behavior
        //when we add multiple AssertionStates some of them return true, becauce they don't match
        //as a result the policy is temporary satisfied for the current event and can only be falsified at last
        EncryptedElementSecurityEvent encryptedElementSecurityEvent = (EncryptedElementSecurityEvent) securityEvent;
        for (int i = 0; i < elements.size(); i++) {
            QName qName = elements.get(i);
            if (qName.equals(encryptedElementSecurityEvent.getElement())) {
                if (encryptedElementSecurityEvent.isNotEncrypted()) {
                    //an element must be encrypted but isn't
                    setAsserted(false);
                    setErrorMessage("Element " + encryptedElementSecurityEvent.getElement() + " must be encrypted");
                    return false;
                } else {
                    setAsserted(true);
                }
            }
        }
        //if we return false here other encrypted elements will trigger a PolicyViolationException
        return true;
    }
}
