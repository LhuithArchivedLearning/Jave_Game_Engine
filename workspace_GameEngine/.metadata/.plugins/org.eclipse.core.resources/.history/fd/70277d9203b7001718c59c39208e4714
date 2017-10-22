package com.base.engine.rendering.resourceManagment;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class TextureResource 
{
	private int id;
	private int refCount;
	
	public TextureResource(int id)
	{
		this.id = id;
		this.refCount = 1;
	}
	
	@Override
	protected void finalize()
	{
		glDeleteBuffers(id);
	}
	
	public void addRefrence()
	{
		refCount++;
	}
	
	public boolean removedRefrence()
	{
		refCount--;
		return refCount == 0;
	}
	
	public int getId() {
		return id;
	}
}
