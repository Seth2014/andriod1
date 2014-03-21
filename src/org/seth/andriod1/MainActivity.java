package org.seth.andriod1;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btn_ok,btn_cancel;
	private TextView tv;
	private EditText txt_password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 显示自定义的SurfaceView视图
		//setContentView(new MySurfaceView(this));
		setContentView(new MySurfaceView4130(this));
/*		setContentView(R.layout.activity_main);
		
		btn_ok = (Button)findViewById(R.id.btn_ok);
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		tv = (TextView)findViewById(R.id.tv);
		txt_password = (EditText)findViewById(R.id.txt_password);
		
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String input = txt_password.getText().toString();
				//tv.setText("ok按钮被按下");
				tv.setText(input);
				
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv.setText("cancel按钮被按下");
				
			}
		});*/
		
	}

}
