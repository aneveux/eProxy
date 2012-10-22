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
package com.github.aneveux.eproxy.data;

import java.util.ArrayList;
import java.util.List;

/**
 * EProxy stands for EasyProxy and is actually a container of information in
 * order to describe a Proxy
 * 
 * @author Antoine Neveux
 * @version 1.0
 * 
 */
public class EProxy {

	/**
	 * A human readable reference in order to idenfity your proxy easily if
	 * needed
	 */
	protected String reference;

	/**
	 * Proxy host (Captain Obvious !)
	 */
	protected String host;

	/**
	 * Proxy port (Captain Obvious !)
	 */
	protected int port;

	/**
	 * Should be true if authentication is required by the proxy you're defining
	 */
	protected boolean authenticationRequired;

	/**
	 * User name if authentication is needed
	 * 
	 * @see #authenticationRequired
	 */
	protected String user;

	/**
	 * Password if authentication is needed
	 * 
	 * @see #authenticationRequired
	 */
	protected String password;

	/**
	 * All the hosts that the proxy should bypass
	 */
	protected String[] nonProxyHosts;

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

	/**
	 * Allows to define all the nonProxyHosts from a string with hosts separated
	 * by a comma
	 * 
	 * @param toParse
	 *            a string which contains hosts separated by a comma
	 */
	public EProxy setNonProxyHosts(String toParse) {
		ArrayList<String> array = new ArrayList<String>();
		for (String s : toParse.split(","))
			array.add(s.trim());
		this.setNonProxyHosts(array);
		return this;
	}

}
