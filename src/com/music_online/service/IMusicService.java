package com.music_online.service;

import java.util.ArrayList;


import com.music_online.pojo.Music;

public interface IMusicService {
	
	public ArrayList<Music> getTop10SongsOf3Genres();
	
	public ArrayList<Music> getTop10SongsOfRank();

}
