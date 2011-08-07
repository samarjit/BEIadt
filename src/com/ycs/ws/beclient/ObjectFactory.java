
package com.ycs.ws.beclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ycs.ws.beclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RemoteCommandProcessorResponse_QNAME = new QName("http://ws.ycs.com/", "remoteCommandProcessorResponse");
    private final static QName _SelectOnLoad_QNAME = new QName("http://ws.ycs.com/", "selectOnLoad");
    private final static QName _RemoteCommandProcessor_QNAME = new QName("http://ws.ycs.com/", "remoteCommandProcessor");
    private final static QName _SelectOnLoadResponse_QNAME = new QName("http://ws.ycs.com/", "selectOnLoadResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ycs.ws.beclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectOnLoadResponse }
     * 
     */
    public SelectOnLoadResponse createSelectOnLoadResponse() {
        return new SelectOnLoadResponse();
    }

    /**
     * Create an instance of {@link RemoteCommandProcessorResponse }
     * 
     */
    public RemoteCommandProcessorResponse createRemoteCommandProcessorResponse() {
        return new RemoteCommandProcessorResponse();
    }

    /**
     * Create an instance of {@link SelectOnLoad }
     * 
     */
    public SelectOnLoad createSelectOnLoad() {
        return new SelectOnLoad();
    }

    /**
     * Create an instance of {@link RemoteCommandProcessor }
     * 
     */
    public RemoteCommandProcessor createRemoteCommandProcessor() {
        return new RemoteCommandProcessor();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteCommandProcessorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ycs.com/", name = "remoteCommandProcessorResponse")
    public JAXBElement<RemoteCommandProcessorResponse> createRemoteCommandProcessorResponse(RemoteCommandProcessorResponse value) {
        return new JAXBElement<RemoteCommandProcessorResponse>(_RemoteCommandProcessorResponse_QNAME, RemoteCommandProcessorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectOnLoad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ycs.com/", name = "selectOnLoad")
    public JAXBElement<SelectOnLoad> createSelectOnLoad(SelectOnLoad value) {
        return new JAXBElement<SelectOnLoad>(_SelectOnLoad_QNAME, SelectOnLoad.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteCommandProcessor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ycs.com/", name = "remoteCommandProcessor")
    public JAXBElement<RemoteCommandProcessor> createRemoteCommandProcessor(RemoteCommandProcessor value) {
        return new JAXBElement<RemoteCommandProcessor>(_RemoteCommandProcessor_QNAME, RemoteCommandProcessor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectOnLoadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ycs.com/", name = "selectOnLoadResponse")
    public JAXBElement<SelectOnLoadResponse> createSelectOnLoadResponse(SelectOnLoadResponse value) {
        return new JAXBElement<SelectOnLoadResponse>(_SelectOnLoadResponse_QNAME, SelectOnLoadResponse.class, null, value);
    }

}
