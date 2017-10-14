package com.base.engine;

public class Player 
{
	private Camera camera;
	
	private boolean mouseLocked = false;
	private Vector2f centrePosition = new Vector2f(Window.getWidth()/2, Window.GetHeight()/2);
	private Vector3f movementVector;
	private static final float PLAYER_SIZE = 0.2f;
	private static final float MOUSE_SENSITIVITY = 0.5f;
	private static final float MOVE_SPEED = 2.5f;
	private static final Vector3f zeroVector = new Vector3f(0,0,0);
	
	public Player(Vector3f position)
	{
		camera = new Camera(position, new Vector3f(0,0,1), new Vector3f(0,1,0));
	}
	
	public void input()
	{

		if(Input.GetKey(Input.KEY_ESCAPE))
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
		
		movementVector = zeroVector;
		
		if(Input.GetKey(Input.KEY_W))
			movementVector = movementVector.add(camera.getForward());//camera.move(camera.getForward(), moveAmt);
		if(Input.GetKey(Input.KEY_S))
			movementVector = movementVector.sub(camera.getForward());//camera.move(camera.getForward(), -moveAmt);
		if(Input.GetKey(Input.KEY_A))
			movementVector = movementVector.add(camera.getLeft());//camera.move(camera.getLeft(), moveAmt);
		if(Input.GetKey(Input.KEY_D))
			movementVector = movementVector.add(camera.getRight());//camera.move(camera.getRight(), moveAmt);

		if(mouseLocked)
		{
			Vector2f deltaPos = Input.GetMousePosition().sub(centrePosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				camera.rotateY(deltaPos.getX() * MOUSE_SENSITIVITY);
			if(rotX)
				camera.rotateX(-deltaPos.getY() * MOUSE_SENSITIVITY);
			
			
			if(rotY || rotX)
				Input.SetMousePosition(centrePosition);
		}
	}
	
	public void update()
	{
		float moveAmt = (float)(MOVE_SPEED * Time.getDelta());
		
		movementVector.setY(0);
		
		if(movementVector.length() > 0)
		movementVector = movementVector.normalized();
		
		Vector3f oldPos = camera.getPos();
		Vector3f newPos = oldPos.add(movementVector.mul(moveAmt));
		
		Vector3f collisionVector = Game.getLevel().checkCollisions(oldPos, newPos, PLAYER_SIZE, PLAYER_SIZE);
		movementVector = movementVector.mul(collisionVector);
		
		camera.move(movementVector, moveAmt);
		
	}
	
	public void render()
	{
		
	}

	public Camera getCamera() {
		return camera;
	}
}
