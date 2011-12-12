package com.ycs.ezlink.scheduler.cmd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.log4j.Logger;

import expectj.ExpectJ;
import expectj.ExpectJException;
import expectj.TimeoutException;

public class CmdRunner {

	private static Logger logger = Logger.getLogger(CmdRunner.class);
	
	static class StreamGobbler extends Thread
	{
	    InputStream is;
	    String type;
	    
	    StreamGobbler(InputStream is, String type)
	    {
	        this.is = is;
	        this.type = type;
	    }
	    
		public void run() {
			try {
				System.out.println("in run!");
//				InputStreamReader isr = new InputStreamReader(is);
//				BufferedReader br = new BufferedReader(isr);
//				String line = null;
//				while ((line = br.readLine()) != null) {
//					if (type.equals("ERROR")) {
//						logger.error(type + ">" + line);
//					} else {
//						logger.info(type + ">" + line);
//					}
//				}
				    System.out.println(type);
					final byte[] buffer = new byte[1];
					for (int length = 0; (length = is.read(buffer)) != -1;) {
						System.out.write(buffer, 0, length);
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
		}
	}
	
	public static int process(String cmd){
		int exitVal = 0; 
		try {
			Runtime rt = Runtime.getRuntime();
			 Process proc = rt.exec(cmd);
			 // any error message?
			 StreamGobbler errorGobbler = new 
			     StreamGobbler(proc.getErrorStream(), "ERROR");            
			 
			 // any output?
			 StreamGobbler outputGobbler = new 
			     StreamGobbler(proc.getInputStream(), "OUTPUT");
			 PrintWriter pr = new PrintWriter(proc.getOutputStream());
			 pr.append("y\r\n");
			 
			 // kick them off
			 errorGobbler.start();
			 outputGobbler.start();
			                         
			 // any error???
			 System.out.println("Waiting for cmd process to complete " );
			exitVal = proc.waitFor();
			 System.out.println("ExitValue: " + exitVal);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}    
		return exitVal;
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 * @throws ExpectJException 
	 */
	public static void main1(String[] a) throws IOException, InterruptedException, TimeoutException, ExpectJException {
		//gpg.exe --passphrase-file C:/Eclipse/workspace2/EzLinkBE/src/sAtw.passphrase -c --output MTR241_20111124.htm.gpg MTR241_20111124.htm
		String gzipCmd = "gpg.exe  --passphrase-file C:/Eclipse/workspace2/EzLinkBE/src/sAtw.passphrase --decrypt --output C:/Eclipse/workspace2/sync_inbox/MTR241_20111124.htm C:/Eclipse/workspace2/sync_inbox/MTR241_20111124.htm.gpg";	
		//CmdRunner.process(gzipCmd);
//		ProcessBuilder pb = new ProcessBuilder("gpg", "--passphrase-file", "C:/Eclipse/workspace2/EzLinkBE/src/sAtw.passphrase", "--decrypt","--output","C:/Eclipse/workspace2/sync_inbox/MTR241_20111124.htm","C:/Eclipse/workspace2/sync_inbox/MTR241_20111124.htm.gpg");
//		System.out.println(pb.command());
//		pb.redirectErrorStream(true);
//		Process proc = pb.start();
//		
//		ExpectJ expectinator = new ExpectJ(5);

		// Fork the process
//		Spawn shell = expectinator.spawn("cmd");
//
//		// Talk to it
//		shell.send(gzipCmd+"\n");
//		shell.expect("y");
//		shell.send("y\n");
//		shell.expectClose();
		ProcessBuilder pb = new ProcessBuilder ("cmd");
//		pb.directory(new File());
		pb.redirectErrorStream(true);
		Process process = pb.start();
		OutputStream stdin = process.getOutputStream ();
		InputStream stderr = process.getErrorStream ();
		InputStream stdout = process.getInputStream ();
		 StreamGobbler errorGobbler = new StreamGobbler(stderr, "ERROR");            
		 errorGobbler.start();
//		BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
		 StreamGobbler stdoutGobbler = new StreamGobbler(stdout, "DEBUG");            
		 stdoutGobbler.start();
		 
		 BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
		 String line;
		 while ((line = scan.readLine())!= null) {
			    String input = line;// scan.nextLine();
			    System.out.println("inp>"+input);
			    if (input.trim().equals("exit")) {
			        // Putting 'exit' amongst the echo --EOF--s below doesn't work.
			        stdin.write("exit\r\n".getBytes()); 
			        stdin.flush();
			        break;
			    } else {
			    	 stdin.write((input+"\r\n").getBytes());
//			        writer.write("((" + input + ") && echo --EOF--) || echo --EOF--\n");
			    	 stdin.flush();
			    }

//			    line = reader.readLine();
//			    while (line != null && ! line.trim().equals("--EOF--")) {
//			        System.out.println ("Stdout: " + line);
//			        line = reader.readLine();
//			    }
//			    if (line == null) {
//			        break;
//			    }
			}
		 System.out.println("exited..");
//		 System.out.println(TestProcessIO.isAlive(process));
		int returnCode  = process.waitFor();
		System.out.println(returnCode);
/*		String gzipCmd = "--passphrase-file,C:/Eclipse/workspace2/EzLinkBE/src/sAtw.passphrase,--decrypt,--output,C:/Eclipse/workspace2/sync_inbox/MTR241_20111124.htm,C:/Eclipse/workspace2/sync_inbox/MTR241_20111124.htm.gpg";
			Commandline commandLine = new Commandline();
			commandLine.setExecutable( "sh.exe" );
//			commandLine.createArg().setValue( "/c" );
			commandLine.createArg().setValue( "gpgrun.sh" );
			
			commandLine.setWorkingDirectory( new File("C:\\Eclipse\\workspace2\\EzLinkBE\\src"));

			//		    commandLine.setExecutable(gzipCmd);

//		    List<String> args = Arrays.asList(gzipCmd.split(","));
//
//		    for (String arg : args) {
//		        Arg _arg = commandLine.createArg();
//		        _arg.setValue(arg);
//		    }
//	
			WriterStreamConsumer systemOut = new WriterStreamConsumer(new OutputStreamWriter(System.out));
		    WriterStreamConsumer systemErr = new WriterStreamConsumer(new OutputStreamWriter(System.out));

		int returnCode = 0;
		try {
			returnCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr, 10);
		} catch (CommandLineException e) {
			e.printStackTrace();
		}
		            if (returnCode != 0) {
						   // bad
		            	System.out.println("bad!"+returnCode);
						} else {
						  // good
							System.out.println("good!"+returnCode);
						}*/
	}
	public static void main(String[] a) throws IOException, InterruptedException, TimeoutException, ExpectJException {
		ProcessBuilder pb = new ProcessBuilder ("cmd");
		pb.redirectErrorStream(true);
		Process process = pb.start();
		OutputStream stdin = process.getOutputStream ();
		InputStream stderr = process.getErrorStream ();
		InputStream stdout = process.getInputStream ();
		 StreamGobbler errorGobbler = new StreamGobbler(stderr, "ERROR");            
		 errorGobbler.start();
		 StreamGobbler stdoutGobbler = new StreamGobbler(stdout, "DEBUG");            
		 stdoutGobbler.start();
		 
		 BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
		 String line;
		 while ((line = scan.readLine())!= null) {
			    String input = line;
			    System.out.println("inp>"+input);
			    if (input.trim().equals("exit")) {
			        stdin.write("exit\r\n".getBytes()); 
			        stdin.flush();
			        break;
			    } else {
			    	 stdin.write((input+"\r\n").getBytes());
			    	 stdin.flush();
			    }
			}
		 System.out.println("exited..");
		int returnCode  = process.waitFor();
		System.out.println(returnCode);
	}
}
