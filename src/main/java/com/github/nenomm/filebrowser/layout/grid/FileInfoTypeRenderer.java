package com.github.nenomm.filebrowser.layout.grid;

import com.github.nenomm.filebrowser.file.FileInfoType;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.renderers.HtmlRenderer;
import elemental.json.JsonValue;

public class FileInfoTypeRenderer extends HtmlRenderer {

	@Override
	public JsonValue encode(String value) {
		FileInfoType type = FileInfoType.valueOf(value);
		String result = "";

		switch (type) {
			case GO_UP: {
				result = FontAwesome.ARROW_UP.getHtml();
				break;
			}
			case DIR: {
				result = FontAwesome.FOLDER.getHtml();
				break;

			}
			case FILE: {
				result = FontAwesome.FILE.getHtml();
				break;
			}
			default: {
				result = FontAwesome.QUESTION.getHtml();
				break;
			}
		}

		return super.encode(result);
	}
}
