package com.game.karapansapi;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;


public class MainActivity1 extends BaseGameActivity {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 480;


	private Camera mCamera;
	

	private Scene mScene;
	private BitmapTextureAtlas x;
	private TextureRegion y;
	

	@Override
	public EngineOptions onCreateEngineOptions() {


		mCamera = new Camera(0, 0, WIDTH, HEIGHT);

		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				mCamera);


		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		

		return engineOptions;
	}


	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback) {
		

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		x = new BitmapTextureAtlas(this.getTextureManager(), 367, 70, TextureOptions.REPEATING_NEAREST);
		y = BitmapTextureAtlasTextureRegionFactory.createFromAsset(x, this, "ec3d99.png", 0, 0);
		
		x.load();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}


	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {

				mScene = new Scene();
				
				ProgressBar pro = new ProgressBar(0, 0, 100, 100, y, getVertexBufferObjectManager());
				pro.setProgressColor(1f, 1f, 0); // Red colour
				pro.setMax(100);
				pro.setProgress(40);// 50% filled
			mScene.attachChild(pro);
				
				
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) {

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}


}