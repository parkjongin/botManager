package bugs.bot.basic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BotManager extends JFrame{
	private JLabel lbBotName = new JLabel("봇 이름"); 
	private JTextField tfBotName = new JTextField(100); 
	private JLabel lbBotImgsrc = new JLabel("봇 이미지주소"); 
	private JTextField tfBotImgsrc = new JTextField(100); 
	private JLabel lbBotText = new JLabel("봇 텍스트"); 
	private JTextField tfBotText = new JTextField(100); 
	private JLabel lbDoorayUrl = new JLabel("두레이URL"); 
	private JTextField tfDoorayUrl = new JTextField(100); 
	private JLabel lbInterval = new JLabel("인터벌"); 
	private JTextField tfInterval = new JTextField(100); 
	private JButton btnOK = new JButton("추가");
	private JButton btnStop = new JButton("봇중지");
	private JList monitor = new JList();
	
	public BotManager(){
		super("BotManager");
		
		setBounds(300,300,500,550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		tfBotName.setBounds(150, 30, 300, 30);
		tfBotImgsrc.setBounds(150, 70, 300, 30);
		tfBotText.setBounds(150, 110, 300, 30);
		tfDoorayUrl.setBounds(150, 150, 300, 30);
		tfInterval.setBounds(150, 190, 300, 30);
		
		lbBotName.setBounds(30, 30, 90, 30);
		lbBotImgsrc.setBounds(30, 70, 90, 30);
		lbBotText.setBounds(30, 110, 90, 30);
		lbDoorayUrl.setBounds(30, 150, 90, 30);
		lbInterval.setBounds(30, 190, 90, 30);
		
		btnOK.setBounds(400, 230, 50, 30);
		btnStop.setBounds(400, 480, 80, 30);
		
		monitor.setBounds(30, 400, 390, 200);
		
		this.add(tfBotName);
		this.add(tfBotImgsrc);
		this.add(tfBotText);
		this.add(tfDoorayUrl);
		this.add(tfInterval);
		
		this.add(lbBotName);
		this.add(lbBotImgsrc);
		this.add(lbBotText);
		this.add(lbDoorayUrl);
		this.add(lbInterval);
		
		this.add(btnOK);
		this.add(btnStop);
		this.add(monitor);
		
		runBotManager();
	}
	
	public void runBotManager(){
		BotScheduler botScheduler = BotScheduler.getBSInstance(monitor);

		
		btnOK.addActionListener(new ActionListener() {
			Bot bot;
			BotScheduler botScheduler = BotScheduler.getBSInstance();
			@Override
			public void actionPerformed(ActionEvent e) {
				bot = new Bot(tfDoorayUrl.getText(), tfBotName.getText(), tfBotImgsrc.getText(), tfBotText.getText(), Integer.parseInt(tfInterval.getText()));
				botScheduler.addBot(bot);
			}
        });
		
		btnStop.addActionListener(new ActionListener() {
			BotScheduler botScheduler = BotScheduler.getBSInstance();
			@Override
			public void actionPerformed(ActionEvent e) {
				botScheduler.stopBot(botScheduler.getSelectedIdx());
			}
        });
		
		monitor.addListSelectionListener(new ListSelectionListener(){
			BotScheduler botScheduler = BotScheduler.getBSInstance();
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(monitor.getSelectedIndex() >= 0){
					botScheduler.setSelectedIdx(monitor.getSelectedIndex());
				}
			}
			
		});

		botScheduler.start();
		

		while(true){
			this.repaint();
			monitor.setBounds(30, 270, 420, 200);
		}
	}
	
	public static void main(String[] args) {
		new BotManager();

	}

}
