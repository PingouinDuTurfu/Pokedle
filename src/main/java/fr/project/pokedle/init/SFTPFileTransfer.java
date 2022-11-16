package fr.project.pokedle.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jcraft.jsch.*;
import fr.project.pokedle.init.loader.PokemonItem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SFTPFileTransfer {

    private static final String REMOTE_HOST = "www.pingouinduturfu.fr";
    private static final String USERNAME = "remy";
    private static final String PASSWORD = "pingouin";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;

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
        System.out.println("get " + remoteFile + "success" + "\n" + "Saved to " + localFile +"\n" + "Done");
    }


    public List<PokemonItem> mapFromJSON(File localFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(
                    List.class, PokemonItem.class);
            return objectMapper.readValue(localFile, collectionType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
