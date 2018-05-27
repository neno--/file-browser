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
import java.io.File;
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

		grid.addColumn(FileInfo::getName).setCaption("Name").setExpandRatio(1);

		grid.addColumn(FileInfo::getSize).setCaption("Size");

		grid.addColumn(FileInfo::getModified,
				new DateRenderer("%1$tY/%1$tm/%1$td %1$tH:%1$tm:%1$tS",
						Locale.ENGLISH)).setCaption("Modified");

		grid.setSelectionMode(Grid.SelectionMode.SINGLE);

		grid.addItemClickListener(event -> {
			FileInfo selected = event.getItem();
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
				FilePreviewWindow.create(new File(path));
				break;
			}
			case NOT_FOUND: {
				displayWarning("Path not found.");
				break;
			}
			case CANNOT_READ: {
				displayWarning("Cannot read path content.");
				break;
			}
			case OTHER: {
				displayWarning("Cannot display path content.");
			}
		}
	}

	private void displayWarning(String caption) {
		setVisible(false);
		Notification.show(caption, Notification.Type.WARNING_MESSAGE);
	}

	private void addItems(List<FileInfo> list) {
		grid.setItems(list);
	}

	public void registerCallback(RefreshPathCallback callback) {
		this.callback = callback;
	}
}
