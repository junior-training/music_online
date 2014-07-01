package com.music_online.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.music_online.pojo.Music;
import com.music_online.dao.DBConnector;
import java.sql.Connection;




public class MusicDao {
	
	Connection dbconn = null;
    PreparedStatement pstmt =null;
    static ResultSet rs = null;
	
    public ArrayList<Music> getTop10SongsOf3Genres(){
		
		 ArrayList<Music> listMusic = new ArrayList<Music>();
		 try {
				String sql = "select * from tb_music,tb_popularity where tb_music.id=tb_popularity.id and song_genre='华语' order by song_popularity DESC limit 10 ";
				dbconn = DBConnector.getMySQLConnection(null, null, null, "db_music_online", "root", "0926");
				pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()){
					System.out.println("mysql test");
					Music music = new Music();
				    music.setId(rs.getInt("id"));
                    music.setSong_name(rs.getString("song_name"));
                    music.setSinger_name(rs.getString("singer_name"));
                    music.setAlbum_name(rs.getString("album_name"));
                    music.setSong_format(rs.getString("song_format"));
                    music.setSong_addr(rs.getString("song_addr"));
                    music.setImage_addr(rs.getString("image_addr"));
                    music.setLyric_addr(rs.getString("lyric_addr"));
                    music.setSong_genre(rs.getString("song_genre"));
				    listMusic.add(music);
				}
				sql = "select * from tb_music,tb_popularity where tb_music.id=tb_popularity.id and song_genre='欧美' order by song_popularity DESC limit 10 ";
				pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()){
					Music music = new Music();
				    music.setId(rs.getInt("id"));
                    music.setSong_name(rs.getString("song_name"));
                    music.setSinger_name(rs.getString("singer_name"));
                    music.setAlbum_name(rs.getString("album_name"));
                    music.setSong_format(rs.getString("song_format"));
                    music.setSong_addr(rs.getString("song_addr"));
                    music.setImage_addr(rs.getString("image_addr"));
                    music.setLyric_addr(rs.getString("lyric_addr"));
                    music.setSong_genre(rs.getString("song_genre"));
				    listMusic.add(music);
				}
				sql = "select * from tb_music,tb_popularity where tb_music.id=tb_popularity.id and song_genre='日韩' order by song_popularity DESC limit 10";
				pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()){
					Music music = new Music();
				    music.setId(rs.getInt("id"));
                    music.setSong_name(rs.getString("song_name"));
                    music.setSinger_name(rs.getString("singer_name"));
                    music.setAlbum_name(rs.getString("album_name"));
                    music.setSong_format(rs.getString("song_format"));
                    music.setSong_addr(rs.getString("song_addr"));
                    music.setImage_addr(rs.getString("image_addr"));
                    music.setLyric_addr(rs.getString("lyric_addr"));
                    music.setSong_genre(rs.getString("song_genre"));
				    listMusic.add(music);
				}
				rs.close();
			    pstmt.close();  
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					dbconn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			/*int j=0;
			int tmp=listMusic.size();
			Music aMusic;
			while(j<tmp)
			{
				aMusic = listMusic.get(j);
				System.out.println(aMusic.getSong_name());
			}*/
			System.out.println("hello!");
	   return listMusic;
}
    public ArrayList<Music> getTop10SongsOfRank(){
    	
    	ArrayList<Music> listMusic = new ArrayList<Music>();
		 try {
				String sql = "select * from tb_music,tb_popularity where tb_music.id=tb_popularity.id order by song_popularity DESC limit 10";
				dbconn = DBConnector.getMySQLConnection(null, null, null, "db_music_online", "root", "0926");
				pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()){
					Music music = new Music();
				    music.setId(rs.getInt("id"));
                    music.setSong_name(rs.getString("song_name"));
                    music.setSinger_name(rs.getString("singer_name"));
                    music.setAlbum_name(rs.getString("album_name"));
                    music.setSong_format(rs.getString("song_format"));
                    music.setSong_addr(rs.getString("song_addr"));
                    music.setImage_addr(rs.getString("image_addr"));
                    music.setLyric_addr(rs.getString("lyric_addr"));
                    music.setSong_genre(rs.getString("song_genre"));
				    listMusic.add(music);
				}
				rs.close();
			    pstmt.close();  
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					dbconn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	   return listMusic;
    	
    }
public ArrayList<Music> getMoreSongsOf3Genres(String song_genre,int TransmittedNumber){
    	
    	ArrayList<Music> listMusic = new ArrayList<Music>();
		 try {
				String sql = "select * from tb_music,tb_popularity where tb_music.id=tb_popularity.id and song_genre='"+song_genre+"' order by song_popularity DESC limit "+TransmittedNumber+",15";
				dbconn = DBConnector.getMySQLConnection(null, null, null, "db_music_online", "root", "0926");
				pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()){
					Music music = new Music();
				    music.setId(rs.getInt("id"));
                    music.setSong_name(rs.getString("song_name"));
                    music.setSinger_name(rs.getString("singer_name"));
                    music.setAlbum_name(rs.getString("album_name"));
                    music.setSong_format(rs.getString("song_format"));
                    music.setSong_addr(rs.getString("song_addr"));
                    music.setImage_addr(rs.getString("image_addr"));
                    music.setLyric_addr(rs.getString("lyric_addr"));
                    music.setSong_genre(rs.getString("song_genre"));
				    listMusic.add(music);
				}
				rs.close();
			    pstmt.close();  
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					dbconn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	   return listMusic;
     }
public ArrayList<Music> getMoreSongsOfRank(int TransmittedNumber){
	
	ArrayList<Music> listMusic = new ArrayList<Music>();
	 try {
			String sql = "select * from tb_music,tb_popularity where tb_music.id=tb_popularity.id order by song_popularity DESC limit "+TransmittedNumber+",15";
			dbconn = DBConnector.getMySQLConnection(null, null, null, "db_music_online", "root", "0926");
			pstmt = dbconn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()){
				Music music = new Music();
			    music.setId(rs.getInt("id"));
                music.setSong_name(rs.getString("song_name"));
                music.setSinger_name(rs.getString("singer_name"));
                music.setAlbum_name(rs.getString("album_name"));
                music.setSong_format(rs.getString("song_format"));
                music.setSong_addr(rs.getString("song_addr"));
                music.setImage_addr(rs.getString("image_addr"));
                music.setLyric_addr(rs.getString("lyric_addr"));
                music.setSong_genre(rs.getString("song_genre"));
			    listMusic.add(music);
			}
			rs.close();
		    pstmt.close();  
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
   return listMusic;
 }
}
