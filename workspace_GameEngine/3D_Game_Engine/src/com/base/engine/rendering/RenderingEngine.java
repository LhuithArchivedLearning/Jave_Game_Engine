package com.base.engine.rendering;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.core.GameObject;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.*;

public class RenderingEngine 
{
	private Camera mainCamera;
	private Vector3f ambientLight;
	
	/*More Permenant Strecture */
	 private ArrayList<BaseLight> lights;
	 private BaseLight activeLight;
	 
	public RenderingEngine()
	{
		lights = new ArrayList<BaseLight>();
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
		
		//mainCamera = new Camera((float)(Math.toRadians(70.0f)), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000.0f);
		
		ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
		
		//activeDirectionalLight = new DirectionalLight(new BaseLight(new Vector3f(1.0f,0.0f,0.0f), 0.4f), new Vector3f(1.0f,1.0f,1.0f));
		//pointLight = new PointLight(new BaseLight(new Vector3f(0.0f,1.0f,0.0f), 0.4f), new Attenuation(0.0f,0.0f,1.0f), new Vector3f(0,0,0), 100);

//		int lightFieldWidth = 5;
//		
//		int lightFieldDepth = 5;
//		
//		float lightFieldStartX = 0;
//		float lightFieldStartY = 0;
//		float lightFieldStepX = 7;
//		float lightFieldStepY = 7;
		
//		pointLights = new PointLight[lightFieldWidth * lightFieldDepth];
//		
//		for(int i = 0; i < lightFieldWidth; i++)
//		{
//			for(int j = 0; j < lightFieldDepth; j++)
//			{
//				pointLights[i * lightFieldWidth + j] = new PointLight(new BaseLight(new Vector3f(0,1.0f,0.0f), 0.5f),
//						new Attenuation(0,0,1),
//						new Vector3f(lightFieldStartX + lightFieldStepX * i, 0 ,lightFieldStartY + lightFieldStepY * j), 100);
//			}
//		}
		
//		activeSpotLight = 
//				new SpotLight(new PointLight(new BaseLight(new Vector3f(0.0f,1.0f, 1.0f), 0.6f), 
//				new Attenuation(0.0f,0.0f,0.1f),
//				new Vector3f(lightFieldStartX, 0.0f, lightFieldStartY), 100), 
//				new Vector3f(1,0,0), 0.7f);
		
		
	}
	
	
	public void render(GameObject object)
	{
	    clearScreen();
	    
	    lights.clear();
	    //clearLightList();
	    object.addToRenderingEngine(this);
	    
	    Shader forwardAmbient = ForwardAmbient.getInstance();
	    
	    forwardAmbient.setRenderingEngine(this);
	    
		object.render(forwardAmbient);
		
		//glEnable(GL_DEPTH_TEST);
		//glDepthMask(false);
		//TODO: FIX GLDEPTH MASK ISSUE!!!!!!!!!!!!!!
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		//glDisable(GL_DEPTH_TEST);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		
		for(BaseLight light : lights)
		{
			//object.render(light.getShader());
			light.getShader().setRenderingEngine(this);
			activeLight = light;
			object.render(light.getShader());
		}
		
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		//glEnable(GL_DEPTH_TEST);
		glDisable(GL_BLEND);
	}
	
	
//	private void clearLightList()
//	{
//		directionalLights.clear();
//		pointLights.clear();
//	}
	
	public Vector3f getAmbientLight()
	{
		return ambientLight;
	}
	
	private static void clearScreen()
	{
		//TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private static void unbindTextures()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	private  static void setClearColor(Vector3f color)
	{
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
	}
	
	private static void setTexture(boolean enabled)
	{
		if(enabled)
			glEnable(GL_TEXTURE_2D);
		else
			glDisable(GL_TEXTURE_2D);
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
