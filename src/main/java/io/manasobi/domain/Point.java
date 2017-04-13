package io.manasobi.domain;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Point {

	private long    timestamp;

	private String  tagId;
	private String  tagName;
	private String  type;

	private String  value;

	private String  siteId;
	private String  opcId;
	private String  group_name;

	private int    quality;
	private int    errorCode;
	
	private Map<String,String> attribute = Maps.newHashMap();

}
