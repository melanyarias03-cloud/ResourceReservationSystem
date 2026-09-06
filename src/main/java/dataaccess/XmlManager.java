package dataaccess;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


public class XmlManager {


    //loads an XML file into a document object
    public Document loadDocument(String filePath) throws Exception {

        File file = new File(filePath);

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder builder =
                factory.newDocumentBuilder();

        Document document =
                builder.parse(file);

        document.getDocumentElement().normalize();

        return document;
    }


    //save a document object into an XML file
    public void saveDocument(
            Document document,
            String filePath)
            throws Exception {

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();

        Transformer transformer =
                transformerFactory.newTransformer();

        transformer.setOutputProperty(
                OutputKeys.INDENT,
                "yes"
        );

        DOMSource source =
                new DOMSource(document);

        StreamResult result =
                new StreamResult(
                        new File(filePath)
                );

        transformer.transform(
                source,
                result
        );
    }




}
