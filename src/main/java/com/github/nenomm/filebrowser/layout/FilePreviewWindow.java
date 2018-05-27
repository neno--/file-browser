package com.github.nenomm.filebrowser.layout;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class FilePreviewWindow extends Window {

	private TextArea textArea;

	public FilePreviewWindow() {
		super("Loading Preview...");
		center();
		setWidth("50%");
		setHeight("50%");

		this.textArea = new TextArea();
		this.textArea.setReadOnly(true);
		this.textArea.setSizeFull();
		this.textArea.setWordWrap(false);

		setContent(this.textArea);
	}

	private void setValue(String fileContent) {
		this.textArea.setValue(fileContent);
	}

	public static void create(String fileName, String fileContent) {
		FilePreviewWindow window = new FilePreviewWindow();

		UI.getCurrent().addWindow(window);

		window.setCaption(fileName);
		window.setValue(fileContent);

		UI.getCurrent().push();
	}
}
