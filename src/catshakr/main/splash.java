package catshakr.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class splash extends Activity {
	
	private boolean _active = true;
	private int _splashtime = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
        Thread splashThread = new Thread(){
        	@Override
        	public void run(){
        		
        		try {
					int waited = 0;
					while(_active && waited < _splashtime){
						sleep(100);
						if(_active){
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					finish();
					Intent i = new Intent(splash.this, catshakr.class);				
					startActivity(i);
				}
        	}
        };
        splashThread.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			_active = false;
		}
		return super.onTouchEvent(event);
	}
}
