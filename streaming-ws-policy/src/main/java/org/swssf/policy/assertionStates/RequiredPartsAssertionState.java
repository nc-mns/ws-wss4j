/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.swssf.policy.assertionStates;

import org.apache.ws.secpolicy.AssertionState;
import org.apache.ws.secpolicy.WSSPolicyException;
import org.apache.ws.secpolicy.model.AbstractSecurityAssertion;
import org.apache.ws.secpolicy.model.Header;
import org.apache.ws.secpolicy.model.RequiredParts;
import org.swssf.policy.Assertable;
import org.swssf.wss.securityEvent.RequiredPartSecurityEvent;
import org.swssf.wss.securityEvent.SecurityEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class RequiredPartsAssertionState extends AssertionState implements Assertable {

    private Map<Header, Boolean> headers = new HashMap<Header, Boolean>();

    public RequiredPartsAssertionState(AbstractSecurityAssertion assertion, boolean asserted) {
        super(assertion, asserted);

        RequiredParts requiredParts = (RequiredParts) assertion;
        for (int i = 0; i < requiredParts.getHeaders().size(); i++) {
            Header header = requiredParts.getHeaders().get(i);
            headers.put(header, Boolean.FALSE);
        }
    }

    @Override
    public SecurityEvent.Event[] getSecurityEventType() {
        return new SecurityEvent.Event[]{
                SecurityEvent.Event.RequiredPart
        };
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        RequiredPartSecurityEvent requiredPartSecurityEvent = (RequiredPartSecurityEvent) securityEvent;
        //todo better matching until we have a streaming xpath evaluation engine (work in progress)

        Iterator<Map.Entry<Header, Boolean>> elementMapIterator = headers.entrySet().iterator();
        while (elementMapIterator.hasNext()) {
            Map.Entry<Header, Boolean> next = elementMapIterator.next();
            Header header = next.getKey();
            if (header.getNamespace().equals(requiredPartSecurityEvent.getElement().getNamespaceURI())
                    && (header.getName() == null //== wildcard
                    || header.getName().equals(requiredPartSecurityEvent.getElement().getLocalPart()))) {
                next.setValue(Boolean.TRUE);
                break;
            }
        }
        //if we return false here other required elements will trigger a PolicyViolationException
        return true;
    }

    @Override
    public boolean isAsserted() {
        Iterator<Map.Entry<Header, Boolean>> elementMapIterator = headers.entrySet().iterator();
        while (elementMapIterator.hasNext()) {
            Map.Entry<Header, Boolean> next = elementMapIterator.next();
            if (Boolean.FALSE.equals(next.getValue())) {
                setErrorMessage("Element " + next.getKey().toString() + " must be present");
                return false;
            }
        }
        return true;
    }
}