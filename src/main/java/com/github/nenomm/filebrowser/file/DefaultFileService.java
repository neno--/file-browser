package com.github.nenomm.filebrowser.file;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFileService implements FileService {

	private Logger logger = LoggerFactory.getLogger(DefaultFileService.class);

	@Override
	public PathQueryResult queryPath(File file) {

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
	public List<FileInfo> getFiles(File file) {

		List<FileInfo> files = new ArrayList<>();

		if (file.getParentFile() != null) {
			files.add(FileInfo.createGoUp(file));
		}

		Arrays.stream(file.listFiles()).forEach(f -> files.add(new FileInfo(f)));

		return files.stream().filter(f -> f.getType() != FileInfoType.OTHER).collect(Collectors.toList());
	}

	@Override
	public String getTextContent(File file) throws IOException {

		try {
			return FileUtils.readFileToString(file, Charset.defaultCharset());
		} catch (IOException e) {
			logger.error("Error while getting text content for {}", file.getAbsolutePath(), e);
			throw e;
		}
	}
}
