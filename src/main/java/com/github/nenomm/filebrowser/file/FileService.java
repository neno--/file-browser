package com.github.nenomm.filebrowser.file;

import java.util.List;

public interface FileService {

	PathQueryResult queryPath(String path);

	List<FileInfo> getFiles(String path);

	String getTextContent(String path);
}
