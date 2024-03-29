package com.base.engine.rendering;

import java.util.HashMap;

import com.base.engine.core.Texture;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagment.MappedValues;

public class Material extends MappedValues
{
	private HashMap<String, Texture> textureHashMap;


	public Material()
	{
		super();
		textureHashMap = new HashMap<String, Texture>();
	}

	public void addTexture(String name, Texture texture){textureHashMap.put(name, texture);}

	public Texture getTexture(String name)
	{
		Texture result = textureHashMap.get(name);		
		if(result != null)
		return result;
		
		return new Texture("fail.png");
	}

}
