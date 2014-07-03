package com.music_online.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.music_online.pojo.Popularity;
import com.music_online.service.IPopularityService;
import com.music_online.serviceImpl.PopularityImpl;


import net.sf.json.JSONObject; 
import net.sf.json.JSONArray;

public class SendListeningHistoryServlet extends HttpServlet {

	private static final long serialVersionUID = 4685238469209091355L;

	public SendListeningHistoryServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String listeningHistory = request.getParameter("listeningHistory");
		ArrayList<Popularity> songPopularity = new ArrayList<Popularity>();
		
		IPopularityService aPopularityService = new PopularityImpl();
		
		JSONArray historyList = JSONArray.fromObject(listeningHistory);
		int length = historyList.size();
		JSONObject aHistory;
		int id;
		int listenTime;
		int downloadTime;
		
		for(int i = 0;i < length;i++)
		{
			aHistory = historyList.getJSONObject(i);
			id = Integer.parseInt((String)aHistory.get("id"));
			listenTime = Integer.parseInt((String)aHistory.get("listenTime"));
			downloadTime = Integer.parseInt((String)aHistory.get("downloadTime"));
			
			Popularity aSongPopularity = new Popularity(id,listenTime,downloadTime,0);
			songPopularity.add(aSongPopularity);
		}
		
		int result = aPopularityService.updatePopularity(songPopularity);
		
		if(1 == result)
			System.out.println("Update popularity success!");
		else if(-1 == result)
			System.out.println("Update popularity failed!");
		
	}

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
