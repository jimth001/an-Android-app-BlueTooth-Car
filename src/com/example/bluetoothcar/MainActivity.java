package com.example.bluetoothcar;
import com.bn.sample11_3.R;

import android.R.integer;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class MainActivity extends Activity {
    private String myDeviceName;
    
    //UI消息显示设备名称
	public static final String DeviceName = null;
	
	//蓝牙适配器蓝牙服务
	private BluetoothAdapter btAdapter = null;
	private MyService myService = null;
	//记录小车命令的服务：
	private MyAutoControl myControl = null;
	//蓝牙服务连接handler消息，线程间通信
    public static final int Message = 1;
    public static final int State_Change = 96;
    public static final int Device_Name = 4;
    
    //打开蓝牙设备
    private static final int Enable_Bluetooth = 2;
    
    static float screenStanderWidth=960;
	static float screenStanderHeight=540;
	
	static float c=screenStanderWidth/2;
	static float d=screenStanderHeight/2;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		MySurfaceView mv=new MySurfaceView(this);
        setContentView(mv);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter == null) {
        	Toast.makeText(this, R.string.bt_not_available, Toast.LENGTH_LONG).show();
        	return;
        }
        if(myControl==null)
    	{
    		myControl=new MyAutoControl(this.getApplicationContext(),mHandler);
    	}
        myControl.start();
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	if(myControl==null)
    	{
    		myControl=new MyAutoControl(this.getApplicationContext(),mHandler);
    	}
    	if(!btAdapter.isEnabled()) {
    		Intent btintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    		startActivityForResult(btintent, Enable_Bluetooth);
    	}
    		else if(myService == null) {
    			myService = new MyService(this, mHandler,myControl.getMyAutoControlHandler());
    		}
    	if(!myControl.isAlive())
    	{
    		Toast.makeText(getApplicationContext(), "myControl运行状态异常"
                    , Toast.LENGTH_SHORT).show();
    	}
        }
		
    @Override
    public synchronized void onResume() {
    	super.onResume();
    	if(myService != null) {
    		if(myService.getState() == MyService.STATE_NONE) {
    			myService.start();
    		}
    	}
    	if(myControl!=null){
    		myControl.reSet();
    	}
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if(btAdapter.isEnabled()) {
        	//sendMessage((byte)0x00);//.........151104
        }
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void onDestroy() {
        super.onDestroy();
        if (myService != null) myService.stop();
        //if(myControl!=null) myControl.destroy();
    }
   
	public void mySendMessage(byte b) {
		if (myService.getState() != MyService.STATE_CONNECTED) {
			Toast.makeText(getApplicationContext(), "尚未连接到设备"
                    , Toast.LENGTH_SHORT).show();
            return;
        }
		if(myControl.isAuto()==false)//如果处于自动控制状态，屏蔽输入
            {myService.write(b);}
        
	}
   
    //交互界面事件处理返回结果
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {//requestCode标识从哪个Activity跳转到该Activity 和startActivityForResult中的requestCode相对应 
		case 1://1代表连接设备  //resultCode表示返回值状态 由子Activity通过其setResult()方法返回       data包含了返回数据
			// 如果设备列表Activity返回一个连接的设备
			if (resultCode == Activity.RESULT_OK) {
				// 获取设备的MAC地址
				String address = data.getExtras().getString(
						MyDeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// 获取BLuetoothDevice对象
				BluetoothDevice device = btAdapter.getRemoteDevice(address);
				myService.connect(device);// 连接该设备
			}
			break;
		case Enable_Bluetooth:
          if (resultCode == Activity.RESULT_OK) {
        	  if(myControl==null) myControl=new MyAutoControl(this.getApplicationContext(),mHandler);
        	  myService = new MyService(this, mHandler,myControl.getMyAutoControlHandler());
        	  // 初始化BluetoothService并执行蓝牙连接
              //Handler对象mHandler来负责数据的交换和线程之间的通信
             } 
          else {
            Toast.makeText(this, R.string.bt_not_enable, Toast.LENGTH_SHORT).show();
            return;
            }
		}
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// 启动设备列表Activity
		Intent serverIntent = new Intent(this, MyDeviceListActivity.class);
		startActivityForResult(serverIntent, 1);//1与方法onActivityResult中的1相对应
		return true;
	}	
  //处理handler不同的信息
    private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			 switch (msg.what){
	            case Device_Name:
	                myDeviceName = msg.getData().getString(DeviceName);
	                Toast.makeText(getApplicationContext(), "已连接到 "
	                               + myDeviceName, Toast.LENGTH_SHORT).show();
	                break;
					case MyAutoControl.angle0:mySendMessage(MyAutoControl.Turnangle0);break;
					case MyAutoControl.angle1:mySendMessage(MyAutoControl.Turnangle1);break;
					case MyAutoControl.angle2:mySendMessage(MyAutoControl.Turnangle2);break;
					case MyAutoControl.angle3:mySendMessage(MyAutoControl.Turnangle3);break;
					case MyAutoControl.angle4:mySendMessage(MyAutoControl.Turnangle4);break;
					case MyAutoControl.angle5:mySendMessage(MyAutoControl.Turnangle5);break;
					case MyAutoControl.angle6:mySendMessage(MyAutoControl.Turnangle6);break;
					case MyAutoControl.angle7:mySendMessage(MyAutoControl.Turnangle7);break;
					case MyAutoControl.angle8:mySendMessage(MyAutoControl.Turnangle8);break;
					case MyAutoControl.angle9:mySendMessage(MyAutoControl.Turnangle9);break;
					case MyAutoControl.up:mySendMessage(MyAutoControl.Turnup);break;
					case MyAutoControl.down:mySendMessage(MyAutoControl.Turndown);break;
					case MyAutoControl.forward:mySendMessage(MyAutoControl.Turnforward);break;
					case MyAutoControl.back:mySendMessage(MyAutoControl.Turnback);break;
					case MyAutoControl.stop:mySendMessage(MyAutoControl.Turnstop);break;
					case MyAutoControl.optTips:Toast.makeText(getApplicationContext(), msg.obj.toString()
                            , Toast.LENGTH_SHORT).show();break;
					case MyAutoControl.fileNotExist:Toast.makeText(getApplicationContext(), "记录文件不存在，请先记录操作"
                            , Toast.LENGTH_SHORT).show();break;
					default: /*Toast.makeText(getApplicationContext(), "自动控制指令错误"+msg.what+" "+msg.arg1+" "+msg.arg2
                            , Toast.LENGTH_SHORT).show();*/
						break;
	            }
		}
    	
    };
    public void sendMessageToAuto(int s)
    {
    	if(myService.getState()==myService.STATE_CONNECTED)
    	myService.autoHandler.obtainMessage(s,0,0,null).sendToTarget();
    	else {
    		Toast.makeText(getApplicationContext(), "尚未连接到设备"
                    , Toast.LENGTH_SHORT).show();
		}
    }

}