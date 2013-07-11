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
package com.github.aneveux.eproxy.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.github.aneveux.eproxy.Activator;
import com.github.aneveux.eproxy.data.EProxy;

/**
 * This UI element allows to display a wizard which will ask for various
 * information related to proxies in Eclipse, all these information should fill
 * an {@link EProxy} object
 * 
 * @author Antoine Neveux
 * @version 1.0
 * 
 */
public class EProxyUI {

	/**
	 * @see Display
	 */
	private final Display display;

	/**
	 * @see Shell
	 */
	private Shell shell;

	/**
	 * Allows to know if this wizard can be opened or not
	 */
	private boolean isOpenable = true;

	/**
	 * Is used during the wizard execution in order to know when to close it
	 */
	private boolean keepOpen = true;

	/**
	 * {@link Label} to ask for the proxy host and port
	 */
	protected Label proxy;

	/**
	 * {@link Text} to get the proxy from user
	 */
	protected Text proxyText;

	/**
	 * Allows to know if authentication is needed or not
	 */
	protected Button checkbox;

	/**
	 * {@link Label} to ask for the user
	 */
	protected Label user;

	/**
	 * {@link Text} to get the user from user (inception)
	 */
	protected Text userText;

	/**
	 * {@link Label} to ask for the password
	 */
	protected Label password;

	/**
	 * {@link Text} to get the password from user
	 */
	protected Text passwordText;

	/**
	 * {@link Label} to ask for nonProxyHosts
	 */
	protected Label nonProxyHosts;

	/**
	 * {@link Text} to get the nonProxyHosts from user
	 */
	protected Text nonProxyHostsText;

	/**
	 * Save {@link Button} in order to save the user selection
	 */
	protected Button save;

	/**
	 * Cancel {@link Button} in order to exit the wizard without doing anything
	 */
	protected Button cancel;

	/**
	 * {@link EProxy} container to use in order to fill the wizard's form with
	 * some default information
	 */
	protected EProxy reference;

	/**
	 * {@link EProxy} container which will contain the result of the user's
	 * selection
	 */
	protected EProxy result;

	/**
	 * Allows to know if a default reference has been provided to the user or
	 * not
	 */
	protected boolean isReferenceProvided;

	/**
	 * Allows to get the result of the user's selection in the {@link EProxy}
	 * wizard
	 * 
	 * @return the {@link EProxy} container filled by the user's information
	 */
	public EProxy getResult() {
		return result;
	}

	/**
	 * <p>
	 * Default constructor
	 * </p>
	 * <p>
	 * Please note that invoking this constructor will actually run a new
	 * {@link Thread} and display the wizard to the user
	 * </p>
	 */
	public EProxyUI() {
		isReferenceProvided = false;
		display = Display.getCurrent() == null ? Display.getDefault() : Display
				.getCurrent();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				EProxyUI.this.display();
			}
		});
	}

	/**
	 * <p>
	 * Constructor with a default {@link EProxy} container to use as default
	 * information
	 * </p>
	 * <p>
	 * Please note that invoking this constructor will actually run a new
	 * {@link Thread} and display the wizard to the user
	 * </p>
	 * 
	 * @param reference
	 *            default information to use so as to fill the wizard's form
	 */
	public EProxyUI(final EProxy reference) {
		this.reference = reference;
		isReferenceProvided = true;
		display = Display.getCurrent() == null ? Display.getDefault() : Display
				.getCurrent();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				EProxyUI.this.display();
			}
		});
	}

	/**
	 * Allows to display the wizard and set all the listeners, and other
	 * graphical stuff
	 */
	protected void display() {
		if (isOpenable) {
			initializeDialog();
			addListenersToDialog();
			createComponents();
			if (isReferenceProvided)
				displayReference();
			addListenersToComponents();
			open();
		}
	}

	/**
	 * Allows to create the basic dialog object
	 */
	protected void initializeDialog() {
		shell = new Shell(display, SWT.BORDER | SWT.APPLICATION_MODAL
				| SWT.DIALOG_TRIM);
		shell.setImage(Activator.getDefault().getImage("/icons/proxy.png"));
		shell.setSize(300, 270);
		shell.setText("Easy Proxy !");
		shell.setLayout(new FormLayout());
	}

	/**
	 * Allows to open the dialog box in the center of the user's screen
	 */
	protected void open() {
		shell.update();
		Rectangle bounds;
		try {
			bounds = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell().getBounds();
		} catch (final NullPointerException npe) {
			bounds = display.getPrimaryMonitor().getBounds();
		}
		final Rectangle rect = shell.getBounds();
		shell.setLocation(bounds.x + (bounds.width - rect.width) / 2, bounds.y
				+ (bounds.height - rect.height) / 2);
		shell.open();
		isOpenable = false;
		while (keepOpen)
			if (!display.readAndDispatch())
				display.sleep();
		if (!shell.isDisposed())
			shell.dispose();
		isOpenable = true;
		keepOpen = true;
	}

	/**
	 * Allows to close the wizard
	 */
	protected void close() {
		keepOpen = false;
	}

	/**
	 * Allows to add traverse listeners in order to react to keyboard shortcuts
	 * such as pressing Esc.
	 */
	protected void addListenersToDialog() {
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				event.doit = false;
				EProxyUI.this.close();
			}
		});
		shell.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE) {
					event.doit = false;
					EProxyUI.this.close();
				}
			}
		});
	}

	/**
	 * Allows to add listeners to some components such as the checkbox (
	 * {@link #checkbox}) or the buttons ({@link #save}, {@link #cancel}) and
	 * also a few validation on {@link #proxyText}
	 */
	protected void addListenersToComponents() {
		proxyText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				if (proxyText.getText() == null
						|| !proxyText.getText().contains(":")
						|| proxyText.getText().split(":").length != 2) {
					proxyText.setBackground(new Color(null, 250, 0, 0));
					save.setEnabled(false);
				} else {
					proxyText.setBackground(new Color(null, 250, 250, 250));
					save.setEnabled(true);
				}
			}
		});
		checkbox.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				userText.setEnabled(checkbox.getSelection());
				passwordText.setEnabled(checkbox.getSelection());
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}
		});
		cancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				EProxyUI.this.close();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}
		});
		save.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				result = new EProxy();
				result.setHost(proxyText.getText().split(":")[0]);
				result.setPort(Integer
						.parseInt(proxyText.getText().split(":")[1]));
				result.setAuthenticationRequired(checkbox.getSelection());
				if (checkbox.getSelection()) {
					result.setUser(userText.getText());
					result.setPassword(passwordText.getText());
				}
				result.setNonProxyHosts(nonProxyHostsText.getText());
				EProxyUI.this.close();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}
		});
	}

	/**
	 * Allows to create the components to display in the wizard, it uses the
	 * {@link FormDataBuilder} in order to deal with the Layout
	 */
	protected void createComponents() {
		proxy = new Label(shell, SWT.NONE);
		proxy.setText("Proxy: (host:port)");
		new FormDataBuilder().top().horizontal().apply(proxy);
		proxyText = new Text(shell, SWT.BORDER);
		proxyText
				.setToolTipText("Define proxy following this syntax: host:port");
		new FormDataBuilder().top(proxy).horizontal().apply(proxyText);
		checkbox = new Button(shell, SWT.CHECK);
		checkbox.setText("Requires authentication");
		new FormDataBuilder().top(proxyText).horizontal().apply(checkbox);
		user = new Label(shell, SWT.NONE);
		user.setText("Username:");
		new FormDataBuilder().top(checkbox).horizontal().apply(user);
		userText = new Text(shell, SWT.BORDER);
		userText.setToolTipText("Define username for proxy authentication");
		userText.setEnabled(false);
		new FormDataBuilder().top(user).horizontal().apply(userText);
		password = new Label(shell, SWT.NONE);
		password.setText("Password:");
		new FormDataBuilder().top(userText).horizontal().apply(password);
		passwordText = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		passwordText.setToolTipText("Define password for proxy authentication");
		passwordText.setEnabled(false);
		new FormDataBuilder().top(password).horizontal().apply(passwordText);
		nonProxyHosts = new Label(shell, SWT.NONE);
		nonProxyHosts.setText("Non proxy hosts (comma separated):");
		new FormDataBuilder().top(passwordText).horizontal()
				.apply(nonProxyHosts);
		nonProxyHostsText = new Text(shell, SWT.BORDER);
		nonProxyHostsText
				.setToolTipText("Define non proxy hosts, separe them with a comma");
		new FormDataBuilder().top(nonProxyHosts).horizontal()
				.apply(nonProxyHostsText);
		save = new Button(shell, SWT.PUSH);
		save.setText("Save");
		save.setImage(Activator.getDefault().getImage("/icons/save.png"));
		new FormDataBuilder().top(nonProxyHostsText).right().bottom()
				.apply(save);
		cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		new FormDataBuilder().top(nonProxyHostsText).right(save).bottom()
				.apply(cancel);
	}

	/**
	 * Allows to fill the form with some default references if needed
	 */
	protected void displayReference() {
		if (reference != null) {
			proxyText.setText(reference.getHost() + ":" + reference.getPort());
			checkbox.setSelection(reference.isAuthenticationRequired());
			userText.setEnabled(reference.isAuthenticationRequired());
			passwordText.setEnabled(reference.isAuthenticationRequired());

			userText.setText(reference.getUser() != null ? reference.getUser()
					: "");
			passwordText.setText(reference.getPassword() != null ? reference
					.getPassword() : "");
			if (reference.getNonProxyHosts() != null
					&& reference.getNonProxyHosts().length > 0) {
				String nonProxyHosts = new String();
				for (final String s : reference.getNonProxyHosts())
					nonProxyHosts += s + ",";
				nonProxyHostsText.setText(nonProxyHosts.substring(0,
						nonProxyHosts.length() - 1));
			}
		}
	}

}
