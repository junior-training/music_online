package com.music_online.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.music_online.pojo.Music;
import com.music_online.pojo.Popularity;

public class PopularityDao {
	
	Connection dbconn = null;
    PreparedStatement pstmt =null;
    static ResultSet rs = null;

    
    public int updatePopularity(Popularity pop) throws SQLException{ 
    	
    	pop.getId();
    	pop.getListenTime();
    	pop.getDownloadTime();
    	pop.getPopularityDegree();
    	
		 try {
			    dbconn = DBConnector.getMySQLConnection(null, null, null, "db_music_online", "root", "0926");
				String sql="select audition_time,download_time song_populartity from tb_popularity where id="+pop.getId();
		    	pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				int listenTime=pop.getListenTime()+rs.getInt("audition_time");
				int downloadTime=pop.getDownloadTime()+rs.getInt("download_time");
                int popularityDegree=pop.getListenTime()+3*pop.getDownloadTime()+rs.getInt("song_popularity");
				
				sql="update tb_popularity set audition_time="+listenTime+
	            ",download_time="+downloadTime+
	            ",song_popularity="+popularityDegree+"where id="+pop.getId();
				pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				rs.close();
			    pstmt.close();  
			    return 1;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return -1;
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}catch (Exception e) {
				e.printStackTrace();
				return -1;
			}finally{
				try {
					dbconn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
    }
	
}


