/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Eduardo O. de Souza, Victor D. Lopes, Marcos P. Moreti, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2006/06/22, 13:06:39, by Eduardo O. de Souza.<br>
 * It is part of package br.org.archimedes.circle on the br.org.archimedes.circle project.<br>
 */
package br.org.archimedes.circle;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.curvedshape.CurvedShape;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.model.references.RhombusPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Belongs to package com.tarantulus.archimedes.model.
 */
public class Circle extends CurvedShape implements Offsetable {

    private Point center;

    private double radius;

    private Layer parentLayer;


    /**
     * Constructor.
     * 
     * @param center
     *            The circle's center
     * @param radius
     *            The circle's radius
     * @throws NullArgumentException
     *             In case the point is null
     * @throws InvalidArgumentException
     *             In case the radius is 0
     */
    public Circle (Point center, Double radius) throws NullArgumentException,
            InvalidArgumentException {

        if (center == null) {
            throw new NullArgumentException();
        }
        if (Math.abs(radius) <= Constant.EPSILON) {
            throw new InvalidArgumentException();
        }
        this.center = center;
        this.radius = Math.abs(radius);
    }

    public CurvedShape clone() {

        Circle circle = null;

        try {
            circle = new Circle(center.clone(), radius);
            circle.setLayer(parentLayer);
        }
        catch (NullArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }

        return circle;
    }

    public boolean equals (Object object) {

        boolean result = false;

        if (object != null) {
            try {
                Circle circle = (Circle) object;
                result = this.equals(circle);
            }
            catch (ClassCastException e) {
                // The elements are not equal
            }
        }

        return result;
    }

    public boolean equals (Circle circle) {

        boolean result = true;

        if (circle == null) {
            result = false;
        }
        else if ( !this.center.equals(circle.getCenter())) {
            result = false;
        }
        else if (Math.abs(radius - circle.getRadius()) > Constant.EPSILON) {
            result = false;
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getBoundaryRectangle()
     */
    public Rectangle getBoundaryRectangle () {

        double left = center.getX() - radius;
        double right = center.getX() + radius;
        double bottom = center.getY() - radius;
        double top = center.getY() + radius;

        return new Rectangle(left, bottom, right, top);
    }



    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getReferencePoints(com.tarantulus.archimedes.model.Rectangle)
     */
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();
        try {
            ReferencePoint reference = new CirclePoint(center, center);
            if (reference.isInside(area)) {
                references.add(reference);
            }
            for (double angle = 0; angle < 360; angle += 90) {
                double radians = (angle / 180) * Math.PI;
                Point point = new Point(radius * Math.cos(radians)
                        + center.getX(), radius * Math.sin(radians)
                        + center.getY());
                reference = new RhombusPoint(point);
                if (reference.isInside(area)) {
                    references.add(reference);
                }
            }
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }

        return references;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getProjectionOf(com.tarantulus.archimedes.model.Point)
     */
    public Point getProjectionOf (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        Point projection = null;
        if(getCenter().equals(point)) {
        	return new Point(getCenter().getX() + getRadius(), getCenter().getY());
        }
        
    	Collection<Point> intersectionWithLine = getCirclePoints(point);
    	
        double closestDist = Double.MAX_VALUE;
        for (Point intersection : intersectionWithLine) {
            double dist = Geometrics.calculateDistance(point, intersection);
            if (dist < closestDist) {
                projection = intersection;
                closestDist = dist;
            }
        }
        
        return projection;
    }

    /**
     * Returns the Points that are on the circle and on the line
     * defined by the given Point and the center of the Circle.
     * 
     * @param point Point
     * 
     * @return Collection
     * @throws NullArgumentException If the given argument is null
     */
    private Collection<Point> getCirclePoints(Point point) throws NullArgumentException {
        if (point == null) {
            throw new NullArgumentException();
        }
        
        Collection<Point> points = new ArrayList<Point>();
        
        Vector vec = new Vector(getCenter(), point);
        vec = Geometrics.normalize(vec);
        vec.multiply(getRadius());
        
        Point p1 = getCenter().addVector(vec);
        points.add(p1);
        vec.multiply(-1);
        Point p2 = getCenter().addVector(vec);
        if(!p2.equals(p1)) {
        	points.add(p2);
        }
        
    	return points;
	}

	/*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#contains(com.tarantulus.archimedes.model.Point)
     */
    public boolean contains (Point point) throws NullArgumentException {

        double distance = Geometrics.calculateDistance(getCenter(), point);
        return Math.abs(distance - radius) <= Constant.EPSILON;
    }

    /**
     * @return The circle's center
     */
    public Point getCenter () {

        return center;
    }

    /**
     * @return The circle's radius
     */
    public double getRadius () {

        return radius;
    }

    public Element cloneWithDistance (double distance) throws InvalidParameterException {

        if (distance < 0) {
            if (Math.abs(radius - distance) <= Constant.EPSILON
                    || Math.abs(distance) > radius) {
                throw new InvalidParameterException();
            }
        }
        
        Circle clone = null;
        try {
            clone = new Circle(center.clone(), radius + distance);
            clone.setLayer(parentLayer);
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        return clone;
    }

    public boolean isPositiveDirection (Point point) {

        boolean isOutside = false;

        try {
            if (Geometrics.calculateDistance(center, point) > radius) {
                isOutside = true;
            }
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        return isOutside;
    }
    
    public void scale (Point reference, double proportion)
            throws NullArgumentException, IllegalActionException {

        center.scale(reference, proportion);
        radius *= proportion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.elements.Element#getPoints()
     */
    public @Override
    List<Point> getPoints () {

        List<Point> points = new ArrayList<Point>();
        points.add(center);
        return points;
    }

    public String toString () {

        return center.toString() + " with radius " + radius; //$NON-NLS-1$
    }

    public boolean isClosed () {

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.PointSortable#getSortedPointSet(com.tarantulus.archimedes.model.Point,
     *      java.util.Collection)
     */
    public SortedSet<ComparablePoint> getSortedPointSet (Point referencePoint,
            Collection<Point> intersectionPoints) {

        SortedSet<ComparablePoint> sortedSet = new TreeSet<ComparablePoint>();

        for (Point point : intersectionPoints) {
            try {
                double key = Geometrics.calculateRelativeAngle(center,
                        referencePoint, point);
                ComparablePoint orderedPoint = new ComparablePoint(point,
                        new DoubleKey(key));
                sortedSet.add(orderedPoint);
            }
            catch (NullArgumentException e) {
                // Should not catch this exception
                e.printStackTrace();
            }

        }

        return sortedSet;
        
    }

	@Override
	public void draw(OpenGLWrapper wrapper) {
		Point center = this.getCenter();

		this.drawCurvedShape(wrapper, center, 0, 2 * Math.PI);		
	}
}
