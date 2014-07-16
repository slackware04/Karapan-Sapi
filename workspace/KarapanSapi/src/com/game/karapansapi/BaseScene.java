package com.game.karapansapi;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.game.karapansapi.SceneManager;
import com.game.karapansapi.SceneManager.SceneType;

import android.app.Activity;

public abstract class BaseScene extends Scene 
{
	//VARIABLES
	public Engine engine;
	public Activity activity;
	public ResourcesManager resourcesManager;
	public VertexBufferObjectManager vbom;
	public Camera camera;
	public BoundCamera gameCamera;
	
	public BaseScene()
	{
		this.resourcesManager = ResourcesManager.getInstance();
		this.engine = resourcesManager.engine;
		this.activity = resourcesManager.activity;
		this.vbom = resourcesManager.vbom;
		this.camera = resourcesManager.camera;
		createScene();
		//BaseScene();
	}
	
	//ABSTRACTION
	
	public abstract void createScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
	public abstract void disposeScene();
}
