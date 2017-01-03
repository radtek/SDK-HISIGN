package com.hisign.sdk.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 *  SubList截取
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 29, 2013  2:26:38 PM
 * @version 1.0
 */
public class SubListHelper {
	
	/**
	 * sbuList截取,并且删除原list中截取元素,考虑到同步
	 * @param list
	 * @param subSize  子列表大小
	 * @return
	 */
	public synchronized <T> List<T> subList(List<T> list,int subSize){
		List<T> sublist = new ArrayList<T>();
		
		int size = list.size();
		if (size > subSize) {
			sublist.addAll(list.subList(0, subSize));// 参考bug:970
        } else if (size == 0) {
            ;
        } else {
        	sublist.addAll(list);
        }
		
        if (!list.removeAll(sublist)) {
            // canot remove elements from collection
            if (sublist.size() > 0) {
                throw new RuntimeException("can't remove subList");
            }
        }
		
		return sublist;
	}

}
