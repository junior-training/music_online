package com.music_online.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.music_online.pojo.Music;
import com.music_online.service.IMusicService;
import com.music_online.serviceImpl.MusicServiceImpl;


public class DisplayMoreSongsServlet extends HttpServlet {

	private static final long serialVersionUID = -51706688795335192L;

	public DisplayMoreSongsServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		IMusicService aMusicService = new MusicServiceImpl();
		ArrayList<Music> moreSongs;
		int transmittedNumber;
		String typeChinese = null;
		StringBuffer moreSongsStrbuf = new StringBuffer();
		String moreSongsStr = null;
		
		String type = request.getParameter("type");
		String indexStr = request.getParameter("index");
		int index = Integer.parseInt(indexStr);
		
		if(1 == index)
		{
			transmittedNumber = index * 10;
		}
		else 
		{
			transmittedNumber = 10 + (index - 1)*15;
		}
		
		if(type.equals("rank"))
		{
			moreSongs = aMusicService.getMoreSongsOfRank(transmittedNumber);
		}
		else
		{
			if(type.equals("chinese"))
				typeChinese = "华语";
			else if(type.equals("western"))
				typeChinese = "欧美";
			else if(type.equals("jk"))
				typeChinese = "日韩";
			else 
				System.out.println("type can not transform to Chinese!");
			
			moreSongs = aMusicService.getMoreSongsOf3Genres(typeChinese,transmittedNumber);
		}
		
		int length = moreSongs.size();
		Music aMusic;
		
		if(0 == length)
		{
			moreSongsStrbuf.append("[]");
		}
		else 
		{
			moreSongsStrbuf.append("[");
			int i = 0;
        	while(i < length)
        	{
        		aMusic = moreSongs.get(i);
        		moreSongsStrbuf.append("{\"id\":\""+String.valueOf(aMusic.getId())+"\",");
        		moreSongsStrbuf.append("\"title\":\""+aMusic.getSong_name()+"\",");
        		moreSongsStrbuf.append("\"artist\":\""+aMusic.getSinger_name()+"\",");
        		moreSongsStrbuf.append("\"album\":\""+aMusic.getAlbum_name()+"\",");
        		moreSongsStrbuf.append("\"src\":\""+aMusic.getSong_addr()+"\",");
        		moreSongsStrbuf.append("\"img\":\""+aMusic.getImage_addr()+"\"}");
        		
        		if((length - 1) != i)
        			moreSongsStrbuf.append(",");
        		
        		i++;
        	}
        	
        	moreSongsStrbuf.append("]");
		}
		
		moreSongsStr = moreSongsStrbuf.toString();
		System.out.println(moreSongsStr);
		
		response.setCharacterEncoding("utf-8");
        response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(moreSongsStr);
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
