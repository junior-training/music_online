package com.music_online.service;

import java.util.ArrayList;


import com.music_online.pojo.Music;

public interface IMusicService {
	
	public ArrayList<Music> getTop10SongsOf3Genres();
	
	public ArrayList<Music> getTop10SongsOfRank();
	
	public ArrayList<Music> getMoreSongsOf3Genres(String song_genre,int TransmittedNumber);
	
	public ArrayList<Music> getMoreSongsOfRank(int TransmittedNumber);
	
	public ArrayList<Music> accurateSearch(ArrayList searchKeyList);

}
