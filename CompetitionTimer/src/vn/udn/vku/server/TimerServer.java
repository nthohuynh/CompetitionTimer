package vn.udn.vku.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class TimerServer {
	PrintWriter out = null;
	ObjectInputStream in = null;

	public TimerServer() {
		try {
			JFrame fr = new JFrame();
			fr.setTitle("Control Board");
			fr.setSize(600, 200);
			ControlTable x = new ControlTable(out, in);
			fr.add(x);
			fr.setVisible(true);
			fr.setLocationRelativeTo(null);

			ServerSocket s = new ServerSocket(8189);

			System.out.println("Chap nhan ket noi");
			Socket incoming = s.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(incoming.getOutputStream(), true /* autoFlush */);

			x.setOut(out);

			System.out.print("Server: ");
			String fromServer = kbd.readLine();
			out.println("Server: " + fromServer);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String agrs[]) {
		new TimerServer();
	}

}
