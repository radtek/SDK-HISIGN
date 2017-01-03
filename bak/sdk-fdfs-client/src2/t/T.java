/**
 * 
 */
package t;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

/**
 * @author chailiangzhi
 * @date 2016-11-23
 * 
 */
public class T {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FastFileStorageClient storageClient=new DefaultFastFileStorageClient();
		File file =new File("E:\\求面积.jpg");
		InputStream inputStream = new FileInputStream(file);
		long fileSize=1014770;
		Set<MateData> metaDataSet =null;
		storageClient.uploadImageAndCrtThumbImage(inputStream, fileSize, "jpg", metaDataSet);
	}

}
