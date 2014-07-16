package com.game.karapansapi;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.game.karapansapi.BaseScene;
import com.game.karapansapi.ResourcesManager;

public class SceneManager 
{
	//SCENES
	
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	private BaseScene levelScene;
	private BaseScene highscoreScene;
	private BaseScene helpScene;
	
	private BaseScene gameScene2;
	private BaseScene gameScene3;
	
	//VARIABLES
	private static final SceneManager INSTANCE = new SceneManager();
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().engine;
	public enum SceneType
	{
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_GAME, 
		SCENE_LOADING,
		SCENE_LEVEL,
		SCENE_HIGHSCORE,
		SCENE_HELP,
		
		SCENE_GAME2,
		SCENE_GAME3
	}
	
	//CLASS LOGIC
	public void setScene(BaseScene scene)
	{
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType)
	{
		switch (sceneType)
		{
			case SCENE_MENU:
				setScene(menuScene);
				break;
			case SCENE_GAME:
				setScene(gameScene);
				break;
			case SCENE_SPLASH:
				setScene(splashScene);
				break;
			case SCENE_LOADING:
				setScene(loadingScene);
				break;
			case SCENE_LEVEL:
				setScene(levelScene);
				break;
			case SCENE_HIGHSCORE:
				setScene(highscoreScene);
				break;
			case SCENE_HELP:
				setScene(helpScene);
				break;
				
			// nanti dihapus..jelek implementasi designnya
			case SCENE_GAME2:
				setScene(gameScene2);
				break;
			case SCENE_GAME3:
				setScene(gameScene3);
				break;
			default:
				break;
		}
	}
	
	public static SceneManager getInstance()
	{
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType()
	{
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene()
	{
		return currentScene;
	}
	
	//Splash Scene
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
	{
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		//splashScene.createScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	public void disposeSplashScene()
	{
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
		
	}
	
	
	//MenuScene
	public void createMenuScene()
	{
		ResourcesManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		ResourcesManager.getInstance().loadLoadingTexture();
		loadingScene = new LoadingScene();
		SceneManager.getInstance().setScene(menuScene);
		
		//DEBUG
		//SceneManager.getInstance().setScene(loadingScene);
		disposeSplashScene();
	}
	
	
	
	
	//Game Scene
	public void loadGameScene(final Engine mEngine, int diff)
	{
	    setScene(loadingScene);
	    ResourcesManager.getInstance().unloadMenuTextures();
	    final int tempDiff = diff;
	    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {////
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            ResourcesManager.getInstance().loadGameResources();
	            //gameScene = new GameScene(tempDiff);
	            gameScene = new GameScene();
	            setScene(gameScene);
	        }
	    }));
	}
	
	//LEVEL 2...NANTI DIBEERIN YAH.. :)
	public void loadGameScene2(final Engine mEngine, int diff)
	{
	    setScene(loadingScene);
	    ResourcesManager.getInstance().unloadMenuTextures();
	    final int tempDiff = diff;
	    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            ResourcesManager.getInstance().loadGameResources();
	            gameScene2 = new GameScene2(tempDiff);
	            setScene(gameScene2);
	        }
	    }));
	}
	
	public void loadGameScene3(final Engine mEngine, int diff)
	{
	    setScene(loadingScene);
	    ResourcesManager.getInstance().unloadMenuTextures();
	    final int tempDiff = diff;
	    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            ResourcesManager.getInstance().loadGameResources();
	            gameScene3 = new GameScene3(tempDiff);
	            setScene(gameScene3);
	        }
	    }));
	}
	
	
	//LEVEL SCENE
	public void loadLevelMenu(final Engine mEngine)
	{
		setScene(loadingScene);
		//ResourcesManager.getInstance().unloadLevelScreen();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback()
		{
			
			public void onTimePassed(final TimerHandler pTimerHandler)
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadLevelScreen();
				levelScene = new LevelScene();
				setScene(levelScene);
				
			}
		}));
	}
	
	public void loadMenuScene(final Engine mEngine)
	{
		
		try
		{
			setScene(loadingScene);
			
			ResourcesManager.getInstance().unloadMenuTextures();
			mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback()
			{
				
				public void onTimePassed(final TimerHandler pTimerHandler)
				{
					mEngine.unregisterUpdateHandler(pTimerHandler);
					ResourcesManager.getInstance().loadMenuTextures();
					
					setScene(menuScene);
					
				}
			}));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	//LOADING SCENE
	public void loadLoadingScene()
	{
		
	}
	
	//HIGHSCORE SCENE
	public void loadHighscoreScene(final Engine mEngine)
	{
		setScene(loadingScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback()
		{
			public void onTimePassed(final TimerHandler pTimerHandler)
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadHighscoreScreen();
				highscoreScene = new HighscoreScene();
				setScene(highscoreScene);
			}
		}));
	}
	


}
