package vn.udn.vku.client;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;

public class ClockExample extends JFrame {
	private JLabel labelClock;
	 
    public ClockExample() {
        setTitle("Đồng hồ trong Java Swing");
        labelClock = new JLabel();
        labelClock.setBounds(10, 10, 80, 60);
        labelClock.setFont(new Font("Sans Serif", Font.PLAIN, 30));
        
        add(labelClock);
        setSize(100, 80);
        
        setUndecorated(true); // <-- the title bar is removed here

        setLayout(null);
    //     dóng chương trình khi đóng của sổ
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
        setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        setLocation(dim.width - this.getSize().width, this.getSize().height);
        
        setAlwaysOnTop(true);
        
        int minutes = 5;
        // thiết lập lại đồng hồ sau mỗi 1 giây
        int seconds = minutes*60;
        //int minute = minutes;
        int second = 60;
        if(!SystemTray.isSupported()){
            System.out.println("System tray is not supported !!! ");
            return ;
        }
        //get the systemTray of the system
        SystemTray systemTray = SystemTray.getSystemTray();

        //get default toolkit
        //Toolkit toolkit = Toolkit.getDefaultToolkit();
        //get image 
        //Toolkit.getDefaultToolkit().getImage("src/resources/busylogo.jpg");
        Image image = Toolkit.getDefaultToolkit().getImage("src/images/icon.JPG");

        //popupmenu
        PopupMenu trayPopupMenu = new PopupMenu();

        //1t menuitem for popupmenu
        MenuItem action = new MenuItem("Action");
        action.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Action Clicked");          
            }
        });     
        trayPopupMenu.add(action);

        //2nd menuitem of popupmenu
        MenuItem close = new MenuItem("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);             
            }
        });
        trayPopupMenu.add(close);

        //setting tray icon
        TrayIcon trayIcon = new TrayIcon(image, "SystemTray Demo", trayPopupMenu);
        //adjust to default size as per system recommendation 
        trayIcon.setImageAutoSize(true);

        try{
            systemTray.add(trayIcon);
        }catch(AWTException awtException){
            awtException.printStackTrace();
        }
              
        
        try {
            while (true) {
                if (second > 0) second --;
                else {
                	second = 60;
                	minutes = minutes - 1;
                }
                labelClock.setText(((minutes  <= 9)? ("0" + minutes):minutes) + ":" + ((second <= 9)? "0"+second: second));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
