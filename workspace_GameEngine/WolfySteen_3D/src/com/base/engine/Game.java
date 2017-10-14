package com.base.engine;

public class Game 
{	
	private static Level level;
	private Player player;
	
	public Game()
	{	
		level = new Level("Wolf-3D_Level_0.png", "WolfySteenCollection.png");
		player = new Player(new Vector3f(4.256394f,0.4375f,10f));
		
		Transform.setProjection(70f, Window.getWidth(), Window.GetHeight(), 0.1f, 1000f);	
		Transform.setCamera(player.getCamera());
	}	
	
	public void Input()
	{	
		level.input();
		player.input();
	}	
	
	public void Update()
	{	
		level.update();
		player.update();
	}	
	
	public void Render()
	{
		level.render();
		player.render();
	}
	
	public static Level getLevel()
	{
		return level;
	}
}