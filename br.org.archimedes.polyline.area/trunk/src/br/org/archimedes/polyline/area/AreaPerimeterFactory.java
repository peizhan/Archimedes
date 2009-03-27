/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci, Mariana V. Bravo - later contributions<br>
 * <br>
 * This file was created on 2006/09/29, 08:39:53, by Jeferson R. Silva.<br>
 * It is part of package br.org.archimedes.polyline.area on the br.org.archimedes.polyline.area project.<br>
 */
package br.org.archimedes.polyline.area;

import java.util.List;

import br.org.archimedes.Geometrics;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.PolylineFactory;

/**
 * Belongs to package br.org.archimedes.polyline.area.
 * 
 * @author jefsilva
 */
public class AreaPerimeterFactory extends PolylineFactory {

    protected String createCommand (List<Point> points) {

        String result;

        try {
            setCommand(null);
            Point first = points.get(0);
            int lastIndex = points.size() - 1;
            Point last = points.get(lastIndex);
            if (first.equals(last)) {
                points.remove(lastIndex);
            }
            double area = Geometrics.calculateArea(points);
            double perimeter = Geometrics.calculatePerimeter(points);
            result = Messages.Area + " " + area + " , ";  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            result += Messages.Perimeter + " " + perimeter; //$NON-NLS-2$ //$NON-NLS-1$
        }
        catch (Exception e) {
            result = Messages.AreaError;
        }
        return result;
    }

    public String cancel () {

        super.cancel();
        return Messages.AreaCancel;
    }

    public String getName () {

        return "area"; //$NON-NLS-1$
    }
}
