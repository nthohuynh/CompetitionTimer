package vn.udn.vku.client;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

class UserClock implements Runnable {
	private JLabel labelClock;
	int clockKind;
	int presentationMinutes;
	JFrame jFrame;
	boolean chk = true;
	int studentSeconds = presentationMinutes*60;
      //int minute = minutes;
    int second = 60;
    Thread Objth;
	public UserClock(JFrame jFrame, int clockKind, int presentationMinutes) {
		super();
		this.jFrame = jFrame;
		this.clockKind = clockKind;
		this.presentationMinutes = presentationMinutes - 1;
		
	}
	public void reset() {
		this.stop();
		labelClock.setText("00:00");
	}
	public void create()
 	{
		Objth = new Thread(this);
    	Objth.start();
  	}

	public void stop()
 	{
    	chk = false;
  	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		JFrame fr = new JFrame();
		labelClock = new JLabel();
		labelClock.setBounds(5, 5, 80, 60);
		labelClock.setFont(new Font("Sans Serif", Font.PLAIN, 30));
	
		
		jFrame.setSize(90, 70);

		jFrame.setType(javax.swing.JFrame.Type.UTILITY); // don't display icon in tool bar in windows
		jFrame.setUndecorated(true); // <-- the title bar is removed here

		jFrame.setLayout(null);
	
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		if (clockKind == 1) {
			Border border = BorderFactory.createLineBorder(Color.RED);
			TitledBorder titleBorder = BorderFactory.createTitledBorder(border, "");
			labelClock.setBorder(titleBorder);
			jFrame.setLocation(dim.width - jFrame.getSize().width , jFrame.getSize().height+10);
		}
		else {
			Border border = BorderFactory.createLineBorder(Color.GREEN);
			TitledBorder titleBorder = BorderFactory.createTitledBorder(border, "");
			labelClock.setBorder(titleBorder);
			labelClock.setBackground(Color.lightGray);
			labelClock.setOpaque(true);
			jFrame.setLocation(dim.width - jFrame.getSize().width , dim.height - jFrame.getSize().height - 100);
		}
		jFrame.add(labelClock);
		jFrame.setAlwaysOnTop(true);
		jFrame.setVisible(true);
		
		boolean orientation = true;
		try {
	            while (chk) {
	                if (orientation) {
		            	if (second > 0)  second --;
		                else if (presentationMinutes > 0) {
		                	second = 59;
		                	presentationMinutes = presentationMinutes - 1;
		                } else {
		                	orientation = false;
		                	second++;
		                	labelClock.setForeground(Color.RED);
		                } 
	            	}
	                
	                else {
	                	if (second < 59)  second ++;
	                	else {
	                		second = 0;
	                		presentationMinutes ++;
	                	}
	                }
	                
	                labelClock.setText(((presentationMinutes  <= 9)? ("0" + presentationMinutes):presentationMinutes) + ":" + ((second <= 9)? "0"+second: second));
	                Thread.sleep(1000);
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	}
	
}

public class ClockExample extends JFrame {
	UserClock cl1;
	UserClock cl2;
	ObjectInputStream ois = null;
	Socket socket;

	public ClockExample() {
		// run this frame when receiving a msg from server
		if (!SystemTray.isSupported()) {
			System.out.println("System tray is not supported !!! ");
			return;
		}
		// get the systemTray of the system
		SystemTray systemTray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/icon.JPG");
		// popupmenu
		PopupMenu trayPopupMenu = new PopupMenu();
		// 1t menuitem for popupmenu
		MenuItem action = new MenuItem("IP config");
		action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame fr = new JFrame();
				fr.setSize(400, 80);

				fr.setLayout(new FlowLayout());
				JLabel jlbServerIP = new JLabel("Server IP Address ");
				JTextField jtfServerIP = new JTextField(20);
				fr.add(jlbServerIP);
				fr.add(jtfServerIP);
				jtfServerIP.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {

					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							System.out.println("Enter");
							fr.setVisible(false);
							// Enter was pressed. Your code goes here.
							Thread t1 = new Thread(() -> {
								try {
									socket = new Socket(jtfServerIP.getText(), 8189);
									BufferedReader in = new BufferedReader(
											new InputStreamReader(socket.getInputStream()));
									BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
									PrintWriter out = new PrintWriter(socket.getOutputStream(), true /* autoFlush */);
									System.out.println("Client");
									// System.out.println(in.readLine());

									boolean done = false;
									while (!done) {
										System.out.print("Client: ");
										// String fromClient = kbd.readLine();
										// out.println("Client: " + fromClient);
										String fromServer = in.readLine();
										System.out.println(fromServer);
										// if (fromServer.trim().equals("Server: BYE") || fromClient.equals("BYE")) done
										// = true;

										if (fromServer.equals("startPresentation")) {
											JFrame jf = new JFrame();
											cl1 = new UserClock(jf, 1, 1);
											cl1.create();
										} else if (fromServer.equals("stopPresentation")) {
											cl1.stop();
											JFrame jf2 = new JFrame();
											cl2 = new UserClock(jf2, 2, 1);
											cl2.create();
										} else if (fromServer.equals("stopQuestion")) {
											cl2.stop();
										} else if (fromServer.equals("reset")) {
											cl1.reset();
											cl2.reset();
										}
										// ois.close();
									}
								} catch (Exception ex) {
									System.out.println(ex);
								}

							});
							t1.start();
						}
					}

					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub

					}
				});
				fr.setLocationRelativeTo(null);
				// fr.setDefaultCloseOperation(EXIT_ON_CLOSE);
				fr.setVisible(true);
			}
		});
		trayPopupMenu.add(action);

		// 2nd menuitem of popupmenu
		MenuItem close = new MenuItem("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		trayPopupMenu.add(close);

		// setting tray icon
		TrayIcon trayIcon = new TrayIcon(image, "SystemTray Demo", trayPopupMenu);
		// adjust to default size as per system recommendation
		trayIcon.setImageAutoSize(true);

		try {
			systemTray.add(trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}
	}

	/**
	 * main
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {
		new ClockExample();
	}
}
