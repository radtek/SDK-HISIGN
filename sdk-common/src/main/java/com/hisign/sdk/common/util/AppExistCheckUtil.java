package com.hisign.sdk.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppExistCheckUtil {

	// 日志
	private static Logger log = LoggerFactory.getLogger(AppExistCheckUtil.class);
	private static FileLock lock = null;
	private static long starttime = System.currentTimeMillis();

	public static void checkAppExist(String appName) {
		String fileName = appName + ".banner";
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			lock = new FileOutputStream(fileName).getChannel().tryLock();

			if (lock == null) {
				System.out.println(appName + " Program is already running ");
				System.exit(0);
			}
		} catch (Exception ex) {
			System.out.println(appName + " Program is already running :" + ex.getMessage());
			System.exit(0);
		}
	}

}