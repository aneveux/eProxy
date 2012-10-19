/**
 * 
 */
package com.github.aneveux.eproxy;

import org.eclipse.ui.IStartup;

import com.github.aneveux.eproxy.core.ProxyHelper;
import com.github.aneveux.eproxy.ui.EProxyUI;

/**
 * @author a183276
 * 
 */
public class StartupExtension implements IStartup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@Override
	public void earlyStartup() {
		EProxyUI ui = new EProxyUI(ProxyHelper.getProxyInformation());
		ProxyHelper.defineProxy(ui.getResult());
	}

}
