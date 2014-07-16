package com.game.karapansapi;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity {

	public static int WIDTH = 800;
	public static int HEIGHT = 480;

	public static float CENTER_X = WIDTH * 0.5f;
	public static float CENTER_Y = HEIGHT * 0.5f;


	private Camera mCamera;
	
	private ResourcesManager resourcesManager;
	Music mMusic;

	@Override
	public EngineOptions onCreateEngineOptions() {

		mCamera = new Camera(0, 0, WIDTH, HEIGHT);

		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		

		if(MultiTouch.isSupported(this)) {
			if(MultiTouch.isSupportedDistinct(this)) {
				Toast.makeText(this, "MultiTouch detected --> Both controls will work properly!", Toast.LENGTH_SHORT).show();
			} else {
				//this.mPlaceOnScreenControlsAtDifferentVerticalLocations = true;
				Toast.makeText(this, "MultiTouch detected, but your device has problems distinguishing between fingers.\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
		}
		

		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
	{
		ResourcesManager.prepareManager(mEngine, this, mCamera, getVertexBufferObjectManager());
		resourcesManager = ResourcesManager.getInstance();
		loadBackTheme();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException 
	{
		
		//buat test debug doang..
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
		UserData userData = UserData.getInstance();
		userData.init(this);
		//SceneManager.getInstance().loadGameScene(mEngine);

		//pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
	{
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
		{
			public void onTimePassed(final TimerHandler pTimerHandler)
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				SceneManager.getInstance().createMenuScene();
			}
		}));
		
	
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public synchronized void onResumeGame()
	{
		if(mMusic != null && !mMusic.isPlaying())
		{
			mMusic.play();
			mMusic.setLooping(true);
		}
		super.onResumeGame();
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions)
	{
		return new Engine(pEngineOptions);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		System.exit(0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}
	
	public void loadBackTheme()
	{
		SoundFactory.setAssetBasePath("sfx/");
		MusicFactory.setAssetBasePath("sfx/");
		try
		{
			mMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "backtheme.mp3");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	

}