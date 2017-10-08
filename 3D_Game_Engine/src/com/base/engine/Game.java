package com.base.engine;

import org.lwjgl.input.Keyboard;

public class Game 
{
	public Game()
	{
		
	}
	
	public void Input()
	{
		if(Input.getKeyDown(Keyboard.KEY_UP))
			System.out.println("We just pressed Up!!!");
		
		if(Input.getKeyUp(Keyboard.KEY_UP))
			System.out.println("We just Released Up!!!");
		
		
		if(Input.getMouseDown(0))
			System.out.println("We just pressed LeftMouseButton!!!" + Input.getMousePosition().toString());
		
		if(Input.getMouseUp(0))
			System.out.println("We just released LeftMouseButton!!!");
	}
	
	public void Update()
	{
		
	}
	
	public void Render()
	{
		
	}
}
