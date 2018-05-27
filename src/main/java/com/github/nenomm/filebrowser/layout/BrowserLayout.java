package com.github.nenomm.filebrowser.layout;

import com.github.nenomm.filebrowser.RefreshPathCallback;
import com.github.nenomm.filebrowser.file.FileInfo;
import com.github.nenomm.filebrowser.file.FileService;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.renderers.DateRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;

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

	private Grid<FileInfo> grid;

	private RefreshPathCallback callback;

	@PostConstruct
	public void initGrid() {
		setVisible(false);

		grid = new Grid<>();

		grid.setSizeFull();

		grid.addColumn(FileInfo::getName).setCaption("Name");

		grid.addColumn(FileInfo::getSize).setCaption("Size");

		grid.addColumn(FileInfo::getModified,
				new DateRenderer("%1$tY/%1$tm/%1$td %1$tH:%1$tm:%1$tS",
						Locale.ENGLISH)).setCaption("Modified");

		grid.setSelectionMode(Grid.SelectionMode.SINGLE);

		grid.addItemClickListener(event -> {
			FileInfo selected = event.getItem();
			Notification.show(selected.getName());
			callback.refreshPath(selected.getFullPath());
			browseFiles(selected.getFullPath());
		});

		setContent(grid);
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
			case CANNOT_READ: {
				Notification.show("Cannot read path content.", Notification.Type.WARNING_MESSAGE);
				break;
			}
			case OTHER: {
				Notification.show("Cannot display path content.", Notification.Type.WARNING_MESSAGE);
				break;
			}
		}
	}

	private void addItems(List<FileInfo> list) {
		grid.setItems(list);
	}

	public void registerCallback(RefreshPathCallback callback) {
		this.callback = callback;
	}
}
