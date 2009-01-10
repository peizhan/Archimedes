/**
 * 
 */

package br.org.archimedes.io.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.interfaces.Exporter;
import br.org.archimedes.io.xml.rcp.ElementExporterEPLoader;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;
import br.org.archimedes.rcp.extensionpoints.ElementEPLoader;

/**
 * Belongs to package br.org.archimedes.io.xml.
 * 
 * @author night
 */
public class XMLExporter implements Exporter {

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.interfaces.Exporter#exportDrawing(br.org.archimedes.interfaces.Drawing,
     *      java.io.OutputStream)
     */
    public void exportDrawing (Drawing drawing, OutputStream output)
            throws IOException {

        String charset = "UTF-8"; //$NON-NLS-1$
        // TODO Forçar locale

        exportXMLHeader(drawing, output, charset);

        byte[] endContainerTagBytes = ("\t" + "</container>" + "\n") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                .getBytes(charset);

        ElementEPLoader elementEPLoader = new ElementEPLoader();
        ElementExporterEPLoader exporterLoader = new ElementExporterEPLoader();
        
        for (Layer layer : drawing.getLayerMap().values()) {
            StringBuilder containerTag = new StringBuilder();
            containerTag.append("\t" + "<container name=\"" + layer.getName() //$NON-NLS-1$ //$NON-NLS-2$
                    + "\" lineStyle=\"" + layer.getLineStyle().ordinal() //$NON-NLS-1$
                    + "\" thickness=\"" + layer.getThickness() + "\" >" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            writeColor(containerTag, layer.getColor());

            output.write(containerTag.toString().getBytes(charset));

            for (Element element : layer.getElements()) {
                String elementId = elementEPLoader.getElementId(element);
                ElementExporter<Element> exporter = exporterLoader.getExporter(elementId);
                exporter.exportElement(element, output);
            }

            output.write(endContainerTagBytes);
        }
        output.write(("</drawing>" + "\n").getBytes(charset)); //$NON-NLS-1$ //$NON-NLS-2$
        output.close();
    }

    /**
     * @param drawing
     *            Drawing to be exported
     * @param output
     *            The outputstream to write on
     * @param charset
     *            The charset to use to write the file
     * @throws IOException
     *             Thrown if something goes wrong when writing the file
     * @throws UnsupportedEncodingException
     *             Thrown if the system cannot write in the specified charset
     */
    private void exportXMLHeader (Drawing drawing, OutputStream output,
            String charset) throws IOException, UnsupportedEncodingException {

        StringBuilder drawingTag = new StringBuilder(
                "<?xml version=\"1.0\" encoding=\"" + charset + "\"?>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        drawingTag
                .append("<drawing xmlns=\"http://www.archimedes.org.br/xml/FileXMLSchema\" " //$NON-NLS-1$
                        + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " //$NON-NLS-1$
                        + "xsi:schemaLocation=\"http://www.archimedes.org.br/xml/FileXMLSchema FileXMLSchema.xsd\">" //$NON-NLS-1$
                        + "\n"); //$NON-NLS-1$
        drawingTag.append("\t<zoom>"); //$NON-NLS-1$
        drawingTag.append(drawing.getZoom());
        drawingTag.append("</zoom>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
        drawingTag.append("\t<viewport>"); //$NON-NLS-1$

        Point viewportPosition = drawing.getViewportPosition();
        drawingTag.append("\t" + "\t" //$NON-NLS-1$ //$NON-NLS-2$
                + "<point x=\"" + viewportPosition.getX() + "\" y=\"" //$NON-NLS-1$ //$NON-NLS-2$
                + viewportPosition.getY() + "\" />" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
        drawingTag.append("\t" + "</viewport>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        output.write(drawingTag.toString().getBytes(charset));
    }

    /**
     * @param builder
     *            The stringbuilder that should be used to write
     * @param color
     *            The color that should be written
     */
    private void writeColor (StringBuilder builder, Color color) {

        builder.append("\t" + "\t" + "<color>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        writeComponent(builder, color.getRed());
        writeComponent(builder, color.getGreen());
        writeComponent(builder, color.getBlue());
        builder.append("\t" + "\t" + "</color>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    /**
     * @param builder
     *            The stringbuilder that should be used to write
     * @param component
     *            The component to be written
     */
    private void writeComponent (StringBuilder builder, int component) {

        builder.append("\t" + "\t" + "\t" + "<unsignedByte>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        builder.append(component);
        builder.append("</unsignedByte>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
