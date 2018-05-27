package com.github.nenomm.filebrowser.layout;

import com.github.nenomm.filebrowser.file.FileInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class ItemLayout extends HorizontalLayout {

	public ItemLayout(FileInfo fileInfo) {
		initializeLayout();
		addComponent(new Label(fileInfo.getName()));
	}

	private void initializeLayout() {
		setWidth("100%");
	}
}
