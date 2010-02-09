package com.nirvana.urlmap.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nirvana.urlmap.resource.LinkMapResource;

public class TwitterServiceImpl implements TwitterRestService, TwitterSearchService{

    DateFormat tweetDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
    DateFormat userDateFormat  = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");
    static Map<String,String> timeZoneReference = new HashMap<String,String>(); 
    
    private Date searchBlockTime;
    private int searchBlockPentaltyInSecs;
    private boolean isSearchBlocked=false;
    
    private long restBlockResetTime;
    private boolean isRestBlocked=false;
    
    private static final Logger logger = Logger.getLogger(LinkMapResource.class.getName());

    
    static{
        timeZoneReference.put("Chennai", "India Standard Time : IST :  19800000");
        timeZoneReference.put("Kolkata", "India Standard Time : IST :  19800000");
        timeZoneReference.put("Mumbai", "India Standard Time : IST :  19800000");
        timeZoneReference.put("Indiana (East)", "Eastern Standard Time : US/East-Indiana :  -18000000");
        timeZoneReference.put("International Date Line West", "Line Is. Time : Pacific/Kiritimati :  50400000");
        timeZoneReference.put("Midway Island", null);
        timeZoneReference.put("Central America", null);
        timeZoneReference.put("Guadalajara", null);
        timeZoneReference.put("Mexico City", "Central Standard Time : America/Mexico_City :  -21600000");
        timeZoneReference.put("Monterrey", "Central Standard Time : America/Monterrey :  -21600000");
        timeZoneReference.put("Saskatchewan", "Central Standard Time : Canada/Saskatchewan :  -21600000");
        timeZoneReference.put("Bogota", "Colombia Time : America/Bogota :  -18000000");
        timeZoneReference.put("Lima", "Peru Time : America/Lima :  -18000000");
        timeZoneReference.put("Quito", "Central European Time : ECT :  3600000");
        timeZoneReference.put("Atlantic Time (Canada)", null);
        timeZoneReference.put("Caracas", "Venezuela Time : America/Caracas :  -16200000");
        timeZoneReference.put("La Paz", null);
        timeZoneReference.put("Newfoundland", "Newfoundland Standard Time : CNT :  -12600000");
        timeZoneReference.put("Buenos Aires", "Argentine Time : America/Argentina/Buenos_Aires :  -10800000");
        timeZoneReference.put("Georgetown", null);
        timeZoneReference.put("Greenland", null);
        timeZoneReference.put("Mid-Atlantic", null);
        timeZoneReference.put("Cape Verde Is.", null);
        timeZoneReference.put("Casablanca", "Western European Time : Africa/Casablanca :  0");
        timeZoneReference.put("Dublin", "Greenwich Mean Time : Europe/Dublin :  0");
        timeZoneReference.put("Edinburgh", null);
        timeZoneReference.put("Bern", null);
        timeZoneReference.put("Ljubljana", "Central European Time : Europe/Ljubljana :  3600000");
        timeZoneReference.put("West Central Africa", null);
        timeZoneReference.put("Kyiv", null);
        timeZoneReference.put("Pretoria", null);
        timeZoneReference.put("St. Petersburg", null);
        timeZoneReference.put("Abu Dhabi", null);
        timeZoneReference.put("Ekaterinburg", null);
        timeZoneReference.put("Islamabad", null);
        timeZoneReference.put("Astana", null);
        timeZoneReference.put("Sri Jayawardenepura", null);
        timeZoneReference.put("Hanoi", null);
        timeZoneReference.put("Beijing", null);
        timeZoneReference.put("Kuala Lumpur", null);
        timeZoneReference.put("Ulaan Bataar", null);
        timeZoneReference.put("Osaka", null);
        timeZoneReference.put("Sapporo", null);
        timeZoneReference.put("Port Moresby", null);
        timeZoneReference.put("Magadan", null);
        timeZoneReference.put("Wellington", null);
        timeZoneReference.put("Nuku'alofa", null);
        timeZoneReference.put("Osaka", null);
        timeZoneReference.put("Alaska","Alaska Standard Time : AST :  -32400000");
        timeZoneReference.put("Hawaii","Hawaii Standard Time : US/Hawaii :  -36000000");
        timeZoneReference.put("Samoa","Samoa Standard Time : Pacific/Midway :  -39600000");
        timeZoneReference.put("Tijuana","Pacific Standard Time : America/Tijuana :  -28800000");
        timeZoneReference.put("Pacific Time (US & Canada)","Pacific Standard Time : PST :  -28800000");
        timeZoneReference.put("Chihuahua","Mountain Standard Time : America/Chihuahua :  -25200000");
        timeZoneReference.put("Mazatlan","Mountain Standard Time : America/Mazatlan :  -25200000");
        timeZoneReference.put("Mountain Time (US & Canada)","Mountain Standard Time : MST :  -25200000");
        timeZoneReference.put("Arizona","Mountain Standard Time : US/Arizona :  -25200000");
        timeZoneReference.put("Central Time (US & Canada)","Central Standard Time : CST :  -21600000");
        timeZoneReference.put("Eastern Time (US & Canada)","Eastern Standard Time : EST :  -18000000");
        timeZoneReference.put("Santiago","Chile Time : America/Santiago :  -14400000");
        timeZoneReference.put("Brasilia","Brasilia Time : BET :  -10800000");
        timeZoneReference.put("Azores","Azores Time : Atlantic/Azores :  -3600000");
        timeZoneReference.put("Monrovia","Greenwich Mean Time : Africa/Monrovia :  0");
        timeZoneReference.put("Lisbon","Western European Time : Europe/Lisbon :  0");
        timeZoneReference.put("London","Greenwich Mean Time : Europe/London :  0");
        timeZoneReference.put("Amsterdam","Central European Time : Europe/Amsterdam :  3600000");
        timeZoneReference.put("Belgrade","Central European Time : Europe/Belgrade :  3600000");
        timeZoneReference.put("Berlin","Central European Time : Europe/Berlin :  3600000");
        timeZoneReference.put("Bratislava","Central European Time : Europe/Bratislava :  3600000");
        timeZoneReference.put("Brussels","Central European Time : Europe/Brussels :  3600000");
        timeZoneReference.put("Budapest","Central European Time : Europe/Budapest :  3600000");
        timeZoneReference.put("Copenhagen","Central European Time : Europe/Copenhagen :  3600000");
        timeZoneReference.put("Madrid","Central European Time : Europe/Madrid :  3600000");
        timeZoneReference.put("Paris","Central European Time : Europe/Paris :  3600000");
        timeZoneReference.put("Prague","Central European Time : Europe/Prague :  3600000");
        timeZoneReference.put("Rome","Central European Time : Europe/Rome :  3600000");
        timeZoneReference.put("Sarajevo","Central European Time : Europe/Sarajevo :  3600000");
        timeZoneReference.put("Skopje","Central European Time : Europe/Skopje :  3600000");
        timeZoneReference.put("Stockholm","Central European Time : Europe/Stockholm :  3600000");
        timeZoneReference.put("Vienna","Central European Time : Europe/Vienna :  3600000");
        timeZoneReference.put("Warsaw","Central European Time : Europe/Warsaw :  3600000");
        timeZoneReference.put("Zagreb","Central European Time : Europe/Zagreb :  3600000");
        timeZoneReference.put("Cairo","Eastern European Time : Africa/Cairo :  7200000");
        timeZoneReference.put("Harare","Central African Time : Africa/Harare :  7200000");
        timeZoneReference.put("Jerusalem","Israel Standard Time : Asia/Jerusalem :  7200000");
        timeZoneReference.put("Athens","Eastern European Time : Europe/Athens :  7200000");
        timeZoneReference.put("Bucharest","Eastern European Time : Europe/Bucharest :  7200000");
        timeZoneReference.put("Helsinki","Eastern European Time : Europe/Helsinki :  7200000");
        timeZoneReference.put("Istanbul","Eastern European Time : Europe/Istanbul :  7200000");
        timeZoneReference.put("Minsk","Eastern European Time : Europe/Minsk :  7200000");
        timeZoneReference.put("Riga","Eastern European Time : Europe/Riga :  7200000");
        timeZoneReference.put("Sofia","Eastern European Time : Europe/Sofia :  7200000");
        timeZoneReference.put("Tallinn","Eastern European Time : Europe/Tallinn :  7200000");
        timeZoneReference.put("Vilnius","Eastern European Time : Europe/Vilnius :  7200000");
        timeZoneReference.put("Nairobi","Eastern African Time : Africa/Nairobi :  10800000");
        timeZoneReference.put("Baghdad","Arabia Standard Time : Asia/Baghdad :  10800000");
        timeZoneReference.put("Kuwait","Arabia Standard Time : Asia/Kuwait :  10800000");
        timeZoneReference.put("Riyadh","Arabia Standard Time : Asia/Riyadh :  10800000");
        timeZoneReference.put("Moscow","Moscow Standard Time : Europe/Moscow :  10800000");
        timeZoneReference.put("Volgograd","Volgograd Time : Europe/Volgograd :  10800000");
        timeZoneReference.put("Tehran","Iran Standard Time : Asia/Tehran :  12600000");
        timeZoneReference.put("Baku","Azerbaijan Time : Asia/Baku :  14400000");
        timeZoneReference.put("Muscat","Gulf Standard Time : Asia/Muscat :  14400000");
        timeZoneReference.put("Tbilisi","Georgia Time : Asia/Tbilisi :  14400000");
        timeZoneReference.put("Yerevan","Armenia Time : Asia/Yerevan :  14400000");
        timeZoneReference.put("Kabul","Afghanistan Time : Asia/Kabul :  16200000");
        timeZoneReference.put("Karachi","Pakistan Time : Asia/Karachi :  18000000");
        timeZoneReference.put("Tashkent","Uzbekistan Time : Asia/Tashkent :  18000000");
        timeZoneReference.put("Kathmandu","Nepal Time : Asia/Kathmandu :  20700000");
        timeZoneReference.put("Almaty","Alma-Ata Time : Asia/Almaty :  21600000");
        timeZoneReference.put("Almaty","Bangladesh Time : Asia/Dhaka :  21600000");
        timeZoneReference.put("Novosibirsk","Novosibirsk Time : Asia/Novosibirsk :  21600000");
        timeZoneReference.put("Rangoon","Myanmar Time : Asia/Rangoon :  23400000");
        timeZoneReference.put("Bangkok","Indochina Time : Asia/Bangkok :  25200000");
        timeZoneReference.put("Jakarta","West Indonesia Time : Asia/Jakarta :  25200000");
        timeZoneReference.put("Krasnoyarsk","Krasnoyarsk Time : Asia/Krasnoyarsk :  25200000");
        timeZoneReference.put("Chongqing","China Standard Time : Asia/Chongqing :  28800000");
        timeZoneReference.put("Irkutsk","Irkutsk Time : Asia/Irkutsk :  28800000");
        timeZoneReference.put("Singapore","Singapore Time : Asia/Singapore :  28800000");
        timeZoneReference.put("Taipei","China Standard Time : Asia/Taipei :  28800000");
        timeZoneReference.put("Urumqi","China Standard Time : Asia/Urumqi :  28800000");
        timeZoneReference.put("Perth","Western Standard Time (Australia) : Australia/Perth :  28800000");
        timeZoneReference.put("Hong Kong","Hong Kong Time : Hongkong :  28800000");
        timeZoneReference.put("Singapore","Singapore Time : Singapore :  28800000");
        timeZoneReference.put("Seoul","Korea Standard Time : Asia/Seoul :  32400000");
        timeZoneReference.put("Tokyo","Japan Standard Time : Asia/Tokyo :  32400000");
        timeZoneReference.put("Yakutsk","Yakutsk Time : Asia/Yakutsk :  32400000");
        timeZoneReference.put("Adelaide","Central Standard Time (South Australia) : Australia/Adelaide :  34200000");
        timeZoneReference.put("Darwin","Central Standard Time (Northern Territory) : Australia/Darwin :  34200000");
        timeZoneReference.put("Vladivostok","Vladivostok Time : Asia/Vladivostok :  36000000");
        timeZoneReference.put("Brisbane","Eastern Standard Time (Queensland) : Australia/Brisbane :  36000000");
        timeZoneReference.put("Canberra","Eastern Standard Time (New South Wales) : Australia/Canberra :  36000000");
        timeZoneReference.put("Hobart","Eastern Standard Time (Tasmania) : Australia/Hobart :  36000000");
        timeZoneReference.put("Melbourne","Eastern Standard Time (Victoria) : Australia/Melbourne :  36000000");
        timeZoneReference.put("Sydney","Eastern Standard Time (New South Wales) : Australia/Sydney :  36000000");
        timeZoneReference.put("Guam","Chamorro Standard Time : Pacific/Guam :  36000000");
        timeZoneReference.put("New Caledonia","New Caledonia Time : Pacific/Noumea :  39600000");
        timeZoneReference.put("Solomon Is.","Solomon Is. Time : SST :  39600000");
        timeZoneReference.put("Kamchatka","Petropavlovsk-Kamchatski Time : Asia/Kamchatka :  43200000");
        timeZoneReference.put("Marshall Is.","Marshall Islands Time : Kwajalein :  43200000");
        timeZoneReference.put("Auckland","New Zealand Standard Time : Pacific/Auckland :  43200000");
        timeZoneReference.put("Fiji","Fiji Time : Pacific/Fiji :  43200000");
        timeZoneReference.put("null","Pacific Standard Time : PST :  -28800000");
        timeZoneReference.put(null,"Pacific Standard Time : PST :  -28800000");

    }


    @Override
    public String doFetch(String uri) {
        String responseBody = null;
        BufferedReader bufferedReader = null;
        Map<String,List<String>> responseHeaderMap =null;
        
        if(isRestBlocked  && ((getCurrentTime().getTime()/1000)-restBlockResetTime)<0){
            logger.log(Level.WARNING, "It is REST rate limited");
            return null;
        }
        isRestBlocked = false;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(4500);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-agent", "http://mustseetweets.com/");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                responseHeaderMap = connection.getHeaderFields();
                if(Integer.parseInt(responseHeaderMap.get("x-ratelimit-remaining").get(0))<=10) {
                    isRestBlocked = true;
                    restBlockResetTime=Long.parseLong(responseHeaderMap.get("x-ratelimit-reset").get(0));
                    logger.log(Level.WARNING, "Rate Limited for REST API "+ ((getCurrentTime().getTime()/1000)-restBlockResetTime) +  " secs remaining");
                }
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                responseBody = bufferedReader.readLine();
                bufferedReader.close();
                
                
            } else{
                responseHeaderMap = connection.getHeaderFields();
                if(Integer.parseInt(responseHeaderMap.get("x-ratelimit-remaining").get(0))<=10) {
                    isRestBlocked = true;
                    restBlockResetTime=Long.parseLong(responseHeaderMap.get("x-ratelimit-reset").get(0));
                    logger.log(Level.WARNING, "Rate Limited for REST API "+ ((getCurrentTime().getTime()/1000)-restBlockResetTime) +  " secs remaining");
                }
                
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "This Http Call was screwed : " +uri + "  error was :" + e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "buffer could not be closed " + e.getMessage());
                }
            }

        }
        return responseBody;

    }


    @Override
    public String doSearch(String uri) {
        String responseBody = null;
        BufferedReader bufferedReader = null;

        if(isSearchBlocked  && ((getCurrentTime().getTime()-searchBlockTime.getTime())/1000) < searchBlockPentaltyInSecs){
            logger.log(Level.WARNING, "It is Search rate limited");
            return null;
        }

        isSearchBlocked=false;
        try {

            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(4500);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-agent", "http://mustseetweets.com/");
            
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                responseBody = bufferedReader.readLine();
                bufferedReader.close();
            } else if (connection.getResponseCode() == HttpURLConnection.HTTP_UNAVAILABLE){
                isSearchBlocked=true;
                searchBlockTime=this.getCurrentTime();
                searchBlockPentaltyInSecs =Integer.parseInt(connection.getHeaderField("retry-after"));
                logger.log(Level.WARNING, "Rate Limited for SEARCH API "+ searchBlockPentaltyInSecs +  " secs remaining");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "This Http Call was screwed : " +uri + "  error was :" + e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "buffer could not be closed " + e.getMessage());
                }
            }

        }
        return responseBody;

    }

    public Date getCurrentTime(){
        Calendar calendar = new GregorianCalendar(new SimpleTimeZone(0, "UTC"), Locale.US);
        
        return calendar.getTime();
    }

}
