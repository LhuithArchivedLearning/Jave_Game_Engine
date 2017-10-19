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
		spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0,1,0),(float) Math.toRadians(90.0f)));
		getRootObject().addChild(planeObject);
		//getRootObject().addChild(directionalLightObject);
		getRootObject().addChild(pointLightObject);
		getRootObject().addChild(spotLightObject);
		
		getRootObject().addChild(new GameObject().addComponent(new Camera((float)(Math.toRadians(70.0f)), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000.0f)));
	
		MeshRenderer meshRenderer2 = new MeshRenderer(mesh2, material);
		MeshRenderer meshRenderer3 = new MeshRenderer(mesh2, material);
		GameObject miniplaneObject = new GameObject();
		miniplaneObject.addComponent(meshRenderer2);
		miniplaneObject.getTransform().getPos().set(0, 2, 0);
		
		GameObject miniplaneObject2 = new GameObject();
		miniplaneObject2.addComponent(meshRenderer3);
		miniplaneObject2.getTransform().getPos().set(15, 0, 0);		
		
		getRootObject().addChild(miniplaneObject);
		
		miniplaneObject.addChild(miniplaneObject2);
		
		miniplaneObject.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 2.4f));
	
	}
	
}
