package com.hisign.sdk.common.util;

import java.io.*;

public class RunCmd extends Thread implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean stdoutFlag;
	public String command;
	public StringBuffer stdout;
	public StringBuffer stderr;
	public boolean result;
	public int exitValue;
	Process proc;
	RunCmd readErr;
	boolean finished;

	/** add this variable to sign if a exception occured* */
	public String exceptionOccured = "";

	RunCmd() {
		stdoutFlag = false;
		command = null;
		stdout = new StringBuffer();
		stderr = new StringBuffer();
		result = false;
		exitValue = -1;
		proc = null;
		readErr = null;
		finished = false;
	}

	RunCmd(boolean flag) {
		stdoutFlag = false;
		command = null;
		stdout = new StringBuffer();
		stderr = new StringBuffer();
		result = false;
		exitValue = -1;
		proc = null;
		readErr = null;
		finished = false;
		stdoutFlag = flag;
	}

	public RunCmd(String s) {
		super(s);
		stdoutFlag = false;
		command = null;
		stdout = new StringBuffer();
		stderr = new StringBuffer();
		result = false;
		exitValue = -1;
		proc = null;
		readErr = null;
		finished = false;
		command = s;
	}

	public RunCmd(String s, boolean flag) {
		super(s);
		stdoutFlag = false;
		command = null;
		stdout = new StringBuffer();
		stderr = new StringBuffer();
		result = false;
		exitValue = -1;
		proc = null;
		readErr = null;
		finished = false;
		command = s;
		stdoutFlag = flag;
	}

	@Override
	public void run() {
		if (command == null) {
			if (proc == null)
				return;
			getStdErr();
		} else {
			result = runCommand(command);
		}
		finished = true;
	}

	public boolean runCommand(String s) {
		Object obj = null;
		Process process;
		try {
			process = Runtime.getRuntime().exec(s);
		} catch (Exception exception) {
			exceptionOccured = "Command did not execute: " + s + " \n"
					+ "Exception: " + exception;
			return false;
		}
		readErr = new RunCmd(stdoutFlag);
		proc = process;
		readErr.proc = process;
		readErr.stderr = stderr;
		readErr.start();
		InputStreamReader inputstreamreader = null;
		try{
			inputstreamreader =	new InputStreamReader(process.getInputStream(),"gbk");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		boolean flag = false;
		boolean flag1 = command.indexOf("/usr/sbin/ping") != -1
				|| command.indexOf("/bin/ping") != -1
				|| command.indexOf("ping -n") != -1;
		try {
			char c = (char) inputstreamreader.read();
			if (c != '\uFFFF')
				systemout(c);
			long l = System.currentTimeMillis();
			while (c != '\uFFFF') {
				if (!inputstreamreader.ready()) {
					try {
						process.exitValue();
						break;
					} catch (IllegalThreadStateException illegalthreadstateexception) {
					}
					try {
						Thread.sleep(100L);
						if (!flag1
								|| System.currentTimeMillis() - l <= 0x2bf20L)
							continue;
						stopCommand();
						break;
					} catch (InterruptedException interruptedexception) {
					}
					continue;
				}
				c = (char) inputstreamreader.read();
				systemout(c);
				if (!flag1 || System.currentTimeMillis() - l <= 0x2bf20L)
					continue;
				stopCommand();
				break;
			}
		} catch (IOException ioexception) {
			exceptionOccured = "Error running command: " + s + " : "
					+ ioexception;
			boolean flag2 = flag;
			return flag2;
		} finally {
			try {
				inputstreamreader.close();
			} catch (IOException ioexception1) {
				System.err.println("RunCmd : Error closing InputStream "
						+ ioexception1);
			}
			process.destroy();
		}
		if ((exitValue = process.exitValue()) == 0)
			flag = true;
		return flag;
	}

	boolean getStdErr() {
		Object obj = null;
		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(proc.getErrorStream()));
		try {
			do
				if (!bufferedreader.ready()) {
					try {
						proc.exitValue();
						break;
					} catch (IllegalThreadStateException illegalthreadstateexception) {
						try {
							Thread.sleep(100L);
						} catch (InterruptedException interruptedexception) {
						}
					}
				} else {
					String s = bufferedreader.readLine();
					systemerr(s + "\n");
				}
			while (true);
		} catch (IOException ioexception) {
			systemerr("Error running command: " + command + " : " + ioexception);
			try {
				bufferedreader.close();
			} catch (IOException ioexception2) {
				System.err.println("RunCmd : Error closing ErrorStream "
						+ ioexception2);
			}
			return false;
		}
		try {
			bufferedreader.close();
		} catch (IOException ioexception1) {
			System.err.println("RunCmd : Error closing InputStream "
					+ ioexception1);
		}
		return true;
	}

	private void systemout(char c) {
		if (stdoutFlag)
			System.out.print(c);
		else
			stdout.append(c);
	}

	private void systemerr(String s) {
		if (stdoutFlag)
			System.err.println(s);
		else
			stderr.append(s);
	}

	public void stopCommand() {
		finished = true;
		stop();
		if (readErr != null)
			readErr.stop();
		if (proc != null)
			proc.destroy();
	}
}