package com.base.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import com.base.engine.MainComponent;

public class GameServer extends Thread
{

		private DatagramSocket socket;
		private MainComponent mc;
		public GameServer(MainComponent mc)
		{
			this.mc = mc;
			
			try {
				this.socket = new DatagramSocket(1332);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		public void run()
		{
			while(true)
			{
				byte[] data = new byte[1024];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String message = new String(packet.getData());
				System.out.println("CLIENT > " + message); 
				
				if(message.trim().equalsIgnoreCase("Shlong"))
				{
					sendData("Bong".getBytes(), packet.getAddress(), packet.getPort());
				}
				socket.close();
			}  
		}
		

		 
		public void sendData(byte[] data, InetAddress ipAddress, int port)
		{
			DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
