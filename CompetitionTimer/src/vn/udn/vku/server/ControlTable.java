package vn.udn.vku.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ControlTable extends JPanel {
	JLabel jlbPresentationTime;
	JLabel jlbQuestionTime;
	public ControlTable() {
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
		
		JButton jbtStop = new JButton("Stop");
		pnPresentationTimer.add(jbtStop);
				
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
		add(pnQuestionTimer);
		//Test dsd
	}
	public static void main(String agrs[]) {
		JFrame fr = new JFrame();
		fr.setSize(600,200);
		fr.add(new ControlTable());
		fr.setVisible(true);
		
	}
}
