package com.game.karapansapi;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.widget.Toast;

public class GameOverWindow extends Sprite 
{
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	
	public GameOverWindow(VertexBufferObjectManager pSpriteVertexBufferObject)
	{
		super(0,0, 650, 400, ResourcesManager.getInstance().game_over_region, pSpriteVertexBufferObject);
		
	}
	
	
	public void display(Scene scene, Camera camera)
	{
		
		
		//camera.getHUD().setVisible(false);
		camera.setChaseEntity(null);
		setPosition(camera.getCenterX() - this.getWidth() / 2, camera.getCenterY() - this.getHeight() / 2);
		scene.attachChild(this);

	}
	

}
