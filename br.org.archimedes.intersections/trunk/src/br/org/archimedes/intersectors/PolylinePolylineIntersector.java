/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/09/18, 01:32:21, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersectors on the br.org.archimedes.intersections project.<br>
 */
package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class PolylinePolylineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        Polyline polyline1;
        Polyline polyline2;

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        polyline1 = (Polyline) element;
        polyline2 = (Polyline) otherElement;

        List<Line> lines = polyline1.getLines();
        Collection<Point> intersectionPoints = new ArrayList<Point>();

        Collection<Point> intersection;

        LinePolylineIntersector linePolylineIntersector = new LinePolylineIntersector();
        for (Line line : lines) {
            intersection = linePolylineIntersector.getIntersections(line,
                    polyline2);
            intersectionPoints.addAll(intersection);
        }
        return intersectionPoints;
    }
}
