package com.game.karapansapi;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.game.karapansapi.SceneManager.SceneType;

import com.game.karapansapi.BaseScene;
import com.game.karapansapi.SceneManager.SceneType;

public class LoadingScene extends BaseScene 
{
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	public BuildableBitmapTextureAtlas loading_image_atlas;
	public TiledTextureRegion loading_image;
	AnimatedSprite andengineLogo;

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		setBackground(new Background(Color.BLACK));
		
		
		andengineLogo = new AnimatedSprite(CAMERA_WIDTH / 2 - 125, CAMERA_HEIGHT / 2 - 125, 250, 250, resourcesManager.loading_image, vbom);
		andengineLogo.animate(80);
		
		attachChild(andengineLogo);
		
		attachChild(new Text(CAMERA_WIDTH / 2 - 100, CAMERA_HEIGHT - 150, resourcesManager.font, "Loading...", vbom));
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

}
