package com.github.nenomm.filebrowser.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {

	PathQueryResult queryPath(File file);

	List<FileInfo> getFiles(File file);

	String getTextContent(File file) throws IOException;
}
