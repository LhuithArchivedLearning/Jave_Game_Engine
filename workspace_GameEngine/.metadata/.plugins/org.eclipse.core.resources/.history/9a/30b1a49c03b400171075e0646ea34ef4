package com.base.engine;

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
	
	
	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix,  Material material)
	{
		if(material.getTexture() != null)
		{
			material.getTexture().bind();
		}
		else
			RenderUtil.unbindTextures();

		
		setUniform("transform", projectedMatrix);
		setUniform("color", material.getColor());
		
	}
}
