package com.github.nenomm.filebrowser.file;

import java.io.File;
import java.util.Date;

public class FileInfo {

	private FileInfoType type;
	private String name;
	private long size;
	private Date modified;    // Instant is better, but there is no renderer in Vaadin 8

	private String fullPath;

	public FileInfo(File f) {
		this.type = FileInfoType.forFile(f);
		this.name = f.getName();
		this.size = f.length();
		this.modified = new Date(f.lastModified());
		this.fullPath = f.getPath();
	}

	private FileInfo() {
		this.type = FileInfoType.GO_UP;
		this.name = "..";
	}

	public FileInfoType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public Date getModified() {
		return modified;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public static FileInfo createGoUp(File f) {
		FileInfo goUp = new FileInfo();

		goUp.setFullPath(f.getParentFile().getPath());

		return goUp;
	}

}
