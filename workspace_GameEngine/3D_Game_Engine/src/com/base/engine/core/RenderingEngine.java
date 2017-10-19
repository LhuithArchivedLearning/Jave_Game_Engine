package com.base.engine.core;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import com.base.engine.rendering.*;

public class RenderingEngine 
{
	private Camera mainCamera;
	private Vector3f ambientLight;
	private DirectionalLight directionalLight;
	private DirectionalLight directionalLight2;
	private PointLight pointLight;
	private SpotLight spotLight;
	private PointLight[] pointLights;
	
	public RenderingEngine()
	{
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
		
		mainCamera = new Camera((float)(Math.toRadians(70.0f)), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000.0f);
		
		ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);
		directionalLight = new DirectionalLight(new BaseLight(new Vector3f(1.0f,0.0f,0.0f), 0.4f), new Vector3f(1.0f,1.0f,1.0f));
		directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(0.0f,0.0f,1.0f), 0.4f), new Vector3f(-1.0f,1.0f,-1.0f));
		//pointLight = new PointLight(new BaseLight(new Vector3f(0.0f,1.0f,0.0f), 0.4f), new Attenuation(0.0f,0.0f,1.0f), new Vector3f(0,0,0), 100);

		int lightFieldWidth = 5;
		
		int lightFieldDepth = 5;
		
		float lightFieldStartX = 0;
		float lightFieldStartY = 0;
		float lightFieldStepX = 7;
		float lightFieldStepY = 7;
		
		pointLights = new PointLight[lightFieldWidth * lightFieldDepth];
		
		for(int i = 0; i < lightFieldWidth; i++)
		{
			for(int j = 0; j < lightFieldDepth; j++)
			{
				pointLights[i * lightFieldWidth + j] = new PointLight(new BaseLight(new Vector3f(0,1.0f,0.0f), 0.5f),
						new Attenuation(0,0,1),
						new Vector3f(lightFieldStartX + lightFieldStepX * i, 0 ,lightFieldStartY + lightFieldStepY * j), 100);
			}
		}
		
		spotLight = 
				new SpotLight(new PointLight(new BaseLight(new Vector3f(0.0f,1.0f, 1.0f), 0.6f), 
				new Attenuation(0.0f,0.0f,0.1f),
				new Vector3f(lightFieldStartX, 0.0f, lightFieldStartY), 100), 
				new Vector3f(1,0,0), 0.7f);
		
		
	}
	
	
	public SpotLight getSpotLight()
	{
		return spotLight;
	}
	
	
	public PointLight getPointLight()
	{
		return pointLight;
	}
	
	public Vector3f getAmbientLight()
	{
		return ambientLight;
	}
	
	public DirectionalLight getDirectionalLight()
	{
		return directionalLight;
	}
	
	public void input(float delta)
	{
		mainCamera.input(delta);
	}
	
	public void render(GameObject object)
	{
	    clearScreen();
	 
	    Shader forwardAmbient = ForwardAmbient.getInstance();
	    Shader forwardDirectional = ForwardDirectional.getInstance();
	    Shader forwardPoint = ForwardPoint.getInstance();
	    Shader forwardSpot = ForwardSpot.getInstance();
	    
	    forwardAmbient.setRenderingEngine(this);
	    forwardDirectional.setRenderingEngine(this);
	    forwardPoint.setRenderingEngine(this);
	    forwardSpot.setRenderingEngine(this);
	    
		object.render(forwardAmbient);
		
		//glEnable(GL_DEPTH_TEST);
		//glDepthMask(false);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDisable(GL_DEPTH_TEST);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		//Blending Magic
		
		object.render(forwardDirectional);
		
		DirectionalLight temp = directionalLight;
		directionalLight = directionalLight2;
		directionalLight2 = temp;
		
		object.render(forwardDirectional);
		
		temp = directionalLight;
		directionalLight = directionalLight2;
		directionalLight2 = temp;
		
		for(int i = 0; i < pointLights.length; i++)
		{
			pointLight = pointLights[i];
			object.render(forwardPoint);
		}
		
		object.render(forwardSpot);
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_BLEND);
		//glBlendFunc(GL_ONE, GL_ZERO);
//	    Shader shader = BasicShader.getInstance();
//	    shader.setRenderingEngine(this);
//	    
//		object.render(BasicShader.getInstance());
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


	public Camera getMainCamera() 
	{
		return mainCamera;
	}


	public void setMainCamera(Camera mainCamera) 
	{
		this.mainCamera = mainCamera;
	}
}