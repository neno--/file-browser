package com.github.nenomm.filebrowser.layout;

import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FilePreviewWindow extends Window {

	private TextArea textArea;

	public FilePreviewWindow() {
		super("File Preview"); // Set window caption
		center();
		setWidth("50%");
		setHeight("50%");

		this.textArea = new TextArea();
		this.textArea.setReadOnly(true);
		this.textArea.setSizeFull();
		this.textArea.setWordWrap(false);

		setContent(this.textArea);
	}

	private void update(File file) throws IOException {
		textArea.setValue(FileUtils.readFileToString(file, Charset.defaultCharset()));
		UI.getCurrent().push();
	}

	public static void create(File file) {
		FilePreviewWindow window = new FilePreviewWindow();
		UI.getCurrent().addWindow(window);

		try {
			window.update(file);
		} catch (IOException e) {
			window.close();
			Notification.show("Error while previewing the file.", Notification.Type.ERROR_MESSAGE);
		}
	}
}
