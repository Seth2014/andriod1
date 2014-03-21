package org.seth.andriod1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * SurfaceView��Ϸ���
 * @author seth16888
 *
 */
public class MySurfaceView413  extends SurfaceView implements Callback,Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;	//��Ϸ��־
    private Canvas canvas;
    public static int screenW,screenH;	//��Ļ����
    
	public MySurfaceView413(Context context) {
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
	
	/**
	 * �Զ������Ϸ��ʼ������
	 */
	private void initGame(){
		
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
				canvas.drawText("��Ϸ���", 10, 10, paint);
				
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
		
	}
	

}
