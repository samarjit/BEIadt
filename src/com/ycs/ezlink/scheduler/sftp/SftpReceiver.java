package com.ycs.ezlink.scheduler.sftp;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.ycs.ezlink.util.EzLinkConstant;

public class SftpReceiver {
	private static Logger logger = Logger.getLogger(SftpReceiver.class);
	/**
	 * @param filename The complete filename which will be after receiving file
	 * @param user
	 * @param host
	 * @param password
	 * @param remotFolder
	 * @return
	 */
	public static int getFileSFTP( String filename, String user, String host, String password, String remotFolder)  {
		ResourceBundle rb = ResourceBundle.getBundle(EzLinkConstant.PATH_CONFIG_PROPERTY_FILE_NAME);
		File f = new File(filename);
		logger.error("SFTP file receive start");
		JSch jsch = new JSch();
		Session session = null;
		int error = 0;
		try {
		    session = jsch.getSession(user,host , 22);
		    session.setConfig("StrictHostKeyChecking", "no");
		    session.setPassword(password);
//		    UserInfo ui=new MyUserInfo();
//		      session.setUserInfo(ui);
		    session.connect();

		    Channel channel = session.openChannel("sftp");
		    channel.connect();
		    ChannelSftp sftpChannel = (ChannelSftp) channel;
		    logger.debug("receiving file:"+f.getAbsolutePath());
		    SftpProgressMonitor monitor = new MyProgressMonitor();
		    sftpChannel.cd(remotFolder);
		    sftpChannel.lcd(f.getParent());
			sftpChannel.get(f.getName(), f.getName(),monitor, ChannelSftp.OVERWRITE);
		    sftpChannel.exit();
//		    session.disconnect();
		    logger.error("SFTP file receive successfully");
		} catch (JSchException e) {
		    logger.error("File transfer Exception",e);  //To change body of catch statement use File | Settings | File Templates.
		    error = -1;
		} catch ( SftpException e) {
			logger.error("SFTP Exception",e);
			error = -2;
//		} catch (FileNotFoundException e) {
//			logger.error("File not found to transfer"+filename);
		}finally{
			 
			 session.disconnect();
		}
		return error;
	}
}
