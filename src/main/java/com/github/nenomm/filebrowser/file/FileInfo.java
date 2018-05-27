package com.github.nenomm.filebrowser.file;

import java.io.File;

public class FileInfo {

	private String name;
	private PathQueryResult pathQueryResult;
	private long size;

	public FileInfo(File f) {
		this.name = f.getName();
		this.pathQueryResult = PathQueryResult.forFile(f);
		this.size = f.length();
	}

	public String getName() {
		return name;
	}

	public PathQueryResult getPathQueryResult() {
		return pathQueryResult;
	}
}
