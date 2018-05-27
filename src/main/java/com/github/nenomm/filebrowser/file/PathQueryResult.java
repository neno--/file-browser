package com.github.nenomm.filebrowser.file;

import java.io.File;

public enum PathQueryResult {
	FILE,
	DIR,
	NOT_FOUND,
	CANNOT_READ,
	OTHER,
	GO_UP;

	public static PathQueryResult forFile(File file) {
		if (file.isDirectory()) {
			return PathQueryResult.DIR;
		} else if (file.isFile()) {
			return PathQueryResult.FILE;
		} else {
			return PathQueryResult.OTHER;
		}
	}
}
