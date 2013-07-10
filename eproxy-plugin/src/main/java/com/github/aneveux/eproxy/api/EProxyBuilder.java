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

import java.util.ArrayList;
import java.util.List;

import com.github.aneveux.eproxy.data.EProxy;

/**
 * This fluent API allows to create an {@link EProxy} object following the
 * builder pattern. Use the <b>with*</b> methods in order to define the
 * paramters of your object, and finally call the {@link #build()} method in
 * order to get your object.
 * 
 * @author Antoine Neveux
 * @version 1.1
 * 
 * @see EProxy
 * 
 */
public class EProxyBuilder {

	/**
	 * This internal object allows to store the data provided by the user in
	 * order to create an {@link EProxy} object
	 */
	private EProxy result;

	/**
	 * This internal object allows to store the nonProxyHosts information to use
	 * in the {@link EProxy#setNonProxyHosts(List)} method while calling the
	 * {@link #build()} method
	 */
	private List<String> nonProxyHosts;

	/**
	 * Default constructor
	 */
	public EProxyBuilder() {
		this.result = new EProxy();
		this.nonProxyHosts = new ArrayList<String>();
	}

	/**
	 * Allows to define a reference for your {@link EProxy} instance
	 * 
	 * @see EProxy#setReference(String)
	 * @param reference
	 *            {@link String} : the reference of your {@link EProxy} instance
	 * @return the current {@link EProxyBuilder}
	 */
	public EProxyBuilder withReference(String reference) {
		this.result.setReference(reference);
		return this;
	}

	/**
	 * Allows to define the host of your {@link EProxy} instance
	 * 
	 * @see EProxy#setIsEnabled(boolean)
	 * @param isEnabled
	 *            {@link boolean} : true if the proxy should be enabled
	 * @return the current {@link EProxyBuilder}
	 * @since 1.1
	 */
	public EProxyBuilder withEnabled(boolean isEnabled) {
		this.result.setIsEnabled(isEnabled);
		return this;
	}

	/**
	 * Allows to define the host of your {@link EProxy} instance
	 * 
	 * @see EProxy#setHost(String)
	 * @param host
	 *            {@link String} : the host of your {@link EProxy} instance
	 * @return the current {@link EProxyBuilder}
	 */
	public EProxyBuilder withHost(String host) {
		this.result.setHost(host);
		return this;
	}

	/**
	 * Allows to define the port of your {@link EProxy} instance
	 * 
	 * @see EProxy#setPort(int)
	 * @param port
	 *            int : the port of your {@link EProxy} instance
	 * @return the current {@link EProxyBuilder}
	 */
	public EProxyBuilder withPort(int port) {
		this.result.setPort(port);
		return this;
	}

	/**
	 * Allows to define if authentication is required for your {@link EProxy}
	 * instance
	 * 
	 * @see EProxy#setAuthenticationRequired(boolean)
	 * @param authenticationRequired
	 *            boolean : sets if your {@link EProxy} instance needs
	 *            authentication or not
	 * @return the current {@link EProxyBuilder}
	 */
	public EProxyBuilder withAuthenticationRequired(
			boolean authenticationRequired) {
		this.result.setAuthenticationRequired(authenticationRequired);
		return this;
	}

	/**
	 * Allows to define the user to use for your {@link EProxy} instance
	 * 
	 * @see EProxy#setUser(String)
	 * @param user
	 *            {@link String} : the user of your {@link EProxy} instance
	 * @return the current {@link EProxyBuilder}
	 */
	public EProxyBuilder withUser(String user) {
		this.result.setUser(user);
		return this;
	}

	/**
	 * Allows to define the password to use for your {@link EProxy} instance
	 * 
	 * @see EProxy#setPassword(String)
	 * @param password
	 *            {@link String} : the password of your {@link EProxy} instance
	 * @return the current {@link EProxyBuilder}
	 */
	public EProxyBuilder withPassword(String password) {
		this.result.setPassword(password);
		return this;
	}

	/**
	 * <p>
	 * Allows to add a nonProxyHost to your {@link EProxy} instance
	 * </p>
	 * <p>
	 * Note that you can call this method multiple times if you need to add
	 * several nonProxyHosts
	 * </p>
	 * <p>
	 * If you don't know what to add in this section, perhaps you should call
	 * the {@link #withDefaultNonProxyHost()} instead of this one...
	 * </p>
	 * 
	 * @see EProxy#setNonProxyHosts(List)
	 * @param nonProxyHost
	 *            {@link String} : a nonProxyHost to add to your {@link EProxy}
	 *            instance
	 * @return the current {@link EProxyBuilder}
	 */
	public EProxyBuilder withNonProxyHost(String nonProxyHost) {
		this.nonProxyHosts.add(nonProxyHost);
		return this;
	}

	/**
	 * <p>
	 * Allows to add default nonProxyHosts to your {@link EProxy} instance
	 * </p>
	 * <p>
	 * The default nonProxyHosts are <i>127.0.0.1</i> and <i>localhost</i>
	 * </p>
	 * <p>
	 * Note that you can still add some other nonProxyHosts after calling this
	 * method
	 * </p>
	 * 
	 * @see EProxy#setNonProxyHosts(List)
	 * @return the current {@link EProxyBuilder}
	 */
	public EProxyBuilder withDefaultNonProxyHost() {
		this.nonProxyHosts.add("127.0.0.1");
		this.nonProxyHosts.add("localhost");
		return this;
	}

	/**
	 * Allows to build the actual {@link EProxy} object from all the parameters
	 * received through the <b>with*</b> methods.
	 * 
	 * @see EProxy
	 * @return {@link #result} : an {@link EProxy} instance built from the
	 *         information provided by the user
	 */
	public EProxy build() {
		this.result.setNonProxyHosts(this.nonProxyHosts);
		return this.result;
	}

}
