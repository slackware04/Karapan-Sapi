package com.game.karapansapi;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import com.game.karapansapi.SceneManager.SceneType;

public class HighscoreScene extends BaseScene 
{
	private MenuScene menuChildScene;
	private final int CAMERA_WIDTH = 800;
	private final int CAMERA_HEIGHT = 480;

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createHighscoreChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		SceneManager.getInstance().loadMenuScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createBackground()
	{
		Sprite backgroundSprite = new Sprite(0, 0, resourcesManager.menu_background_region, vbom)
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera)
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		this.setPosition(0, 0);
		attachChild(backgroundSprite);
		
	}
	
	private void createHighscoreChildScene()
	{
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		Sprite highscoreMenuItem = new Sprite(CAMERA_WIDTH / 2 - resourcesManager.highscore_region.getWidth() / 2, 120, resourcesManager.highscore_region, vbom);
		Text record1 = new Text(20, CAMERA_HEIGHT / 2, resourcesManager.font, "1. Nanda 40", vbom);
		Text record2 = new Text(20, CAMERA_HEIGHT / 2 + 60, resourcesManager.font, "1. Kevin 30", vbom);
		Text record3 = new Text(20, CAMERA_HEIGHT / 2 + 120, resourcesManager.font, "1. Nindya 20", vbom);
		
		menuChildScene.attachChild(highscoreMenuItem);
		menuChildScene.attachChild(record1);
		menuChildScene.attachChild(record2);
		menuChildScene.attachChild(record3);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		//highscoreMenuItem.setPosition(highscoreMenuItem.getX(), highscoreMenuItem.getY() + 115);
		
		setChildScene(menuChildScene);
	}

}
