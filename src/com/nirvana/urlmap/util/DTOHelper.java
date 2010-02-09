package com.nirvana.urlmap.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nirvana.urlmap.domain.UrlTweet;
import com.nirvana.urlmap.dto.UrlTweetDTO;



public class DTOHelper {

	static Pattern urlBeginPattern = Pattern.compile("\\bhttp");
	static Pattern urlEndPattern = Pattern.compile("\\s");
    static DateFormat usersTimeZoneFormat = new SimpleDateFormat("EEE MMM d  HH:mm:ss z yyyy");

	
	public static UrlTweetDTO convertFrom(UrlTweet urlTweet){
		UrlTweetDTO urlTweetDTO = new UrlTweetDTO(getUrlTweet(urlTweet.getTweetWithTinyURL()),urlTweet.getPostedByScreenName(),urlTweet.getName(),urlTweet.getCreatedAt().toString(),urlTweet.getStatusId());
		
		return urlTweetDTO;
		
	}

	public static LinkedList<UrlTweetDTO> convertFrom(Collection<UrlTweet> urlTweets){
		LinkedList<UrlTweetDTO> urlTweetDTOs = new LinkedList<UrlTweetDTO>();
		
		for(UrlTweet urlTweet:urlTweets){
		   System.out.println("server response : " + urlTweet.getStatusId() + " : " + getUrlTweet(urlTweet.getTweetWithTinyURL()));
			UrlTweetDTO urlTweetDTO = new UrlTweetDTO(getUrlTweet(urlTweet.getTweetWithTinyURL()),urlTweet.getPostedByScreenName(),urlTweet.getName(),urlTweet.getCreatedAt().toString(),urlTweet.getStatusId());
		    urlTweetDTOs.add(urlTweetDTO);
		}
		System.out.println("ENd of result " + "\n");
		return urlTweetDTOs;
		
	}
	
	private static String formatInTimeZone(Date createdAt,String usersTimeZone)  {
	   //  return getDifferenceInTime(createdAt) + "  "+createdAt.toString();
	    TimeZone userTime = TimeZone.getTimeZone(usersTimeZone);
	      usersTimeZoneFormat.setTimeZone(userTime);
	      String userFormatDate = null;
	      
          userFormatDate =  usersTimeZoneFormat.format(createdAt);
	      
	      return userFormatDate;
    }
	

	   private static String getDifferenceInTime(Date createdAt)  {

	        Calendar calendar = new GregorianCalendar(new SimpleTimeZone(0, "UTC"), Locale.US);
	        long now = calendar.getTimeInMillis();
	        
	        long creationDateInMilliSecs = createdAt.getTime();
	        
            double exactDays = (double)(now-creationDateInMilliSecs)/(1000*60*60*24); 
            int days = (int)exactDays;
            double exactHrs = (exactDays - days)*24;
            
            int hrs = (int)exactHrs;
            
            double exactMin =(exactHrs-hrs)*60;
            
            int min = (int)exactMin;
            
            System.out.println("manav : " + " day : " + days + " hrs : " + hrs + " min : " + min );
            String dayString = (days >1) ? "days" : "day";
            String dayPart = (days>1) ? (days + " "+ dayString + " , "):"";
            String hrsPart = (hrs>0) ? (hrs + " "+ "hours"):"";
            String minPart = (min>0) ? (" & " + min+ " "+ "minutes"):"";
 	        return dayPart  + hrsPart +  minPart + "  ago ";
	    }

	

    private static String getUrlTweet(String urlTweet) {
		
		String url = null;
		
		Matcher mUrlBeginMatcher = urlBeginPattern.matcher(urlTweet);
		
		 Integer startIndex = null;
		 Integer endIndex = null;
		 String afterFirstParse = null;
		 
		 if(mUrlBeginMatcher.find()){
		   startIndex  = new Integer(mUrlBeginMatcher.start());	 
		 }
		 
		 if(startIndex !=null){
		 afterFirstParse = urlTweet.substring(startIndex);
		 
		 Matcher mUrlEndPattern = urlEndPattern.matcher(afterFirstParse);
		 
		 if(mUrlEndPattern.find()){
			 endIndex = new Integer(mUrlEndPattern.start());
			 url = afterFirstParse.substring(0, endIndex);
		 }
		 else{
			 url = afterFirstParse;
			 endIndex = afterFirstParse.length();
		 }
		 //<a target="_blank" rel="nofollow" href="http://buytter.com/boris">http://buytter.com/boris</a>	 
		 return urlTweet.substring(0,startIndex) +"<a target=\"_blank\" rel=\"nofollow\" href=\"" + url + "\">"+ url+"</a> " +afterFirstParse.substring(endIndex,afterFirstParse.length());

	}
		return urlTweet;

	}

	
}
