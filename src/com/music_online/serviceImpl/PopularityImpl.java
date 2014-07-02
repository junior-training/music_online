package com.music_online.serviceImpl;

import java.util.ArrayList;

import com.music_online.dao.PopularityDao;
import com.music_online.service.IPopularityService;

public class PopularityImpl implements IPopularityService{

	public ArrayList<String> updatePopularity(ArrayList Popularity) {
		
		for(int i=1;i<Popularity.size();i++){
			
			PopularityDao pd=new PopularityDao();
			return pd.updatePopularity(Popularity);
		}
		
		
		return null;
	}

}
