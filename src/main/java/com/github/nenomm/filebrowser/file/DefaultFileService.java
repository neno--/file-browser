package com.github.nenomm.filebrowser.file;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFileService implements FileService {


	@Override
	public PathQueryResult queryPath(String path) {
		File file = new File(path);

		if (!file.exists()) {
			return PathQueryResult.NOT_FOUND;
		} else if (!file.canRead()) {
			return PathQueryResult.CANNOT_READ;
		} else if (file.isDirectory()) {
			return PathQueryResult.DIR;
		} else if (file.isFile()) {
			return PathQueryResult.FILE;
		} else {
			return PathQueryResult.OTHER;
		}
	}

	@Override
	public List<FileInfo> getFiles(String path) {
		File file = new File(path);
		List<FileInfo> files = new ArrayList<>();

		if (file.getParentFile() != null) {
			files.add(FileInfo.getGoUp(file));
		}

		Arrays.stream(file.listFiles()).forEach(f -> files.add(new FileInfo(f)));

		return files.stream().filter(f -> f.getPathQueryResult() != PathQueryResult.OTHER).collect(Collectors.toList());
	}

	@Override
	public String getTextContent(String path) {
		return null;
	}
}
