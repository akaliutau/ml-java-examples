package net.ml.data.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A file wrapper for compressed csv files
 * @author akaliutau
 */
public class ZCSVFile {
    private final Path tempDir = Files.createTempDirectory("dir-");
    private final Map<String, File> entries;

    public ZCSVFile(String zipfile) throws IOException {
        this.entries = new HashMap<>();
        unzip(zipfile, tempDir.toFile());
        System.out.println(entries);
    }

    void unzip(String zip, File dest) throws IOException {

        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {

                File newFile = newFile(dest, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    entries.put(newFile.getName(), newFile);
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();

            }
            zis.closeEntry();
        }
    }

    static File newFile(File destDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destDir, zipEntry.getName());
        destFile.deleteOnExit();

        String destDirPath = destDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public File getFileIfExists(String entryName) {
        return entries.get(entryName);
    }

    public File getFile(String entryName) {
        if (!entries.containsKey(entryName)) {
            throw new IllegalArgumentException("Cannot find books.csv in zip archive");
        }
        return entries.get(entryName);
    }

}
