/*
 * Created on 02/10/2006
 */

package br.org.archimedes.controller;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.parser.CommandParser;

/**
 * Belongs to package br.org.archimedes.controller.
 * 
 * @author night
 */
public class IdleState extends InputState {

    private CommandFactory previousFactory;

    private InputState nextState;

    private boolean nextShould;

    private Drawing currentDrawing;

    private DisabledState disabledState;


    public IdleState (DisabledState previousState) {

        previousFactory = null;
        disabledState = previousState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#receiveText(java.lang.String)
     */
    public String receiveText (String text) {

        String returnValue = null;
        CommandFactory factory = null;
        nextState = this;
        nextShould = false;
        if (text == null || text.trim().equals("")) { //$NON-NLS-1$
            factory = previousFactory;
        }
        else {
            CommandParser parser = br.org.archimedes.Utils.getInputController().getCommandParser();
            factory = parser.getCommand(text);
        }

        if (factory != null) {
            returnValue = setCurrentFactory(factory);
        }
        else if (Utils.isPoint(text)) {
            Point point = Utils.getPointCoordinates(text);
            try {
                if ( !br.org.archimedes.Utils.getController().movePoint(point)) {
                    nextState = new SelectionState(this);
                    nextShould = true;
                }
            }
            catch (NoActiveDrawingException e) {
                // Should never happen
                e.printStackTrace();
            }
        }
        else {
            returnValue = Messages.Invalid + Constant.NEW_LINE
                    + Messages.Waiting;
        }

        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#getNext()
     */
    public InputState getNext () {

        return nextState;
    }

    /**
     * Set the current factory to the specified one and execute it.
     * 
     * @param factory
     *            The factory to be executed.
     * @return The message that should be printed.
     */
    protected String setCurrentFactory (CommandFactory factory) {

        String returnValue = factory.getName().toUpperCase() + ": " //$NON-NLS-1$
                + factory.begin();
        CommandParser parser = br.org.archimedes.Utils.getInputController().getCommandParser();
        if (parser.getCommand(factory.getName()) != null) {
            previousFactory = factory;
        }

        if (factory.isDone()) {
            try {
                br.org.archimedes.Utils.getController().execute(factory.getCommands());
            }
            catch (IllegalActionException e) {
                returnValue = factory.getName().toUpperCase()
                        + ": " + e.getMessage() + Constant.NEW_LINE //$NON-NLS-1$
                        + Messages.Waiting;
            }
            catch (NoActiveDrawingException e) {
                nextState = disabledState;
                returnValue = Messages.NoDrawing;
            }
        }
        else {
            nextState = new ActiveState(this, factory, currentDrawing);
        }

        nextShould = false;
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#nextShouldHandle()
     */
    public boolean nextShouldHandle () {

        return nextShould;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#changedDrawing(br.org.archimedes.model.Drawing)
     */
    public InputState changedDrawing (Drawing currentDrawing) {

        InputState state = this;
        this.currentDrawing = currentDrawing;
        if (currentDrawing == null) {
            state = disabledState;
            // Window.getInstance().disableDrawingButtons();
        }
        else {
            // Window.getInstance().enableDrawingButtons();
        }
        return state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.InputState#cancel()
     */
    public String cancel () {

        nextState = this;
        return Messages.Waiting;
    }
}
