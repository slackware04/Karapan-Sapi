package com.game.karapansapi;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;

import com.game.karapansapi.SceneManager.SceneType;
import com.game.karapansapi.BaseScene;

public class SplashScene extends BaseScene
{
	private Sprite splash;
	
	@Override
	public void createScene()
	{
		splash = new Sprite(0, 0, resourcesManager.splash_region, vbom)
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera)
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		splash.setScale(1.5f);
		splash.setPosition((camera.getWidth() - splash.getWidth())/ 2, (camera.getHeight() - splash.getHeight())/2);
		attachChild(splash);
	}
	
	@Override 
	public void onBackKeyPressed()
	{
		
	}
	
	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_SPLASH;
	}
	
	@Override 
	public void disposeScene()
	{
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}
	
}
