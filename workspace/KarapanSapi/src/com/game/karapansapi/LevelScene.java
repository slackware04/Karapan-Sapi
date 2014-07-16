package com.game.karapansapi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.game.karapansapi.LevelSelector.LevelTile;
import com.game.karapansapi.SceneManager.SceneType;

public class LevelScene extends BaseScene 
{
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		int b = UserData.getInstance().getMaxUnlockedLevel();
		LevelSelector levelSelector = new LevelSelector(b, 1, CAMERA_WIDTH, CAMERA_HEIGHT, this , engine);
		levelSelector.createTiles(resourcesManager.level_region, resourcesManager.font1);
		
		levelSelector.show();
		showDialog();
		
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
	
	public void showDialog()
	{
		activity.runOnUiThread(new Thread(new Runnable()
		{

			@Override
			public void run() {
				
				// TODO Auto-generated method stub
				AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);

				helpBuilder.setTitle("Input name");

				LayoutInflater inflater = activity.getLayoutInflater();
				final View inputLayout = inflater.inflate(R.layout.dialog, null);
				helpBuilder.setIcon(0);
				helpBuilder.setView(inputLayout);
				helpBuilder.setPositiveButton("Submit",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								try {
									EditText edt = (EditText) inputLayout.findViewById(R.id.name);
									UserData.getInstance().inputName(edt.getText().toString());
									
								} catch (Exception e) {
									System.out.println("ERROR:" + e.getMessage());
								}
							}
						});
				helpBuilder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) 
							{
								SceneManager.getInstance().loadMenuScene(engine);
							}
						});

				AlertDialog helpDialog = helpBuilder.create();

				helpDialog.show();
			}
			
		}));
			
	}

}