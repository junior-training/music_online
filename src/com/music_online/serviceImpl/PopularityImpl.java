package com.music_online.serviceImpl;




import com.music_online.dao.PopularityDao;

import com.music_online.service.IPopularityService;


public class PopularityImpl implements IPopularityService{

	public int updatePopularity(int id , String type){
		
		
			PopularityDao pd=new PopularityDao();
			try{
				pd.updatePopularity(id, type) ;
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}
		
		return 1;
	}
}
