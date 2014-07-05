package com.music_online.service;

import com.music_online.pojo.LrcInfo;

public interface ILrcParserService {

	public LrcInfo parser(String path) throws Exception;
	
}
