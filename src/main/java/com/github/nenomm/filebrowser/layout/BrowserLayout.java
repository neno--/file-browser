package com.github.nenomm.filebrowser.layout;

import com.github.nenomm.filebrowser.FileBrowserUI;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

public class BrowserLayout extends Panel {

	private Logger logger = LoggerFactory.getLogger(BrowserLayout.class);

	VerticalLayout layout = new VerticalLayout();

	public BrowserLayout() {
		setHeight("100%");
		setWidth("50%");
		setContent(layout);
	}

	public void populate() {
		for (int i = 0; i < 100; i++) {
			logger.info("Add item");
			layout.addComponent(new ItemLayout());
		}
	}


}
