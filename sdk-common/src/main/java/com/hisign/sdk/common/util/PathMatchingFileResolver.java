package com.hisign.sdk.common.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 *  claspath映射文件解析器
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：2012-6-1  16:32:44
 * @version 1.0
 */
public class PathMatchingFileResolver {
	
	//日志
	private static Logger log = LoggerFactory.getLogger(PathMatchingFileResolver.class);
	
	private final static String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
	
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	/**
	 * 获取类路径下指定文件,参照spring格式路径
	 * @param locationPattern
	 * @return
	 * @throws Exception
	 */
	public Set<File> getFiles(String locationPattern) throws Exception{
    	if (locationPattern.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
			if (pathMatcher.isPattern(locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()))) {
				return findPathMatchingResources(locationPattern);
			}
			else {
				return findAllClassPathResources(locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()));
			}
		}
		else {
			int prefixEnd = locationPattern.indexOf(":") + 1;
			if (pathMatcher.isPattern(locationPattern.substring(prefixEnd))) {
				log.debug("-------------1-----------");
				return findPathMatchingResources(locationPattern);
			}
			else {
				Set<File> result = new LinkedHashSet<File>(1);
				File file = ResourceUtils.getFile(locationPattern);
				result.add(file);
				return result;
			}
		}
    }
    
	/**
	 * 查找匹配文件
	 * @param locationPattern
	 * @return
	 */
    private Set<File> findPathMatchingResources(String locationPattern) throws IOException{
    	String rootDirPath = determineRootDir(locationPattern);
    	log.debug("-------------2----rootDirPath="+rootDirPath);
		String subPattern = locationPattern.substring(rootDirPath.length());
		log.debug("-------------2----subPattern="+subPattern);
		Set<File> result = this.findMatchFile(rootDirPath, subPattern);
		return result;
    }
    
    /**
     * 判定根目录
     * @param location
     * @return
     */
    private String determineRootDir(String location) {
		int prefixEnd = location.indexOf(":") + 1;
		int rootDirEnd = location.length();
		while (rootDirEnd > prefixEnd && pathMatcher.isPattern(location.substring(prefixEnd, rootDirEnd))) {
			rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
		}
		if (rootDirEnd == 0) {
			rootDirEnd = prefixEnd;
		}
		return location.substring(0, rootDirEnd);
	}
    
    /**
     * 查找匹配文件
     * @param rootDirPath
     * @param subPattern
     * @return
     * @throws IOException
     */
    private Set<File> findMatchFile(String rootDirPath,String subPattern) throws IOException{
    	String path = rootDirPath;
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		
		log.debug("-------------3----path="+path);
		Set<File> result = new LinkedHashSet<File>(16);
		URL url = this.getClass().getClassLoader().getResource(path);
		log.debug("-------------3----url="+url);
		String dirName = URLDecoder.decode(url.getFile(),"utf-8");
		log.debug("-------------3----url.getFile()="+url.getFile()+",  dirName="+dirName);
		File dirPath = new File(dirName);
		if(dirPath.isDirectory()){
			File []files = dirPath.listFiles();
			for(int i = 0; i < files.length; i++){
				File file = files[i];
				log.debug("-------------3----subPattern="+subPattern+",file.getName()="+file.getName());
				if(pathMatcher.match(subPattern, file.getName())){ //如果匹配上了
					result.add(file);
				}
			}
		}else {
			log.debug("-------------3----subPattern="+subPattern+",dirPath.getName()="+dirPath.getName());
			if(pathMatcher.match(subPattern, dirPath.getName())){ //如果匹配上了
				result.add(dirPath);
			}
		}
		
		return result;
    }
   
    /**
     * 找到类路径下的所有资源
     * @param location
     * @return
     * @throws IOException
     */
	private Set<File> findAllClassPathResources(String location) throws IOException {
		String path = location;
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		Set<File> result = new LinkedHashSet<File>(16);
		URL url = this.getClass().getClassLoader().getResource(path);
		System.out.println(url.getFile());
		File dirPath = ResourceUtils.getFile(path);
		if(dirPath.isDirectory()){
			File []files = dirPath.listFiles();
			for(int i = 0; i < files.length; i++){
				File file = files[i];
				result.add(file);
			}
		}else {
			result.add(dirPath);
		}
		return result;
	}

	public static void main(String []args){
		PathMatchingFileResolver resolver = new PathMatchingFileResolver();
		try {
			Set<File> result = resolver.getFiles("/fault/*");
			System.out.println(""+result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
