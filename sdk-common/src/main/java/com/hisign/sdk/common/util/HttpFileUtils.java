package com.hisign.sdk.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP上传下载文件工具
 * @author chailiangzhi
 * @date 2016-9-23
 * 
 */
public class HttpFileUtils {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(HttpFileUtils.class);

	/**
	 * HTTP上传文件，可以一次传多个文件
	 * @param actionUrl
	 * @param uploadFiles
	 * @return
	 */
	public static String uploadFile(String actionUrl, File[] uploadFiles) {
		FileInfo[] fileInfos = new FileInfo[uploadFiles.length];
		try {
			for (int i = 0; i < uploadFiles.length; i++) {
				File uploadFile = uploadFiles[i];
				//				MagicMatch match = Magic.getMagicMatch(uploadFile, false, true);
				//				String contentType = match.getMimeType();
				String contentType = "unknown";
				String fileName = uploadFile.getName();
				FileInputStream fileStream;
				fileStream = new FileInputStream(uploadFile);
				FileInfo fileInfo = new FileInfo();
				fileInfo.setFileName(fileName);
				fileInfo.setContentType(contentType);
				fileInfo.setFileStream(fileStream);
				fileInfos[i] = fileInfo;
			}
			return uploadFile(actionUrl, fileInfos);
		} catch (Exception e) {
			logger.error("上传文件异常", e);
			return null;
		}
	}

	/**
	 * HTTP上传文件，可以一次传多个文件
	 * @param actionUrl
	 * @param fileInfos
	 * @return
	 */
	public static String uploadFile(String actionUrl, FileInfo[] fileInfos) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		DataOutputStream ds = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			// 统一资源
			URL url = new URL(actionUrl);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpURLConnection.setDoInput(true);
			// 设置是否向httpUrlConnection输出
			httpURLConnection.setDoOutput(true);
			// Post 请求不能使用缓存
			httpURLConnection.setUseCaches(false);
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码连接参数
			//			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Connection", "keep-alive");
			// 设置字符编码
			//			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Accept", "*/*");
			// 设置请求内容类型
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			// 设置DataOutputStream
			OutputStream outputStream = httpURLConnection.getOutputStream();
			ds = new DataOutputStream(outputStream);
			for (int i = 0; i < fileInfos.length; i++) {
				FileInfo fileInfo = fileInfos[i];
				//				MagicMatch match = Magic.getMagicMatch(uploadFile, false, true);
				String contentType = fileInfo.getContentType();
				String filename = fileInfo.getFileName();
				ds.writeBytes(twoHyphens + boundary + end);
				//" + i + "
				ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\"; filename=\"" + filename + "\"" + end);
				ds.writeBytes("Content-Type: " + contentType + end);
				ds.writeBytes(end);
				OutStreamCallback outStreamCallback = fileInfo.getOutStreamCallback();
				if (outStreamCallback != null) {
					outStreamCallback.writeStream(outputStream);
				} else {
					InputStream fStream = fileInfo.getFileStream();
					int bufferSize = 1024;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					while ((length = fStream.read(buffer)) != -1) {
						ds.write(buffer, 0, length);
					}
					/* close streams */
					fStream.close();
				}
				ds.writeBytes(end);
			}
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			ds.flush();
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is "
						+ httpURLConnection.getResponseCode());
			}
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpURLConnection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				tempLine = null;
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
					resultBuffer.append("\n");
				}
			}
			return resultBuffer.toString();
		} catch (Exception e) {
			logger.error("上传文件异常", e);
			return null;
		} finally {
			if (ds != null) {
				try {
					ds.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
	}

	/**
	 * HTTP方式下载文件
	 * @param urlPath
	 *            下载路径
	 * @param downloadDir
	 *            下载存放目录
	 * @return 返回下载文件
	 */
	public static File downloadFile(String urlPath, String downloadDir, String fileName) {
		InputStream inputStream = downloadFileStream(urlPath);
		BufferedInputStream bin = new BufferedInputStream(inputStream);
		String path = downloadDir + File.separatorChar + fileName;
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			OutputStream out = new FileOutputStream(file);
			int size = 0;
			//			int len = 0;
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) {
				//				len += size;
				out.write(buf, 0, size);
				// 打印下载百分比
				// logger.info("下载了-------> " + len * 100 / fileLength +
				// "%\n");
			}
			bin.close();
			out.close();
		} catch (Exception e) {
			logger.error("下载文件异常", e);
		}
		return file;
	}

	/**
	 * HTTP方式下载文件
	 * @param urlPath
	 *            下载路径
	 * @return 返回下载文件流
	 */
	public static InputStream downloadFileStream(String urlPath) {
		try {
			// 统一资源
			URL url = new URL(urlPath);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设定请求的方法，默认是GET
			//			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();
			// 文件大小
			int fileLength = httpURLConnection.getContentLength();
			// 文件名
			//			String filePathUrl = httpURLConnection.getURL().getFile();
			//			String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
			logger.info("file length---->" + fileLength);
			//			URLConnection con = url.openConnection();
			return httpURLConnection.getInputStream();
		} catch (MalformedURLException e) {
			logger.error("下载文件异常", e);
		} catch (IOException e) {
			logger.error("下载文件异常", e);
		}
		return null;
	}

	/**
	 * 删除文件服务器的文件
	 * @param fileId
	 * @param fileServerAddr
	 * @return
	 */
	public static String deleteFile(String fileId, String fileServerAddr) {
		String url = fileServerAddr + "/api/files/" + fileId + "/delete";
		try {
			return HttpUtils.simpleHttp(url, "DELETE");
		} catch (Exception e) {
			logger.error("删除文件异常", e);
			return null;
		}
	}

	/**
	 * 文件信息
	 * @author chailiangzhi
	 * @date 2016-11-18
	 * 
	 */
	public static class FileInfo {
		/**
		 * 文件名
		 */
		private String fileName;
		/**
		 * 文件类型
		 */
		private String contentType;
		/**
		 * 文件流
		 */
		private InputStream fileStream;
		/**
		 * 往文件服务器输出文件流回调接口
		 */
		private OutStreamCallback outStreamCallback;

		/**
		 * @return the fileName
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * @param fileName the fileName to set
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * @return the contentType
		 */
		public String getContentType() {
			return contentType;
		}

		/**
		 * @param contentType the contentType to set
		 */
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		/**
		 * @return the fileStream
		 */
		public InputStream getFileStream() {
			return fileStream;
		}

		/**
		 * @param fileStream the fileStream to set
		 */
		public void setFileStream(InputStream fileStream) {
			this.fileStream = fileStream;
		}

		/**
		 * @return the outStreamCallback
		 */
		public OutStreamCallback getOutStreamCallback() {
			return outStreamCallback;
		}

		/**
		 * @param outStreamCallback the outStreamCallback to set
		 */
		public void setOutStreamCallback(OutStreamCallback outStreamCallback) {
			this.outStreamCallback = outStreamCallback;
		}

	}

	/**
	 * 往文件服务器输出文件流回调接口
	 * @author chailiangzhi
	 * @date 2016-11-18
	 * 
	 */
	public static interface OutStreamCallback {

		/**
		 * 往文件服务器输出文件流回调方法
		 * @param outStream
		 */
		public void writeStream(OutputStream outStream);
	}

	public static void main(String[] args) {
		System.getProperties().setProperty("http.proxyHost", "127.0.0.1");
		System.getProperties().setProperty("http.proxyPort", "8888");
		//downloadFile("http://172.16.0.96:3000/api/files/582eb2204bcdb30001a552ce/download", "e:", "d1.pdf");
		File[] upFiles = new File[] { new File("e:/ff.png") };
		//		File[] upFiles = new File[] { new File("e:/d1.pdf") };
		//		File[] upFiles = new File[] { new File("e:/apphisigndebug.apk") };
		//		File[] upFiles = new File[] { new File("e:/鉴定书20161223.doc") };
		//		String ret = uploadFile("http://127.0.0.1:8833/omp/api/file/upload", upFiles);
		String ret = uploadFile("http://192.168.40.30:58820/fdfs/api/file/upload", upFiles);
		//		String ret = uploadFile("http://172.16.0.96:3000/api/files/upload", upFiles);

		//		String ret = deleteFile("http://192.168.40.30:58820/fdfs/api/file/delete/M00/00/03/rBAAcFhk0CyARFsFAAAs9wfjCqo026.png");
		System.out.println(ret);
		System.out.println("1");
	}
}
