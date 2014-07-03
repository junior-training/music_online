package com.music_online.serviceImpl;

import java.sql.SQLException;
import java.util.ArrayList;

import com.music_online.dao.PopularityDao;
import com.music_online.pojo.Popularity;
import com.music_online.service.IPopularityService;


public class PopularityImpl implements IPopularityService{

	public int updatePopularity(ArrayList<Popularity> pList){
		
		for(int i=0;i<pList.size();i++){
			PopularityDao pd=new PopularityDao();
			try{
				pd.updatePopularity(pList.get(i)) ;
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}
		}
		return 1;
	}
}
