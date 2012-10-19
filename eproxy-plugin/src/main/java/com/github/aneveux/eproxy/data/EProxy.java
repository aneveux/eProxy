package com.github.aneveux.eproxy.data;

import java.util.ArrayList;
import java.util.List;

public class EProxy {

	protected String reference;

	protected String host;

	protected int port;

	protected boolean authenticationRequired;

	protected String user;

	protected String password;

	protected String[] nonProxyHosts;

	public EProxy() {

	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set
	 */
	public EProxy setReference(String reference) {
		this.reference = reference;
		return this;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public EProxy setHost(String host) {
		this.host = host;
		return this;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public EProxy setPort(int port) {
		this.port = port;
		return this;
	}

	/**
	 * @return the authentication
	 */
	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	/**
	 * @param authenticationRequired
	 *            the authentication to set
	 */
	public EProxy setAuthenticationRequired(boolean authenticationRequired) {
		this.authenticationRequired = authenticationRequired;
		return this;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public EProxy setUser(String user) {
		this.user = user;
		return this;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public EProxy setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * @return the nonProxyHosts
	 */
	public String[] getNonProxyHosts() {
		return nonProxyHosts;
	}

	/**
	 * @param nonProxyHosts
	 *            the nonProxyHosts to set
	 */
	public EProxy setNonProxyHosts(String[] nonProxyHosts) {
		this.nonProxyHosts = nonProxyHosts;
		return this;
	}

	/**
	 * @param nonProxyHosts
	 *            the nonProxyHosts to set
	 */
	public EProxy setNonProxyHosts(List<String> nonProxyHosts) {
		String[] array = new String[nonProxyHosts.size()];
		for (int i = 0; i < nonProxyHosts.size(); i++)
			array[i] = nonProxyHosts.get(i);
		this.nonProxyHosts = array;
		return this;
	}

	public EProxy setNonProxyHosts(String toParse) {
		ArrayList<String> array = new ArrayList<String>();
		for (String s : toParse.split(","))
			array.add(s.trim());
		this.setNonProxyHosts(array);
		return this;
	}

}
