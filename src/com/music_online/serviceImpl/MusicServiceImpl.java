package com.music_online.serviceImpl;

import java.util.ArrayList;
import java.util.List;

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

}
