package com.example.bluetoothcar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;

import android.R.bool;
import android.R.integer;
import android.R.string;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Telephony.Mms.Addr;
import android.widget.Toast;

public class MyAutoControl extends Thread{
	private final Handler mHandler;
	public static final int startRecord=1;
	public static final int startAutoControl=2;
	public static final int endRecord=3;
	public static final int endAutoControl=4;
	public static final int angle0=5;
	public static final int angle1=6;
	public static final int angle2=7;
	public static final int angle3=8;
	public static final int angle4=9;
	public static final int angle5=10;
	public static final int angle6=11;
	public static final int angle7=12;
	public static final int angle8=13;
	public static final int angle9=14;
	public static final int forward=15;
	public static final int back=16;
	public static final int up=17;
	public static final int down=18;
	public static final int stop=19;
	public static final byte Turnangle0=0x30;
	public static final byte Turnangle1=0x31;
	public static final byte Turnangle2=0x32;
	public static final byte Turnangle3=0x33;
	public static final byte Turnangle4=0x34;
	public static final byte Turnangle5=0x35;
	public static final byte Turnangle6=0x36;
	public static final byte Turnangle7=0x37;
	public static final byte Turnangle8=0x38;
	public static final byte Turnangle9=0x39;
	public static final byte Turnforward=0x66;
	public static final byte Turnback=0x62;
	public static final byte Turnup=0x75;
	public static final byte Turndown=0x64;
	public static final byte Turnstop=0x73;
	public static final int  optTips=99;
	public static final int  fileNotExist=90;
	public static final boolean ON=true;
	public static final boolean OFF=false;
	private Context context;
	private  boolean recordState;
	private  boolean autoState;
	private  long  startTime;
	private  long nowTime;
	private FileOutputStream out = null;
	
	//private  int totalOpt;//记录存档中的总操作数
	//Collection collection = new ArrayList();
	ArrayList<Integer> timeList;
	ArrayList<Integer> optList;
	private autoThread at=null;
	 private class autoThread extends Thread {
		 	private FileInputStream in = null;
		 	InputStreamReader inStream =null;
		 	ArrayList<Integer> autoTimeArrayList=null;
			ArrayList<Integer> autoOptArrayList=null;
			private static final int timeInterval=100;
			char[] js=new char[4096];
			private boolean isFileExist;
			JSONArray numbers=null;
	         public autoThread(){
	        	autoTimeArrayList=new ArrayList<Integer>();
	    		autoOptArrayList=new ArrayList<Integer>();
	    		FileInputStream tmp = null;
            	try {
        			tmp=context.openFileInput("file_out.txt");
        			isFileExist=true;
        		} catch (Exception e) {
        			// TODO: handle exception
        			/*Toast.makeText(context, "读取文件错误"
        	                , Toast.LENGTH_SHORT).show();*/
        			isFileExist=false;
        		}
            	if(isFileExist==true)
            	{
            		in=tmp;
            		try {
            			inStream =new InputStreamReader(in);
            		} catch (Exception e) {
            			// TODO: handle exception
            		}
            	
            		try {
            			inStream.read(js);
            		} catch (NullPointerException e) {
            			// TODO 自动生成的 catch 块
            			/*Toast.makeText(context, "文件read错误"
			                	, Toast.LENGTH_SHORT).show();*/
            		} catch (IOException e) {
            			// TODO 自动生成的 catch 块
            			e.printStackTrace();
            		}
            		try {
            			String json=new String(js);
            			numbers=new JSONArray(json);
            		} catch (JSONException e) {
            			// TODO 自动生成的 catch 块
            			/*Toast.makeText(context, "生成JSONArray错误"
			                	, Toast.LENGTH_SHORT).show();*/
            		}
            		int i=0;
            		int n=numbers.length();
            		for(i=0;i<n;i++)
            		{
            			try {
            				autoTimeArrayList.add(numbers.getInt(i));
            			} catch (JSONException e) {
            				// TODO 自动生成的 catch 块
            				/*Toast.makeText(context, "填timelist错误"
				                	, Toast.LENGTH_SHORT).show();*/
            			}
            			i++;
            			try {
            				autoOptArrayList.add(numbers.getInt(i));
            			} catch (JSONException e) {
            				// TODO 自动生成的 catch 块
							/*Toast.makeText(context, "填optlist错误"
				                , Toast.LENGTH_SHORT).show();*/
            			}
            		}
            	}
	        }
	        private void delay(int ms){  
	        	try {  
	        		Thread.currentThread();  
	        		Thread.sleep(ms);  
	        	} catch (InterruptedException e) {  
	        		e.printStackTrace();  
	        	}   
	        }    

	        public void run() {
	        	if(isFileExist==true)	
	        	{
	        		int i=0;
		        	int n=autoTimeArrayList.size();
		        	long startT=System.currentTimeMillis();
		        	long nowT;
		        	if(n>0)
		        	{
		        		int maxTime=autoTimeArrayList.get(n-1);
			        	while(true)
			        	{
			        		nowT=System.currentTimeMillis();
			        		if(i==n) break;
			        		if(autoState==OFF) break;
			        		if(Math.abs((int)(nowT-startT)-autoTimeArrayList.get(i))<=timeInterval/2)
			        		{
			        			
								mHandler.obtainMessage(autoOptArrayList.get(i)).sendToTarget();
								String tips=new String("完成操作"+i);
								mHandler.obtainMessage(optTips,tips).sendToTarget();
								/*Toast.makeText(context, "完成操作"+i
						                , Toast.LENGTH_SHORT).show();*/
								
			        			i++;
			        		}
			        		if(nowT-startT-timeInterval>maxTime&&i<n)
			        		{
			        			System.out.println("自动控制查找丢失");
			        			break;
			        		}
			        		delay(timeInterval);
			        	}
			        	if(inStream!=null)
							try {
								inStream.close();
							} catch (IOException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
			        	if(in!=null)
							try {
								in.close();
							} catch (IOException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
			        	autoState=OFF;
		        	}
	        	}
	        	else {
	        		mHandler.obtainMessage(fileNotExist).sendToTarget();
	        	}
	        }
	    }
	
	 public MyAutoControl(Context con,Handler hdl)
	{
		mHandler=hdl;
		recordState=OFF;
		autoState=OFF;
		startTime=0;
		nowTime=0;
		this.context=con;
		at=null;
		timeList=new ArrayList<Integer>();
		optList=new ArrayList<Integer>();
	}
	public void reSet()
	{
		recordState=OFF;
		autoState=OFF;
		startTime=0;
		nowTime=0;
		at=null;
	}
	public void Record(int opt)//开始记录操作信息
	{
		nowTime=System.currentTimeMillis();
		timeList.add((int) (nowTime-startTime));
		optList.add(opt);
	}
	public void startAuto()//开始根据存档自动控制
	{
		/*if(at==null)
		at=new autoThread();
		if(at==null)//打开失败，退回autoState=off状态
		{Toast.makeText(context, "线程指针为空"
                , Toast.LENGTH_SHORT).show();
		autoState=OFF;
		}
		else
		at.start();*/
	}
	public boolean isAuto()
	{
		return autoState;
	}
	public Handler getMyAutoControlHandler()
	{
		return autoHandler;
	}
	public void SaveRecord()//1110未完成
	{
		FileOutputStream tmp = null;
    	try {
			tmp=context.openFileOutput("file_out.txt",Context.MODE_PRIVATE);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("读取文件错误！");
		}
    	out=tmp;
		int n=timeList.size();
		int i=0;
		JSONArray numbers=new JSONArray();
		for(i=0;i<n;i++){
		numbers.put(timeList.get(i).intValue());
		numbers.put(optList.get(i).intValue());
		}
		String jsonString= numbers.toString();
		OutputStreamWriter outStream =null;
		try
		{
			/*fileOut =new FileOutputStream(saveFilePath,false);*/
			outStream =new OutputStreamWriter(out);
			outStream.write(jsonString);
		} 
		catch(Exception e) 
		{
		}
		finally
		{
			try
			{
				if(null!=outStream)
					outStream.close();

				if(out!=null)
					out.close();
			} 
			catch(Exception e) 
			{
			}
			optList.clear();
			timeList.clear();
		}
	}
	public void run()
	{
		Looper.prepare();
	    Looper.loop();
	}
	private final Handler autoHandler= new Handler(Looper.myLooper()) {
		@Override
		public void handleMessage(android.os.Message msg) {
			 switch (msg.what){
	            case startRecord:
	                if(recordState==OFF&&autoState==OFF)//非自动状态才能开启记录功能
	                	{recordState=ON;startTime=System.currentTimeMillis();
	                	FileOutputStream tmp = null;
	                	try {
	            			tmp=context.openFileOutput("file_out.txt",Context.MODE_PRIVATE);
	            		} catch (Exception e) {
	            			// TODO: handle exception
	            			System.out.println("打开文件错误！");
	            		}
	                	out=tmp;}
	                break;
	            case endRecord:
	            	if(recordState==ON)
	            		{if(out!=null) SaveRecord();
	            		recordState=OFF;}
	            		/*cancel record*,询问是否save,先实现默认save*/
	            	break;
	            case startAutoControl:
	            	if(autoState==OFF&&recordState==OFF)
	            	{
	            	autoState=ON;
	            	if(at==null)
	            	at=new autoThread();
	            	else {
	            		//at.destroy();
	            		at=new autoThread();
	            	}
	            	if(at==null)//打开失败，退回autoState=off状态
	            	{Toast.makeText(context, "线程指针为空"
	                            , Toast.LENGTH_SHORT).show();
	            	autoState=OFF;
	            	}
	            	else
	            	at.start();}
	            	break;
	            case endAutoControl:
	            	autoState=OFF;
	            	break;
	            default:if(recordState==ON)
	            		{Record(msg.what);}
	            		break;
	            }
		}
    };
}
