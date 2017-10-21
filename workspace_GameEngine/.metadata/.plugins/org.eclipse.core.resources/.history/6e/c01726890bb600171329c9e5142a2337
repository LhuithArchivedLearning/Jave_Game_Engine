package com.base.engine.rendering;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class BasicShader extends Shader
{
	private static final BasicShader instance = new BasicShader();
	
	public static BasicShader getInstance()
	{
		return instance;
	}
	
	public BasicShader()
	{
		super();
		
		AddVertexShaderFromFile("basic.vs.glsl");	
		AddFragmentShaderFromFile("basic.fs.glsl");
		compileShader();
		
		AddUniform("transform");
		AddUniform("color");
	}
	
	
	public void updateUniforms(Transform transform,  Material material)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		
		
		if(material.getTexture() != null)
			material.getTexture().bind();

		
		setUniform("transform", projectedMatrix);
		setUniform("color", material.getColor());
		
	}
}
