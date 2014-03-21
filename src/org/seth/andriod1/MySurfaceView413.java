package org.seth.andriod1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * SurfaceView游戏框架
 * @author seth16888
 *
 */
public class MySurfaceView413  extends SurfaceView implements Callback,Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;	//游戏标志
    private Canvas canvas;
    public static int screenW,screenH;	//屏幕宽、高
    
	public MySurfaceView413(Context context) {
		super(context);
		sfh = this.getHolder();	//surface控制器
		sfh.addCallback(this);	//控制器与本实例连接
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		//设置焦点
		setFocusable(true);
		setFocusableInTouchMode(true);
		//设置背景高亮
		this.setKeepScreenOn(true);
	}

	@Override
	public void run(){
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();		//游戏绘制
			logic();	//游戏逻辑处理
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		initGame();	//初始化游戏
		flag = true;
		//实例线程
		th = new Thread(this);
		//启动线程
		th.start();
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;	//退出时，结束游戏线程
		
	}
	
	/**
	 * 自定义的游戏初始化函数
	 */
	private void initGame(){
		
	}
	
	/**
	 * 自定义游戏绘制
	 */
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);	//刷屏
				//绘图函数根据游戏状态不同进行不同绘制
				canvas.drawText("游戏框架", 10, 10, paint);
				
			}
		} catch (Exception e) {
			// TODO: handle exception	
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	/**
	 * 自定义游戏逻辑
	 */
	private void logic(){
		//逻辑处理根据游戏状态不同进行不同处理
		
	}
	

}
