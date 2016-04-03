package com.example.projetandroid;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Games extends Activity {

	private ArrayList<FrameLayout> listecouleur = new ArrayList<FrameLayout>();
	private ArrayList<String> listecouleurEcrite = new ArrayList<String>();
	private ArrayList<Integer> listecouleurInt = new ArrayList<Integer>();
	private FrameLayout couleurAjouer;
	Toast currentToast;
	
	private TextView couleur1;
	private TextView couleur2;
	private TextView couleur3;
	private TextView couleur4;
	
	private final int min = 0;
	private final int max = 8;
	private int nbPoints = 0;
	private ProgressBar progressBar;
	
	private int couleurAleatoire1;
	private int couleurAleatoire2;
	private int couleurAleatoire3;
	
	private int nomcouleurAleatoire1;
	private int nomcouleurAleatoire2;
	private int nomcouleurAleatoire3;
	
	CountDownTimer countDownTimer;
	
	public final static String EXTRA_MESSAGE = "score";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		
		
		FrameLayout case1 = (FrameLayout) findViewById(R.id.case1);
		FrameLayout case2 = (FrameLayout) findViewById(R.id.case2);
		FrameLayout case3 = (FrameLayout) findViewById(R.id.case3);
		FrameLayout case4 = (FrameLayout) findViewById(R.id.case4);
		FrameLayout case5 = (FrameLayout) findViewById(R.id.case5);
		FrameLayout case6 = (FrameLayout) findViewById(R.id.case6);
		FrameLayout case7 = (FrameLayout) findViewById(R.id.case7);
		FrameLayout case8 = (FrameLayout) findViewById(R.id.case8);
		FrameLayout case9 = (FrameLayout) findViewById(R.id.case9);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);	
		progressBar.setMax(5000);
				
		listecouleur.add(case1);
		listecouleur.add(case2);
		listecouleur.add(case3);
		listecouleur.add(case4);
		listecouleur.add(case5);
		listecouleur.add(case6);
		listecouleur.add(case7);
		listecouleur.add(case8);
		listecouleur.add(case9);
		
		listecouleurEcrite.add(getResources().getString(R.string.crouge));
		listecouleurEcrite.add(getResources().getString(R.string.cbleu));
		listecouleurEcrite.add(getResources().getString(R.string.cvert));
		listecouleurEcrite.add(getResources().getString(R.string.cjaune));
		listecouleurEcrite.add(getResources().getString(R.string.cnoir));
		listecouleurEcrite.add(getResources().getString(R.string.cturquoise));
		listecouleurEcrite.add(getResources().getString(R.string.corange));
		listecouleurEcrite.add(getResources().getString(R.string.cviolet));
		listecouleurEcrite.add(getResources().getString(R.string.crose));
		
		listecouleurInt.add(getResources().getColor(R.color.rouge));
		listecouleurInt.add(getResources().getColor(R.color.bleu));
		listecouleurInt.add(getResources().getColor(R.color.vert));
		listecouleurInt.add(getResources().getColor(R.color.jaune));
		listecouleurInt.add(getResources().getColor(R.color.noir));
		listecouleurInt.add(getResources().getColor(R.color.turquoise));
		listecouleurInt.add(getResources().getColor(R.color.orange));
		listecouleurInt.add(getResources().getColor(R.color.violet));
		listecouleurInt.add(getResources().getColor(R.color.rose));
		
		for(int i=0; i < 9; ++i){
			listecouleur.get(i).setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if((v.equals(couleurAjouer))){
						++nbPoints;
						countDownTimer.cancel();
						game();
					}
					else{
						countDownTimer.cancel();
						gameOver();
					}	
				}
			});
		}
		couleur1 = (TextView) findViewById(R.id.couleur1);
		couleur2 = (TextView) findViewById(R.id.couleur2);
		couleur3 = (TextView) findViewById(R.id.couleur3);
		couleur4 = (TextView) findViewById(R.id.couleur4);
		game();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.games, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void game(){
		
		generer4couleurs();
		progressBar.setMax((5 * 1000)-(nbPoints*100));
		countDownTimer = new CountDownTimer((5 * 1000)-(nbPoints*100), 10) {
			
	        @Override
	        public void onTick(long leftTimeInMilliseconds) {
	            int barVal= ((5 * 1000)-(nbPoints*100)) - (int)leftTimeInMilliseconds;
	            progressBar.setProgress(barVal);

	        }
	        @Override
	        public void onFinish() {
	        	gameOver();
	        }
	    }.start();
		
		TextView nbpoint = (TextView) findViewById(R.id.nbpoints);
		nbpoint.setText(""+nbPoints);		
	}
	
	public void gameOver() {
		setContentView(R.layout.activity_game_over);
		
		Random rand = new Random();
		int couleurScore = rand.nextInt(max - min + 1) + min;
		TextView tvScore = (TextView) findViewById(R.id.score);
		tvScore.setText(getResources().getString(R.string.score) + " " + nbPoints);
		tvScore.setTextColor(listecouleurInt.get(couleurScore));
		
		SharedPreferences prefs = this.getSharedPreferences("record", Context.MODE_PRIVATE);
		int record = prefs.getInt("record", 0);
		if(nbPoints > record){
			Editor editor = prefs.edit();
			editor.putInt("record", nbPoints);
			editor.commit();
		}
		
		int couleurRecord = rand.nextInt(max - min + 1) + min;
		
		record = prefs.getInt("record", 0);
		
		TextView tvRecord = (TextView) findViewById(R.id.record);
		tvRecord.setText(getResources().getString(R.string.record) + " " + record);
		tvRecord.setTextColor(listecouleurInt.get(couleurRecord));
		
		ImageView restart = (ImageView) findViewById(R.id.retry);
		restart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
		});
		
		ImageView backmenu = (ImageView) findViewById(R.id.menu);
		backmenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Games.this, MainActivity.class);
				finish();
				startActivity(intent);
			}
		});
	}
	
	public void generer4couleurs() {
		Random rand = new Random();
		
		int numerocouleurAjouer = rand.nextInt(max - min + 1) + min;
		couleurAjouer = listecouleur.get(numerocouleurAjouer);
		
		int positionCouleurCorrect = rand.nextInt(3 - 0 + 1) + 0;
		

		switch(positionCouleurCorrect){
			case 0:
				
				couleur1.setText(listecouleurEcrite.get(numerocouleurAjouer));
				couleur1.setTextColor(listecouleurInt.get(numerocouleurAjouer));
				
				couleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				couleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				couleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				
				nomcouleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				nomcouleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				nomcouleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				
				while(nomcouleurAleatoire1 == couleurAleatoire1) {
					nomcouleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				}
				while(nomcouleurAleatoire2 == couleurAleatoire2){
					nomcouleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				}
				
				while(nomcouleurAleatoire3 == couleurAleatoire3) {
					nomcouleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				}
				
				couleur2.setText(listecouleurEcrite.get(nomcouleurAleatoire1));
				couleur2.setTextColor(listecouleurInt.get(couleurAleatoire1));
				
				couleur3.setText(listecouleurEcrite.get(nomcouleurAleatoire2));
				couleur3.setTextColor(listecouleurInt.get(couleurAleatoire2));
				
				couleur4.setText(listecouleurEcrite.get(nomcouleurAleatoire3));
				couleur4.setTextColor(listecouleurInt.get(couleurAleatoire3));
				
				break;
				
			case 1:
				
				couleur2.setText(listecouleurEcrite.get(numerocouleurAjouer));
				couleur2.setTextColor(listecouleurInt.get(numerocouleurAjouer));
				
				couleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				couleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				couleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				
				nomcouleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				nomcouleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				nomcouleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				
				while(nomcouleurAleatoire1 == couleurAleatoire1) {
					nomcouleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				}
				while(nomcouleurAleatoire2 == couleurAleatoire2){
					nomcouleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				}
				
				while(nomcouleurAleatoire3 == couleurAleatoire3) {
					nomcouleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				}
				couleur1.setText(listecouleurEcrite.get(nomcouleurAleatoire1));
				couleur1.setTextColor(listecouleurInt.get(couleurAleatoire1));
				
				couleur3.setText(listecouleurEcrite.get(nomcouleurAleatoire2));
				couleur3.setTextColor(listecouleurInt.get(couleurAleatoire2));
				
				couleur4.setText(listecouleurEcrite.get(nomcouleurAleatoire3));
				couleur4.setTextColor(listecouleurInt.get(couleurAleatoire3));
				break;
				
			case 2:
				couleur3.setText(listecouleurEcrite.get(numerocouleurAjouer));
				couleur3.setTextColor(listecouleurInt.get(numerocouleurAjouer));
				
				couleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				couleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				couleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				
				nomcouleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				nomcouleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				nomcouleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				
				while(nomcouleurAleatoire1 == couleurAleatoire1) {
					nomcouleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				}
				while(nomcouleurAleatoire2 == couleurAleatoire2){
					nomcouleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				}
				
				while(nomcouleurAleatoire3 == couleurAleatoire3) {
					nomcouleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				}
				couleur2.setText(listecouleurEcrite.get(nomcouleurAleatoire1));
				couleur2.setTextColor(listecouleurInt.get(couleurAleatoire1));
				
				couleur1.setText(listecouleurEcrite.get(nomcouleurAleatoire2));
				couleur1.setTextColor(listecouleurInt.get(couleurAleatoire2));
				
				couleur4.setText(listecouleurEcrite.get(nomcouleurAleatoire3));
				couleur4.setTextColor(listecouleurInt.get(couleurAleatoire3));
				break;
				
			case 3:
				couleur4.setText(listecouleurEcrite.get(numerocouleurAjouer));
				couleur4.setTextColor(listecouleurInt.get(numerocouleurAjouer));
				
				couleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				couleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				couleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				
				nomcouleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				nomcouleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				nomcouleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				
				while(nomcouleurAleatoire1 == couleurAleatoire1) {
					nomcouleurAleatoire1 = rand.nextInt(max - min + 1) + min;
				}
				while(nomcouleurAleatoire2 == couleurAleatoire2){
					nomcouleurAleatoire2 = rand.nextInt(max - min + 1) + min;
				}
				
				while(nomcouleurAleatoire3 == couleurAleatoire3) {
					nomcouleurAleatoire3 = rand.nextInt(max - min + 1) + min;
				}
				couleur2.setText(listecouleurEcrite.get(nomcouleurAleatoire1));
				couleur2.setTextColor(listecouleurInt.get(couleurAleatoire1));
				
				couleur3.setText(listecouleurEcrite.get(nomcouleurAleatoire2));
				couleur3.setTextColor(listecouleurInt.get(couleurAleatoire2));
				
				couleur1.setText(listecouleurEcrite.get(nomcouleurAleatoire3));
				couleur1.setTextColor(listecouleurInt.get(couleurAleatoire3));
				break;
		}
	}
}
