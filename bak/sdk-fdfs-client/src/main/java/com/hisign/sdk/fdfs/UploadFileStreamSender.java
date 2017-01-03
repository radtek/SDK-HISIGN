package com.hisign.sdk.fdfs;

/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
*/

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
* upload file callback class, local file sender
* @author Happy Fish / YuQing
* @version Version 1.0
*/
public class UploadFileStreamSender {//implements UploadCallback {
	
	/**
	 * 
	 */
	private InputStream inputStream;

	/**
	 * @param inputStream
	 */
	public UploadFileStreamSender(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	* send file content callback function, be called only once when the file uploaded
	* @param out output stream for writing file content
	* @return 0 success, return none zero(errno) if fail
	*/
	public int send(OutputStream out) throws IOException {
		int readBytes;
		byte[] buff = new byte[256 * 1024];
		try {
			while ((readBytes = inputStream.read(buff)) >= 0) {
				if (readBytes == 0) {
					continue;
				}
				out.write(buff, 0, readBytes);
			}
		} finally {
			inputStream.close();
		}
		return 0;
	}
}
