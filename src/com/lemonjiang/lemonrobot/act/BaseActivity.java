package com.lemonjiang.lemonrobot.act;

import com.lemonjiang.common.Common;
import com.lemonjiang.lemonrobot.app.ActivityManager;
import com.lemonjiang.util.NetUtil;
import com.lemonjiang.view.RoundProgressDialog;
import com.lemonjiang.view.RoundToast;
import com.lemonjiang.view.RoundToastDialog;

import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 基类
 * 
 * @author Lemon Jiang
 * 
 */
public class BaseActivity extends FragmentActivity {
	// 提示框
	public RoundToast mRoundToast;
	// 提示对话框
	public RoundToastDialog mRoundToastDialog;
	// 进度框
	public RoundProgressDialog mRoundProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().onActivityCreate(this);
	}

	@Override
	protected void onDestroy() {
		ActivityManager.getInstance().onActivityDestroy(this);
		// 隐藏提示框
		if (mRoundToast != null) {
			mRoundToast.cancel();
		}
		if (mRoundToastDialog != null) {
			mRoundToastDialog.cancel();
		}
		super.onDestroy();
	}
	
	/**
	 * 显示提示信息
	 * 
	 * @param msg
	 */
	public void showMessageByRoundToast(String msg) {
		mRoundToast = Common.ShowMessageByRoundToast(BaseActivity.this,
				mRoundToast, msg);
	}
	
	/**
	 * 显示提示对话框
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		try {
			mRoundToastDialog = RoundToastDialog.getInstance(this, msg, false,
					null);
			if (!mRoundToastDialog.isShowing()) {
				mRoundToastDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void showToast(int id) {
		showToast(getResources().getString(id));
	}
	
	/**
	 * 显示提示对话框
	 * 
	 * @param msg
	 * @param cancelListener
	 */
	public void showToast(String msg, OnCancelListener cancelListener) {
		try {
			mRoundToastDialog = RoundToastDialog.getInstance(this, msg, true,
					cancelListener);
			if (!mRoundToastDialog.isShowing()) {
				mRoundToastDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showLoading() {
		showLoading("");
	}
	
	/**
	 * 显示进度对话框
	 * 
	 * @param msg
	 */
	public void showLoading(String msg) {
		try {
			if (mRoundProgressDialog == null) {
				mRoundProgressDialog = RoundProgressDialog.getInstance(this,
						msg, false, null);
			} else {
				mRoundProgressDialog.setMessage(msg);
			}
			if (!mRoundProgressDialog.isShowing()) {
				mRoundProgressDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 初始化进度对话框
	 * 
	 * @param msg
	 */
	public void initProgressDialog(String msg) {
		if (mRoundProgressDialog == null) {
			mRoundProgressDialog = RoundProgressDialog.getInstance(this, msg,
					false, null);
		}
	}
	
	/**
	 * 显示进度对话框
	 * 
	 * @param msg
	 * @param cancelListener
	 */
	public void showLoading(String msg, OnCancelListener cancelListener) {
		if (mRoundProgressDialog == null) {
			mRoundProgressDialog = RoundProgressDialog.getInstance(this, msg,
					true, cancelListener);
		} else {
			mRoundProgressDialog.setMessage(msg);
		}
		if (!mRoundProgressDialog.isShowing()) {
			mRoundProgressDialog.show();
		}
	}
	
	/**
	 * 取消提示对话框
	 */
	public void hideToast() {
		if (mRoundToastDialog != null && mRoundToastDialog.isShowing()) {
			mRoundToastDialog.cancel();
		}
	}
	
	/**
	 * 取消进度对话框
	 */
	public void cancelLoading() {
		if (mRoundProgressDialog != null && mRoundProgressDialog.isShowing()) {
			mRoundProgressDialog.cancel();
		}
	}
	
	public void showNetworNotAvailable() {
		showToast("无法连接到网络，请您稍后再试。");
	}

	public boolean isNetworkAvailable() {
		if (NetUtil.isAvailable(this)) {
			return true;
		} else {
			showNetworNotAvailable();
			return false;
		}
	}

	public boolean isNetworkAvailableNoToast() {
		if (NetUtil.isAvailable(this)) {
			return true;
		} else {
			return false;
		}

	}

	public void showToast(int id, OnCancelListener l) {
		showToast(getResources().getString(id), l);
	}
}
