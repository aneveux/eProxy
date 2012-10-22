package com.github.aneveux.eproxy.api;

import com.github.aneveux.eproxy.core.ProxyHelper;
import com.github.aneveux.eproxy.data.EProxy;
import com.github.aneveux.eproxy.ui.EProxyUI;

/**
 * <p>
 * User API which allows to invoke the EasyProxy Wizard in order to ask the user
 * for a proxy, and to set it in Eclipse preferences
 * </p>
 * <p>
 * Everything relies on {@link EProxy} objects which stand for EasyProxy
 * objects, they're containers for Proxy information.
 * </p>
 * <p>
 * If you need to create {@link EProxy} objects, you can use the fluent API
 * provided by {@link EProxyBuilder}
 * </p>
 * 
 * @author Antoine Neveux
 * @version 1.0
 * 
 */
public class EProxyWizard {

	/**
	 * Allows to invoke an empty {@link EProxyUI} to display to the user in
	 * order to ask him for a proxy definition, and then to set it in Eclipse
	 * preferences
	 */
	public static void invokeEmptyWizard() {
		EProxyUI ui = new EProxyUI();
		if (ui.getResult() != null)
			ProxyHelper.defineProxy(ui.getResult());
	}

	/**
	 * Allows to invoke an {@link EProxyUI} filled with the information which is
	 * already present in Eclipse configuration, then the user will be asked for
	 * modifying these values if necessary, and the Eclipse configuration will
	 * be updated
	 */
	public static void invokeDefaultWizard() {
		EProxyUI ui = new EProxyUI(ProxyHelper.getProxyInformation());
		if (ui.getResult() != null)
			ProxyHelper.defineProxy(ui.getResult());
	}

	/**
	 * Allows to invoke an {@link EProxyUI} filled with the default information
	 * you provided through the parameter. You can create a default
	 * {@link EProxy} to use using the fluent API provided by
	 * {@link EProxyBuilder}, it'll allow you to fill some information in the
	 * {@link EProxyUI} by default, then ask the user for some modification, and
	 * finally update Eclipse configuration following the user's modifications
	 * 
	 * @param defaultConfiguration
	 *            the {@link EProxy} default configuration to use in the
	 *            {@link EProxyUI}
	 */
	public static void invokeWizard(EProxy defaultConfiguration) {
		EProxyUI ui = new EProxyUI(defaultConfiguration);
		if (ui.getResult() != null)
			ProxyHelper.defineProxy(ui.getResult());
	}

}
