package com.github.nenomm.filebrowser.layout;

import com.github.nenomm.filebrowser.file.FileInfo;
import com.github.nenomm.filebrowser.file.FileService;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This should be component, to support Spring autowiring.
 * However, it should be of UIScope, since this is bound to Vaadin session.
 */

@UIScope
@Component
public class BrowserLayout extends Panel {

	private Logger logger = LoggerFactory.getLogger(BrowserLayout.class);

	@Autowired
	private FileService fileService;

	VerticalLayout layout = new VerticalLayout();

	public BrowserLayout() {
		setVisible(false);
		setContent(layout);
	}

	private void showLayout() {
		setVisible(true);
		setHeight("100%");
		setWidth("50%");
	}

	public void browseFiles(String path) {
		switch (fileService.queryPath(path)) {
			case DIR: {
				showLayout();
				addItems(fileService.getFiles(path));
				break;
			}
			case FILE: {
				Notification.show("This is file.", Notification.Type.HUMANIZED_MESSAGE);
				break;
			}
			case NOT_FOUND: {
				Notification.show("Path not found.", Notification.Type.WARNING_MESSAGE);
				break;
			}
			case OTHER: {
				Notification.show("Cannot display path content.", Notification.Type.WARNING_MESSAGE);
				break;
			}
		}
	}

	private void addItems(List<FileInfo> list) {
		list.forEach(item -> layout.addComponent(new ItemLayout(item)));

	}
}
