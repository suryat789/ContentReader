package com.demo.utils;

import java.util.Collection;
import java.util.List;

public final class CommonUtils {

	private CommonUtils() {}
	
	public static <T> boolean isNull(T object){
		if(object == null)
			return true;
		else
			return false;
	}
	
	public static <T> boolean isListNullOrEmpty(List<T> list){
		if(list == null || list.isEmpty())
			return true;
		else
			return false;
	}
	
	public static <T> boolean isCollectionNullOrEmpty(Collection<T> collection){
		if(collection == null || collection.isEmpty())
			return true;
		else
			return false;
	}
}
