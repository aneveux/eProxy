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

public class EProxyUI {

	private Display display;

	private Shell shell;

	private boolean isOpenable = true;

	private boolean keepOpen = true;

	protected Label proxy;

	protected Text proxyText;

	protected Button checkbox;

	protected Label user;

	protected Text userText;

	protected Label password;

	protected Text passwordText;

	protected Label nonProxyHosts;

	protected Text nonProxyHostsText;

	protected Button save;

	protected Button cancel;

	protected EProxy reference;

	protected EProxy result;

	protected boolean isReferenceProvided;

	public EProxy getResult() {
		return this.result;
	}

	public EProxyUI() {
		this.isReferenceProvided = false;
		this.display = (Display.getCurrent() == null) ? Display.getDefault()
				: Display.getCurrent();
		this.display.syncExec(new Runnable() {
			@Override
			public void run() {
				EProxyUI.this.display();
			}
		});
	}

	public EProxyUI(EProxy reference) {
		this.reference = reference;
		this.isReferenceProvided = true;
		this.display = (Display.getCurrent() == null) ? Display.getDefault()
				: Display.getCurrent();
		this.display.syncExec(new Runnable() {
			@Override
			public void run() {
				EProxyUI.this.display();
			}
		});
	}

	protected void display() {
		if (this.isOpenable) {
			this.initializeDialog();
			this.addListenersToDialog();
			this.createComponents();
			if (isReferenceProvided)
				this.displayReference();
			this.addListenersToComponents();
			this.open();
		}
	}

	protected void initializeDialog() {
		this.shell = new Shell(this.display, SWT.BORDER | SWT.APPLICATION_MODAL
				| SWT.DIALOG_TRIM);
		this.shell
				.setImage(Activator.getDefault().getImage("/icons/proxy.png"));
		this.shell.setSize(300, 270);
		this.shell.setText("Easy Proxy !");
		this.shell.setLayout(new FormLayout());
	}

	protected void open() {
		this.shell.update();
		Rectangle bounds;
		try {
			bounds = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell().getBounds();
		} catch (NullPointerException npe) {
			bounds = this.display.getPrimaryMonitor().getBounds();
		}
		Rectangle rect = this.shell.getBounds();
		this.shell.setLocation(bounds.x + (bounds.width - rect.width) / 2,
				bounds.y + (bounds.height - rect.height) / 2);
		this.shell.open();
		this.isOpenable = false;
		while (this.keepOpen)
			if (!this.display.readAndDispatch())
				this.display.sleep();
		if (!this.shell.isDisposed())
			this.shell.dispose();
		this.isOpenable = true;
		this.keepOpen = true;
	}

	protected void close() {
		this.keepOpen = false;
	}

	protected void addListenersToDialog() {
		this.shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.doit = false;
				EProxyUI.this.close();
			}
		});
		this.shell.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.doit = false;
				EProxyUI.this.close();
			}
		});
	}

	protected void addListenersToComponents() {
		this.proxyText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (EProxyUI.this.proxyText.getText() == null
						|| !EProxyUI.this.proxyText.getText().contains(":")
						|| EProxyUI.this.proxyText.getText().split(":").length != 2) {
					EProxyUI.this.proxyText.setBackground(new Color(null, 250,
							0, 0));
					EProxyUI.this.save.setEnabled(false);
				} else {
					EProxyUI.this.proxyText.setBackground(new Color(null, 250,
							250, 250));
					EProxyUI.this.save.setEnabled(true);
				}
			}
		});
		this.checkbox.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EProxyUI.this.userText.setEnabled(EProxyUI.this.checkbox
						.getSelection());
				EProxyUI.this.passwordText.setEnabled(EProxyUI.this.checkbox
						.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		this.cancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EProxyUI.this.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		this.save.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EProxyUI.this.result = new EProxy();
				EProxyUI.this.result.setHost(EProxyUI.this.proxyText.getText()
						.split(":")[0]);
				EProxyUI.this.result.setPort(Integer
						.parseInt(EProxyUI.this.proxyText.getText().split(":")[1]));
				EProxyUI.this.result
						.setAuthenticationRequired(EProxyUI.this.checkbox
								.getSelection());
				if (EProxyUI.this.checkbox.getSelection()) {
					EProxyUI.this.result.setUser(EProxyUI.this.userText
							.getText());
					EProxyUI.this.result.setPassword(EProxyUI.this.passwordText
							.getText());
				}
				EProxyUI.this.result
						.setNonProxyHosts(EProxyUI.this.nonProxyHostsText
								.getText());
				EProxyUI.this.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	protected void createComponents() {
		this.proxy = new Label(this.shell, SWT.NONE);
		this.proxy.setText("Proxy: (host:port)");
		new FormDataBuilder().top().horizontal().apply(this.proxy);
		this.proxyText = new Text(this.shell, SWT.BORDER);
		this.proxyText
				.setToolTipText("Define proxy following this syntax: host:port");
		new FormDataBuilder().top(this.proxy).horizontal()
				.apply(this.proxyText);
		this.checkbox = new Button(shell, SWT.CHECK);
		this.checkbox.setText("Requires authentication");
		new FormDataBuilder().top(this.proxyText).horizontal()
				.apply(this.checkbox);
		this.user = new Label(this.shell, SWT.NONE);
		this.user.setText("Username:");
		new FormDataBuilder().top(this.checkbox).horizontal().apply(this.user);
		this.userText = new Text(this.shell, SWT.BORDER);
		this.userText
				.setToolTipText("Define username for proxy authentication");
		this.userText.setEnabled(false);
		new FormDataBuilder().top(this.user).horizontal().apply(this.userText);
		this.password = new Label(this.shell, SWT.NONE);
		this.password.setText("Password:");
		new FormDataBuilder().top(this.userText).horizontal()
				.apply(this.password);
		this.passwordText = new Text(this.shell, SWT.BORDER | SWT.PASSWORD);
		this.passwordText
				.setToolTipText("Define password for proxy authentication");
		this.passwordText.setEnabled(false);
		new FormDataBuilder().top(this.password).horizontal()
				.apply(this.passwordText);
		this.nonProxyHosts = new Label(this.shell, SWT.NONE);
		this.nonProxyHosts.setText("Non proxy hosts (comma separated):");
		new FormDataBuilder().top(this.passwordText).horizontal()
				.apply(this.nonProxyHosts);
		this.nonProxyHostsText = new Text(this.shell, SWT.BORDER);
		this.nonProxyHostsText
				.setToolTipText("Define non proxy hosts, separe them with a comma");
		new FormDataBuilder().top(this.nonProxyHosts).horizontal()
				.apply(this.nonProxyHostsText);
		this.save = new Button(this.shell, SWT.PUSH);
		this.save.setText("Save");
		this.save.setImage(Activator.getDefault().getImage("/icons/save.png"));
		new FormDataBuilder().top(this.nonProxyHostsText).right().bottom()
				.apply(this.save);
		this.cancel = new Button(this.shell, SWT.PUSH);
		this.cancel.setText("Cancel");
		new FormDataBuilder().top(this.nonProxyHostsText).right(this.save)
				.bottom().apply(this.cancel);
	}

	protected void displayReference() {
		if (this.reference != null) {
			this.proxyText.setText(this.reference.getHost() + ":"
					+ this.reference.getPort());
			this.checkbox.setSelection(this.reference
					.isAuthenticationRequired());
			this.userText
					.setText(this.reference.getUser() != null ? this.reference
							.getUser() : "");
			this.passwordText
					.setText(this.reference.getPassword() != null ? this.reference
							.getPassword() : "");
			if (this.reference.getNonProxyHosts() != null
					&& this.reference.getNonProxyHosts().length > 0) {
				String nonProxyHosts = new String();
				for (String s : this.reference.getNonProxyHosts())
					nonProxyHosts += s + ",";
				this.nonProxyHostsText.setText(nonProxyHosts.substring(0,
						nonProxyHosts.length() - 1));
			}
		}
	}

}
