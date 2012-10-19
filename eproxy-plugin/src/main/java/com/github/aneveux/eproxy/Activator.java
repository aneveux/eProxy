package com.github.aneveux.eproxy;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.github.aneveux.eproxy"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		Activator.plugin.getImageRegistry().dispose();
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
	 * Sends the message to the Error Log with the INFO severity
	 * 
	 * @param message
	 */
	public static void sendInfoToErrorLog(String message) {
		Activator.plugin.getLog().log(
				new Status(IStatus.INFO, Activator.PLUGIN_ID, message));
	}

	/**
	 * Sends the message to the Error Log with the WARN severity
	 * 
	 * @param message
	 */
	public static void sendWarningToErrorLog(String message) {
		Activator.plugin.getLog().log(
				new Status(IStatus.WARNING, Activator.PLUGIN_ID, message));
	}

	/**
	 * Sends the message to the Error Log with the ERROR severity
	 * 
	 * @param message
	 */
	public static void sendErrorToErrorLog(String message) {
		Activator.plugin.getLog().log(
				new Status(IStatus.ERROR, Activator.PLUGIN_ID, message));
	}

	/**
	 * Sends the message and the Throwable to the Error Log with the ERROR
	 * severity
	 * 
	 * @param message
	 * @param t
	 *            Throwable
	 */
	public static void sendErrorToErrorLog(String message, Throwable t) {
		Activator.plugin.getLog().log(
				new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, t));
	}

	/**
	 * Returns image in plugin
	 * 
	 * @param pluginId
	 *            : Id of the plugin containing thie image
	 * @param imageFilePath
	 *            : image File Path in plugin
	 * @return Image if exists
	 */
	public Image getImage(String pluginId, String imageFilePath) {
		Image image = Activator.getDefault().getImageRegistry()
				.get(pluginId + ":" + imageFilePath);
		if (image == null) {
			image = loadImage(pluginId, imageFilePath);
		}
		return image;
	}

	/**
	 * Loads image in Image Registry is not available in it
	 * 
	 * @param pluginId
	 *            : Id of the plugin containing thie image
	 * @param imageFilePath
	 *            : image File Path in plugin
	 * @return Image if loaded
	 */
	private synchronized Image loadImage(String pluginId, String imageFilePath) {
		String id = pluginId + ":" + imageFilePath;
		Image image = Activator.getDefault().getImageRegistry().get(id);
		if (image != null)
			return image;
		ImageDescriptor imageDescriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(pluginId, imageFilePath);
		if (imageDescriptor != null) {
			image = imageDescriptor.createImage();
			Activator.getDefault().getImageRegistry()
					.put(pluginId + ":" + imageFilePath, image);
		}
		return image;
	}

	/**
	 * Returns image in this plugin
	 * 
	 * @param imageFilePath
	 *            : image File Path in this plugin
	 * @return Image if exists
	 */
	public Image getImage(String imageFilePath) {
		Image image = Activator.getDefault().getImageRegistry()
				.get(Activator.PLUGIN_ID + ":" + imageFilePath);
		if (image == null)
			image = loadImage(Activator.PLUGIN_ID, imageFilePath);
		return image;
	}

}
