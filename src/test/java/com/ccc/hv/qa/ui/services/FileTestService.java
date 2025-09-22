package com.ccc.hv.qa.ui.services;

import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.file.pojo.ServerCredentials;
import com.ccc.service.xfer.FileTransferConfig;
import com.ccc.service.xfer.FileTransferException;
import com.ccc.service.xfer.FileTransferService;
import com.ccc.service.xfer.FileTransferServiceFactory;
import com.ccc.service.xfer.TransferProtocol;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpDownloadSFTPCreds;
import static com.ccc.hv.qa.file.data.PredefinedSrvCredentials.crushFtpUploadCreds;
import static com.ccc.hv.qa.utils.FileOpsUtils.getAbsoluteResourceFilePath;

/**
 * Created by R0manL on 8/3/22.
 */

public class FileTestService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final TransferProtocol DEFAULT_PROTOCOL = TransferProtocol.SFTP;
    private static final int connectionRetryCount = 3;
    private static final int connectionRetryInterval = 1000;	// 1 second wait between connection retries

    private FileTestService() {
        // NONE
    }

    public static FileTestService getFileTestService() {
        return new FileTestService();
    }

    public void uploadFileToCrushFTPRootFolder(@NotNull Path absFilePath, ServerCredentials creds) {
        uploadFileToCrushFTPRootFolder(DEFAULT_PROTOCOL, absFilePath, creds);
    }

    public void uploadFileToCrushFTPRootFolder(TransferProtocol protocol, @NotNull Path absFilePath, ServerCredentials creds) {
        FileTransferConfig config = getServerConfigFor(protocol, creds);
        uploadFileToCrushFTP(absFilePath, "/", config);
    }

    public void uploadFileToCrushFTPNormalizationFolder(@NotNull Path absFilePath) {
        uploadFileToCrushFTPNormalizationFolder(absFilePath, crushFtpUploadCreds);
    }

    public void uploadFileToCrushFTPNormalizationFolder(@NotNull Path absFilePath, ServerCredentials creds) {
        uploadFileToCrushFTPNormalizationFolder(DEFAULT_PROTOCOL, absFilePath, creds);
    }

    public void uploadFileToCrushFTPNormalizationFolder(TransferProtocol protocol, @NotNull Path absFilePath, ServerCredentials creds) {
        FileTransferConfig config = getServerConfigFor(protocol, creds);
        uploadFileToCrushFTP(absFilePath, "/normalize/", config);
    }

    /**
     * Method upload all files (from Resources sub dir) on crushFTP server.
     * @param relResourceDirPath - relative or path e.g. /assets/smoke.
     */
    public void uploadFilesToCrushFTPRootFolder(@NotNull Path relResourceDirPath) {
        uploadFilesToCrushFTPRootFolder(DEFAULT_PROTOCOL, relResourceDirPath);
    }

    public void uploadFilesToCrushFTPRootFolder(TransferProtocol protocol, @NotNull Path relResourceDirPath) {
        Path localDirectory = getAbsoluteResourceFilePath(relResourceDirPath);
        FileUtils.listFiles(localDirectory.toFile(), TrueFileFilter.TRUE, null)
                .forEach(file -> uploadFileToCrushFTPRootFolder(protocol, file.toPath()));
    }

    public boolean isFileExistsOnSFTPServer(@NotNull String absRemoteFilePath) {
        FileTransferConfig config = getServerConfigFor(DEFAULT_PROTOCOL, crushFtpDownloadSFTPCreds);
        try (FileTransferService service = FileTransferServiceFactory.getService(config, connectionRetryCount, connectionRetryInterval)) {
            return service.exists(absRemoteFilePath);
        } catch (Exception e) {
            throw new IllegalStateException("Can't check if file exist with path: '" + absRemoteFilePath + "'. " +
                    "\nError: " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Download file via SFTP from crushFTP server.
     * @param remoteFile - path to file on remote server.
     * @param localDir - path to local directory.
     * @return - path to downloaded file in the local directory.
     */
    public Path downloadFileFromCrushFTP(@NotNull Path remoteFile, @NotNull Path localDir) {
        FileTransferConfig config = getServerConfigFor(DEFAULT_PROTOCOL, crushFtpDownloadSFTPCreds);
        return downloadFileFromCrushFTP(remoteFile, localDir, config);
    }

    /**
     * Method returns list of files from given assets directory.
     * @param relDirPath - related directory in assets folder.
     */
    public List<File> getListOfFilesFrom(@NotNull Path relDirPath) {
        Path absolutePath = getAbsoluteResourceFilePath(relDirPath);
        File localFile = new File(absolutePath.toString());
        return (List<File>) FileUtils.listFiles(localFile, TrueFileFilter.TRUE, null);
    }

    @NotNull
    public LocalDateTime getFileModifyDateOnCrushFTP(@NotNull String remoteFile) {
        FileTransferConfig config = getServerConfigFor(DEFAULT_PROTOCOL, crushFtpDownloadSFTPCreds);
        try (FileTransferService service = FileTransferServiceFactory.getService(config, connectionRetryCount, connectionRetryInterval)) {
            return service.getModificationTime(remoteFile);
        } catch (Exception e) {
            throw new IllegalStateException("Can't check if file exist with path: '" + remoteFile + "'. " +
                    "\nError: " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void uploadFileToCrushFTPRootFolder(TransferProtocol protocol, @NotNull Path absFilePath) {
        uploadFileToCrushFTPRootFolder(protocol, absFilePath, crushFtpUploadCreds);
    }

    private void uploadFileToCrushFTP(@NotNull Path absFilePath, @NotNull String destDirPath, FileTransferConfig config) {
        log.debug("Upload to crushFTP file: '" + absFilePath + "' > '" + destDirPath + "'.");
        try (FileTransferService service = FileTransferServiceFactory.getService(config, connectionRetryCount, connectionRetryInterval)) {
            service.putFile(new File(absFilePath.toUri()), destDirPath + absFilePath.getFileName().toString());
        } catch (Exception e) {
            throw new IllegalStateException("Fail to upload file to crushFTP. Msg= " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    private Path downloadFileFromCrushFTP(@NotNull Path remoteAbsFilePath, @NotNull Path localDir, FileTransferConfig config) {
       final String remoteFileName = remoteAbsFilePath.getFileName().toString();
       log.debug("Download file: '" + remoteAbsFilePath + "' to local dir: '" + localDir + "'.");
       localDir = getAbsoluteResourceFilePath(localDir);
       File localFile = new File(Paths.get(localDir.toString(), remoteFileName).toUri());

       try (FileTransferService service = FileTransferServiceFactory.getService(config, connectionRetryCount, connectionRetryInterval)) {
           service.getFile(remoteAbsFilePath.toString(), localFile);
           return localFile.toPath();
       } catch (Exception e) {
           throw new IllegalStateException("Fail to download file from crushFTP. Msg= " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
       }
    }

    @NotNull
    private FileTransferConfig getServerConfigFor(TransferProtocol protocol, ServerCredentials creds) {
        FileTransferConfig config;
        try {
            config = new FileTransferConfig(protocol);
            config.setUsername(creds.getUsername());
            config.setPassword(creds.getPassword());
            config.setHostname(ENV_CONFIG.crushFtpHostName());

            switch(protocol) {
                case SFTP:
                    config.setPort(ENV_CONFIG.crushFtpSFTPPort());
                    break;
                case FTP:
                    config.setPort(ENV_CONFIG.crushFtpFTPPort());
                    break;
                case FTPS:
                    config.setPort(ENV_CONFIG.crushFtpFTPSPort());
                    break;
                case UNKNOWN:
                default:
                    throw new FileTransferException("Unknown or unsupported delivery protocol: " + config.getProtocol().getName());
            }
        } catch (FileTransferException e) {
            throw new IllegalStateException("FileTransferException: " + e.getMessage());
        }

        return config;
    }
}
