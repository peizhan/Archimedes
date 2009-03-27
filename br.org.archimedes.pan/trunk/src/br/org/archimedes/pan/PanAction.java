/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/03/13, 20:23:40, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.pan on the br.org.archimedes.pan project.<br>
 */
package br.org.archimedes.pan;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.pan.
 * 
 * @author nitao
 */
public class PanAction extends FactoryAction {

    /**
     * Constructor.
     */
    public PanAction () {

        super(Activator.PLUGIN_ID);
    }
}
