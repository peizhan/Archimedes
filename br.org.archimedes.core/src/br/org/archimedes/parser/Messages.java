/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/02/07, 22:38:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.parser;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.parser.
 * 
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.parsers"; //$NON-NLS-1$

    public static String Direction_expectedDirection;

    public static String Point_expectingPoint;

    public static String Text_confirmPoint;

    public static String Text_iteration;

    public static String Text_no;

    public static String Text_yes;
    
    public static String Integer_expectingInteger;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
