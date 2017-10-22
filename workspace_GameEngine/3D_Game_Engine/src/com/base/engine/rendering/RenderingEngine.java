package com.base.engine.rendering;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.core.GameObject;
import com.base.engine.core.Texture;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagment.MappedValues;

public class RenderingEngine extends MappedValues
{
	private Camera mainCamera;
	private ArrayList<BaseLight> lights;
	private BaseLight activeLight;
	 
	private HashMap<String, Integer> samplerMap;
	private Shader forwardAmbient;
	 
	public RenderingEngine()
	{
		super();
		lights = new ArrayList<BaseLight>();
		samplerMap = new HashMap<String, Integer>();		
		samplerMap.put("diffuse", 0);
		
		addVector3f("ambient", new Vector3f(0.05f, 0.05f, 0.05f));
		
	    forwardAmbient = new Shader("forward-ambient"); //ForwardAmbient.getInstance();
    		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
						
	}
	
	public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType)
	{
		throw new IllegalArgumentException(uniformName + " is not a valid component in Rendering Engine");
	}
	
	public void render(GameObject object)
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	    
		object.renderAll(forwardAmbient, this);
		
		//glEnable(GL_DEPTH_TEST);
		//glDepthMask(false);
		
		//TODO: FIX GLDEPTH MASK ISSUE!!!!!!!!!!!!!!
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		
		for(BaseLight light : lights)
		{
			activeLight = light;
			object.renderAll(light.getShader(), this);
		}
		
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}


	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}
		
	public void addLight(BaseLight light)
	{
		lights.add(light);
	}
	
	public void addCamera(Camera camera)
	{
		mainCamera = camera;
	}
	
	public int getSamplerSlot(String samplerName)
	{
		return samplerMap.get(samplerName);
	}
	
	public BaseLight getActivelight()
	{
		return activeLight;
	}
	
	public Camera getMainCamera() 
	{
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera) 
	{
		this.mainCamera = mainCamera;
	}
}
