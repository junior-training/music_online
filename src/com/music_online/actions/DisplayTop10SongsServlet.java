package com.music_online.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.music_online.service.IMusicService;
import com.music_online.pojo.Music;
import com.music_online.serviceImpl.MusicServiceImpl;


public class DisplayTop10SongsServlet extends HttpServlet {

	private static final long serialVersionUID = 1272384712306875580L;

	
	public DisplayTop10SongsServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("step1!");
		IMusicService aMusicService = new MusicServiceImpl();
        ArrayList<Music> top10SongsOf3Genres = aMusicService.getTop10SongsOf3Genres();
        ArrayList<Music> top10SongsOfRank;
        
        String top10SongsOfAllTypesStr;
        StringBuffer top10SongsOfAllTypesStrbuf = new StringBuffer();
        Music aMusic;
		
        if(0 == top10SongsOf3Genres.size())
        {
        	System.out.println("top10SongsOf3Genres is null!");
        }
        else
        {
        	int i = 0;
        	while(i < 30)
        	{
        		if(0 == i)
        		{
        			top10SongsOfAllTypesStrbuf.append("{\"chinese\":[");
        		}
        		else if(10 == i)
        		{
        			top10SongsOfAllTypesStrbuf.append("],\"western\":[");
        		}
        		else if(20 == i)
        		{
        			top10SongsOfAllTypesStrbuf.append("],\"jk\":[");
        		}
        		else
        		{
        			top10SongsOfAllTypesStrbuf.append(",");
        		}
        		
        		aMusic = top10SongsOf3Genres.get(i);
        		top10SongsOfAllTypesStrbuf.append("{\"id\":\""+String.valueOf(aMusic.getId())+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"title\":\""+aMusic.getSong_name()+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"artist\":\""+aMusic.getSinger_name()+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"album\":\""+aMusic.getAlbum_name()+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"src\":\""+aMusic.getSong_addr()+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"img\":\""+aMusic.getImage_addr()+"\"}");
        		
        		i++;
        	}
        	
        	top10SongsOfAllTypesStrbuf.append("],");
        }
        
        //将排行榜中的前十首添加到JSON字符串中
        top10SongsOfAllTypesStrbuf.append("\"rank\":[");
        top10SongsOfRank = aMusicService.getTop10SongsOfRank();
        if(0 == top10SongsOfRank.size())
        {
        	System.out.println("top10SongsOfRank is null!");
        }
        else
        {
        	int i = 0;
        	while(i < 10)
        	{
        		aMusic = top10SongsOfRank.get(i);
        		top10SongsOfAllTypesStrbuf.append("{\"id\":\""+String.valueOf(aMusic.getId())+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"title\":\""+aMusic.getSong_name()+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"artist\":\""+aMusic.getSinger_name()+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"album\":\""+aMusic.getAlbum_name()+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"src\":\""+aMusic.getSong_addr()+"\",");
        		top10SongsOfAllTypesStrbuf.append("\"img\":\""+aMusic.getImage_addr()+"\"}");
        		
        		if(9 != i)
        			top10SongsOfAllTypesStrbuf.append(",");
        		
        		i++;
        	}
        	
        	top10SongsOfAllTypesStrbuf.append("]}");
        }
        
        
        top10SongsOfAllTypesStr = top10SongsOfAllTypesStrbuf.toString();
        System.out.println(top10SongsOfAllTypesStr);
        
        //将组装好的JSON字符串发给前台
		String searchResult = "{\"chinese\":[{\"id\":\"1\",\"title\":\"一眼万年\",\"artist\":\"S.H.E\",\"album\":\"《天外飞仙》主题曲\"," +
		"\"src\":\"http://localhost:8080/music_online/WebRoot/song_files/一眼万年-SHE.mp3\"," +
		"\"img\":\"http://localhost:8080/music_online/WebRoot/image_files/天外飞仙主题曲-SHE.png\"}]}";
		
        response.setCharacterEncoding("utf-8");
        response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(top10SongsOfAllTypesStr);
        
        
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
