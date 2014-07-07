package com.music_online.actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.music_online.pojo.LrcInfo;
import com.music_online.service.ILrcParserService;
import com.music_online.service.IMusicService;
import com.music_online.serviceImpl.LrcParserServiceImpl;
import com.music_online.serviceImpl.MusicServiceImpl;


public class GetLyricServlet extends HttpServlet {

	
	private static final long serialVersionUID = -7574711604148589668L;
	
	
	public GetLyricServlet() {
		super();
	}

	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Step in lyric!");
		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);
		
		IMusicService aMusicService = new MusicServiceImpl();
		ILrcParserService aLrcParserService = new LrcParserServiceImpl();
		
		ArrayList<String> lyricFilePathList = aMusicService.getLyricFilePathById(id);
		String lyricFilePath = lyricFilePathList.get(0);
		
		//System.out.println(lyricFilePath);
		

		String curProjectPath = request.getSession().getServletContext().getRealPath("/");
		//System.out.println(curProjectPath);
		
		lyricFilePath = curProjectPath + "/" + lyricFilePath;
		System.out.println(lyricFilePath);
		File lyricFile = new File(lyricFilePath);
		
		String lyricInfoJson;
		
		if(lyricFile.exists())
		{
			LrcInfo aLrcInfo = null;
			try{
			aLrcInfo = aLrcParserService.parser(lyricFilePath);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			lyricInfoJson = aLrcInfo.getJsonOfLrc();
			System.out.println(lyricInfoJson);
		}
		else
		{
			lyricInfoJson = "[]";
			System.out.println(lyricInfoJson);
		}
		
		
		
		
		
		response.setCharacterEncoding("utf-8");
        response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(lyricInfoJson);
		
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
