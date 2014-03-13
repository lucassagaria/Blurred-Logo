package com.example.blurredlogo;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int rouletteSize;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.frame);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		rouletteSize = size.x/4*3;
		
		final RouletteView rw = new RouletteView(getApplicationContext(), BitmapFactory.decodeResource(getResources(), R.drawable.ruleta),rouletteSize,rouletteSize);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(rouletteSize,rouletteSize);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		rl.addView(rw, params);
		
	    rw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rw.spinRoulette();	
			}
		});
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

}


