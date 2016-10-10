package org.licket.xml.writer;

/**
 * @author activey
 */

import java.util.Stack;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class PrettyPrintXMLStreamWriter implements XMLStreamWriter {

    private static final Object SEEN_NOTHING = new Object();
    private static final Object SEEN_ELEMENT = new Object();
    private static final Object SEEN_DATA = new Object();
    private Object state;
    private Stack<Object> stateStack;
    private String indentStep;
    private int depth;
    private XMLStreamWriter writer;

    public PrettyPrintXMLStreamWriter(XMLStreamWriter writer) {
        this.writer = writer;
        this.state = SEEN_NOTHING;
        this.stateStack = new Stack();
        this.indentStep = "    ";
        this.depth = 0;
    }

    private void onStartElement() throws XMLStreamException {
        this.stateStack.push(SEEN_ELEMENT);
        this.state = SEEN_NOTHING;
        if (this.depth > 0) {
            writer.writeCharacters("\n");
        }

        this.doIndent();
        ++this.depth;
    }

    private void onEndElement() throws XMLStreamException {
        --this.depth;
        if (this.state == SEEN_ELEMENT) {
            writer.writeCharacters("\n");
            this.doIndent();
        }

        this.state = this.stateStack.pop();
    }

    private void onEmptyElement() throws XMLStreamException {
        this.state = SEEN_ELEMENT;
        if (this.depth > 0) {
            writer.writeCharacters("\n");
        }

        this.doIndent();
    }

    private void doIndent() throws XMLStreamException {
        if (this.depth > 0) {
            for (int i = 0; i < this.depth; ++i) {
                writer.writeCharacters(this.indentStep);
            }
        }

    }

    public void writeStartDocument() throws XMLStreamException {
        writer.writeStartDocument();
        writer.writeCharacters("\n");
    }

    public void writeStartDocument(String version) throws XMLStreamException {
        writer.writeStartDocument(version);
        writer.writeCharacters("\n");
    }

    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        writer.writeStartDocument(encoding, version);
        writer.writeCharacters("\n");
    }

    public void writeStartElement(String localName) throws XMLStreamException {
        this.onStartElement();
        writer.writeStartElement(localName);
    }

    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
        this.onStartElement();
        writer.writeStartElement(namespaceURI, localName);
    }

    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        this.onStartElement();
        writer.writeStartElement(prefix, localName, namespaceURI);
    }

    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
        this.onEmptyElement();
        writer.writeEmptyElement(namespaceURI, localName);
    }

    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        this.onEmptyElement();
        writer.writeEmptyElement(prefix, localName, namespaceURI);
    }

    public void writeEmptyElement(String localName) throws XMLStreamException {
        this.onEmptyElement();
        writer.writeEmptyElement(localName);
    }

    public void writeEndElement() throws XMLStreamException {
        this.onEndElement();
        writer.writeEndElement();
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
        writer.writeEndDocument();
    }

    @Override
    public void close() throws XMLStreamException {
        writer.close();
    }

    @Override
    public void flush() throws XMLStreamException {
        writer.flush();
    }

    @Override
    public void writeAttribute(String localName, String value) throws XMLStreamException {
        writer.writeAttribute(localName, value);
    }

    @Override
    public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(prefix, namespaceURI, localName, value);
    }

    @Override
    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(namespaceURI, localName, value);
    }

    @Override
    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
        writer.writeNamespace(prefix, namespaceURI);
    }

    @Override
    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
        writer.writeDefaultNamespace(namespaceURI);
    }

    @Override
    public void writeComment(String data) throws XMLStreamException {
        writer.writeComment(data);
    }

    @Override
    public void writeProcessingInstruction(String target) throws XMLStreamException {
        writer.writeProcessingInstruction(target);
    }

    @Override
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        writer.writeProcessingInstruction(target, data);
    }

    public void writeCharacters(String text) throws XMLStreamException {
        this.state = SEEN_DATA;
        writer.writeCharacters(text);
    }

    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
        this.state = SEEN_DATA;
        writer.writeCharacters(text, start, len);
    }

    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return writer.getPrefix(uri);
    }

    @Override
    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        writer.setPrefix(prefix, uri);
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        writer.setDefaultNamespace(uri);
    }

    @Override
    public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
        writer.setNamespaceContext(context);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return writer.getNamespaceContext();
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return writer.getProperty(name);
    }

    public void writeCData(String data) throws XMLStreamException {
        this.state = SEEN_DATA;
        writer.writeCData(data);
    }

    @Override
    public void writeDTD(String dtd) throws XMLStreamException {
        writer.writeDTD(dtd);
    }

    @Override
    public void writeEntityRef(String name) throws XMLStreamException {
        writer.writeEntityRef(name);
    }
}
