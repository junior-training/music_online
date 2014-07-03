package com.music_online.serviceImpl;

import java.util.ArrayList;


import com.music_online.dao.MusicDao;
import com.music_online.pojo.Music;
import com.music_online.service.IMusicService;

public class MusicServiceImpl implements IMusicService {

	public ArrayList<Music> getTop10SongsOf3Genres() {

        MusicDao md=new MusicDao();
		return md.getTop10SongsOf3Genres();
	}

	public ArrayList<Music> getTop10SongsOfRank() {

        MusicDao md=new MusicDao();
		return md.getTop10SongsOfRank();
	}

	public ArrayList<Music> getMoreSongsOf3Genres(String song_genre,
			int TransmittedNumber) {
		
		MusicDao md=new MusicDao();
		return md.getMoreSongsOf3Genres(song_genre, TransmittedNumber);
	}

	public ArrayList<Music> getMoreSongsOfRank(int TransmittedNumber) {
		
		MusicDao md=new MusicDao();
		return md.getMoreSongsOfRank(TransmittedNumber);
		
	}

	public ArrayList<Music> accurateSearch(ArrayList searchKeyList) {

		MusicDao md=new MusicDao();
		return md.accurateSearch(searchKeyList);
	}

	public ArrayList<String> fuzzySearch(String type, String Str) {
		
        MusicDao md=new MusicDao();
        return md.fuzzySearch(type, Str);
	}

	public ArrayList<String> getLyricFilePathById(int id) {
	
		MusicDao md=new MusicDao();
		return md.getLyricFilePathById(id);
	}

}
