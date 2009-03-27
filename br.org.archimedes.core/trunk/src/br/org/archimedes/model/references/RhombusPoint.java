/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/10/04, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model.references on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model.references;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.ReferencePoint;

/**
 * Belongs to package br.org.archimedes.model.references.
 * 
 * @author jefsilva
 */
public class RhombusPoint extends ReferencePoint {

    public RhombusPoint (Point point) throws NullArgumentException {

        super(point);
    }

    public RhombusPoint (Point point, List<Point> pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    public RhombusPoint (Point point, Point... pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.references.ReferencePoint#draw()
     */
    @Override
    public void draw () {

        OpenGLWrapper openGLWrapper = br.org.archimedes.Utils.getOpenGLWrapper();

        double size = br.org.archimedes.Utils.getWorkspace().getGripSize() / 2;
        Point point = null;
        try {
            point = br.org.archimedes.Utils.getWorkspace().modelToScreen(getPoint());
        }
        catch (NullArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }

        List<Point> losangle = new ArrayList<Point>();
        losangle.add(new Point(point.getX() - size, point.getY()));
        losangle.add(new Point(point.getX(), point.getY() + size));
        losangle.add(new Point(point.getX() + size, point.getY()));
        losangle.add(new Point(point.getX(), point.getY() - size));

        try {
            openGLWrapper.draw(losangle);
        }
        catch (NullArgumentException e) {
            // Should never reach this block since square was initialized
            e.printStackTrace();
        }
    }
}
