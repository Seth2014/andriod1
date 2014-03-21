package org.seth.andriod1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 控制机器人主角动起来
 * @author seth16888
 *
 */
public class MySurfaceView4130  extends SurfaceView implements Callback,Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;	//游戏标志
    private Canvas canvas;
    public static int screenW,screenH;	//屏幕宽、高
    
    private Bitmap bmpRobot;	//机器人包含所有帧的位图
    private final int DIR_LEFT = 0;	//机器人的方向常量
    private final int DIR_RIGHT = 1;	//机器人的方向常量
    private int dir = DIR_RIGHT;		//机器人当前方向
    private int currentFrame;		//机器人动画当前帧
    private int robot_x, robot_y;		//机器人帧位图x,y坐标
    private boolean isUp, isDown, isRight, isLeft;
    
	public MySurfaceView4130(Context context) {
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_DPAD_UP){
			isUp = true;
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
			isDown  = true;
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			isRight = true;
			dir = DIR_RIGHT;
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			isLeft = true;
			dir = DIR_LEFT;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_DPAD_UP){
			isUp = false;
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
			isDown  = false;
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			isRight = false;
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			isLeft = false;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	/**
	 * 自定义的游戏初始化函数
	 */
	private void initGame(){
		bmpRobot = BitmapFactory.decodeResource(this.getResources(), R.drawable.robot);
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
				//canvas.drawText("游戏框架", 10, 10, paint);
				drawRobotFrame(currentFrame, canvas, paint);
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
		if(isUp){
			robot_y -= 5;
		}
		if(isDown){
			robot_y += 5;
		}
		if(isLeft){
			robot_x -= 5;
		}
		if(isRight){
			robot_x += 5;
		}
		currentFrame++;
		if(currentFrame >= 12){
			currentFrame = 0;
		}
	}
	
	/**
	 * 绘制当前机器人动画当前帧
	 * @param currentFrame
	 * @param canvas
	 * @param paint
	 */
	public void drawRobotFrame(int currentFrame, Canvas canvas, Paint paint){
		int frameW = bmpRobot.getWidth() / 6;	//一行有6帧
		int frameH = bmpRobot.getHeight() / 2;	//有2行
		int col = bmpRobot.getWidth() / frameW;		//获得列数
		int x = currentFrame % col * frameW;	//当前帧在位图中的x坐标
		int y = currentFrame / col * frameH; 
		canvas.save();
		//设置可视区域，与机器人大小相同
		canvas.clipRect(robot_x,robot_y,robot_x + bmpRobot.getWidth() /6, robot_y + bmpRobot.getHeight() /2);
		if(dir == DIR_LEFT){	//如果是向左移动
			//做镜像
			canvas.scale(-1, 1,robot_x -x + bmpRobot.getWidth() /2 ,robot_y - y +bmpRobot.getHeight() /2);
		}
		canvas.drawBitmap(bmpRobot, robot_x -x, robot_y -y, paint);
		canvas.restore();
	}

}
