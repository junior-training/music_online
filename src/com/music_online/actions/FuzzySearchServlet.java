package com.music_online.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.music_online.service.IMusicService;
import com.music_online.serviceImpl.MusicServiceImpl;

public class FuzzySearchServlet extends HttpServlet {

	private static final long serialVersionUID = 911359365969927226L;

	public FuzzySearchServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchKey = request.getParameter("searchKey");
		
		IMusicService aMusicService = new MusicServiceImpl();
		
		ArrayList<String> singerNameList;
		ArrayList<String> albumNameList;
		ArrayList<String> songNameList;
		int length;
		
		StringBuffer searchResultStrbuf = new StringBuffer();
		String searchResultStr;
		
		//获取歌手列表
		singerNameList = aMusicService.fuzzySearch("singer_name",searchKey);
		searchResultStrbuf.append("{\"歌手\":[");
		length = singerNameList.size();
		if(0 == length)
		{
			System.out.println("Singer name list is null!");
			searchResultStrbuf.append("],");
		}
		else
		{
			int i = 0;
			String tmp4;
			while(i < length)
			{
				tmp4 = singerNameList.get(i);
				searchResultStrbuf.append("{\"singerName\":\""+tmp4+"\"}");
				if((length - 1) != i)
					searchResultStrbuf.append(",");
				
				i++;
			}
			searchResultStrbuf.append("],");
		}
		
		//获取专辑列表
		albumNameList = fuzzySearch("album_name",searchKey);
		searchResultStrbuf.append("\"专辑\":[");
		length = albumNameList.size();
		if(0 == length)
		{
			System.out.println("Album name list is null!");
			searchResultStrbuf.append("],");
		}
		else
		{
			int i = 0;
			String tmp4;
			while(i < length)
			{
				tmp4 = albumNameList.get(i);
				searchResultStrbuf.append("{\"albumName\":\""+tmp4+"\"}");
				if((length - 1) != i)
					searchResultStrbuf.append(",");
				
				i++;
			}
			searchResultStrbuf.append("],");
		}
		
		//获取歌曲列表
		songNameList = fuzzySearch("song_name",searchKey);
		searchResultStrbuf.append("\"歌曲\":[");
		length = songNameList.size();
		if(0 == length)
		{
			System.out.println("Song name list is null!");
			searchResultStrbuf.append("]");
		}
		else
		{
			int i = 0;
			String tmp4;
			while(i < length)
			{
				tmp4 = songNameList.get(i);
				searchResultStrbuf.append("{\"songName\":\""+tmp4+"\"}");
				if((length - 1) != i)
					searchResultStrbuf.append(",");
				
				i++;
			}
			searchResultStrbuf.append("]");
		}
		
		searchResultStrbuf.append("}");
		
		searchResultStr = searchResultStrbuf.toString();
        System.out.println(searchResultStr);
		
		response.setCharacterEncoding("utf-8");
        response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(searchResultStr);
		
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
