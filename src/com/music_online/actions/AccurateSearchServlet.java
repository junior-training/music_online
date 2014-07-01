package com.music_online.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.music_online.pojo.Music;
import com.music_online.service.IMusicService;
import com.music_online.serviceImpl.MusicServiceImpl;

public class AccurateSearchServlet extends HttpServlet {

	private static final long serialVersionUID = 5288355062414738930L;

	public AccurateSearchServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchKeyStr = request.getParameter("searchKey");
		ArrayList<String> searchKeyList = new ArrayList<String>();
		ArrayList<Music> searchResultNotTackled;
		ArrayList<Music> searchResultTackled = new ArrayList<Music>();
		StringBuffer searchResultStrbuf = new StringBuffer();
		String searchResultStr;
		
		IMusicService aMusicService = new MusicServiceImpl();
		
		String[] tmp1 = searchKeyStr.split(" ");
		int searchKeyNumber = tmp1.length;
		for(int i = 0;i < searchKeyNumber;i++)
			searchKeyList.add(tmp1[i]);
		
		searchResultNotTackled = aMusicService.accurateSearch(searchKeyList);
		
		class SortMusicById implements Comparator{
			
		}
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
