package com.base.game;

import com.base.engine.components.Camera;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Input;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Texture;
import com.base.engine.core.Time;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;
import com.base.engine.rendering.Window;

public class TestGame extends Game 
{
	GameObject test3;
	float temp;
	
	public static final Vector3f GRAVITY = new Vector3f(0, -9.8f, 0);
	public static final float FRICTION = -0.999f;
	Vector3f oldp, accel;
	
	public void init()
	{
		
		oldp = new Vector3f(0,0,0);
		
		
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		
		Vertex[] vertices = new Vertex[]
				{
						new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth),  new Vector2f(0.0f, 0.0f)),
						new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
						new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth),   new Vector2f(1.0f, 0.0f)),
						new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth* 3),       new Vector2f(1.0f, 1.0f)),
				};
		int[] indices = new int[] {0, 1, 2,
								   2, 1, 3
		};
		
		Material material = new Material();//new Texture("test.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f, 8.0f;
		material.addTexture("diffuse", new Texture("wood.jpg"));
		material.addFloat("specularIntensity", 1.0f);
		material.addFloat("specularPower", 8.0f);
		
		Material material2 = new Material();//new Texture("test.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f, 8.0f;
		material2.addTexture("diffuse", new Texture("wood.jpg"));
		material2.addFloat("specularIntensity", 0.5f);
		material2.addFloat("specularPower", 0.01f);
		
		
		Mesh tempMesh = new Mesh("monkey3.obj");
		
		Mesh mesh = new Mesh(vertices, indices, true);
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		
		
		Vertex[] vertices2 = new Vertex[]
				{
						new Vertex(new Vector3f(-fieldWidth/4, 0.0f, -fieldDepth/4),  new Vector2f(0.0f, 0.0f)),
						new Vertex(new Vector3f(-fieldWidth/4, 0.0f, fieldDepth * .75f), new Vector2f(0.0f, 1.0f)),
						new Vertex(new Vector3f(fieldWidth/4 * 3, 0.0f, -fieldDepth/4),   new Vector2f(1.0f, 0.0f)),
						new Vertex(new Vector3f(fieldWidth * 0.75f, 0.0f, fieldDepth* .75f),       new Vector2f(1.0f, 1.0f)),
				};
		int[] indices2 = new int[] {0, 1, 2,
								   2, 1, 3
		};
			
		Mesh mesh2 = new Mesh(vertices2, indices, true);
		
		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPos().set(0, -1, 5);
		
		
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight =  new DirectionalLight(new Vector3f(1.0f,1.0f,1.0f), 0.4f);
		
		directionalLightObject.addComponent(directionalLight);
		
		GameObject pointLightObject = new GameObject();
		
		PointLight pointLight = new PointLight(
				new Vector3f(0.0f,1.0f,0.0f), 0.4f, 
				new Vector3f(0.0f,0.0f,1.0f));
		
		pointLightObject.addComponent(pointLight);
		
		GameObject spotLightObject = new GameObject();
		
		PointLight spotLight = new SpotLight(
				new Vector3f(0.0f,1.0f, 1.0f), 0.6f, 
				new Vector3f(0.0f,0.0f,0.1f), 0.7f);
		
		
		spotLightObject.addComponent(spotLight);
		
		addObject(planeObject);
		addObject(directionalLightObject);
		addObject(pointLightObject);
		//addObject(spotLightObject);
		
		//getRootObject().addChild();
	
		GameObject test1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject test2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		test3 = new GameObject().addComponent(new MeshRenderer(tempMesh, material));
		
		test1.getTransform().getPos().set(0, 2, 0);
		test1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 2.4f));
		
		test2.getTransform().getPos().set(0, 0, 15);		
		
		test1.addChild(test2);
		
		test2.addChild(new GameObject().addComponent(new Camera((float)(Math.toRadians(70.0f)), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000.0f)));
		
		
		addObject(test1);
		addObject(test3);
		
		test3.getTransform().getPos().set(5,5,5);
		test3.getTransform().setRot(new Quaternion(new Vector3f(0,1,0),(float) Math.toRadians(-70.0f)));
		
		addObject(new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), material2)));
		
		directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1,0,0),(float) Math.toRadians(-45)));
		
		test3.addChild(spotLightObject);
		
		//spotLightObject.getTransform().getPos().set(5,0,5);
		//spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0,1,0),(float) Math.toRadians(90.0f)));
		
		
		
	}
	
	public void update(float delta)
	{
	    temp += delta;
	    
	    //Super shitty Physics
	    Vector3f velocity = test3.getTransform().getPos().sub(oldp);

	    if( test3.getTransform().getPos().getY() <= 0)
	    {
	    	oldp.setY((test3.getTransform().getPos().getY() + velocity.getY()) * (FRICTION));	
	    }
	    else
	    {		    
		    oldp = (test3.getTransform().getPos());	    
		    test3.getTransform().getPos().set(test3.getTransform().getPos().add(velocity));	    
		    test3.getTransform().getPos().set(test3.getTransform().getPos().add(GRAVITY.mul(delta)));
		    
	    }
	    
	    
		test3.getTransform().getRot().set(new Quaternion(new Vector3f(0,1,0),(float) Math.toRadians(45f * temp)));
	}
}
