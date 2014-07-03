package com.music_online.pojo;

public class Popularity {
	
	int id;
	
	int listenTime;
	
	int downloadTime;
	
	int popularityDegree;
	
	public Popularity(int id, int lt, int dt,int pp){
		this.id = id;
		this.listenTime = lt;
		this.downloadTime=dt;
		this.popularityDegree=pp;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getListenTime() {
		return listenTime;
	}

	public void setListenTime(int listenTime) {
		this.listenTime = listenTime;
	}

	public int getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(int downloadTime) {
		this.downloadTime = downloadTime;
	}

	public int getPopularityDegree() {
		return popularityDegree;
	}

	public void setPopularityDegree(int popularityDegree) {
		this.popularityDegree = popularityDegree;
	}

}
