package com.music_online.pojo;

public class Music {

	private int id;
	
	private String song_name;
	
	private String singer_name;
	
	private String album_name;
	
	private String song_format;
	
	private String song_addr;
	
	private String image_addr;
	
	private String lyric_addr;
	
	private String song_genre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSong_name() {
		return song_name;
	}

	public void setSong_name(String songName) {
		song_name = songName;
	}

	public String getSinger_name() {
		return singer_name;
	}

	public void setSinger_name(String singerName) {
		singer_name = singerName;
	}

	public String getAlbum_name() {
		return album_name;
	}

	public void setAlbum_name(String albumName) {
		album_name = albumName;
	}

	public String getSong_format() {
		return song_format;
	}

	public void setSong_format(String songFormat) {
		song_format = songFormat;
	}

	public String getSong_addr() {
		return song_addr;
	}

	public void setSong_addr(String songAddr) {
		song_addr = songAddr;
	}

	public String getImage_addr() {
		return image_addr;
	}

	public void setImage_addr(String imageAddr) {
		image_addr = imageAddr;
	}

	public String getLyric_addr() {
		return lyric_addr;
	}

	public void setLyric_addr(String lyricAddr) {
		lyric_addr = lyricAddr;
	}

	public String getSong_genre() {
		return song_genre;
	}

	public void setSong_genre(String songGenre) {
		song_genre = songGenre;
	}
	
}
