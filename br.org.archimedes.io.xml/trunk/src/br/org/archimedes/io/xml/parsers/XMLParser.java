// TODO: salvar o ROTATE
/*
 * Created on 11/07/2006
 */

package br.org.archimedes.io.xml.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.gui.rca.Messages;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;

/**
 * Belongs to package com.tarantulus.archimedes.xml.
 * 
 * @author fernandorb
 */
public class XMLParser {

    private Validator validator;

    /**
     * @param validator The validator to use. If null, will not validate the file.
     */
    public XMLParser (Validator validator) {

        this.validator = validator;
    }

    /**
     * Parse and Archimedes XML file
     * 
     * @param xmlInputStream
     *            The File to be Parsed
     * @return Returns the Drawing of the xmlFile or null if the file is
     *         invalid.
     * @throws InvalidFileFormatException
     *             Thrown if the inputstream does not define a valid format
     */
    public Drawing parse (InputStream xmlInputStream)
            throws InvalidFileFormatException {

        Document doc = getDocument(xmlInputStream);
        Drawing drawing = null;
        if (doc != null && validadeXMLSchema(doc)) {
            NodeList windowList = doc.getFirstChild().getChildNodes();
            LayerParser parser = new LayerParser();

            Map<String, Layer> layerMap = new HashMap<String, Layer>();
            Double zoom = null;
            Point viewPoint = null;
            for (int i = 0; i < windowList.getLength(); i++) {
                Node parsingNode = windowList.item(i);

                if (parsingNode.getNodeName().equals("container")) { //$NON-NLS-1$
                    Layer layer = parser.parse(parsingNode);
                    if (layerMap.get(layer.getName()) == null) {
                        layerMap.put(layer.getName(), layer);
                    }
                    else {
                        Layer sameNameLayer = layerMap.get(layer.getName());
                        for (Element element : layer.getElements()) {
                            try {
                                sameNameLayer.putElement(element);
                            }
                            catch (Exception e) {
                                // ignores
                            }
                        }
                    }
                }
                else if (parsingNode.getNodeName().equals("zoom")) { //$NON-NLS-1$
                    zoom = XMLUtils.nodeToDouble(parsingNode);
                }
                else if (parsingNode.getNodeName().equals("viewport")) { //$NON-NLS-1$
                    NodeList list = ((org.w3c.dom.Element) parsingNode)
                            .getElementsByTagName("point"); //$NON-NLS-1$
                    if (list.getLength() > 0)
                        viewPoint = XMLUtils.nodeToPoint(list.item(0));
                }
            }

            // TODO Check how to set the file
            drawing = new Drawing(Messages.NewDrawingName, layerMap);
            drawing.setZoom(zoom);
            drawing.setViewportPosition(viewPoint);
            // drawing.setFile(xmlInputStream);
            drawing.setSaved(true);
        }
        else {
            throw new InvalidFileFormatException();
        }

        return drawing;
    }

    /**
     * @param xmlInputStream
     *            The file to be parsed
     * @return The document
     */
    private Document getDocument (InputStream xmlInputStream) {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            // The "Serious" configuration error, should never Happen
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = docBuilder.parse(xmlInputStream);
            doc.normalize();
        }
        catch (SAXException e) {
            // Can happen if the file is invalid
        }
        catch (IOException e) {
            // Can happen if it is the wrong file type
        }
        return doc;
    }

    /**
     * Validades a XML file based on his xsd Schema
     * 
     * @param doc
     *            The document to be validated
     * @return True if the file is valid or the validator is null, false otherwise
     */
    private boolean validadeXMLSchema (Document doc) {
        if(validator == null)
            return true;
        
        try {
            validator.validate(new DOMSource(doc));
        }
        catch (SAXException e) {
            // Happens if the XML is not valid
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            // This should never happen
            e.printStackTrace();
        }

        return true;
    }
}
