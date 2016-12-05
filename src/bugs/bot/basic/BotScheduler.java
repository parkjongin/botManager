package bugs.bot.basic;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JList;

public class BotScheduler extends Thread{
	ArrayList<Bot> botList;
	Vector<String> stateList;
	JList monitor;
	int curSelectedIdx = -1;
	boolean pause = true;
	private static BotScheduler instance;
	public static BotScheduler getBSInstance(JList monitor){
		if(instance == null)
			instance = new BotScheduler(monitor);
		return instance;
	}
	
	public static BotScheduler getBSInstance(){
		if(instance == null)
			instance = new BotScheduler();
		return instance;
	}
	
	private BotScheduler(JList monitor){
		this.botList = new ArrayList<Bot>();
		this.stateList = new Vector<String>();
		this.monitor = monitor;
	}
	
	private BotScheduler(){
		this.botList = new ArrayList<Bot>();
		this.stateList = new Vector<String>();
	}
	
	public void addBot(Bot bot){
		bot.start();
		botList.add(bot);
	}
	@Override
	public void run() {
		while(true){
			if(pause){
				stateList.clear();
				for(int i=0; i<botList.size(); i++){
					if(!botList.get(i).getExit()){
						stateList.add("pID : " + i + ", botName : " + botList.get(i).getBotName() + 
								", 호출주기 : " + botList.get(i).getInterval() + ", 남은시간 : " + botList.get(i).getRestTime());
					}
				}
				if(stateList.size() > 0)
					monitor.setListData(stateList);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	public void stopBot(int id){
		botList.get(id).interrupt();
	}
	@Override
	public void interrupt() {
	}
	public void setPause(boolean pause){
		this.pause = pause;
	}
	
	public Vector getStateList(){
		return this.stateList;
	}
	
	public void setSelectedIdx(int idx){
		curSelectedIdx = idx;
	}
	
	public int getSelectedIdx(){
		return this.curSelectedIdx;
	}
}
