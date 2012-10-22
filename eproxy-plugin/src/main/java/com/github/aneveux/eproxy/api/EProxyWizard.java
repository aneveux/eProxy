/*
 *   eProxy - An user friendly Eclipse plugin to manage easily your
 *   proxy inside Eclipse
 *   Copyright (c) 2012, Antoine Neveux
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *   1. Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 *   2. Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 *   3. Neither the name of the project's author nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *   FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *   COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *   INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *   BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *   CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *   LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 *   WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
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
