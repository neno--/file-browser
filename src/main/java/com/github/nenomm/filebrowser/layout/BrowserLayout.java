package com.github.nenomm.filebrowser.layout;

import com.github.nenomm.filebrowser.RefreshPathCallback;
import com.github.nenomm.filebrowser.file.FileInfo;
import com.github.nenomm.filebrowser.file.FileService;
import com.github.nenomm.filebrowser.layout.grid.FileDateModifiedComparator;
import com.github.nenomm.filebrowser.layout.grid.FileInfoTypeRenderer;
import com.github.nenomm.filebrowser.layout.grid.FileNameComparator;
import com.github.nenomm.filebrowser.layout.grid.FileSizeComparator;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.DateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * This should be component, to support Spring autowiring.
 * However, it should be of UIScope, since this is bound to Vaadin session.
 */

@UIScope
@Component
public class BrowserLayout extends Panel {


	@Autowired
	private FileService fileService;

	private Grid<FileInfo> grid;

	private RefreshPathCallback callback;

	@PostConstruct
	public void initGrid() {
		setVisible(false);

		grid = new Grid<>();

		grid.setSizeFull();

		grid.addColumn(FileInfo::getTypeAsString).setRenderer(new FileInfoTypeRenderer()).setSortable(false);
		grid.addColumn(FileInfo::getName).setCaption("Name").setExpandRatio(3).setComparator(new FileNameComparator());
		grid.addColumn(FileInfo::getSize).setCaption("Size").setExpandRatio(1).setComparator(new FileSizeComparator());

		grid.addColumn(FileInfo::getModified,
				new DateRenderer("%1$tY/%1$tm/%1$td %1$tH:%1$tm:%1$tS",
						Locale.ENGLISH)).setCaption("Modified")
				.setExpandRatio(1)
				.setComparator(new FileDateModifiedComparator())
				.setStyleGenerator(item -> "v-align-right")
				.setId("Modified");

		HeaderRow headerRow = grid.getDefaultHeaderRow();
		HeaderCell testCycleHeaderCell = headerRow.getCell("Modified");
		testCycleHeaderCell.setStyleName("text-align-right");

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

		File file = new File(path);

		switch (fileService.queryPath(file)) {
			case DIR: {
				showLayout();
				addItems(fileService.getFiles(file));
				break;
			}
			case FILE: {
				try {
					FilePreviewWindow.create(file.getName(), fileService.getTextContent(file));
				} catch (IOException e) {
					Notification.show("Error while previewing the file.", Notification.Type.ERROR_MESSAGE);
				}
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
