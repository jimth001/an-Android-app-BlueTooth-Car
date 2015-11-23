package com.example.bluetoothcar;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.example.bluetoothcar.MainActivity.*;

@SuppressLint("WrongCall") public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

	MainActivity activity;
	Paint paint;//绘制文字与按钮边框
	Paint paint1;//绘制路径
	Paint paint2;//绘制画布
	Paint paint3;
	Path path1;
	Path path2;
	Path path3;
	Path path4;
	Path path5;
	Path path6;
	Path path7;
	Path path8;
	Path path9;
	/*Path tpath0;
	Path tpath1;
	Path tpath2;
	Path tpath3;
	Path tpath4;
	Path tpath5;
	Path tpath6;
	Path tpath7;
	Path tpath8;
	Path tpath9;*/
	
	static float plusWidth=1000;
	static float decWidth=350;
	static float minusHeight=-500;
	static float distanceWidth=180;
	static float status=0;//0前进、1后退、3左转、4右转
	static float statusl=2;//2左转、3右转
	public MySurfaceView(MainActivity activity) {
		super(activity);
		this.activity=activity;
		this.getHolder().addCallback(this);
		
		paint=new Paint();
		paint.setAntiAlias(true);//设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢
		paint.setColor(Color.YELLOW);
		paint.setStyle(Style.STROKE);//设置画笔样式
		paint.setStrokeWidth(4);
		
		
		paint1=new Paint();//按钮背景
		paint1.setAntiAlias(true);
		paint1.setColor(Color.GRAY);
		
		paint2=new Paint();//
		paint2.setAntiAlias(true);
		paint2.setARGB(255, 234, 175, 191);
		
		paint3=new Paint();//字
		paint3.setAntiAlias(true);
		paint3.setColor(Color.YELLOW);
		paint3.setTextSize(60);
		
		path1=new Path();
		path1.moveTo(screenStanderWidth/2-100, screenStanderHeight/2-60);//380，
		path1.lineTo(screenStanderWidth/2, screenStanderHeight/2-210);//480
		path1.lineTo(screenStanderWidth/2+100, screenStanderHeight/2-60);//580
		path1.lineTo(screenStanderWidth/2-100, screenStanderHeight/2-60);//380
		
		path2=new Path();
		path2.moveTo(screenStanderWidth/2-100, screenStanderHeight/2+60);//380
		path2.lineTo(screenStanderWidth/2, screenStanderHeight/2+210);//480
		path2.lineTo(screenStanderWidth/2+100, screenStanderHeight/2+60);//580
		path2.lineTo(screenStanderWidth/2-100, screenStanderHeight/2+60);//380
		
		path3=new Path();
		path3.moveTo(screenStanderWidth/2-110, screenStanderHeight/2-100);//370
		path3.lineTo(screenStanderWidth/2-270, screenStanderHeight/2);//210
		path3.lineTo(screenStanderWidth/2-110, screenStanderHeight/2+100);//370
		path3.lineTo(screenStanderWidth/2-110, screenStanderHeight/2-100);//370
		
		path4=new Path();
		path4.moveTo(screenStanderWidth/2+110, screenStanderHeight/2-100);//590
		path4.lineTo(screenStanderWidth/2+270, screenStanderHeight/2);//750
		path4.lineTo(screenStanderWidth/2+110, screenStanderHeight/2+100);//590
		path4.lineTo(screenStanderWidth/2+110, screenStanderHeight/2-100);//590
		
		path5=new Path();
		path5.moveTo(screenStanderWidth/2-100+plusWidth, screenStanderHeight/2-60);//380，
		path5.lineTo(screenStanderWidth/2+plusWidth, screenStanderHeight/2-210);//480
		path5.lineTo(screenStanderWidth/2+100+plusWidth, screenStanderHeight/2-60);//580
		path5.lineTo(screenStanderWidth/2-100+plusWidth, screenStanderHeight/2-60);//380
		
		path6=new Path();
		path6.moveTo(screenStanderWidth/2-100+plusWidth, screenStanderHeight/2+60);//380
		path6.lineTo(screenStanderWidth/2+plusWidth, screenStanderHeight/2+210);//480
		path6.lineTo(screenStanderWidth/2+100+plusWidth, screenStanderHeight/2+60);//580
		path6.lineTo(screenStanderWidth/2-100+plusWidth, screenStanderHeight/2+60);//380
		
		path7=new Path();
		path7.moveTo(screenStanderWidth/2-110+plusWidth, screenStanderHeight/2-100);//370
		path7.lineTo(screenStanderWidth/2-270+plusWidth, screenStanderHeight/2);//210
		path7.lineTo(screenStanderWidth/2-110+plusWidth, screenStanderHeight/2+100);//370
		path7.lineTo(screenStanderWidth/2-110+plusWidth, screenStanderHeight/2-100);//370
		
		path8=new Path();
		path8.moveTo(screenStanderWidth/2+110+plusWidth, screenStanderHeight/2-100);//590
		path8.lineTo(screenStanderWidth/2+270+plusWidth, screenStanderHeight/2);//750
		path8.lineTo(screenStanderWidth/2+110+plusWidth, screenStanderHeight/2+100);//590
		path8.lineTo(screenStanderWidth/2+110+plusWidth, screenStanderHeight/2-100);//590
		
		/*tpath0=new Path();
		tpath0.moveTo(screenStanderWidth/2-110, screenStanderHeight/2-100-minusHeight);//370
		tpath0.lineTo(screenStanderWidth/2-270, screenStanderHeight/2-minusHeight);//210
		tpath0.lineTo(screenStanderWidth/2-110, screenStanderHeight/2+100-minusHeight);//370
		tpath0.lineTo(screenStanderWidth/2-110, screenStanderHeight/2-100-minusHeight);//370
		
		tpath1=new Path();
		tpath1.moveTo(screenStanderWidth/2-110+distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath1.lineTo(screenStanderWidth/2-270+distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath1.lineTo(screenStanderWidth/2-110+distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath1.lineTo(screenStanderWidth/2-110+distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		
		tpath2=new Path();
		tpath2.moveTo(screenStanderWidth/2-110+2*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath2.lineTo(screenStanderWidth/2-270+2*distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath2.lineTo(screenStanderWidth/2-110+2*distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath2.lineTo(screenStanderWidth/2-110+2*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		
		tpath3=new Path();
		tpath3.moveTo(screenStanderWidth/2-110+3*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath3.lineTo(screenStanderWidth/2-270+3*distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath3.lineTo(screenStanderWidth/2-110+3*distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath3.lineTo(screenStanderWidth/2-110+3*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		
		tpath4=new Path();
		tpath4.moveTo(screenStanderWidth/2-110+4*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath4.lineTo(screenStanderWidth/2-270+4*distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath4.lineTo(screenStanderWidth/2-110+4*distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath4.lineTo(screenStanderWidth/2-110+4*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		
		tpath5=new Path();
		tpath5.moveTo(screenStanderWidth/2-110+5*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath5.lineTo(screenStanderWidth/2-270+5*distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath5.lineTo(screenStanderWidth/2-110+5*distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath5.lineTo(screenStanderWidth/2-110+5*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		
		tpath6=new Path();
		tpath6.moveTo(screenStanderWidth/2-110+6*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath6.lineTo(screenStanderWidth/2-270+6*distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath6.lineTo(screenStanderWidth/2-110+6*distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath6.lineTo(screenStanderWidth/2-110+6*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		
		tpath7=new Path();
		tpath7.moveTo(screenStanderWidth/2-110+7*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath7.lineTo(screenStanderWidth/2-270+7*distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath7.lineTo(screenStanderWidth/2-110+7*distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath7.lineTo(screenStanderWidth/2-110+7*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		
		tpath8=new Path();
		tpath8.moveTo(screenStanderWidth/2-110+8*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath8.lineTo(screenStanderWidth/2-270+8*distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath8.lineTo(screenStanderWidth/2-110+8*distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath8.lineTo(screenStanderWidth/2-110+8*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		
		tpath9=new Path();
		tpath9.moveTo(screenStanderWidth/2-110+9*distanceWidth, screenStanderHeight/2-100-minusHeight);//370
		tpath9.lineTo(screenStanderWidth/2-270+9*distanceWidth, screenStanderHeight/2-minusHeight);//210
		tpath9.lineTo(screenStanderWidth/2-110+9*distanceWidth, screenStanderHeight/2+100-minusHeight);//370
		tpath9.lineTo(screenStanderWidth/2-110+9*distanceWidth, screenStanderHeight/2-100-minusHeight);//370*/
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		
		//绘制向上的按钮
		canvas.drawPath(path1, paint1);
		canvas.drawPath(path1, paint);
		
		//绘制向下的按钮
		canvas.drawPath(path2, paint1);
		canvas.drawPath(path2, paint);
		
		//绘制向左的按钮
		canvas.drawPath(path3, paint1);
		canvas.drawPath(path3, paint);
	
		//绘制向右的按钮
		canvas.drawPath(path4, paint1);
		canvas.drawPath(path4, paint);
		
		//绘制5按钮
		canvas.drawPath(path5, paint1);
		canvas.drawPath(path5, paint);
				
		//绘制6按钮
		canvas.drawPath(path6, paint1);
		canvas.drawPath(path6, paint);
				
		//绘制7按钮
		canvas.drawPath(path7, paint1);
		canvas.drawPath(path7, paint);
		
		//绘制8按钮
		canvas.drawPath(path8, paint1);
		canvas.drawPath(path8, paint);
		
		/*canvas.drawPath(tpath0, paint1);
		canvas.drawPath(tpath0, paint);
		
		canvas.drawPath(tpath1, paint1);
		canvas.drawPath(tpath1, paint);
		
		canvas.drawPath(tpath2, paint1);
		canvas.drawPath(tpath2, paint);
		
		canvas.drawPath(tpath3, paint1);
		canvas.drawPath(tpath3, paint);
		
		canvas.drawPath(tpath4, paint1);
		canvas.drawPath(tpath4, paint);
		
		canvas.drawPath(tpath5, paint1);
		canvas.drawPath(tpath5, paint);
		
		canvas.drawPath(tpath6, paint1);
		canvas.drawPath(tpath6, paint);
		
		canvas.drawPath(tpath7, paint1);
		canvas.drawPath(tpath7, paint);
		
		canvas.drawPath(tpath8, paint1);
		canvas.drawPath(tpath8, paint);
		
		canvas.drawPath(tpath9, paint1);
		canvas.drawPath(tpath9, paint);*/
		
		
		//开始绘制文字
		canvas.drawText("前进", screenStanderWidth/2-60, screenStanderHeight/2-73, paint3);
		
		canvas.drawText("后退", screenStanderWidth/2-65, screenStanderHeight/2+120, paint3);
		
		canvas.drawText("up", screenStanderWidth/2-227, screenStanderHeight/2+20, paint3);
		
		canvas.drawText("down", screenStanderWidth/2+110, screenStanderHeight/2+20, paint3);
		
		canvas.drawText("停止", screenStanderWidth/2-60+plusWidth, screenStanderHeight/2-73, paint3);
		
		canvas.drawText("cs1", screenStanderWidth/2-65+plusWidth, screenStanderHeight/2+120, paint3);
		
		canvas.drawText("手动", screenStanderWidth/2-227+plusWidth, screenStanderHeight/2+20, paint3);
		
		canvas.drawText("自动", screenStanderWidth/2+110+plusWidth, screenStanderHeight/2+20, paint3);
		
		canvas.drawText("0", screenStanderWidth/2-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("1", screenStanderWidth/2+distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("2", screenStanderWidth/2+2*distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("3", screenStanderWidth/2+3*distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("4", screenStanderWidth/2+4*distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("5", screenStanderWidth/2+5*distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("6", screenStanderWidth/2+6*distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("7", screenStanderWidth/2+7*distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("8", screenStanderWidth/2+8*distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
		canvas.drawText("9", screenStanderWidth/2+9*distanceWidth-decWidth, screenStanderHeight/2-73-minusHeight, paint3);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e){
		//获取触控的动作编号
		int action=e.getAction()&MotionEvent.ACTION_MASK;
		int id=(e.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>>MotionEvent.ACTION_POINTER_ID_SHIFT;	
		switch(action){
			case MotionEvent.ACTION_DOWN: //主点down 	
				//向Map中记录一个新点
				float a=e.getX();
				float b=e.getY();
				if(a>=screenStanderWidth/2-100&&a<=screenStanderWidth/2+100
						&&b>=screenStanderHeight/2-210&&b<=screenStanderHeight/2-60)
				{
					status=0;//前进
					statusl=0;
					//activity.mySendMessage((byte)0x66);
					activity.mySendMessage(MyAutoControl.Turnforward);
					activity.sendMessageToAuto(MyAutoControl.forward);
				}
				else if(a>=screenStanderWidth/2-100&&a<=screenStanderWidth/2+100
						  &&b>=screenStanderHeight/2+60&&b<=screenStanderHeight/2+210)
				{
					status=1;//后退
					statusl=0;
					//activity.mySendMessage((byte)0x62);
					activity.mySendMessage(MyAutoControl.Turnback);
					activity.sendMessageToAuto(MyAutoControl.back);
				}
				else if(a>=screenStanderWidth/2-270&&a<=screenStanderWidth/2-110
						&&b>=screenStanderHeight/2-100&&b<=screenStanderHeight/2+100)
				{
					status=2;//up
					statusl=4;
					//activity.mySendMessage((byte)0x75);
					activity.mySendMessage(MyAutoControl.Turnup);
					activity.sendMessageToAuto(MyAutoControl.up);
				}
				else if(a>=screenStanderWidth/2+110&&a<=screenStanderWidth/2+270
						 &&b>=screenStanderHeight/2-100&&b<=screenStanderHeight/2+100)
				{
					status=3;//down
					statusl=5;
					//activity.mySendMessage((byte)0x64);
					activity.mySendMessage(MyAutoControl.Turndown);
					activity.sendMessageToAuto(MyAutoControl.down);
				}
				else if(a>=screenStanderWidth/2-100+plusWidth&&a<=screenStanderWidth/2+100+plusWidth
						&&b>=screenStanderHeight/2-210&&b<=screenStanderHeight/2-60)
				{
					status=0;//停止
					statusl=0;
					//activity.mySendMessage((byte)0x73);
					activity.mySendMessage(MyAutoControl.Turnstop);
					activity.sendMessageToAuto(MyAutoControl.stop);
				}
				else if(a>=screenStanderWidth/2-100+plusWidth&&a<=screenStanderWidth/2+100+plusWidth
						  &&b>=screenStanderHeight/2+60&&b<=screenStanderHeight/2+210)
				{
					status=1;//停止记录，自动
					statusl=0;
					//activity.mySendMessage((byte)(0xf));
					activity.sendMessageToAuto(MyAutoControl.endRecord);
					activity.sendMessageToAuto(MyAutoControl.endAutoControl);
				}
				else if(a>=screenStanderWidth/2-270+plusWidth&&a<=screenStanderWidth/2-110+plusWidth
						&&b>=screenStanderHeight/2-100&&b<=screenStanderHeight/2+100)
				{
					status=2;//手动
					statusl=4;
					//activity.mySendMessage((byte)0x30);
					activity.sendMessageToAuto(MyAutoControl.startRecord);
				}
				else if(a>=screenStanderWidth/2+110+plusWidth&&a<=screenStanderWidth/2+270+plusWidth
						 &&b>=screenStanderHeight/2-100&&b<=screenStanderHeight/2+100)
				{
					status=3;//自动
					statusl=5;
					//activity.mySendMessage((byte)0x40);
					activity.sendMessageToAuto(MyAutoControl.startAutoControl);
				}
				else if(a>=screenStanderWidth/2-100&&a<=screenStanderWidth/2+100
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//0
					statusl=0;
					//activity.mySendMessage((byte)0x30);
					activity.mySendMessage(MyAutoControl.Turnangle0);
					activity.sendMessageToAuto(MyAutoControl.angle0);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth-decWidth&&a<=screenStanderWidth/2+100+distanceWidth-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//1
					statusl=0;
					//activity.mySendMessage((byte)0x31);
					activity.mySendMessage(MyAutoControl.Turnangle1);
					activity.sendMessageToAuto(MyAutoControl.angle1);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth*2-decWidth&&a<=screenStanderWidth/2+100+distanceWidth*2-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//2
					statusl=0;
					//activity.mySendMessage((byte)0x32);
					activity.mySendMessage(MyAutoControl.Turnangle2);
					activity.sendMessageToAuto(MyAutoControl.angle2);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth*3-decWidth&&a<=screenStanderWidth/2+100+distanceWidth*3-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//3
					statusl=0;
					//activity.mySendMessage((byte)0x33);
					activity.mySendMessage(MyAutoControl.Turnangle3);
					activity.sendMessageToAuto(MyAutoControl.angle3);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth*4-decWidth&&a<=screenStanderWidth/2+100+distanceWidth*4-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//4
					statusl=0;
					//activity.mySendMessage((byte)0x34);
					activity.mySendMessage(MyAutoControl.Turnangle4);
					activity.sendMessageToAuto(MyAutoControl.angle4);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth*5-decWidth&&a<=screenStanderWidth/2+100+distanceWidth*5-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//5
					statusl=0;
					//activity.mySendMessage((byte)0x35);
					activity.mySendMessage(MyAutoControl.Turnangle5);
					activity.sendMessageToAuto(MyAutoControl.angle5);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth*6-decWidth&&a<=screenStanderWidth/2+100+distanceWidth*6-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//6
					statusl=0;
					//activity.mySendMessage((byte)0x36);
					activity.mySendMessage(MyAutoControl.Turnangle6);
					activity.sendMessageToAuto(MyAutoControl.angle6);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth*7-decWidth&&a<=screenStanderWidth/2+100+distanceWidth*7-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//7
					statusl=0;
					//activity.mySendMessage((byte)0x37);
					activity.mySendMessage(MyAutoControl.Turnangle7);
					activity.sendMessageToAuto(MyAutoControl.angle7);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth*8-decWidth&&a<=screenStanderWidth/2+100+distanceWidth*8-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//8
					statusl=0;
					//activity.mySendMessage((byte)0x38);
					activity.mySendMessage(MyAutoControl.Turnangle8);
					activity.sendMessageToAuto(MyAutoControl.angle8);
				}
				else if(a>=screenStanderWidth/2-100+distanceWidth*9-decWidth&&a<=screenStanderWidth/2+100+distanceWidth*9-decWidth
						&&b>=screenStanderHeight/2-210-minusHeight&&b<=screenStanderHeight/2-60-minusHeight)
				{
					status=0;//9
					statusl=0;
					//activity.mySendMessage((byte)0x39);
					activity.mySendMessage(MyAutoControl.Turnangle9);
					activity.sendMessageToAuto(MyAutoControl.angle9);
				}
			break;
			/*case MotionEvent.ACTION_POINTER_DOWN: //辅点down	  多点触控
				//多点触控部分需要重写15.11.4
				float c=e.getX(id);
                float d=e.getY(id);
				if(c>=screenStanderWidth/2-270&&c<=screenStanderWidth/2-110
						&&d>=screenStanderHeight/2-100&&d<=screenStanderHeight/2+100)
				{
					statusl=2;
					if(status==0)
					{
						activity.mySendMessage((byte)0x5f);
					}
					else if(status==1)
					{
						activity.mySendMessage((byte)0x7f);
					}
					
				}
				else if(c>=screenStanderWidth/2+110&&c<=screenStanderWidth/2+270
						 &&d>=screenStanderHeight/2-100&&d<=screenStanderHeight/2+100)
				{
					statusl=3;
					if(status==0)
					{
						activity.mySendMessage((byte)0x6f);
					}
					else if(status==1)
					{
						activity.mySendMessage((byte)0x8f);
					}
					
				}
				else if(c>=screenStanderWidth/2-100&&c<=screenStanderWidth/2+100
						&&d>=screenStanderHeight/2-210&&d<=screenStanderHeight/2-60)
				{
					status=0;//前进
					if(statusl==4)//左转
					{
						activity.mySendMessage((byte)0x5f);
					}
					else if(statusl==5)//右转
					{
						activity.mySendMessage((byte)0x6f);
					}
				}
				else if(c>=screenStanderWidth/2-100&&c<=screenStanderWidth/2+100
						  &&d>=screenStanderHeight/2+60&&d<=screenStanderHeight/2+210)
				{
					status=1;//后退
					if(statusl==4)//左转
					{
						activity.mySendMessage((byte)0x7f);
					}
					else if(statusl==5)//右转
					{
						activity.mySendMessage((byte)0x8f);
					}
				}
			break;
			case MotionEvent.ACTION_MOVE: //主/辅点move  
			break;
			case MotionEvent.ACTION_UP: //主点up
				activity.mySendMessage((byte)0x00);
			break;
			case MotionEvent.ACTION_POINTER_UP: //辅点up	
				float f=e.getX();
				float g=e.getY();
				//前进或后退
				if((f>=screenStanderWidth/2-100&&f<=screenStanderWidth/2+100
						&&g>=screenStanderHeight/2-210&&g<=screenStanderHeight/2-60)||
						(f>=screenStanderWidth/2-100&&f<=screenStanderWidth/2+100
						   &&g>=screenStanderHeight/2+60&&g<=screenStanderHeight/2+210))
				{
					statusl=0;
					if(status==0)
					{
						activity.mySendMessage((byte)0x1f);
					}
					else if(status==1)
					{
						activity.mySendMessage((byte)0x2f);
					}
					else if(status==2)
					{
						activity.mySendMessage((byte)0x3f);
					}
					else if(status==3)
					{
						activity.mySendMessage((byte)0x4f);
					}
				}
				else if((f>=screenStanderWidth/2-270&&f<=screenStanderWidth/2-110
						&&g>=screenStanderHeight/2-100&&g<=screenStanderHeight/2+100)||
						(f>=screenStanderWidth/2+110&&f<=screenStanderWidth/2+270
						 &&g>=screenStanderHeight/2-100&&g<=screenStanderHeight/2+100)){

					status=9;//置前后状态不可用
					if(statusl==4){
						activity.mySendMessage((byte)0x3f);//左转
					}else if(statusl==5){
						activity.mySendMessage((byte)0x4f);//右转
					}
				}
			break;*/
		} 
		return true;
	}	
	
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas=holder.lockCanvas();
		synchronized(holder)
		{
			try
			{
				onDraw(canvas);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(canvas!=null)
				{
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {}

}
