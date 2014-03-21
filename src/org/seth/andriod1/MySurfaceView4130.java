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
 * ���ƻ��������Ƕ�����
 * @author seth16888
 *
 */
public class MySurfaceView4130  extends SurfaceView implements Callback,Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;	//��Ϸ��־
    private Canvas canvas;
    public static int screenW,screenH;	//��Ļ����
    
    private Bitmap bmpRobot;	//�����˰�������֡��λͼ
    private final int DIR_LEFT = 0;	//�����˵ķ�����
    private final int DIR_RIGHT = 1;	//�����˵ķ�����
    private int dir = DIR_RIGHT;		//�����˵�ǰ����
    private int currentFrame;		//�����˶�����ǰ֡
    private int robot_x, robot_y;		//������֡λͼx,y����
    private boolean isUp, isDown, isRight, isLeft;
    
	public MySurfaceView4130(Context context) {
		super(context);
		sfh = this.getHolder();	//surface������
		sfh.addCallback(this);	//�������뱾ʵ������
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		//���ý���
		setFocusable(true);
		setFocusableInTouchMode(true);
		//���ñ�������
		this.setKeepScreenOn(true);
	}

	@Override
	public void run(){
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();		//��Ϸ����
			logic();	//��Ϸ�߼�����
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
		initGame();	//��ʼ����Ϸ
		flag = true;
		//ʵ���߳�
		th = new Thread(this);
		//�����߳�
		th.start();
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;	//�˳�ʱ��������Ϸ�߳�
		
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
	 * �Զ������Ϸ��ʼ������
	 */
	private void initGame(){
		bmpRobot = BitmapFactory.decodeResource(this.getResources(), R.drawable.robot);
	}
	
	/**
	 * �Զ�����Ϸ����
	 */
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);	//ˢ��
				//��ͼ����������Ϸ״̬��ͬ���в�ͬ����
				//canvas.drawText("��Ϸ���", 10, 10, paint);
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
	 * �Զ�����Ϸ�߼�
	 */
	private void logic(){
		//�߼����������Ϸ״̬��ͬ���в�ͬ����
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
	 * ���Ƶ�ǰ�����˶�����ǰ֡
	 * @param currentFrame
	 * @param canvas
	 * @param paint
	 */
	public void drawRobotFrame(int currentFrame, Canvas canvas, Paint paint){
		int frameW = bmpRobot.getWidth() / 6;	//һ����6֡
		int frameH = bmpRobot.getHeight() / 2;	//��2��
		int col = bmpRobot.getWidth() / frameW;		//�������
		int x = currentFrame % col * frameW;	//��ǰ֡��λͼ�е�x����
		int y = currentFrame / col * frameH; 
		canvas.save();
		//���ÿ�������������˴�С��ͬ
		canvas.clipRect(robot_x,robot_y,robot_x + bmpRobot.getWidth() /6, robot_y + bmpRobot.getHeight() /2);
		if(dir == DIR_LEFT){	//����������ƶ�
			//������
			canvas.scale(-1, 1,robot_x -x + bmpRobot.getWidth() /2 ,robot_y - y +bmpRobot.getHeight() /2);
		}
		canvas.drawBitmap(bmpRobot, robot_x -x, robot_y -y, paint);
		canvas.restore();
	}

}
