package com.lemonjiang.lemonrobot.app;

import java.util.Stack;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lemonjiang.lemonrobot.act.BaseActivity;
import com.lemonjiang.lemonrobot.act.MainActivity;

/**
 * 类的堆栈管理器
 * @author Lemon Jiang
 *
 */
public class ActivityManager {
	private static ActivityManager mInstance = null;
	private Stack<BaseActivity> mActivitys = null;
	private ActivityManager(){
		init();
	}
	private void init(){
		mActivitys = new Stack<BaseActivity>();
	}
	
	public static ActivityManager getInstance(){
		if(null == mInstance){
			mInstance = new ActivityManager();
		}
		return mInstance;
	}
	//向栈中添加
	public void onActivityCreate(BaseActivity act){
		mActivitys.add(act);
	}
	//从栈中移出
	public void onActivityDestroy(BaseActivity act){
		mActivitys.remove(act);
	}
	//peek查看堆栈顶部的对象，但不从堆栈中移除它。
	public BaseActivity getTopActivity(){
		return mActivitys.peek();
	}
	//pop移除堆栈顶部的对象，并作为此函数的值返回该对象。
	public void goBackToHome(){
		while(!mActivitys.isEmpty() &&!(mActivitys.peek() instanceof MainActivity)){
			mActivitys.pop().finish();
		}
	}
	
	public void goBackTo(Class c){
		while(!mActivitys.isEmpty() &&!(mActivitys.peek().getClass() == c)){
			mActivitys.pop().finish();
		}
	}
	//这个方法是用来调用已经存在于堆栈中的某个类，并对其做类似finish等相关操作时调用的(比如微信支付成功之后，用来关闭之前的支付页面)。
	public BaseActivity getActivity(Class c){
		Log.d("wxPay", " getActivity " + c.getName());
		for(int i = mActivitys.size() - 1; i >= 0 ; i --){
			if(mActivitys.get(i).getClass() == c){
				Log.d("wxPay", " getActivity succeed");
				return mActivitys.get(i);
			}
		}
		
		return null;
	}
	//这个方法主要用于从service中启动activity之类的非正常启动activity的方法（不是act跳转）时要加FLAG_ACTIVITY_NEW_TASK，
	//比如Jpush推送捕获异常或者Jpush推送消息为空的时候，给予这种处理。
	public void goBackToHomeOrNoAction(Context context){
		if(mActivitys.size()>0){
			return ;
		}else{
			Intent in=new Intent(context,MainActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
			context.startActivity(in);
		}
	}
}
