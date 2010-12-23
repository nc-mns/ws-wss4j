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
package ch.gigerstyle.xmlsec.securityEvent;

import javax.xml.namespace.QName;

/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class SignedPartSecurityEvent extends SecurityEvent {

    //todo xpath or something unique
    //todo message signature or supporting sig
    private QName element;
    private boolean notSigned;

    public SignedPartSecurityEvent(Event securityEventType, boolean notSigned) {
        super(securityEventType);
        this.notSigned = notSigned;
    }

    public QName getElement() {
        return element;
    }

    public void setElement(QName element) {
        this.element = element;
    }

    public boolean isNotSigned() {
        return notSigned;
    }

    public void setNotSigned(boolean notSigned) {
        this.notSigned = notSigned;
    }
}
