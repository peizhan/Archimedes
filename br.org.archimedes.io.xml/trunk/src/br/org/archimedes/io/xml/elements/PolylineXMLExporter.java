
package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class PolylineXMLExporter implements ElementExporter<Polyline> {

    public void exportElement (Polyline element, Object outputObject)
            throws IOException {
        OutputStream output = (OutputStream) outputObject;

        StringBuilder lineTag = new StringBuilder();
        lineTag.append("<polyline>"); //$NON-NLS-1$

        for (Point p : element.getPoints()) {
            lineTag.append(XMLExporterHelper.xmlFor("point", p)); //$NON-NLS-1$
        }

        lineTag.append("</polyline>"); //$NON-NLS-1$

        output.write(lineTag.toString().getBytes());
    }
}
