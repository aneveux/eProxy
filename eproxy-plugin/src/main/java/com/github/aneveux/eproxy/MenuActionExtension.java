package com.github.aneveux.eproxy;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.github.aneveux.eproxy.core.ProxyHelper;
import com.github.aneveux.eproxy.ui.EProxyUI;

public class MenuActionExtension implements IWorkbenchWindowActionDelegate {

	@Override
	public void run(IAction action) {
		EProxyUI ui = new EProxyUI(ProxyHelper.getProxyInformation());
		ProxyHelper.defineProxy(ui.getResult());
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}

}
