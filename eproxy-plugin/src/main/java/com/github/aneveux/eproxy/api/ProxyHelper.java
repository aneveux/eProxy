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

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.github.aneveux.eproxy.Activator;
import com.github.aneveux.eproxy.data.EProxy;

/**
 * This helper class uses the Eclipse {@link IProxyService} in order to interact
 * with the Eclipse proxy configuration, it'll provide a few methods in order to
 * get/set the proxy from Eclipse
 * 
 * @author Antoine Neveux
 * @version 1.0
 * 
 * @see IProxyService
 */
public class ProxyHelper {

	/**
	 * This technical method allows to get the Eclipse {@link IProxyService} in
	 * order to interact with the proxy configuration from Eclipse platform
	 * 
	 * @return the Eclipse {@link IProxyService} to use in order to interact
	 *         with the proxy configuration
	 */
	protected static IProxyService getProxyService() {
		BundleContext bc = Activator.getDefault().getBundle()
				.getBundleContext();
		ServiceReference<?> serviceReference = bc
				.getServiceReference(IProxyService.class.getName());
		IProxyService service = (IProxyService) bc.getService(serviceReference);
		return service;
	}

	/**
	 * Allows to define the HTTP and HTTPS proxy in Eclipse using the
	 * information provided in the {@link EProxy} container. It'll take care of
	 * the host, port, authentication if needed, and nonProxyHosts
	 * 
	 * @param proxy
	 *            an {@link EProxy} container to use in order to define
	 *            Eclipse's proxy
	 */
	public static void defineProxy(EProxy proxy) {
		IProxyService proxyService = getProxyService();
		IProxyData[] proxyData = proxyService.getProxyData();
		for (IProxyData data : proxyData) {
			if (IProxyData.HTTP_PROXY_TYPE.equals(data.getType())
					|| IProxyData.HTTPS_PROXY_TYPE.equals(data.getType())) {
				data.setHost(proxy.getHost());
				data.setPort(proxy.getPort());
				if (proxy.isAuthenticationRequired() && proxy.getUser() != null
						&& proxy.getPassword() != null) {
					data.setUserid(proxy.getUser());
					data.setPassword(proxy.getPassword());
				}
			}
		}
		proxyService.setSystemProxiesEnabled(false);
		proxyService.setProxiesEnabled(true);
		try {
			proxyService.setProxyData(proxyData);
			if (proxy.getNonProxyHosts() != null
					&& proxy.getNonProxyHosts().length > 0)
				proxyService.setNonProxiedHosts(proxy.getNonProxyHosts());
		} catch (CoreException e) {
			Activator.sendErrorToErrorLog(
					"Error while trying to define the proxy...", e);
		}
	}

	/**
	 * Allows to get the Eclipse HTTP proxy configuration in an {@link EProxy}
	 * container
	 * 
	 * @return an {@link EProxy} container which contains all the actual Eclipse
	 *         configuration for the HTTP proxy
	 */
	public static EProxy getProxyInformation() {
		EProxy proxy = new EProxy();
		proxy.setReference("HTTP-" + System.currentTimeMillis());
		IProxyService proxyService = getProxyService();
		IProxyData[] proxyData = proxyService.getProxyData();
		for (IProxyData data : proxyData) {
			if (IProxyData.HTTP_PROXY_TYPE.equals(data.getType())) {
				proxy.setHost(data.getHost());
				proxy.setPort(data.getPort());
				proxy.setAuthenticationRequired(data.isRequiresAuthentication());
				proxy.setUser(data.getUserId());
				proxy.setPassword(data.getPassword());
			}
		}
		proxy.setNonProxyHosts(proxyService.getNonProxiedHosts());
		return proxy;
	}

}
