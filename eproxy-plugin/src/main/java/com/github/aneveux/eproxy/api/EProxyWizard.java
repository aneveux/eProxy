package com.github.aneveux.eproxy.api;

import com.github.aneveux.eproxy.core.ProxyHelper;
import com.github.aneveux.eproxy.data.EProxy;
import com.github.aneveux.eproxy.ui.EProxyUI;

public class EProxyWizard {

	public static void invokeEmptyWizard() {
		EProxyUI ui = new EProxyUI();
		if (ui.getResult() != null)
			ProxyHelper.defineProxy(ui.getResult());
	}

	public static void invokeDefaultWizard() {
		EProxyUI ui = new EProxyUI(ProxyHelper.getProxyInformation());
		if (ui.getResult() != null)
			ProxyHelper.defineProxy(ui.getResult());
	}

	public static void invokeWizard(EProxy defaultConfiguration) {
		EProxyUI ui = new EProxyUI(defaultConfiguration);
		if (ui.getResult() != null)
			ProxyHelper.defineProxy(ui.getResult());
	}

}
