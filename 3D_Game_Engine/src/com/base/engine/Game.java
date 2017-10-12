package com.base.engine;

public class Game 
{
	private Mesh mesh;
	private Shader shader;
	private Material material;
	private Transform transform;
	private Camera camera;
	
	PointLight pLight1 = new PointLight(new BaseLight(new Vector3f(1,0.5f,0), 0.8f), new Attenuation(1,0,0), new Vector3f(-2, 0, 5));
	PointLight pLight2 = new PointLight(new BaseLight(new Vector3f(0,0.5f,1), 0.8f), new Attenuation(1,0,0), new Vector3f(2, 0, 7f));
	
	
	public Game()
	{
		mesh = new Mesh(); //ResourcesLoad.loadMesh("cube.obj");
		camera = new Camera();
		material = new Material(ResourceLoader.LoadTexture("test.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1, 8);
		shader = PhongShader.getInstance();
		transform = new Transform();
		
		
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
		
		
//		
//		Vertex[] vertices = new Vertex[]
//				{
//						new Vertex(new Vector3f(-1.0f, -1.0f, 0.5773f),  new Vector2f(0.0f, 0.0f)),
//						new Vertex(new Vector3f(0.0f, -1.0f, -1.15475f), new Vector2f(0.5f, 0.0f)),
//						new Vertex(new Vector3f(1.0f, -1.0f, 0.5773f),   new Vector2f(1.0f, 0.0f)),
//						new Vertex(new Vector3f(0.0f, 1.0f, 0.0f),       new Vector2f(0.5f, 1.0f)),
//				};
//		int[] indices = new int[] {0, 3, 1,
//								   1, 3, 2,
//								   2, 3, 0,
//								   1, 2, 0
//		};
		
		mesh.AddVertices(vertices, indices, true);
		
		Transform.setProjection(70f, Window.getWidth(), Window.GetHeight(), 0.1f, 1000);
		Transform.setCamera(camera);
		
		PhongShader.setAmbientLight(new Vector3f(0.1f,0.1f,0.1f));
		//PhongShader.setDirectionalLight( new DirectionalLight(new BaseLight(new Vector3f(1,1,1), 0.8f), new Vector3f(1,1,1)));
		
		PhongShader.setPointLight(new PointLight[]{pLight1, pLight2});
	}
	
	
	public void Input()
	{
		camera.input();
	}
	
	float temp = 0.0f;
	
	public void Update()
	{
		temp += Time.getDelta();
		
		float sinTemp = (float)Math.sin(temp);
		
		transform.setTranslation(0, -1, 5);
		//transform.setRotation(0, sinTemp * 180, 0);
		
		pLight1.setPosition(new Vector3f(3, 0, 8.0f * (float)Math.sin(temp) + 1.0f/2.0f + 10));
		pLight2.setPosition(new Vector3f(7, 0, 8.0f * (float)Math.cos(temp) + 1.0f/2.0f + 10));
	}
	
	
	public void Render()
	{
		RenderUtil.setClearColor(Transform.getCamera().getPos().div(2048f).abs());
		shader.bind();
		shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
		mesh.draw();
	}
}
