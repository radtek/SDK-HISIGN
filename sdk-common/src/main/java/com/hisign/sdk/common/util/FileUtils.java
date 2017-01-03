package com.hisign.sdk.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Title: 文件操作工具类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin
 * @E-mail:lnj2050@hotmail.com
 * @create time：2012-7-26 下午4:19:44
 * @version 1.0
 */
public class FileUtils {

	/**
	 * 清空但不删除文件
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public static void clearFile(String fileName) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			fos.write("".getBytes());
			fos.flush();
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * List directory contents for a resource folder. Not recursive. This is
	 * basically a brute-force implementation. Works for regular files and also
	 * JARs.
	 * 
	 * @author Greg Briggs
	 * @param clazz
	 *            Any java class that lives in the same place as the resources
	 *            you want.
	 * @param path
	 *            Should end with "/", but not start with one.
	 * @return Just the name of each member item, not the full paths.
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
		URL dirURL = clazz.getClassLoader().getResource(path);
		if (dirURL != null && dirURL.getProtocol().equals("file")) {
			/* A file path: easy enough */
			return new File(dirURL.toURI()).list();
		}

		if (dirURL == null) {
			/*
			 * In case of a jar file, we can't actually find a directory. Have
			 * to assume the same jar as clazz.
			 */
			String me = clazz.getName().replace(".", "/") + ".class";
			dirURL = clazz.getClassLoader().getResource(me);
		}

		if (dirURL.getProtocol().equals("jar")) {
			/* A JAR path */
			String jarPath = dirURL.getPath().substring(5,
					dirURL.getPath().indexOf("!")); // strip out only the JAR
													// file
			JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
			Enumeration<JarEntry> entries = jar.entries(); // gives ALL entries
															// in jar
			Set<String> result = new HashSet<String>(); // avoid duplicates in
														// case it is a
														// subdirectory
			while (entries.hasMoreElements()) {
				String name = entries.nextElement().getName();
				if (name.startsWith(path)) { // filter according to the path
					String entry = name.substring(path.length());
					int checkSubdir = entry.indexOf("/");
					if (checkSubdir >= 0) {
						// if it is a subdirectory, we just return the directory
						// name
						entry = entry.substring(0, checkSubdir);
					}
					result.add(entry);
				}
			}
			return result.toArray(new String[result.size()]);
		}

		throw new UnsupportedOperationException("Cannot list files for URL "+ dirURL);
	}
	
    /**
     * 根据类路径获取文件对象
     * @param path
     * @return
     */
    public static File getFileByClassPath(String path,String charSetName) throws Throwable{
        URL url = FileUtils.class.getClassLoader().getResource(path);
        String dirName = URLDecoder.decode(url.getFile(),charSetName);
        File file = new File(dirName);
        return file;
    }
    
    /**
     * 将文件内容写到文件中去
     * @param file
     * @param content
     * @param charSetName 字符集
     * @return
     */
    public static void writeContentToFile(File file,String content,String charSetName) throws Exception{
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes(charSetName));
            fos.flush();
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
            }
        }
    }

}
