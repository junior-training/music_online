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

    
    public int updatePopularity(int id,String type) throws SQLException{ 
    	
    	try {
			    dbconn = DBConnector.getMySQLConnection(null, null, null, "db_music_online", "root", "123456");
				String sql="select audition_time,download_time,song_populartity from tb_popularity where id="+id;
		    	pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(type.equals("listen")){
					
				rs.next();
				int listenTime=rs.getInt("audition_time")+1;
				int popularityDegree=1 + rs.getInt("song_popularity");
				sql="update tb_popularity set audition_time="+listenTime+
	                ",song_popularity="+popularityDegree+" where id="+id;
				
				pstmt = dbconn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				rs.close();
		        pstmt.close();  
		        
		        try {
					dbconn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return -1;
				}
				
		        return 1;
				
				}
				else
					rs.next();
				    int downloadTime=rs.getInt("download_time")+1;
				    int popularityDegree=3 + rs.getInt("song_popularity");
				    sql="update tb_popularity set download_time="+downloadTime+
	                ",song_popularity="+popularityDegree+" where id="+id;
				    
				    pstmt = dbconn.prepareStatement(sql);
				    rs = pstmt.executeQuery();
				    rs.close();
			        pstmt.close();  
			        
			        try {
						dbconn.close();
					} catch (SQLException e) {
						e.printStackTrace();
						return -1;
					}
					
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


