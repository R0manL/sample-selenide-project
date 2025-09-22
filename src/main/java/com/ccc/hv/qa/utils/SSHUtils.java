package com.ccc.hv.qa.utils;

import com.jcraft.jsch.*;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

/**
 * Created by R0manL on 8/5/22.
 */

public class SSHUtils {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private SSHUtils() {
        // NONE
    }

    public static void executeCommandViaSSH(@NotNull String host,
                                            @NotNull String username,
                                            @NotNull String pass,
                                            @NotNull String command) {
        Session session = null;
        Channel channel = null;

        try {
            final int CONNECTION_TIMEOUT = 0; // Note. Default connection timeout = 50s.
            JSch jsch = new JSch();

            session = jsch.getSession(username, host, 22);
            session.setPassword(pass);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            channel.connect(CONNECTION_TIMEOUT);

            StringBuilder outputBuffer = new StringBuilder();
            StringBuilder errorBuffer = new StringBuilder();
            InputStream in = channel.getInputStream();
            InputStream err = channel.getExtInputStream();

            channel.connect(CONNECTION_TIMEOUT);
            log.debug("Execute command: '" + command + "' on host: '" + host + "'.");

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    outputBuffer.append(new String(tmp, 0, i));
                }

                while (err.available() > 0) {
                    int i = err.read(tmp, 0, 1024);
                    if (i < 0) break;
                    errorBuffer.append(new String(tmp, 0, i));
                }

                if (channel.isClosed()) {
                    if ((in.available() > 0) || (err.available() > 0)) continue;
                    log.debug("ssh exit-status: " + channel.getExitStatus() + " ");
                    break;
                }
            }

            log.debug("Command execution output:", outputBuffer.toString());
            log.debug("Command execution errors:", errorBuffer.toString());

            channel.disconnect();
            session.disconnect();
        } catch (JSchException | IOException e) {
            log.warn("JSchException. Details:", e.getMessage());
        } finally {
            if (channel != null) { channel.disconnect(); }
            if (session != null) { session.disconnect(); }
        }
    }
}
