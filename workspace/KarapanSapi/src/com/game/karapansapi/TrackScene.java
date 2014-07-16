package com.game.karapansapi;
import com.game.karapansapi.LevelSelector.LevelTile;
import com.game.karapansapi.SceneManager.SceneType;

public class TrackScene extends BaseScene 
{
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		LevelSelector levelSelector = new LevelSelector(1, 1, CAMERA_WIDTH, CAMERA_HEIGHT, this , engine);
		levelSelector.createTiles(resourcesManager.level_region, resourcesManager.font1);
		
		levelSelector.show();
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

}