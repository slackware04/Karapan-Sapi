package com.game.karapansapi;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;

import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.math.MathUtils;

import android.opengl.GLES20;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.karapansapi.BaseScene;
import com.game.karapansapi.LevelCompleteWindow.StarsCount;
import com.game.karapansapi.SceneManager.SceneType;

public class GameScene3 extends BaseScene
{
	//CONSTANT
	private static final int RACETRACK_WIDTH = 160;

	private static final int OBSTACLE_SIZE = 40;
	private static final int CAR_SIZE = 120;

	//private final float CAMERA_WIDTH = getX();
	private final float CAMERA_HEIGHT = getY();
	private static final int CAMERA_WIDTH = 800;
	//private static final int CAMERA_HEIGHT = 480;
	
	public static int WIDTH = 800;
	public static int HEIGHT = 480;
	
	
	
	float vectorX;
	float vectorY;
	
	private HUD gameHUD;
	private Text scoreText;
	private int score = 0;
	private PhysicsWorld physicWorld;
	
	Body mCarBody;
	AnimatedSprite mCar;
	//Sprite mCar;
	Sprite finishBorder;
	Sprite box;
	
	private LevelCompleteWindow levelCompleteWindow;
	private GameOverWindow GameOverWindow;
	
	public float gameTime = 0;
	public Text gameTimeNow;
	public IUpdateHandler gameTimeUpdateHandler;
	public IUpdateHandler energiUpdateHandler;
	
	ProgressBar pro;
	float currentProgress;
	
	Rectangle x;
	int energyKepake = 0;
	
	GameScene3(int tempDiff)
	{
		
		if(tempDiff == 1)
		{
			initObstacles0();
			//System.out.println(this.diff);
		}
		else if (tempDiff == 2)
		{
			initObstacles3();
			//System.out.println(this.diff);
		}
		else
		{
			initObstacles7();
			//System.out.println(this.diff + "/" + diff);
		}
		initCar();
	}
	
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		initHUD();
		createPhysic();
		initRaceTrack();
		initRaceTrackBorders();
		//initObstacles();
		//initCar();
		
		createHUD();
		
		
		
		levelCompleteWindow = new LevelCompleteWindow(vbom);
		GameOverWindow = new GameOverWindow(vbom);
		
		

	}
	
	public void createPhysic()
	{
		physicWorld = new FixedStepPhysicsWorld(30, new Vector2(0, 0), false, 8, 1);
		registerUpdateHandler(physicWorld);
	}
	
	public void initHUD()
	{
		gameTimeUpdateHandler = new IUpdateHandler()
		{
			@Override
			public void onUpdate(float pSecondsElapsed)
			{
				if(pSecondsElapsed > 1)
				{
					gameTime = 1;
					gameTimeNow.setText(String.valueOf(gameTime));
					//System.out.println(pSecondsElapsed + " : " + gameTime);
				}
				else
				{
					gameTime += pSecondsElapsed;
					gameTimeNow.setText(String.valueOf(Math.round(gameTime)));
					//System.out.println(pSecondsElapsed + " : " + gameTime);
				}
			}
			@Override 
			public void reset()
			{
				
			}
		};
		
		

	}
	
	public void createHUD()
	{
		//x = new Rectangle(CAMERA_WIDTH / 2 - 200, CAMERA_HEIGHT / 2 + 350, 400, 50, vbom);
		//x = new AnimatedSprite(CAMERA_WIDTH / 2 - 200, CAMERA_HEIGHT / 2 + 350, 400, 50, resourcesManager.bar, vbom);
		//final long frameDuration[] = {150, 300};
		
		pro = new ProgressBar(CAMERA_WIDTH / 2 - 200, CAMERA_HEIGHT / 2 + 400, 100, 100, resourcesManager.bar1, vbom);
		pro.setProgressColor(1f, 1f, 0); // Red colour
		pro.setMax(100);
		pro.setProgress(100);
		//final Body carBody = mCarBody;
		gameHUD = new HUD();
		gameTimeNow = new Text(CAMERA_WIDTH / 2 - 10, -25, resourcesManager.font2, "10", 10, vbom);
		gameHUD.attachChild(gameTimeNow);
		gameHUD.attachChild(pro);
		GameScene3.this.registerUpdateHandler(gameTimeUpdateHandler);

		
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(20, 300, camera, resourcesManager.mOnScreenControlBaseTextureRegion, resourcesManager.mOnScreenControlKnobTextureRegion, 0.1f, vbom, new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				
				if(pValueX == 0 && pValueY == 0)
				{
					//long arr[]={100,100};
					mCar.animate(250);
				}
				
				final float rotationInRad = (float)Math.atan2(-pValueX, pValueY);
				//carBody.setTransform(carBody.getWorldCenter(), rotationInRad);

				mCar.setRotation(MathUtils.radToDeg(rotationInRad));
				vectorX = pValueX;
				vectorY = pValueY;
				
				//System.out.println("x :" + pValueX + "y :" + pValueY);
				


				//mCar.setRotation(50);
				//x.animate(frameDuration, 0, 1, 0);
				//x = new Rectangle(CAMERA_WIDTH / 2 - 200, CAMERA_HEIGHT / 2 + 350, 200, 50, vbom);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
			
			}
		});
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		//		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		//		analogOnScreenControl.getControlBase().setScale(0.75f);
		//		analogOnScreenControl.getControlKnob().setScale(0.75f);
		analogOnScreenControl.refreshControlKnobPosition();
		//mCar.stopAnimation();

		setChildScene(analogOnScreenControl);
		//hud.attachChild(analogOnScreenControl);
		
		final AnalogOnScreenControl analogOnScreenControlRight = new AnalogOnScreenControl(CAMERA_WIDTH - 20 - resourcesManager.mOnScreenControlBaseTextureRegion.getWidth(), 300, camera, resourcesManager.mOnScreenControlBaseTextureRegion, resourcesManager.mOnScreenControlKnobTextureRegion, 0.1f, vbom, new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, float zValueX, float zValueY) 
			{
				if(zValueX == 0 && zValueY == 0)
				{
					mCarBody.setLinearVelocity(0,0);
					GameScene3.this.unregisterUpdateHandler(energiUpdateHandler);
				}
				else
				{

					zValueX = vectorX;
					zValueY = vectorY;
					

					final Vector2 velocity = Vector2Pool.obtain(zValueX *2, zValueY * 2);
					mCarBody.setLinearVelocity(velocity);	
					Vector2Pool.recycle(velocity);
					
					currentProgress = pro.getProgress() - 0.05f;
					pro.setProgress(currentProgress);
					if(currentProgress < 0)
					{
						GameOverWindow.display(GameScene3.this, camera);
						setVisible(true);
						setIgnoreUpdate(true);
						
						GameScene3.this.unregisterUpdateHandler(gameTimeUpdateHandler);
					}
					
				}
				
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl xAnalogOnScreenControl) 
			{
				//Toast.makeText(this, "asdf", Toast.LENGTH_LONG).show();
				//System.out.println("asdf");
			
			}
			

		});
		analogOnScreenControlRight.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControlRight.getControlBase().setAlpha(0.5f);
		analogOnScreenControlRight.refreshControlKnobPosition();
		
		analogOnScreenControl.setChildScene(analogOnScreenControlRight);
		camera.setHUD(gameHUD);		
	}
	
	public void initRaceTrack()
	{
		Sprite StraightRace1 = new Sprite(0 + 1, 0, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		StraightRace1.setRotation(90);
		attachChild(StraightRace1);
		Sprite StraightRace2 = new Sprite(600, 0, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		StraightRace2.setRotation(90);
		attachChild(StraightRace2);
		Sprite StraightRace3 = new Sprite(600 * 2 - 1, 0, resourcesManager.racetrackCurveTextureRegion, vbom);
		StraightRace3.setRotation(180);
		attachChild(StraightRace3);
		
		Sprite StraightRace4 = new Sprite(600 * 2 - 1, 600, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		//StraightRace1.setRotation(90);
		attachChild(StraightRace4);
		
		Sprite StraightRace5 = new Sprite(600 * 2 - 1, 600 * 2, resourcesManager.racetrackCurveTextureRegion, vbom);
		StraightRace5.setRotation(270);
		attachChild(StraightRace5);
		Sprite StraightRace6 = new Sprite(600, 600 * 2, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		StraightRace6.setRotation(90);
		attachChild(StraightRace6);
		Sprite StraightRace7 = new Sprite(0 + 1, 600 * 2, resourcesManager.racetrackCurveTextureRegion, vbom);
		StraightRace7.setRotation(90);
		attachChild(StraightRace7);
		
		Sprite StraightRace8 = new Sprite(0 + 1, 600 * 3, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		//StraightRace8.setRotation(90);
		attachChild(StraightRace8);
		
		Sprite StraightRace9 = new Sprite(0 + 1, 600 * 4, resourcesManager.racetrackCurveTextureRegion, vbom);
		//StraightRace9.setRotation(270);
		attachChild(StraightRace9);
		Sprite StraightRace10 = new Sprite(600, 600 * 4, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		StraightRace10.setRotation(90);
		attachChild(StraightRace10);
		Sprite StraightRace11 = new Sprite(600 * 2 - 1, 600 * 4, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		StraightRace11.setRotation(90);
		attachChild(StraightRace11);
	}
	
	public void initCar()
	{

		mCar = new AnimatedSprite(0,300, CAR_SIZE, CAR_SIZE, resourcesManager.mVechilesTextureRegion, vbom);
		//mCar = new Sprite(CAMERA_WIDTH / 2,0, CAR_SIZE, CAR_SIZE, resourcesManager.mVechilesTextureRegion, vbom);
		//mCar.setCurrentTileIndex(0);
				
		final FixtureDef carFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		mCarBody = PhysicsFactory.createBoxBody(physicWorld, mCar, BodyType.DynamicBody, carFixtureDef);
		mCarBody.setFixedRotation(true);
		physicWorld.registerPhysicsConnector(new PhysicsConnector(mCar, mCarBody, true, false));
		mCarBody.setUserData("sapi");
		camera.setChaseEntity(mCar);
		//camera.setBoundsEnabled(false);
		attachChild(mCar);
		
	}
	
	public void initObstacles0()
	{
		
	}
	
	public void initObstacles3()
	{
		addObstacle(600 + 600 / 2, 600 / 2);
		
		addObstacle(600 * 2 + 600 / 2, 600 * 2 + 600 / 2);
		
		addObstacle(600 * 2 + 600 / 2, 600 * 4 + 600 / 2);
		
	}
	
	public void initObstacles7()
	{
		addObstacle(600 + 600 / 2, 600 / 2);
		addObstacle(600 * 2 + 600 / 2, 600 / 2);
		addObstacle(600 * 2 + 600 / 2, 600 + 600 / 2);
		addObstacle(600 * 2 + 600 / 2, 600 * 2 + 600 / 2);
		addObstacle(600 + 600 * 2, 600 * 2 + 600 / 2);
		addObstacle(600 / 2, 600 * 4 + 600 / 2);
		addObstacle(600 * 2 + 600 / 2, 600 * 4 + 600 / 2);
		
	}
	
	private void addObstacle(final float pX, final float pY)
	{
		box = new Sprite(pX, pY, 64, 64, resourcesManager.mBoxTextureRegion, vbom)
		
		{
			@Override
			public void onManagedUpdate(float pSecondsElapsed)
			{
				if(mCar.collidesWith(this))
				{
					
					final Vector2 velocity = Vector2Pool.obtain(vectorX * 1, vectorY * 1);
					mCarBody.setLinearVelocity(velocity);
					
					//GameOverWindow.display(GameScene.this, camera);
					//setVisible(true);
					//setIgnoreUpdate(true);
					//GameScene.this.unregisterUpdateHandler(gameTimeUpdateHandler);
					
					
				}
			}
		};
		
		/*
		final FixtureDef boxFixtureDef = PhysicsFactory.createFixtureDef(0.1f,0.1f,0.5f);
		final Body boxBody = PhysicsFactory.createBoxBody(physicWorld, box, BodyType.DynamicBody, boxFixtureDef);
		//boxBody.setFixedRotation(true);
		boxBody.setLinearDamping(10);
		boxBody.setAngularDamping(10);
		boxBody.setUserData("kotak");
		
		physicWorld.registerPhysicsConnector(new PhysicsConnector(box, boxBody, true, true));
		*/
		/*
		physicWorld.setContactListener(new ContactListener()
		{

			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
				if (x2.getBody().getUserData().equals("kotak")&&x1.getBody().getUserData().equals("sapi"))
				{
				    Log.i("CONTACT", "BETWEEN PLAYER AND MONSTER!");
				}
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
			
		});
		*/
		attachChild(box);
		
	}
	
	private void initRaceTrackBorders()
	{
		
		
		final Rectangle topBorder = new Rectangle(0, 0 - 5, 600 * 3, 5, vbom);
		final Rectangle leftBorder = new Rectangle(0 - 5,0,5, 600 * 5, vbom);
		final Rectangle rightBorder = new Rectangle(600 * 3, 0, 5, 600 * 5, vbom);
		final Rectangle bottomBorder = new Rectangle(0, 600 * 5, 600 * 3, 5, vbom);
		
		final Rectangle middleBorder1 = new Rectangle(0, 600, 600 * 2, 600, vbom);
		final Rectangle middleBorder2 = new Rectangle(600, 600 * 3, 600 * 2, 600, vbom);
		
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		
		
		PhysicsFactory.createBoxBody(physicWorld, topBorder, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, leftBorder, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, rightBorder, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, bottomBorder, BodyType.StaticBody, wallFixtureDef);
		
		PhysicsFactory.createBoxBody(physicWorld, middleBorder1, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, middleBorder2, BodyType.StaticBody, wallFixtureDef);
		
		finishBorder = new Sprite(CAMERA_WIDTH / 6, 600 * 3 - 30, 600, 50, resourcesManager.raceFinishRegion, vbom)
		{
			@Override
			public void onManagedUpdate(float pSecondsElapsed)
			{
				if(mCar.collidesWith(finishBorder)) 
				{
					StarsCount starsNow;
					if (gameTime < 35)
					{
						starsNow = StarsCount.THREE;
					
					}
					else if(gameTime < 50)
					{
						starsNow = StarsCount.TWO;	
					}
					else
					{
						starsNow = StarsCount.ONE;
					}
					UserData.getInstance().unlockNextLevel();
					levelCompleteWindow.display(starsNow,GameScene3.this, camera);
					setVisible(false);
					setIgnoreUpdate(true);
					 
					GameScene3.this.unregisterUpdateHandler(gameTimeUpdateHandler);
					
	            
	            }
			}
		};
		

		attachChild(topBorder);
		attachChild(leftBorder);
		attachChild(rightBorder);
		attachChild(bottomBorder);
		attachChild(middleBorder1);
		attachChild(middleBorder2);
		//attachChild(finishBorder);
		
		
	}
	
	
	
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		SceneManager.getInstance().loadMenuScene(engine);
		disposeScene();
	}
	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}
	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		camera.setHUD(null);
		//camera.setCenter(camera.getWidth() / 2, camera.getHeight() / 2);
		//camera.setCenter(0, 0);
		camera.setChaseEntity(null);
		camera.setCenter(camera.getWidth() / 2, camera.getHeight() / 2);
	}
	
	


}
