package com.github.aneveux.eproxy.api;

import java.util.ArrayList;
import java.util.List;

import com.github.aneveux.eproxy.data.EProxy;

public class EProxyBuilder {

	private EProxy result;

	private List<String> nonProxyHosts;

	public EProxyBuilder() {
		this.result = new EProxy();
		this.nonProxyHosts = new ArrayList<String>();
	}

	public EProxyBuilder withReference(String reference) {
		this.result.setReference(reference);
		return this;
	}

	public EProxyBuilder withHost(String host) {
		this.result.setHost(host);
		return this;
	}

	public EProxyBuilder withPort(int port) {
		this.result.setPort(port);
		return this;
	}

	public EProxyBuilder withAuthenticationRequired(
			boolean authenticationRequired) {
		this.result.setAuthenticationRequired(authenticationRequired);
		return this;
	}

	public EProxyBuilder withUser(String user) {
		this.result.setUser(user);
		return this;
	}

	public EProxyBuilder withPassword(String password) {
		this.result.setPassword(password);
		return this;
	}

	public EProxyBuilder withNonProxyHost(String nonProxyHost) {
		this.nonProxyHosts.add(nonProxyHost);
		return this;
	}

	public EProxy build() {
		this.result.setNonProxyHosts(this.nonProxyHosts);
		return this.result;
	}

}
