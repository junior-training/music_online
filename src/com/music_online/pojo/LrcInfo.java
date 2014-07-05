package com.music_online.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class LrcInfo {
	private String title;
	private String singer;
	private String album;
	
	private HashMap<Long, String> infos;
	private ArrayList<Long> timestamp;
	
	public ArrayList<Long> getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ArrayList<Long> timestamp) {
		this.timestamp = timestamp;
	}

	public String getJsonOfLrc() {
		String lrcInJson = "";

		Comparator<Long> comparator = new Comparator<Long>() {
			public int compare (Long l1, Long l2) {
				return (int) (l1 - l2);
			}
		};
		Collections.sort(timestamp, comparator);
		
		lrcInJson += "[";
		int arraySize = timestamp.size();
		int lastElement = arraySize - 1;
		
		for (int i = 0; i < arraySize; i++) {
			Long time = timestamp.get(i);
			String value = infos.get(time);
			if (value.isEmpty()) continue;
			
			double secs = Double.parseDouble(String.valueOf(time));
			secs /= 1000;
			
			lrcInJson = lrcInJson + "{" + "\"timeline\"" + ":" + secs + ",";
			lrcInJson = lrcInJson + "\"text\"" + ":" + "\"" + value + "\"" + "}";
			if (i != lastElement) lrcInJson += ",";
		}
		lrcInJson += "]";
		return lrcInJson;

	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public HashMap<Long, String> getInfos() {
		return infos;
	}
	public void setInfos(HashMap<Long, String> infos) {
		this.infos = infos;
	}
	
	
	
}

