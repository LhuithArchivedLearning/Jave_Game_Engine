package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class FreeLook extends GameComponent
{
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private boolean mouseLocked = false;
	private float sensitivity;
	private int unlockMouseKey;
	
	public FreeLook(float sensitivity)
	{
		this(sensitivity, Input.KEY_ESCAPE);
	}

	public FreeLook(float sensitivity, int unlockMouseKey)
	{
		this.sensitivity = sensitivity;
		this.unlockMouseKey = unlockMouseKey;
	}
	
	@Override
	public void input (float delta)
	{
		Vector2f centrePosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
		

		if(Input.GetKey(unlockMouseKey))
		{
			Input.SetCursor(true);
			mouseLocked = false;
		}
		if(Input.GetMouseDown(1))
		{
			Input.SetMousePosition(centrePosition);
			Input.SetCursor(false);
			mouseLocked = true;
		}
		
		if(Input.GetMouseUp(1))
		{
			Input.SetMousePosition(centrePosition);
			Input.SetCursor(true);
			mouseLocked = false;
		}
		
		if(mouseLocked)
		{
			Vector2f deltaPos = Input.GetMousePosition().sub(centrePosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				getTransform().rotate(yAxis, (float)Math.toRadians(deltaPos.getX() * sensitivity));
		
			if(rotX)
				getTransform().rotate(getTransform().getRot().getRight(), -(float)Math.toRadians(deltaPos.getY() * sensitivity));
						
			if(rotY || rotX)
				Input.SetMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
		}
		
			
	}
}
