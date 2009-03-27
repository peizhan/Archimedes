/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/01/20, 19:16:40, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.swt on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.swt;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 *
 * @author night
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.dialogs"; //$NON-NLS-1$

    public static String Cancel;

    public static String CloseError_Message;

    public static String CloseError_Title;

    public static String LayerEditor_ColorChange;

    public static String LayerEditor_ColorLabel;

    public static String LayerEditor_Create;

    public static String LayerEditor_DuplicateName;

    public static String LayerEditor_Layer;

    public static String LayerEditor_Locked;

    public static String LayerEditor_NameLabel;

    public static String LayerEditor_PrintColorChange;

    public static String LayerEditor_PrintColorLabel;

    public static String LayerEditor_StyleLabel;

    public static String LayerEditor_ThicknessLabel;

    public static String LayerEditor_Title;

    public static String LayerEditor_Visible;

    public static String LayerLocked;

    public static String OK;

    public static String TextEditor_WindowName;

    public static String Workspace_Browse;

    public static String Workspace_CantWriteText;

    public static String Workspace_CantWriteTitle;

    public static String Workspace_CursorSizeTitle;

    public static String Workspace_GripoSizeTitle;

    public static String Workspace_Interval;

    public static String Workspace_SelectionSizeTitle;

    public static String Workspace_Title;

    public static String Workspace_TmpFolder;

    public static String Workspace_TmpFolderMessage;

    public static String Workspace_TmpFolderTitle;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
