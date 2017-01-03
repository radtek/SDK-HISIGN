/**
 * 
 */
package com.hisign.sdk.fdfs.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import com.hisign.sdk.fdfs.UploadFileStreamSender;
import com.hisign.sdk.fdfs.service.FdfsService;

/**
 * FastDFS服务实现
 * @author chailiangzhi
 * @date 2016-6-23
 * 
 */
public class FdfsServiceImpl implements FdfsService {

	/**
	 * 
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see com.hisign.sdk.fdfs.service.FdfsService#upload(java.io.InputStream, long)
	 */
	@Override
	public String upload(InputStream in, long size) throws Exception {
		FastFileStorageClient storageClient=new DefaultFastFileStorageClient();
		File file =new File("E:\\求面积.jpg");
		InputStream inputStream = new FileInputStream(file);
		long fileSize=1014770;
		Set<MateData> metaDataSet =null;
		storageClient.uploadImageAndCrtThumbImage(inputStream, fileSize, "jpg", metaDataSet);
		
//		ClientGlobal.init("fdfs_client.conf");
//
//		logger.info("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
//		logger.info("charset=" + ClientGlobal.g_charset);
//
//		TrackerClient tracker = new TrackerClient();
//		TrackerServer trackerServer = tracker.getConnection();
//		StorageServer storageServer = null;
//		StorageClient client = new StorageClient(trackerServer, storageServer);
//		String[] results = client.upload_file(null, size, new UploadFileStreamSender(in), null, null);
//		String groupName = results[0];
//		String remoteFileName = results[1];
//
//		logger.info("groupName: " + groupName + ", remoteFileName: " + remoteFileName);
//		return remoteFileName;
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hisign.sdk.fdfs.service.FdfsService#genToken(java.lang.String)
	 */
	@Override
	public String genToken(String fileNameRemote) throws Exception {
		int seconds = (int) (System.currentTimeMillis() / 1000);
		String token = null;//ProtoCommon.getToken(fileNameRemote, seconds, "FastDFS1234567890");
		String tokenUrl = "token=" + token + "&ts=" + seconds;
		return tokenUrl;
	}

}
