/**
 * CopyRight @MapleLabs
 */
package com.cisco.acisizer.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.cisco.acisizer.exceptions.AciFileIoException;
import com.cisco.acisizer.exceptions.AciSizingException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @author Mahesh
 *
 */
@Component
@Profile("dev")
public class SizingExecOnWindows implements SizingExecution {
	private static final Logger LOGGER = LoggerFactory.getLogger(SizingExecOnWindows.class);

	@Value("${cisco.remote.host}")
	private String host;
	@Value("${cisco.remote.userName}")
	private String user;
	@Value("${cisco.remote.password}")
	private String password;

	private JSch jsch;
	

	@PostConstruct
	public void init() throws Exception {
		this.jsch = new JSch();
	}

	private Session connect() throws JSchException {
		LOGGER.info("connecting to " + host);
		
		Session session = jsch.getSession(user, host, 22);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(password);
		session.connect();

		LOGGER.info("connected to " + host);
		return session;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cisco.acisizer.util.SizingExecution#writeJsonToFile(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void writeJsonToFile(String toolDirectory, String inputJson, String str_FileName) throws AciFileIoException {
		Channel channel = null;
		Session session = null;
		try {
		session=connect();
			 channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp) channel;
			channelSftp.cd(toolDirectory);
			InputStream obj_InputStream = new ByteArrayInputStream(inputJson.getBytes());
			channelSftp.put(obj_InputStream, toolDirectory + str_FileName);
			channelSftp.exit();
			obj_InputStream.close();
			channel.disconnect();
			session.disconnect();

		} catch (JSchException | SftpException | IOException e) {
			LOGGER.info("Error writing the input Json File");
			e.printStackTrace();
			throw new AciFileIoException("Error writing the input Json File", e.getCause());
			
		}finally{
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cisco.acisizer.util.SizingExecution#executeTool()
	 */
	@Override
	public String executeTool(String directory, String fileName) throws AciSizingException {

		String command1 = directory + "scale-calc " + directory + fileName;
		String result = "";
		Channel channel = null;
		Session session = null;

		try {
			session=connect();
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();
			InputStream inError=channel.getExtInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					result = result + new String(tmp, 0, i);
					LOGGER.debug(result);
				}
				if (channel.isClosed()) {
					LOGGER.info("exit-status: " + channel.getExitStatus());
					break;
				}
			}
			while(true){
				while (inError.available() > 0) {
					int i = inError.read(tmp, 0, 1024);
					if (i < 0)
						break;
					result = result + new String(tmp, 0, i);
					LOGGER.debug(result);
				}
				if (channel.isClosed()) {
					LOGGER.info("exit-status: " + channel.getExitStatus());
					break;
				}
			}
			if(channel.getExitStatus()!=0){
				throw new AciSizingException(ACISizerConstant.ERROR_IN_SIZING+result.split("at")[1].split(",")[0]);
			}
		} catch (JSchException | IOException e) {
			LOGGER.info(ACISizerConstant.ERROR_IN_SIZING);
			e.printStackTrace();
			throw new AciSizingException(ACISizerConstant.ERROR_IN_SIZING, e.getCause());
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}

		LOGGER.info("sizing completed");

		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see com.cisco.acisizer.util.SizingExecution#deleteTheGeneratedFile(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteTheGeneratedFile(String toolDirectory,String fileName) throws AciFileIoException{
		Channel channel = null;
		Session session = null;
		try {
			session=connect();
			 channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp) channel;
			channelSftp.cd(toolDirectory);
			channelSftp.rm(fileName);
			channelSftp.exit();
			channel.disconnect();
			session.disconnect();

		} catch (JSchException | SftpException e) {
			LOGGER.info("Error deleting the input Json File  :"+e.getCause());
			e.printStackTrace();
			throw new AciFileIoException("Error deleting the input Json File", e.getCause());
		}finally{
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
		
	}
}
