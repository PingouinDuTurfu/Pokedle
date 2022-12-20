package fr.project.pokedle.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jcraft.jsch.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SFTPFileTransfer {

    private final String REMOTE_HOST;
    private final String USERNAME;
    private final String PASSWORD;
    private final int REMOTE_PORT;
    private final int SESSION_TIMEOUT;
    private final int CHANNEL_TIMEOUT;

    public SFTPFileTransfer(String remoteHost, String username, String password, int remotePort, int sessionTimeout, int channelTimeout) {
        this.REMOTE_HOST = remoteHost;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.REMOTE_PORT = remotePort;
        this.SESSION_TIMEOUT = sessionTimeout;
        this.CHANNEL_TIMEOUT = channelTimeout;
    }

    public void getRemoteFile(String remoteFile, String localFile) {
        Session jschSession = null;
        try {
            JSch jsch = new JSch();
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig("StrictHostKeyChecking", "no");
            jschSession.setPassword(PASSWORD);
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;
            channelSftp.get(remoteFile, localFile);

            channelSftp.exit();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (jschSession != null)
                jschSession.disconnect();
        }
        System.out.println("get " + remoteFile + " success. Saved to " + localFile +".");
    }

    public static <T> List<T> mapFromJSON(File localFile, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, clazz);
            return objectMapper.readValue(localFile, collectionType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
