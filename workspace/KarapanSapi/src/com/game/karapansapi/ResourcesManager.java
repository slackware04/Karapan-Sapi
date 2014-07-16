package com.game.karapansapi;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
//import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.physics.box2d.Body;

import android.graphics.Color;

public class ResourcesManager 
{
	//VARIABLES
	private static final ResourcesManager INSTANCE = new ResourcesManager();
	
	public Engine engine;
	public MainActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public Font font, font1, font2;
	
	//TEXTURES REGIONS
	
	//splash
	public ITextureRegion splash_region;
	private BitmapTextureAtlas splashTextureAtlas;
	
	//menu
	public ITextureRegion menu_background_region;
	public ITextureRegion play_region;
	public ITextureRegion highscore_region;
	
	public ITextureRegion options_region;
	public ITextureRegion help_region;
	public ITextureRegion refresh_region;
	public ITextureRegion key_region;
	public ITextureRegion next_region;
	
	//level
	public ITextureRegion level_region;
	public ITextureRegion level_locked_region;
	public BitmapTextureAtlas levelTextureAtlas, levelLockedTextureAtlas ;
	
	//loading
	public BuildableBitmapTextureAtlas loading_image_atlas;
	public TiledTextureRegion loading_image;
	
	//game
	public BuildableBitmapTextureAtlas mVechilesTexture;
	public TiledTextureRegion mVechilesTextureRegion;
	
	public BitmapTextureAtlas mBoxTexture;
	public ITextureRegion mBoxTextureRegion;
	
	public BuildableBitmapTextureAtlas mRacetrackTexture;
	public BuildableBitmapTextureAtlas mRacetrackTexture2;
	public ITextureRegion mRacetrackStraightTextureRegion;
	//public ITextureRegion mRacetrackCurveTextureRegion;
	public ITextureRegion raceTrackHorizontalStraightTextureRegion;
	public ITextureRegion raceTrackVerticalStraightTextureRegion;
	public ITextureRegion racetrackCurveTextureRegion;
	
	public ITextureRegion raceTrackStraightBorderRegion;
	public ITextureRegion raceFinishRegion;
	public BuildableBitmapTextureAtlas raceBorderCenterAtlas;
	public ITextureRegion raceBorderCenterTextureRegion;
	
	public BitmapTextureAtlas mOnScreenControlTexture;
	public ITextureRegion mOnScreenControlBaseTextureRegion;
	public ITextureRegion mOnScreenControlKnobTextureRegion;
	
	public BuildableBitmapTextureAtlas mBarAtlas;
	public TiledTextureRegion bar;
	public ITextureRegion bar1;
	
	public Body mCarBody;
	public TiledSprite mCar;
	
	
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	
	public BuildableBitmapTextureAtlas completeAtlas;
	public ITextureRegion complete_window_region;
	public ITiledTextureRegion complete_stars_region;
	public ITextureRegion game_over_region;

	public BuildableBitmapTextureAtlas crateAtlas;
	public ITextureRegion crateRegion;
	
	
	//METHODS
	
	//MENU
	//MUSIC 
	
	public void loadMenuResources()
	{
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}
	
	public void loadMenuGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play_button.png");
		highscore_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "highscore_button.png");
		menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
		options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options_button.png");
		help_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "help_button.png");
		refresh_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "refresh.png");
		key_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "key.png");
		next_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "next.png");
		
		try
		{
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas > (0,1,0));
			this.menuTextureAtlas.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		
		
	}
	
	private void loadMenuAudio()
	{}
	
	private void loadMenuFonts()
	{
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "Pacifico.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
		font.load();
		
	}
	
	public void unloadMenuTextures()
	{
		menuTextureAtlas.unload();
	}
	
	public void loadMenuTextures()
	{
		menuTextureAtlas.load();
	}
	
	
	//GAME
	public void loadGameResources()
	{
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
		
		//buat tombol doang..ntar dibenerin..
		//loadMenuGraphics();
	}
	
	private void loadGameGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		mVechilesTexture = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 294, 525, BitmapTextureFormat.RGBA_8888, TextureOptions.NEAREST);
		mVechilesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mVechilesTexture, activity, "KS__Animation.png", 1,2);
		//mVechilesTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mVechilesTexture, activity, "KS__Animation.png", 1,2);
		
		try
		{
			this.mVechilesTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas > (0,1,0));
			this.mVechilesTexture.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		
		mRacetrackTexture = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		mRacetrackStraightTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mRacetrackTexture, activity, "racetrack_straight.png");
		
		
		raceTrackHorizontalStraightTextureRegion = mRacetrackStraightTextureRegion.deepCopy();
		raceTrackHorizontalStraightTextureRegion.setTextureWidth(3 * this.mRacetrackStraightTextureRegion.getWidth());
		raceTrackVerticalStraightTextureRegion = mRacetrackStraightTextureRegion;
		
		raceTrackStraightBorderRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mRacetrackTexture, activity, "pager.png");
		raceFinishRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mRacetrackTexture, activity, "finish_line.png");
		try
		{
			this.mRacetrackTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas > (0,1,0));
			this.mRacetrackTexture.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		
		mRacetrackTexture2 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		racetrackCurveTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mRacetrackTexture2, activity, "racetrack_curve.png");
		try
		{
			this.mRacetrackTexture2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas > (0,1,0));
			this.mRacetrackTexture2.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		
		raceBorderCenterAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 610, 610, TextureOptions.NEAREST);
		raceBorderCenterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(raceBorderCenterAtlas, activity, "background.png");
		try
		{
			this.raceBorderCenterAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas > (0,1,0));
			this.raceBorderCenterAtlas.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		
		mOnScreenControlTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTexture, activity, "onscreen_control_base.png", 0, 0);
		mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTexture, activity, "onscreen_control_knob.png", 128, 0);
		mOnScreenControlTexture.load();

		mBoxTexture = new BitmapTextureAtlas(activity.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		mBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBoxTexture, activity, "air2.png", 0, 0);
		mBoxTexture.load();
				
		completeAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		complete_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(completeAtlas, activity, "levelComplete.png");
		complete_stars_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(completeAtlas, activity, "stars.png", 2,1);
		game_over_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(completeAtlas, activity, "gameOver.png");
		try
		{
			this.completeAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas > (0,1,0));
			this.completeAtlas.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		
		
		mBarAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 367, 70, TextureOptions.BILINEAR);
		//bar = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBarAtlas, activity, "bar.png", 1,2);
		bar1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBarAtlas, activity, "ec3d99.png");
		try
		{
			this.mBarAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas > (0,1,0));
			this.mBarAtlas.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		
	}
	
	private void loadGameFonts()
	{
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font2 = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "Pacifico.ttf", 100, true, Color.WHITE, 2, Color.BLACK);
		font2.load();
	}
	
	private void loadGameAudio()
	{}
	
	public void unloadGameTextures()
	{
		
	}
	
	
	//SPLASH
	public void loadSplashScreen()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "3853282_orig.png", 0, 0);
		splashTextureAtlas.load();
	}
	
	public void unloadSplashScreen()
	{
		splashTextureAtlas.unload();
		splash_region = null;
	}
	
	//LEVEL
	public void loadLevelScreen()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		levelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 75, 75, TextureOptions.BILINEAR);
		level_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelTextureAtlas, activity, "level.png", 0, 0);
		
		levelTextureAtlas.load();
		
		levelLockedTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 75, 75, TextureOptions.BILINEAR);
		level_locked_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelLockedTextureAtlas, activity, "levelLocked.png", 0, 0);
		levelLockedTextureAtlas.load();
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font1 = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "Pacifico.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
		font1.load();
			
		
	}
	
	public void unloadLevelScreen()
	{
		levelTextureAtlas.unload();
		level_region = null;
	}
	
	//LOADING
	
	public void loadLoadingTexture()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		loading_image_atlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 740, 740, TextureOptions.BILINEAR);
		loading_image = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(loading_image_atlas, activity, "andengine.png", 5,5);
		
		
		try
		{
			this.loading_image_atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas > (0,1,0));
			this.loading_image_atlas.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
	}
	
	//HIGHSCORE
	
	public void loadHighscoreScreen()
	{
		loadMenuResources();
	}
	
	//MISC
	public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom)
	{
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
		
	}
	
	public static ResourcesManager getInstance()
	{
		return INSTANCE;
	}
	

	

	
	

}
