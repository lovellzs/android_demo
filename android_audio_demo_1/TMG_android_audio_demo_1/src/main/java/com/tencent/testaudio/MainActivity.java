package com.tencent.testaudio;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.TMG.ITMGContext;
import com.tencent.av.sdk.AVError;
import com.tencent.av.sig.AuthBuffer;


public class MainActivity extends Activity implements View.OnClickListener, TMGDispatcherBase{
	private static final String TAG = "MainActivity";
	EditText mEditAppID = null;
	EditText mEditAccountType = null;
	EditText mEditKey = null;
	EditText mEditUserID = null;
	EditText mEditRoomID = null;
	Button mBtnLogin = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		mEditAppID = (EditText)findViewById(R.id.edit_app_id);
		mEditAppID.setText("1400035750");

		mEditAccountType = (EditText)findViewById(R.id.edit_account_type);
		mEditAccountType.setText("14181");

		mEditKey = (EditText)findViewById(R.id.edit_key);
		mEditKey.setText("5d3728db75adf458");

		mEditUserID = (EditText)findViewById(R.id.edit_user_id);
		mEditUserID.setText(System.currentTimeMillis()%1000000 + "");


		mEditRoomID = (EditText)findViewById(R.id.edit_user_roomid);
		mEditRoomID.setText("201710");

		mBtnLogin = (Button)findViewById(R.id.btn_login);
		mBtnLogin.setOnClickListener(this);
    }



	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.btn_login:
			Log.i(TAG, "start context...");

			//接入步骤1：获取相关信息，由腾讯云申请， RoomID为大于等于6位的整数
			//AppVersion 为客户端的版本信息，查Log， 查BUG有用
			String sdkAppId = mEditAppID.getText().toString();
			String accountType = mEditAccountType.getText().toString();
			String key = mEditKey.getText().toString();
			String identifier = mEditUserID.getText().toString();
			String strRoomID = mEditRoomID.getText().toString();
			String appVersion = "demo_1_1";

			//接入步骤2：用户自己实现回调分发函数
			TMGCallbackDispatcher.getInstance().AddDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENTER_ROOM, this);

			//接入步骤3：将自己实现的回调函数注册给SDK
			ITMGContext.GetInstance(this).SetTMGDelegate(TMGCallbackDispatcher.getInstance().getItmgDelegate());

			//接入步骤4：将步骤1中的信息设置到SDK中
			ITMGContext.GetInstance(this).SetAppInfo(sdkAppId, accountType, identifier);
			ITMGContext.GetInstance(this).SetAppVersion(appVersion);

			//接入步骤5：生成AuthBuffer，鉴权秘钥
			long nExpUTCTime = 1800 + System.currentTimeMillis() / 1000L;
			byte[] authBuffer =  AuthBuffer.getInstance().genAuthBuffer(Integer.parseInt(sdkAppId), Integer.parseInt(strRoomID),
					identifier, Integer.parseInt(accountType), key, (int)nExpUTCTime, (int) ITMGContext.ITMG_AUTH_BITS_DEFAULT);

			//接入步骤6：用生成的秘钥进房， 会收到ITMG_MAIN_EVENT_TYPE_ENTER_ROOM的回调， 标识进房成功
			ITMGContext.GetInstance(this).EnterRoom(Integer.parseInt(strRoomID), "user", authBuffer);
			mBtnLogin.setText("Login...");
			break;
		}
		
	}
    
    @Override
    protected void onDestroy(){
		//接入步骤8：降低内存， 将无用的监听事件清理出去（看需求是否清除，业务逻辑判定）
		TMGCallbackDispatcher.getInstance().RemoveDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENTER_ROOM, this);
    	super.onDestroy();
    }

	@Override
	public void OnEvent(ITMGContext.ITMG_MAIN_EVENT_TYPE type, Intent data) {
		int nErrCode = TMGCallbackHelper.ParseIntentParams2(data).nErrCode;
		String strMsg = TMGCallbackHelper.ParseIntentParams2(data).strErrMsg;

		//接入步骤7：收到进房信令， 进房成功， 可以操作设备， 参看RoomActivity
		if (nErrCode == AVError.AV_OK)
		{
			Intent intent = new Intent(MainActivity.this, RoomActivity.class);
			MainActivity.this.startActivity(intent);
			finish();
		}
		else
		{
			Toast.makeText(MainActivity.this, String.format("result=%d, errorInfo=%s", nErrCode, strMsg), Toast.LENGTH_SHORT).show();
		}
	}
}
