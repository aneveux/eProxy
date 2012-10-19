package com.github.aneveux.eproxy.core;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.github.aneveux.eproxy.Activator;
import com.github.aneveux.eproxy.data.EProxy;

public class ProxyHelper {

	protected static IProxyService getProxyService() {
		BundleContext bc = Activator.getDefault().getBundle()
				.getBundleContext();
		ServiceReference<?> serviceReference = bc
				.getServiceReference(IProxyService.class.getName());
		IProxyService service = (IProxyService) bc.getService(serviceReference);
		return service;
	}

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
