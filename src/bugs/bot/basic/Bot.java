package bugs.bot.basic;

import java.util.Map;

import org.json.simple.JSONObject;
import org.jsoup.nodes.Element;

import bugs.bot.net.HTTP;

public class Bot extends Thread{
	HTTP http;
	JSONObject jsonData = new JSONObject();
	String botName;
	int interval;
	int restTime;
	boolean runnable = true;
	boolean exit = false;
	DataProcessor dp = null;
	
	public Bot(String doorayURL, String requestURL, String name, String imgsrc, String text, int interval){
		this.http = new HTTP(doorayURL, requestURL);
		this.interval = interval;
		this.botName = name;
		this.jsonData.put("botName", name);
	 	this.jsonData.put("botIconImage", imgsrc);
		this.jsonData.put("text", text);
	}
	
	public Bot(String doorayURL, String name, String imgsrc, String text, int interval){
		this.http = new HTTP(doorayURL, null);
		this.interval = interval;
		this.botName = name;
		this.jsonData.put("botName", name);
	 	this.jsonData.put("botIconImage", imgsrc);
		this.jsonData.put("text", text);
	}
	
	public void setBotName(String name){
		this.botName = name;
		this.jsonData.put("botName", name);
	}
	
	public void setBotImgsrc(String imgsrc){
		this.jsonData.put("botIconImage", imgsrc);
	}
	
	public void setBotText(String text){
		this.jsonData.put("text", text);
	}
	
	public void setDataProcessor(DataProcessor dp){
		this.dp = dp;
	}
	@Override
	public void run() {
		while(!exit){
			if(runnable == true){
				restTime = interval;
				if(http.getRequestURL() != null){
					setBotText(dp.startDataProcessor(http.requestHttp()));
				}
				http.sendToDooray(jsonData.toJSONString());
				runnable = false;
			}
			try {
				if(restTime <= 0) runnable = true;
				Thread.sleep(1000);
				restTime--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getBotName(){
		return this.botName;
	}
	
	public int getInterval(){
		return this.interval;
	}
	
	public int getRestTime(){
		return this.restTime;
	}
	
	public boolean getExit(){
		return this.exit;
	}

	@Override
	public void interrupt() {
		exit = true;
	}
}
