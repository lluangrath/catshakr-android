package catshakr.main;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewSwitcher.ViewFactory;

public class catshakr extends Activity implements shakr.Callback, ViewFactory {
	
	private shakr shaker;
	private Gallery gallery;
	private ImageSwitcher imgLols;
	final Handler cHandler = new Handler();
	final GalleryWrapper gWrap = new GalleryWrapper();
	final ViewFactory vFact = this;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		initVars();
    }
    
    public void initVars(){    	
        shaker = new shakr(this, 1.25d, 500, this);
        imgLols = (ImageSwitcher)findViewById(R.id.imgLols);
        gallery = (Gallery)findViewById(R.id.glryImages);
        //Thread t = new Thread(){
			//public void run(){
				//START
				try {
					gWrap.parseXml(gWrap.sendGetRequest("http://api.cheezburger.com/xml/category/cats/lol/random/10", ""));

					
				} catch (Exception e) {
					e.printStackTrace();
				}
				//END
			//}
    	//};
    	//t.start();
		imgLols.setFactory(vFact);
		imgLols.setInAnimation(AnimationUtils.loadAnimation((Context) vFact, android.R.anim.fade_in));
		imgLols.setOutAnimation(AnimationUtils.loadAnimation((Context) vFact, android.R.anim.fade_out));
		
		gallery.setAdapter(new ImageAdapter((Context) vFact));
		gallery.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
			{
				imgLols.setImageDrawable(gWrap.LoadImageFromWeb(gWrap.getImages()[position].getUrl()));
			}
		});
		gallery.performItemClick(null, 0, 0);
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	shaker.close();
    }

    public void shakingStarted() {
	    //Log.d("ShakerDemo", "Shaking started!");
    }

    public void shakingStopped() {
	    //Log.d("ShakerDemo", "Shaking stopped!");
	    //txtInput.setText(txtInput.getText().toString()+"Shaking stopped\n");
	    //scroll.fullScroll(View.FOCUS_DOWN);
	    //Toast.makeText(getBaseContext(), txtInput.getText().toString(), Toast.LENGTH_LONG).show();
    }
    
    public View makeView() {
		ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(0xFF000000);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new 
                ImageSwitcher.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.FILL_PARENT));
        return imageView;

	}
    
    public void shakeStart(){
	    int selected = gallery.getSelectedItemPosition()+1<gallery.getCount()?gallery.getSelectedItemPosition()+1:0;
	    gallery.performItemClick(null, selected, 0);
    }
    
    public class ImageAdapter extends BaseAdapter{
		private Context context;
		private int itemBackground;
		
		public ImageAdapter(Context c){
			context = c;
			//---setting the style---
	        TypedArray a = obtainStyledAttributes(R.styleable.GalleryImages);
	        itemBackground = a.getResourceId(
	            R.styleable.GalleryImages_android_galleryItemBackground, 0);
	        a.recycle(); 
		}
	    //---returns the number of images---
	    public int getCount() {
	        return gWrap.getImages().length;
	    }

	    //---returns the ID of an item--- 
	    public Object getItem(int position) {
	        return position;
	    }            

	    public long getItemId(int position) {
	        return position;
	    }

	    //---returns an ImageView view---
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(context);
	        imageView.setImageDrawable(gWrap.LoadImageFromWeb(gWrap.getImages()[position].getUrl()));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setLayoutParams(new Gallery.LayoutParams(150, 120));
	        imageView.setBackgroundResource(itemBackground);
	        return imageView;
	    }
	}
}