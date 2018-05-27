package com.github.nenomm.filebrowser.file;

import java.io.File;

public enum PathQueryResult {
	FILE,
	DIR,
	NOT_FOUND,
	OTHER;

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
