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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public interface InputProcessorChain extends ProcessorChain {

    public void addProcessor(InputProcessor inputProcessor);

    public void removeProcessor(InputProcessor inputProcessor);

    public SecurityContext getSecurityContext();

    public DocumentContext getDocumentContext();

    public InputProcessorChain createSubChain(InputProcessor inputProcessor) throws XMLStreamException, XMLSecurityException;

    //public void processSecurityHeaderEvent(XMLEvent xmlEvent) throws XMLStreamException, XMLSecurityException;

    public XMLEvent processHeaderEvent() throws XMLStreamException, XMLSecurityException;

    public XMLEvent processEvent() throws XMLStreamException, XMLSecurityException;
}
