package vn.udn.vku.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class ControlTable extends JPanel {
	JLabel jlbPresentationTime;
	JLabel jlbQuestionTime;
	PrintWriter oos;
	ObjectInputStream in;
	
	public PrintWriter getOut() {
		return oos;
	}
	public void setOut(PrintWriter oos1) {
		this.oos = oos1;
	}
	public ObjectInputStream getIn() {
		return in;
	}
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	public ControlTable(PrintWriter out1, ObjectInputStream in1) {
		this.oos = out1;
		//this.in = in1;
		
		setLayout(new GridLayout(1,2));
		JPanel pnPresentationTimer = new JPanel();
		
		Border border = BorderFactory.createLineBorder(Color.RED);
		TitledBorder titleBorder = BorderFactory.createTitledBorder(border, "Presentation");
		pnPresentationTimer.setBorder(titleBorder);
		
		pnPresentationTimer.setLayout(new GridLayout(3, 2));
		
		JLabel jlbTeamName = new JLabel("Team name");
		pnPresentationTimer.add(jlbTeamName);
		
		JTextField jtfTeamName = new JTextField();
		pnPresentationTimer.add(jtfTeamName);
		
		JLabel jlbTimeLabel = new JLabel("Presentation Time");
		pnPresentationTimer.add(jlbTimeLabel);
		
		jlbPresentationTime = new JLabel("");
		pnPresentationTimer.add(jlbPresentationTime);
		
		JButton jbtStart = new JButton("Start");
		pnPresentationTimer.add(jbtStart);
		jbtStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
					oos.println("Hi Client3");
					oos.println("startPresentation");
				
				System.out.println("server start presentation");
			}
		});
		
		
		JButton jbtStop = new JButton("Stop");
		pnPresentationTimer.add(jbtStop);
		jbtStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
					oos.println("stopPresentation");
				
			}
		});
				
		add(pnPresentationTimer);
		
		JPanel pnQuestionTimer = new JPanel();
		Border questionBorder = BorderFactory.createLineBorder(Color.RED);
		TitledBorder questionTitleBorder = BorderFactory.createTitledBorder(questionBorder, "Question");
		pnQuestionTimer.setBorder(questionTitleBorder);
		pnQuestionTimer.setLayout(new GridLayout(2,1));
		
		jlbQuestionTime = new JLabel("Question time here");
		pnQuestionTimer.add(jlbQuestionTime);
	
		JButton jbtStopQuestion = new JButton("Stop");
		pnQuestionTimer.add(jbtStopQuestion);
		jbtStopQuestion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
					oos.println("stopQuestion");
				
			}
		});
		
		add(pnQuestionTimer);
		//Test dsd  fsdd
	}
	public static void main(String agrs[]) {
		JFrame fr = new JFrame();
		fr.setSize(600,200);
		fr.add(new ControlTable(null,null));
		fr.setVisible(true);
	}
}
