package com.base.game;

import com.base.engine.components.Camera;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Texture;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;
import com.base.engine.rendering.Window;

public class TestGame extends Game 
{
	public void init()
	{
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
		
		Material material = new Material(new Texture("test.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f, 8.0f);
		Mesh mesh = new Mesh(vertices, indices, true);
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		
		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPos().set(0, -1, 5);
		
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight =  new DirectionalLight(new Vector3f(1.0f,0.0f,0.0f), 0.4f, new Vector3f(1.0f,1.0f,1.0f));
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
		
		spotLightObject.getTransform().getPos().set(5,0,5);
		spotLightObject.getTransform().setRot(new Quaternion().initRotation(new Vector3f(0,1,0),(float) Math.toRadians(90.0f)));
		getRootObject().addChild(planeObject);
		//getRootObject().addChild(directionalLightObject);
		getRootObject().addChild(pointLightObject);
		getRootObject().addChild(spotLightObject);
		
		getRootObject().addChild(new GameObject().addComponent(new Camera((float)(Math.toRadians(70.0f)), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000.0f)));
	}
	
}
