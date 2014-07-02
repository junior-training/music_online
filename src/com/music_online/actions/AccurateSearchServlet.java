package com.music_online.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
		int length;
		
		IMusicService aMusicService = new MusicServiceImpl();
		
		String[] tmp1 = searchKeyStr.split(" ");
		int searchKeyNumber = tmp1.length;
		for(int i = 0;i < searchKeyNumber;i++)
		{
			searchKeyList.add(tmp1[i]);
			//System.out.println(tmp1[i]);
		}
			
		
		searchResultNotTackled = aMusicService.accurateSearch(searchKeyList);
		length = searchResultNotTackled.size();
		if(0 == length)
		{
			System.out.println("Accurate search result is null!");
			searchResultStrbuf.append("[]");
		}
		else
		{
			//定义了比较器，按照id升序排序
			class SortMusicById implements Comparator{
				public int compare(Object o1, Object o2) 
				{
					  Music music1 = (Music)o1;
					  Music music2 = (Music)o2;
					  int music1_id = music1.getId();
					  int music2_id = music2.getId();
					  if (music1_id > music2_id)
					   return 1;
					  else
					   return -1;
			    }
		    }
			
			Collections.sort(searchResultNotTackled,new SortMusicById());
			
			/*Music aMusic2;
			for(int i=0;i< length;i++)
			{
				aMusic2 = searchResultNotTackled.get(i);
				System.out.println(String.valueOf(aMusic2.getId()));
			}*/
			
			//将排好序的数组进行去重操作，并且将搜索到的结果按匹配度排序
			class SearchResultMatchedDegree{
				private int indexOfSearchResultTackled;
				private int matchedDegree;
				
				public SearchResultMatchedDegree(int indexOfSearchResultTackled,int matchedDegree)
				{
					setIndexOfSearchResultTackled(indexOfSearchResultTackled);
					setMatchedDegree(matchedDegree);
				}
				public int getIndexOfSearchResultTackled() {
					return indexOfSearchResultTackled;
				}
				public void setIndexOfSearchResultTackled(int indexOfSearchResultTackled) {
					this.indexOfSearchResultTackled = indexOfSearchResultTackled;
				}
				public int getMatchedDegree() {
					return matchedDegree;
				}
				public void setMatchedDegree(int matchedDegree) {
					this.matchedDegree = matchedDegree;
				}
				public void addMatchedDegree()
				{
					this.matchedDegree += 1;
				}
			}
			
			ArrayList<SearchResultMatchedDegree> matchedDegreeList = new ArrayList<SearchResultMatchedDegree>();
			int indexOfMatchedDegreeList = -1;
			int indexOfSearchResultTackled = -1;
			int curId = -1;
			int tmpId;
			Music aMusic;
			SearchResultMatchedDegree aSearchResultMatchedDegree;
			int i = 0;
			
			while(i < length)
			{
				aMusic = searchResultNotTackled.get(i);
				tmpId = aMusic.getId();
				if(tmpId != curId)
				{
					indexOfSearchResultTackled += 1;
					searchResultTackled.add(aMusic);
					
					aSearchResultMatchedDegree = new SearchResultMatchedDegree(indexOfSearchResultTackled,1);
					indexOfMatchedDegreeList += 1;
					matchedDegreeList.add(aSearchResultMatchedDegree);
					
					curId = tmpId;
				}
				else
				{
					matchedDegreeList.get(indexOfMatchedDegreeList).addMatchedDegree();
				}
				
				i++;
			}
			
			/*Music aMusic2;
			for(int j=0;j<= indexOfSearchResultTackled;j++)
			{
				aMusic2 = searchResultTackled.get(j);
				System.out.println(String.valueOf(aMusic2.getId()));
			}*/
			
			
			//对matchedDegreeList数组按照匹配度进行排序
			class SortMatchedDegreeListByMatchedDegree implements Comparator{
				public int compare(Object o1, Object o2) 
				{
					  SearchResultMatchedDegree a = (SearchResultMatchedDegree)o1;
					  SearchResultMatchedDegree b = (SearchResultMatchedDegree)o2;
					  int md1 = a.getMatchedDegree();
					  int md2 = b.getMatchedDegree();
					  if (md1 > md2)
					   return -1;
					  else
					   return 1;
			    }
		    }
			
			Collections.sort(matchedDegreeList,new SortMatchedDegreeListByMatchedDegree());
			
			/*SearchResultMatchedDegree test;
			int test2 = matchedDegreeList.size();
			for(int j =0;j<test2;j++)
			{
				test = matchedDegreeList.get(j);
				System.out.println(String.valueOf(test.getIndexOfSearchResultTackled())+","+String.valueOf(test.getMatchedDegree()));
			}*/
			
			i = 0;
			int tmp2,tmp3;
			length = matchedDegreeList.size();
			if(length < 15)
				tmp3 = length;
			else 
				tmp3 = 15;
			
			searchResultStrbuf.append("[");
			while(i < tmp3)
			{
				aSearchResultMatchedDegree = matchedDegreeList.get(i);
				tmp2 = aSearchResultMatchedDegree.getIndexOfSearchResultTackled();
				aMusic = searchResultTackled.get(tmp2);
				
				//System.out.println(String.valueOf(aMusic.getId()));
				
				searchResultStrbuf.append("{\"id\":\""+String.valueOf(aMusic.getId())+"\",");
				searchResultStrbuf.append("\"title\":\""+aMusic.getSong_name()+"\",");
				searchResultStrbuf.append("\"artist\":\""+aMusic.getSinger_name()+"\",");
				searchResultStrbuf.append("\"album\":\""+aMusic.getAlbum_name()+"\",");
				searchResultStrbuf.append("\"src\":\""+aMusic.getSong_addr()+"\",");
				searchResultStrbuf.append("\"img\":\""+aMusic.getImage_addr()+"\"}");
	        		
	        	if((tmp3 - 1) != i)
	        		searchResultStrbuf.append(",");
	        		
	        	i++;
	        }
	        	
			searchResultStrbuf.append("]");
			
		}
		
		
		searchResultStr = searchResultStrbuf.toString();
		System.out.println(searchResultStr);
		
		response.setCharacterEncoding("utf-8");
        response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(searchResultStr);
		
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
