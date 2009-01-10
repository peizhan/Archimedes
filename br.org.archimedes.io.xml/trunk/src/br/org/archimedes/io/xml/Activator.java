package br.org.archimedes.io.xml;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.framework.BundleContext;

import br.org.archimedes.rcp.AbstractFileLocatorActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractFileLocatorActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "br.org.archimedes.io.xml"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	/**
     * @param path
     *            The path to the file relative to this plugin's root
     * @return An Inputstream to the file or null if the activator is not set
     *         (this is not a regular RCP run... happens for tests)
     * @throws IOException
     *             Thrown if there is an error while reading the file
     */
    public static InputStream locateFile (String path) throws IOException {

        InputStream input = null;

        if (getDefault() != null) {
            input = locateFile(path, getDefault().getBundle());
        }
        return input;
    }
}
