package com.github.nenomm.filebrowser.layout.grid;

import com.github.nenomm.filebrowser.file.FileInfo;
import com.github.nenomm.filebrowser.file.FileInfoType;
import com.vaadin.server.SerializableComparator;

public class FileSizeComparator implements SerializableComparator<FileInfo> {
	@Override
	public int compare(FileInfo fileInfo1, FileInfo fileInfo2) {
		if ((fileInfo1.getType() == FileInfoType.GO_UP) || (fileInfo2.getType() == FileInfoType.GO_UP)) {
			return 1;
		}

		return Long.compare(fileInfo1.getSize(), fileInfo2.getSize());
	}
}
