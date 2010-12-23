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
package ch.gigerstyle.xmlsec.policy;

import org.apache.neethi.Policy;

/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class OperationPolicy {

    private String operationName;
    private String operationAction;
    private Policy policy;

    public OperationPolicy(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationAction() {
        return operationAction;
    }

    public void setOperationAction(String operationAction) {
        this.operationAction = operationAction;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OperationPolicy)) {
            return false;
        }
        OperationPolicy other = (OperationPolicy) obj;
        if (getOperationName().equals(other.getOperationName())) {
            return true;
        }
        return false;
    }
}
