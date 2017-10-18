package com.base.engine.core;
import java.awt.Canvas;
import javax.swing.JOptionPane;
import javax.xml.crypto.Data;

import com.base.engine.net.GameClient;
import com.base.engine.net.GameServer;
import com.base.engine.rendering.RenderUtil;
import com.base.engine.rendering.Window;

public class CoreEngine extends Canvas
{
	private boolean isRunning;
	private Game game;
	private int width;
	private int height;
	
	private double frameTime;
	
	public CoreEngine(int width, int height, double framerate, Game game)
	{
		
		isRunning = false;
		this.game = game;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0/framerate;
	}
	
	private void initializeRenderingSystem()
	{
		RenderUtil.initGraphics();
		System.out.println(RenderUtil.getOpenGLVersion());
	}
	
	public void createWindow(String title)
	{
		Window.createWindow(width, height, title);
		initializeRenderingSystem();
	}
	
	public void Start()
	{
		
		if(isRunning)
			return;
		
		Run();		
		
	}
	
	public void Stop()
	{
		if(!isRunning)
			return;
		
		isRunning = false;
	}
	
	private void Run()
	{
		isRunning = true;
		
		int frames = 0;
		long frameCounter = 0;
		
		game.init();
		
		long lastTime = Time.getTime();
		double unproccesedTime = 0;
	
		while(isRunning)
		{
			boolean render = false;
			
			long startTime = Time.getTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unproccesedTime += passedTime / (double)Time.SECOND;
			frameCounter += passedTime;
			
			while(unproccesedTime > frameTime)
			{
				render = true;
				
				unproccesedTime -= frameTime;
				
				if(Window.isCloseRequested())
					Stop();
				
				Time.setDelta(frameTime);
				
				game.input();
				Input.Update();
				game.update();
				
				if(frameCounter >= Time.SECOND)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			
			if(render)
			{
				Render();
				frames ++;
			}
			else
				try {
					Thread.sleep(1);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
		}
		
		CleanUp();			
	}
	
	private void Render()
	{
		RenderUtil.clearScreen();
		game.render();
		Window.render();
	}
	
	private void CleanUp()
	{
		Window.dispose();
	}
}
