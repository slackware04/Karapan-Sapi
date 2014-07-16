package com.game.karapansapi;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;
import org.andengine.input.touch.TouchEvent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.game.karapansapi.SceneManager.SceneType;


public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener
{
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_HIGHSCORE = 1;
	private final int MENU_REFRESH = 2;
	private final int MENU_HELP = 3;
	private final int MENU_KEY = 4;
	
	private final int CAMERA_WIDTH = 800;
	private final int CAMERA_HEIGHT = 480;
	
	private void createMenuChildScene()
	{
		
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		
		IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, engine.getVertexBufferObjectManager()), 1.2f, 1);
		IMenuItem highscoreMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HIGHSCORE, resourcesManager.highscore_region, engine.getVertexBufferObjectManager()), 1.2f, 1);
		IMenuItem refreshMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_REFRESH, resourcesManager.options_region, engine.getVertexBufferObjectManager()), 1.2f, 1);
		IMenuItem helpMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HELP, resourcesManager.help_region, engine.getVertexBufferObjectManager()), 1.2f, 1);
		IMenuItem keyMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_KEY, resourcesManager.key_region, engine.getVertexBufferObjectManager()), 1.2f, 1);
		
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(highscoreMenuItem);
		menuChildScene.addMenuItem(refreshMenuItem);
		menuChildScene.addMenuItem(helpMenuItem);
		menuChildScene.addMenuItem(keyMenuItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		
		playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() + 100 + 50);
		highscoreMenuItem.setPosition(highscoreMenuItem.getX(), highscoreMenuItem.getY() + 115 + 50);
		refreshMenuItem.setPosition(20, CAMERA_HEIGHT - 20 - refreshMenuItem.getHeight());
		helpMenuItem.setPosition(CAMERA_WIDTH - 20 - helpMenuItem.getWidth(), CAMERA_HEIGHT - 20 - helpMenuItem.getHeight());
		keyMenuItem.setPosition(CAMERA_WIDTH / 2 - (helpMenuItem.getWidth() / 2), CAMERA_HEIGHT - 20 - helpMenuItem.getHeight());
		
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
		
		/*
		Sprite playMenuItem = new Sprite(0, 0 + 100 + 50, resourcesManager.play_region, engine.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
			{
				SceneManager.getInstance().loadLevelMenu(engine);
				return true;
			}
		};
		MainMenuScene.this.registerTouchArea(playMenuItem);
		attachChild(playMenuItem);
		*/
	}
	
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
		switch(pMenuItem.getID())
		{
		case MENU_PLAY:
			SceneManager.getInstance().loadLevelMenu(engine);
			return true;
		case MENU_HIGHSCORE:
			SceneManager.getInstance().loadHighscoreScene(engine);
			return true;
		case MENU_REFRESH:
			UserData.getInstance().resetAll();
			return true;
		case MENU_HELP:
			String b = UserData.getInstance().getName();
			System.out.println(" levels : " + b);
			return true;
		case MENU_KEY:
			UserData.getInstance().openAllLevels();
			return true;
		default:
			return false;
		}
	}
	

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		
		createBackground();
		createMenuChildScene();
		
		/*
		UserData.getInstance().setSoundMuted(false);
		UserData.getInstance().unlockNextLevel();
		boolean a = UserData.getInstance().isSoundMuted();
		
		
		int b = UserData.getInstance().getMaxUnlockedLevel();
		System.out.println("sound : " + a + " levels : " + b);
		*/
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		System.exit(0);
		
	}

	@Override
	public SceneType getSceneType() {
		
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createBackground()
	{
		Sprite backgroundSprite = new Sprite(0, 0, resourcesManager.menu_background_region, engine.getVertexBufferObjectManager())
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
	
	


}
