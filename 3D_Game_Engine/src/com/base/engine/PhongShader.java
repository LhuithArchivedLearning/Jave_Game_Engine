package com.base.engine;

public class PhongShader extends Shader
{
	private static final int MAX_POINT_LIGHTS = 4;
	
	private static final PhongShader instance = new PhongShader();
	
	public static PhongShader getInstance()
	{
		return instance;
	}
	
	private static Vector3f ambientLight = new Vector3f(0.1f,0.1f,0.1f);
	private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(1,1,1), 0), new Vector3f(0,0,0));
	private static PointLight[] pointLights = new PointLight[]{};
	
	public PhongShader()
	{
		super();
		
		AddVertexShader(ResourceLoader.loadShader("phong.vs.glsl"));	
		AddFragmentShader(ResourceLoader.loadShader("phong.fs.glsl"));
		compileShader();
		
		AddUniform("transform");
		AddUniform("transformProjected");
		AddUniform("BaseColor");
		AddUniform("ambientLight");
		
		AddUniform("specularIntensity");
		AddUniform("specularPower");
		AddUniform("eyePos");
		
		AddUniform("directionalLight.base.color");
		AddUniform("directionalLight.base.intensity");
		AddUniform("directionalLight.direction");
		
		for(int i = 0; i < MAX_POINT_LIGHTS; i++)
		{
			AddUniform("pointLights[" + i + "].base.color");
			AddUniform("pointLights[" + i + "].base.intensity");
			AddUniform("pointLights[" + i + "].atten.constant");
			AddUniform("pointLights[" + i + "].atten.linear");
			AddUniform("pointLights[" + i + "].atten.exponent");			
			AddUniform("pointLights[" + i + "].position");
		}
	}
	
	
	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix,  Material material)
	{
		if(material.getTexture() != null)
		{
			material.getTexture().bind();
		}
		else
			RenderUtil.unbindTextures();

		
		setUniform("transformProjected", projectedMatrix);
		setUniform("transform", worldMatrix);
		setUniform("BaseColor", material.getColor());
			
		setUniform("ambientLight", ambientLight);
		setUniform("directionalLight", directionalLight);
		
		for(int i = 0; i < pointLights.length; i++)
		setUniform("pointLights["+i+"]", pointLights[i]);
		
		setUniformf("specularIntensity", material.getSpecularIntensity());
		setUniformf("specularPower", material.getSpecularPower());
		
		setUniform("eyePos", Transform.getCamera().getPos());
	}


	public static Vector3f getAmbientLight() {
		return ambientLight;
	}

	public static void setAmbientLight(Vector3f ambientLight) {
		PhongShader.ambientLight = ambientLight;
	}
	
	public static DirectionalLight getDirectionalLight() {
		return directionalLight;
	}

	public static void setDirectionalLight(DirectionalLight directionalLight) {
		PhongShader.directionalLight = directionalLight;
	}

	public static void setPointLight(PointLight[] pointLights)
	{
		if(pointLights.length > MAX_POINT_LIGHTS)
		{
			System.err.println("ERROR: To Many Point Lights. Max is " + MAX_POINT_LIGHTS + "You Passed" + pointLights.length);
			new Exception().printStackTrace();
			System.exit(1);
		}
			
		PhongShader.pointLights = pointLights;
	}
	
	public void setUniform(String uniformName, BaseLight baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, DirectionalLight directionalLight)
	{
		setUniform(uniformName + ".base", directionalLight.getBase());
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}
	
	public void setUniform(String uniformName, PointLight pointLight)
	{
		setUniform(uniformName + ".base", pointLight.getBaseLight());
		setUniformf(uniformName + ".atten.constant", pointLight.getAtten().getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getAtten().getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getAtten().getExponent());		
		setUniform(uniformName + ".position", pointLight.getPosition());
	}
	
}
