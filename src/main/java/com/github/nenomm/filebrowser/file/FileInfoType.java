package com.github.nenomm.filebrowser.file;

import java.io.File;

public enum FileInfoType {
	FILE,
	DIR,
	OTHER,
	GO_UP;

	public static FileInfoType forFile(File file) {
		if (file.isDirectory()) {
			return DIR;
		} else if (file.isFile()) {
			return FILE;
		} else {
			return OTHER;
		}
	}
}
