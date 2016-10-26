package com.lemonjiang.lemonrobot.act;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;

import com.lemonjiang.lemonrobot.R;

public class WelcomeActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_welcome);
		init();
	}

	private void init() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				Intent in = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(in);
				finish();
			}
		};
		timer.schedule(task, 2000);
	}
}
