package com.example.blurredlogo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RouletteView extends SurfaceView implements SurfaceHolder.Callback {

	private final Bitmap mBitmap;
	private float mRotation;
	private static final float ROT_STEP = 10.0f;
	private final Paint mPainter = new Paint();
	private Thread mDrawingThread;
	private final SurfaceHolder mSurfaceHolder;
	private boolean rolling = false;
	
	
	public RouletteView(Context context, Bitmap bitmap,int h, int w) {
		super(context);
		
		mRotation = 1.0f;
		
		mBitmap = Bitmap.createScaledBitmap(bitmap,	w, h, false);
		
		mPainter.setAntiAlias(true);
		setZOrderOnTop(true);
		
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
		
	}
	
	private void drawRoulette(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		mRotation += ROT_STEP;
		canvas.rotate(mRotation,mBitmap.getHeight()/2, mBitmap.getWidth()/2);
		canvas.drawBitmap(mBitmap, 0, 0,mPainter);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		mDrawingThread = new Thread(new Runnable() {
			public void run() {
				Canvas canvas = null;
				while (!Thread.currentThread().isInterrupted()) {
					canvas = mSurfaceHolder.lockCanvas();
					if (null != canvas) {
						drawRoulette(canvas);
						mSurfaceHolder.unlockCanvasAndPost(canvas);
						if(!rolling)
							surfaceDestroyed(mSurfaceHolder);
					}
				}
			}
		});
		mDrawingThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (null != mDrawingThread)
			mDrawingThread.interrupt();
	}
	
	public void spinRoulette(){
		
		new Thread(new Runnable() {
			public void run() {			
				rolling = true;
				surfaceCreated(mSurfaceHolder);
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rolling= false;

			}
		}).start();
		
	}
	
}
	