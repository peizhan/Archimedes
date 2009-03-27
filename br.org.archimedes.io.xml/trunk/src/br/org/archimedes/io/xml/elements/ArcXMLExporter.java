/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.elements on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;

public class ArcXMLExporter implements ElementExporter<Arc> {

	public void exportElement(Arc element, Object outputObject) throws IOException {
		
	    OutputStream output = (OutputStream) outputObject;
		StringBuilder lineTag = new StringBuilder();

		lineTag.append("<arc>"); //$NON-NLS-1$
		
		lineTag.append(XMLExporterHelper.xmlFor(element.getInitialPoint())); //$NON-NLS-1$
        lineTag.append(XMLExporterHelper.xmlFor(element.getIntermediatePoint())); //$NON-NLS-1$
		lineTag.append(XMLExporterHelper.xmlFor(element.getEndingPoint())); //$NON-NLS-1$
		
		
		lineTag.append("</arc>"); //$NON-NLS-1$

		output.write(lineTag.toString().getBytes());
	}
}
