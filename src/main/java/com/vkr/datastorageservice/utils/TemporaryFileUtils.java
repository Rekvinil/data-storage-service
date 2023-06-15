package com.vkr.datastorageservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class TemporaryFileUtils {

    public static Path newTemporaryFolder(String filePrefix) {
        Path temporaryFolderPath = createTemporaryPath(filePrefix);
        while (Files.notExists(temporaryFolderPath)) {
            try {
                Files.createDirectory(temporaryFolderPath);
            } catch (FileAlreadyExistsException exception) {
                log.warn("Temporary folder " + temporaryFolderPath + " already exists. Try generate again", exception);
                temporaryFolderPath = createTemporaryPath(filePrefix);
            } catch (IOException exception) {
                throw new IllegalArgumentException("The temporary folder " + temporaryFolderPath + " cannot be created!", exception);
            }
        }

        return temporaryFolderPath;
    }

    private static Path createTemporaryPath(String fileName) {
        final Path temporaryDirectoryPath = FileUtils.getTempDirectory().toPath();
        fileName = System.nanoTime() + fileName;

        final Path temporaryFolder = Paths.get(temporaryDirectoryPath.toString(), "TemporaryFolderForStorageFiles");
        try {
            if (!Files.exists(temporaryFolder)) {
                Files.createDirectory(temporaryFolder);
            }
        } catch (IOException e) {
            log.error("The temporary folder for anonymization files " + temporaryFolder + " cannot be created!");
        }
        return Paths.get(temporaryFolder.toString(), fileName);
    }
}
