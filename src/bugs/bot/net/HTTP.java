package bugs.bot.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HTTP {
	private URL requestURL;
	private URL doorayURL;
	
	public HTTP(String doorayURL, String requestURL){

		try {
			if(requestURL == null){
				this.requestURL = null;
			}
			else{
				this.requestURL = new URL(requestURL);
			}
			this.doorayURL = new URL(doorayURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToDooray(String jsonData){
	    URLConnection conn = null;
	    OutputStream out = null;
        String buffer = null;
		try {
			conn = doorayURL.openConnection();
		    conn.setDoOutput(true);
		    conn.setDoInput(true);
		    conn.setRequestProperty("Content-Type", "application/json");
		    
	       	out = conn.getOutputStream();
	       	out.write(jsonData.getBytes());
	       	out.flush();
	       	out.close();
	       	
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        while((buffer = in.readLine())!=null){
	        }
	        
	        in.close();	       	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String requestHttp(){
		if(requestURL == null){
			return null;
		}
		
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		try {
			is = requestURL.openStream();

		    byte[] b = new byte[4096];
			for (int n; (n = is.read(b)) != -1;) {
				sb.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return sb.toString();
	}
	
	public URL getRequestURL(){
		return this.requestURL;
	}
}
