package com.base.game;

import com.base.engine.components.DirectionalLight;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Texture;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;

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
		planeObject.getTransform().setPos(0, -1, 5);
		
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
				new Vector3f(0.0f,0.0f,0.1f),
				new Vector3f(1,0,0), 0.7f);
		
		
		spotLightObject.addComponent(spotLight);
		
		spotLightObject.getTransform().setPos(5,0,5);
		
		getRootObject().addChild(planeObject);
		//getRootObject().addChild(directionalLightObject);
		getRootObject().addChild(pointLightObject);
		getRootObject().addChild(spotLightObject);
	}
	
}
