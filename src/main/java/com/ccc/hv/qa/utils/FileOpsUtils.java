package com.ccc.hv.qa.utils;

import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.FeedPathOption;
import com.ccc.hv.qa.ui.enums.PathFormatOption;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.comparator.SizeFileComparator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;
import static com.ccc.hv.qa.utils.StringUtils.removeSpaces;
import static org.apache.commons.io.FilenameUtils.EXTENSION_SEPARATOR;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by R0manL on 22/09/20.
 */

public class FileOpsUtils {
    public static final String FILE_PATH_DELIMITER = "/";
    public static final String REVISION_DELIMITER = "_";
    public static final String DOUBLE_FILE_PATH_DELIMITER = FILE_PATH_DELIMITER + FILE_PATH_DELIMITER;
    public static final String FEED_NAME_DELIMITER = "(-)|(\\.)";

    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private FileOpsUtils() {
        //NONE
    }

    @NotNull
    public static Path getAbsoluteResourceFilePath(@NotNull Path relPath) {
        log.debug("Getting absolute path to resources dir from related.", relPath.toString());

        Path result = relPath;
        if (!relPath.isAbsolute()) {
            URL url = FileOpsUtils.class.getClassLoader().getResource(relPath.toString());
            Objects.requireNonNull(url, "Can't convert related path: '" + relPath + "' to absolute.");
            result = Paths.get(url.getPath());
        }
        log.debug("Absolute path is:.", result.toString());

        return result;
    }

    /**
     * Add unique (base on date) suffix to file's name.
     *
     * @param filePath - path relative or absolute to the file.
     * @return path to the file with updated name.
     */
    @NotNull
    public static Path addUniqueSuffixTo(@NotNull Path filePath) {
        log.debug("Add unique suffix to: ", filePath.toString());
        String path = filePath.toString();
        String root = FilenameUtils.removeExtension(path);
        String ext = FilenameUtils.getExtension(path);

        return Paths.get(generateUniqueStringBasedOnDate(root + REVISION_DELIMITER) + EXTENSION_SEPARATOR + ext);
    }

    @NotNull
    public static Path zipFile(@NotNull Path absFilePath) {
        log.debug("Archive file: '" + absFilePath + "'.");

        String filePathWithName = absFilePath.toString();
        String zipFileName = FilenameUtils.removeExtension(filePathWithName).concat(".zip");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(zipFileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Can't find file by path: '" + filePathWithName + "'. Error:" + e.getMessage());
        }

        try (ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.putNextEntry(new ZipEntry(absFilePath.getFileName().toString()));
            byte[] bytes = Files.readAllBytes(absFilePath);
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
        } catch (IOException e) {
            throw new IllegalStateException("Can't archive a file: '" + filePathWithName + ";. I/O error: " + e);
        }

        return Paths.get(zipFileName);
    }

    public static String getRevisionDelimiter() {
        return REVISION_DELIMITER;
    }

    @NotNull
    public static String xmlFileToString(@NotNull Path absFilePath) {
        log.debug("Read file into String by absolute path: '" + absFilePath + "'.");
        String result;
        try {
            result = Files.readAllLines(absFilePath, StandardCharsets.UTF_8)
                    .stream()
                    .map(Object::toString)
                    .reduce("", String::concat)
                    .replace("\t", "");
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read '" + absFilePath + "' file \n" + e.getMessage());
        }

        return result;
    }

    public static void compareFileLists(List<File> expectedList, List<File> actualList) {
        log.debug("Compare two list of files.");
        assertEquals(actualList.size(), expectedList.size());

        expectedList.sort(SizeFileComparator.SIZE_COMPARATOR);
        actualList.sort(SizeFileComparator.SIZE_COMPARATOR);

        IntStream.range(0, expectedList.size()).forEach(id -> {
                    File expectedFile = expectedList.get(id);
                    File actualFile = actualList.get(id);

                    try {
                        if (FilenameUtils.getExtension(expectedFile.getName()).equals("xml")) {
                            String expected = removeSpaces(xmlFileToString(Paths.get(expectedFile.getAbsolutePath())));
                            String actual = removeSpaces(xmlFileToString(Paths.get(actualFile.getAbsolutePath())));
                            assertEquals(expected, actual);
                        } else {
                            assertTrue(FileUtils.contentEquals(expectedFile, actualFile), "Content of files "
                                    + expectedFile + " and " + actualFile + " is not the same");
                        }
                    } catch (IOException e) {
                        throw new IllegalStateException("Can't compare files " + expectedFile + " and " + actualFile);
                    }
                }
        );
    }

    /**
     * Method create a copy of original file with a new file name.
     *
     * @param origFilePath    path to original file
     * @param newFileName new file name
     * @return absolute path to the new file
     */
    @NotNull
    public static Path cloneFile(@NotNull String newFileName, @NotNull String origFilePath) {
        log.debug("Clone a file.", "From: '" + origFilePath + "' to: '" + newFileName + "'.");
        Path path = getAbsoluteResourceFilePath(Paths.get(origFilePath));

        try {
            Files.copy(path, path.resolveSibling(newFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't clone '" + origFilePath + "' file." );
        }

        return Paths.get(path.getParent() + File.separator + newFileName);
    }

    public static Path createDirectory(@NotNull String absolutePathName) {
        log.debug("Create directory: '" + absolutePathName + "'.");

        File theDir = new File(absolutePathName);
        if (!theDir.exists()) {
            try {
                Files.createDirectories(Paths.get(absolutePathName));
            } catch (IOException e) {
                throw new IllegalArgumentException("Do not able to create directory by path" + absolutePathName);
            }
        }

        return theDir.toPath();
    }

    public static List<String> getFileNamesFromZipFile(@NotNull Path absZipFilePath) {
        assertTrue(absZipFilePath.isAbsolute(), "Path to the zipFile " + absZipFilePath + " is not absolute ");

        List<String> fileNames;
        try (ZipFile zipFile = new ZipFile(absZipFilePath.toFile())) {
            fileNames = zipFile.stream()
                    .map(ZipEntry::getName)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't get filenames from zip file: " + absZipFilePath);
        }

        return fileNames;
    }

    public static String generateFileNameWith(@NotNull String newRecordReference, @NotNull String origAssetFilePath) {
        return generateFileNameWith(newRecordReference, Paths.get(origAssetFilePath));
    }

    /**
     * Generate Asset file name with a new record reference
     *
     * @param newRecordReference - new recref
     * @param origAssetFilePath  - path with old asset file name
     * @return new asset file name
     */
    @NotNull
    public static String generateFileNameWith(@NotNull String newRecordReference, Path origAssetFilePath) {
        log.debug("Generate asset file path with a new record reference: '" + newRecordReference + "'.");

        //Extract old record reference
        int lastOccurrence = newRecordReference.lastIndexOf(REVISION_DELIMITER);
        if (lastOccurrence == -1) {
            throw new IllegalArgumentException("New record reference '" + newRecordReference
                    + "' does not contains '_'. Is it old record reference?");
        }

        String oldRecRef = newRecordReference.substring(0, lastOccurrence);

        //Generate new asset file name
        String path = origAssetFilePath.toString();
        lastOccurrence = path.lastIndexOf(oldRecRef);
        if (lastOccurrence == -1) {
            throw new IllegalArgumentException("Asset file path '" + origAssetFilePath
                    + "' does not contains '" + oldRecRef + "' record reference.");
        }

        return newRecordReference + path.substring(lastOccurrence + oldRecRef.length());
    }

    @NotNull
    public static String getPathFormatOptionValueFrom(@NotNull String distributionPath,
                                                      @NotNull String pathTemplate,
                                                      PathFormatOption pathFormatOption) {
        log.debug("Getting advanced keyword: '" + pathFormatOption + "' value from '" + distributionPath + "' by '" + pathTemplate + "' template.");

        //Replace '//' with one '/'.
        distributionPath = distributionPath.replace(DOUBLE_FILE_PATH_DELIMITER, FILE_PATH_DELIMITER);
        pathTemplate = pathTemplate.replace(DOUBLE_FILE_PATH_DELIMITER, FILE_PATH_DELIMITER);
        pathTemplate = pathTemplate.replaceAll("^/+", "");

        final String FORMAT_OPTION = pathFormatOption.toString();
        String[] distributionPathNodes = distributionPath.split(FILE_PATH_DELIMITER);
        int numOfDistributionPathNodes = distributionPathNodes.length;
        String[] pathTemplateNodes = pathTemplate.split(FILE_PATH_DELIMITER);
        String result = null;

        for (int i = 0; i < pathTemplateNodes.length; i++) {
            String pathTemplateNode = pathTemplateNodes[i];

            if (pathTemplateNode.contains(FORMAT_OPTION)) {
                if (numOfDistributionPathNodes <= i) {
                    throw new IllegalArgumentException("Distribution path is too short. " +
                            "Trying to find value for node " + i + ", but only " + (numOfDistributionPathNodes - 1) + " exist. " +
                            "Details: distribution path='" + distributionPath + "', " +
                            "path template='" + pathTemplate + "', " +
                            "path format option='" + pathFormatOption + "'");
                }

                String distributionPathNode = distributionPathNodes[i];
                int beginIndex = pathTemplateNode.indexOf(FORMAT_OPTION);
                int endIndex = distributionPathNode.length();

                String suffix = pathTemplateNode.substring(beginIndex + FORMAT_OPTION.length());
                if (!suffix.isEmpty()) {
                    endIndex = distributionPathNode.indexOf(suffix);
                }

                String nextResult = distributionPathNode.substring(beginIndex, endIndex);
                if (result != null && !nextResult.equals(result)) {
                    throw new IllegalStateException("Advanced keyword has two different values: '" + result + "', '" + nextResult + "'.");
                }

                result = nextResult;
            }
        }

        Objects.requireNonNull(result, "Can't find '" + FORMAT_OPTION + "' in template: '" + pathTemplate + "'.");

        //If path template not ends with "/", that mean that last path option related to filename (not folder name),
        // to extract value correctly we must remove extension from result.
        if(!pathTemplate.endsWith(FILE_PATH_DELIMITER)) {
            result = result.replaceAll("(?<!^)[.][^.]*$", "");
        }

        return result;
    }

    @NotNull
    public static String getOptionValueFromFeed(@NotNull String feedDistributionPath,
                                                FeedPathOption feedPathOption) {

        final String feedNameTemplate = "ACTUAL_BU_NAME-CHANNEL_NAME-DATE-HOURS_MIN_SEC.MLS.xml";

        log.debug("Getting '" + feedPathOption + "' value from '" + feedDistributionPath + "' feed distr path by '" + feedNameTemplate + "' template.");

        if(feedDistributionPath.startsWith(DOUBLE_FILE_PATH_DELIMITER)){
            feedDistributionPath = feedDistributionPath.replace(DOUBLE_FILE_PATH_DELIMITER, "");
        }
        final String FORMAT_OPTION = feedPathOption.toString();
        String[] distributionPathNodes = feedDistributionPath.split(FEED_NAME_DELIMITER);
        int numOfDistributionPathNodes = distributionPathNodes.length;
        String[] pathTemplateNodes = feedNameTemplate.split(FEED_NAME_DELIMITER);
        String result = null;

        for (int i = 0; i < pathTemplateNodes.length; i++) {
            String pathTemplateNode = pathTemplateNodes[i];

                if (pathTemplateNode.contains(FORMAT_OPTION)) {
                    if (numOfDistributionPathNodes <= i) {
                        throw new IllegalArgumentException("Distribution path is too short. " +
                                "Trying to find value for node " + i + ", but only " + (numOfDistributionPathNodes - 1) + " exist.");
                    }

                    String distributionPathNode = distributionPathNodes[i];
                    int beginIndex = pathTemplateNode.indexOf(FORMAT_OPTION);
                    int endIndex = distributionPathNode.length();

                    String suffix = pathTemplateNode.substring(beginIndex + FORMAT_OPTION.length());
                    if (!suffix.isEmpty()) {
                        endIndex = distributionPathNode.indexOf(suffix);
                    }

                    String nextResult = distributionPathNode.substring(beginIndex, endIndex);
                    if (result != null && !nextResult.equals(result)) {
                        throw new IllegalStateException("Advanced keyword has two different values: '" + result + "', '" + nextResult + "'.");
                    }

                    result = nextResult;
                }
            }

        Objects.requireNonNull(result, "Can't find '" + FORMAT_OPTION + "' in template: '" + feedNameTemplate + "'.");

        return result;
    }

    @NotNull
    public static String getFileExtension(@NotNull String filename) {
        int delimiterIndex = filename.lastIndexOf(EXTENSION_SEPARATOR);
        if (delimiterIndex == -1) {
            throw new IllegalArgumentException("'" + filename + "' hasn't valid filename with extension.");
        }

        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
