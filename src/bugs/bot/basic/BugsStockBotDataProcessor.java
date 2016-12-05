package bugs.bot.basic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class BugsStockBotDataProcessor implements DataProcessor{

	@Override
	public String startDataProcessor(String sourceData) {
		String str = "";
	    Document doc = Jsoup.parse(sourceData);
	    Elements rows = doc.select("ul.list_stockrate");
	     
        for (Element row : rows) {
        	Element el = row.getElementsByClass("curPrice").first();
       	 	str+= "주식현재가 : " + el.text();
       	 	el = row.getElementsByClass("sise").first();
       	 	str += "등락폭 : " + el.text() + ", ";
       	 	el = row.getElementsByClass("rate").first();
       	 	str += "등락률 : " + el.text();
        }
        return str;
	}	
}
