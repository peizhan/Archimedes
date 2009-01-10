/*
 * Created on 23/08/2006
 */

package br.org.archimedes.controller.commands;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;


public class ZoomByAreaCommand extends ZoomCommand {

    private Point p1;

    private Point p2;

    private Point midPoint;


    /**
     * Zooms by area. The area is defined by two points.
     * 
     * @param p1
     *            The first point
     * @param p2
     *            The second point
     * @throws NullArgumentException
     *             In case some point is null
     * @throws IllegalActionException
     *             In case the area is zero
     */
    public ZoomByAreaCommand (Point p1, Point p2) throws NullArgumentException,
            IllegalActionException {

        if (p1 == null || p2 == null) {
            throw new NullArgumentException();
        }
        if (Math.abs(p1.getX() - p2.getX()) < Constant.EPSILON
                || Math.abs(p1.getY() - p2.getY()) < Constant.EPSILON) {
            throw new IllegalActionException();
        }

        this.p1 = p1;
        this.p2 = p2;
        midPoint = new Point((p1.getX() + p2.getX()) / 2.0,
                (p1.getY() + p2.getY()) / 2.0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.TheZoomCommand#getNewViewport()
     */
    @Override
    protected Point getNewViewport (Drawing drawing) {

        return midPoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.TheZoomCommand#calculateZoom()
     */
    @Override
    protected double calculateZoom (Drawing drawing) {

        Rectangle selectArea = new Rectangle(p1.getX(), p1.getY(), p2.getX(),
                p2.getY());
        Rectangle windowSize = br.org.archimedes.Utils.getWorkspace().getWindowSize();

        double zoomWidth = windowSize.getWidth() / selectArea.getWidth();
        double zoomHeight = windowSize.getHeight() / selectArea.getHeight();
        return Math.min(zoomWidth, zoomHeight);
    }
}
