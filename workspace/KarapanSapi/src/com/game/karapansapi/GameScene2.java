package com.game.karapansapi;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.math.MathUtils;

import android.opengl.GLES20;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.game.karapansapi.LevelCompleteWindow.StarsCount;
import com.game.karapansapi.SceneManager.SceneType;

public class GameScene2 extends BaseScene
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
	Sprite bottomBorder;
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
	
	GameScene2(int tempDiff)
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
		
		energiUpdateHandler = new IUpdateHandler()
		{
			@Override
			public void onUpdate(float pSecondsElapsed)
			{
				//energyKepake+=1;
				//System.out.println(energyKepake);
				//x.animate(300, false);
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
		GameScene2.this.registerUpdateHandler(gameTimeUpdateHandler);

		
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
					GameScene2.this.unregisterUpdateHandler(energiUpdateHandler);
				}
				else
				{

					zValueX = vectorX;
					zValueY = vectorY;
					

					final Vector2 velocity = Vector2Pool.obtain(zValueX *2, zValueY * 2);
					mCarBody.setLinearVelocity(velocity);	
					Vector2Pool.recycle(velocity);
					
					currentProgress = pro.getProgress() - 0.2f;
					pro.setProgress(currentProgress);
					if(currentProgress < 0)
					{
						GameOverWindow.display(GameScene2.this, camera);
						setVisible(true);
						setIgnoreUpdate(true);
						
						GameScene2.this.unregisterUpdateHandler(gameTimeUpdateHandler);
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
		Sprite StraightRace1 = new Sprite(0, 0, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		//StraightRace1.setRotation(90);
		attachChild(StraightRace1);
		
		
		Sprite StraightRace2 = new Sprite(0,600 - 1, resourcesManager.racetrackCurveTextureRegion, vbom);
		//StraightRace2.setRotation(90);
		attachChild(StraightRace2);
		
		Sprite StraightRace3 = new Sprite(600 - 1, 600 - 1, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		StraightRace3.setRotation(90);
		attachChild(StraightRace3);
		
		Sprite StraightRace4 = new Sprite((600 - 1) * 2, 600 - 1, resourcesManager.racetrackCurveTextureRegion, vbom);
		StraightRace4.setRotation(-90);
		attachChild(StraightRace4);
		
		Sprite StraightRace5 = new Sprite((600 - 1) * 2, 0, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		//StraightRace5.setRotation(90);
		attachChild(StraightRace5);
		
		Sprite StraightRace6 = new Sprite((600 - 1) * 2, -600 + 1, resourcesManager.racetrackCurveTextureRegion, vbom);
		StraightRace6.setRotation(180);
		attachChild(StraightRace6);
		
		Sprite StraightRace7 = new Sprite(600 - 1, -600 + 1, resourcesManager.raceTrackVerticalStraightTextureRegion, vbom);
		StraightRace7.setRotation(-90);
		attachChild(StraightRace7);
		
		Sprite StraightRace8 = new Sprite(0, -600 + 1, resourcesManager.racetrackCurveTextureRegion, vbom);
		StraightRace8.setRotation(90);
		attachChild(StraightRace8);
		
		
		
		
		
	}
	
	public void initCar()
	{

		mCar = new AnimatedSprite(CAMERA_WIDTH / 2,50, CAR_SIZE, CAR_SIZE, resourcesManager.mVechilesTextureRegion, vbom);
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
		addObstacle(CAMERA_WIDTH / 2, 960 / 2);
		addObstacle(CAMERA_WIDTH / 2, 960 / 2 * 2);
		addObstacle(CAMERA_WIDTH / 2, 960 / 2 * 3);
		
	}
	
	public void initObstacles7()
	{
		addObstacle(CAMERA_WIDTH / 2, 960 / 2);
		addObstacle(CAMERA_WIDTH / 2, 960 / 2 + 100);
		addObstacle(CAMERA_WIDTH / 2, 960 / 2 * 2);
		addObstacle(CAMERA_WIDTH / 2, 960 / 2 * 2 + 100);
		addObstacle(CAMERA_WIDTH / 2, 960 / 2 * 3);
		addObstacle(CAMERA_WIDTH / 2, 960 / 2 * 3 + 100);
		
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
		final Rectangle finishBorder = new Rectangle(0,20,600,5,vbom);
		final Sprite leftBorder = new Sprite(-(CAMERA_WIDTH / 6), 0, CAMERA_WIDTH / 6, 600, resourcesManager.raceTrackStraightBorderRegion,vbom);
		final Sprite leftBorder2 = new Sprite(-(CAMERA_WIDTH / 6), 600, CAMERA_WIDTH / 6, 600, resourcesManager.raceTrackStraightBorderRegion, vbom);
		final Sprite leftBorder3 = new Sprite(-(CAMERA_WIDTH / 6), -600, CAMERA_WIDTH / 6, 600,resourcesManager.raceTrackStraightBorderRegion, vbom);
		
		final Sprite rightBorder = new Sprite(600 * 3, 0, CAMERA_WIDTH / 6, 600, resourcesManager.raceTrackStraightBorderRegion, vbom);
		final Sprite rightBorder2 = new Sprite(600 * 3, 600, CAMERA_WIDTH / 6, 600, resourcesManager.raceTrackStraightBorderRegion, vbom);
		final Sprite rightBorder3 = new Sprite(600 * 3, -600, CAMERA_WIDTH / 6, 600, resourcesManager.raceTrackStraightBorderRegion, vbom);
		
		final Rectangle topBorder = new Rectangle(0, -600 -10,600 * 3, 10, vbom);
		final Rectangle bottomXBorder = new Rectangle(0, 600 * 2, 600 * 3, 10, vbom);
		rightBorder.setRotation(180);
		rightBorder2.setRotation(180);
		rightBorder3.setRotation(180);
		/*
		final Sprite topBorder = new Sprite(0, -600 - (CAMERA_WIDTH / 6) , CAMERA_WIDTH / 6, CAMERA_WIDTH / 6, resourcesManager.raceTrackStraightBorderRegion,vbom);
		final Sprite topBorder2 = new Sprite(600, -600 - (CAMERA_WIDTH / 6), CAMERA_WIDTH / 6, CAMERA_WIDTH / 6, resourcesManager.raceTrackStraightBorderRegion, vbom);
		final Sprite topBorder3 = new Sprite(600 * 2, -600 - (CAMERA_WIDTH / 6), CAMERA_WIDTH / 6, CAMERA_WIDTH / 6,resourcesManager.raceTrackStraightBorderRegion, vbom);

		
		
		
		
		final Sprite bottomBorderX = new Sprite(CAMERA_WIDTH / 6 + 600 * 3, 0, CAMERA_WIDTH / 6, 600, resourcesManager.raceTrackStraightBorderRegion, vbom);
		final Sprite bottomBorder2 = new Sprite(CAMERA_WIDTH / 6 + 600 * 3, 600, CAMERA_WIDTH / 6, 600, resourcesManager.raceTrackStraightBorderRegion, vbom);
		final Sprite bottomBorder3 = new Sprite(CAMERA_WIDTH / 6 + 600 * 3, -600, CAMERA_WIDTH / 6, 600, resourcesManager.raceTrackStraightBorderRegion, vbom);
		
		
		
		
		topBorder.setRotation(90);
		topBorder2.setRotation(90);
		topBorder3.setRotation(90);
		*/

		Sprite centerBorder = new Sprite(600 - 1, 0, resourcesManager.raceBorderCenterTextureRegion, vbom);
		
		
		
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		
		PhysicsFactory.createBoxBody(physicWorld, finishBorder, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, leftBorder, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, leftBorder2, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, leftBorder3, BodyType.StaticBody, wallFixtureDef);
		
		PhysicsFactory.createBoxBody(physicWorld, rightBorder, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, rightBorder2, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, rightBorder3, BodyType.StaticBody, wallFixtureDef);
		
		PhysicsFactory.createBoxBody(physicWorld, centerBorder, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, topBorder, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(physicWorld, bottomXBorder, BodyType.StaticBody, wallFixtureDef);
		bottomBorder = new Sprite(0,0 - 60, 600, 50, resourcesManager.raceFinishRegion, vbom)
		{
			@Override
			public void onManagedUpdate(float pSecondsElapsed)
			{
				if(mCar.collidesWith(bottomBorder)) 
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
					levelCompleteWindow.display(starsNow,GameScene2.this, camera);
					setVisible(false);
					setIgnoreUpdate(true);
					 
					GameScene2.this.unregisterUpdateHandler(gameTimeUpdateHandler);
					
	            
	            }
			}
		};
		
		
		
		attachChild(centerBorder);
		
		attachChild(leftBorder);
		attachChild(leftBorder2);
		attachChild(leftBorder3);
		
		attachChild(rightBorder);
		attachChild(rightBorder2);
		attachChild(rightBorder3);
		
		attachChild(topBorder);
		attachChild(bottomXBorder);
		attachChild(finishBorder);
		//attachChild(topBorder2);
		//attachChild(topBorder3);
		attachChild(bottomBorder);
		
		
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
