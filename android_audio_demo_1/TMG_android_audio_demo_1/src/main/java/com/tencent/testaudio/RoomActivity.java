package com.tencent.testaudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TMG.ITMGAudioCtrl;
import com.tencent.TMG.ITMGContext;
import com.tencent.av.sdk.AVError;

import java.util.HashMap;
import java.util.Map;

public class RoomActivity extends Activity  implements View.OnClickListener,CheckBox.OnCheckedChangeListener, TMGDispatcherBase{
    private static final String TAG = "RoomActivity";


    private TextView mTextInfo = null;
    Map<String, Integer> mUserInfo = new HashMap<String, Integer>();
    public static final int STATE_NONE  = 0x00000000;
    public static final int STATE_AUDIO = 0x00000001;
    public static final int STATE_VIDEO = 0x00000020;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        findViewById(R.id.btn_exit_room).setOnClickListener(this);
        mTextInfo = (TextView) findViewById(R.id.text_info);

        CheckBox checkBoxMic = (CheckBox)findViewById(R.id.checkbox_mic);
        CheckBox checkBoxSpeaker = (CheckBox)findViewById(R.id.checkbox_speaker);

        int micState = ITMGContext.GetInstance(this).GetAudioCtrl().GetMicState();
        int speakerState = ITMGContext.GetInstance(this).GetAudioCtrl().GetSpeakerState();
        checkBoxMic.setChecked(micState == 1 || micState == 2);
        checkBoxSpeaker.setChecked(speakerState == 1 || speakerState == 2);

        checkBoxMic.setOnCheckedChangeListener(this);
        checkBoxSpeaker.setOnCheckedChangeListener(this);

        //
        // 添加事件回调
        //
        //接入步骤9：注意监听SDK事件
        TMGCallbackDispatcher.getInstance().AddDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_USER_UPDATE, this);
        TMGCallbackDispatcher.getInstance().AddDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_EXIT_ROOM, this);
        TMGCallbackDispatcher.getInstance().AddDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT, this);
        TMGCallbackDispatcher.getInstance().AddDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENABLE_MIC, this);
        TMGCallbackDispatcher.getInstance().AddDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_DISABLE_MIC, this);
        TMGCallbackDispatcher.getInstance().AddDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_DISABLE_SPEAKER, this);
        TMGCallbackDispatcher.getInstance().AddDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENABLE_SPEAKER, this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_exit_room:
                Log.i(TAG, "exit room ...");
                //接入步骤14： 发送退房事件
                ITMGContext.GetInstance(this).ExitRoom();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ITMGContext.GetInstance(this).ExitRoom();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void finish(){
        mUserInfo.clear();
        super.finish();
    }

    @Override
    protected void onDestroy(){
        //清空回调函数
        //接入步骤16： 清空多余事件，保证内存， 完成一次通话以及结束事件。回到原始状态
        TMGCallbackDispatcher.getInstance().RemoveDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_USER_UPDATE, this);
        TMGCallbackDispatcher.getInstance().RemoveDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_EXIT_ROOM, this);
        TMGCallbackDispatcher.getInstance().RemoveDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ROOM_DISCONNECT, this);
        TMGCallbackDispatcher.getInstance().RemoveDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENABLE_MIC, this);
        TMGCallbackDispatcher.getInstance().RemoveDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_DISABLE_MIC, this);
        TMGCallbackDispatcher.getInstance().RemoveDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_DISABLE_SPEAKER, this);
        TMGCallbackDispatcher.getInstance().RemoveDelegate(ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENABLE_SPEAKER, this);

        super.onDestroy();
    }

    private void updateUserInfo(){
        String info = "Member:";

        for (Map.Entry<String, Integer> entry : mUserInfo.entrySet()){
            String state = "";
            if ((entry.getValue() & STATE_AUDIO) != 0){
                state += "a";
            }
            if ((entry.getValue() & STATE_VIDEO) != 0) {
                if (!state.equals("")) {
                    state += "&v";
                } else {
                    state = "v";
                }
            }
            info += String.format("%s(%s);",entry.getKey(), state);
        }

        mTextInfo.setText(info);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.checkbox_mic:
                //接入步骤9：开启麦克风，会收到ITMG_MAIN_EVENT_TYPE_ENABLE_MIC， ITMG_MAIN_EVENT_TYPE_DISABLE_MIC事件
                ITMGContext.GetInstance(this).GetAudioCtrl().EnableMic(isChecked);
                break;
            case R.id.checkbox_speaker:
                //接入步骤12：开启扬声器
                ITMGContext.GetInstance(this).GetAudioCtrl().EnableSpeaker(isChecked);
                break;
        }
    }

    @Override
    public void OnEvent(ITMGContext.ITMG_MAIN_EVENT_TYPE type, Intent data) {
        if (ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVNET_TYPE_USER_UPDATE == type)
        {
            //接入步骤11： 步骤9开了自己的音频， 会收到成员状态变化状态信息
            TMGCallbackHelper.ParamsUerInfo info = TMGCallbackHelper.ParseUserInfoUpdateInfoIntent(data);
            for (String id :info.identifierList)
            {
                Log.i(TAG, String.format("onEndpointsUpdateInfo|eventid=%d, id=%s", info.nEventID, id));
                int state = STATE_NONE;
                if (mUserInfo.containsKey(id)){
                    state = mUserInfo.get(id);
                }

                switch (info.nEventID){
                    case 3:
                        state |= STATE_VIDEO;
                        break;
                    case 4:
                        state &= (~STATE_VIDEO);
                        break;
                    case 5:
                        state |= STATE_AUDIO;
                        break;
                    case 6:
                        state &= (~STATE_AUDIO);
                        break;
                }

                if (state != STATE_NONE){
                    mUserInfo.put(id, state);
                }else if(mUserInfo.containsKey(id)){
                    mUserInfo.remove(id);
                }

                updateUserInfo();
            }
        }
        else if (ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENABLE_MIC == type)
        {
            //接入步骤10： 收到mic事件
            TMGCallbackHelper.ParamsAudioDeviceInfo info = TMGCallbackHelper.ParseAudioDeviceInfoIntent(data);
            if (info.nErrCode == AVError.AV_OK)
            {
                Toast.makeText(this, String.format("打开麦克风成功!"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, String.format("打开麦克风失败!, ErrorCode=%d", info.nErrCode), Toast.LENGTH_SHORT).show();
            }
        }
        else if (ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_DISABLE_MIC == type)
        {
            //接入步骤10： 收到mic事件
            TMGCallbackHelper.ParamsAudioDeviceInfo info = TMGCallbackHelper.ParseAudioDeviceInfoIntent(data);
            if (info.nErrCode == AVError.AV_OK)
            {
                Toast.makeText(this, String.format("关闭麦克风成功!"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, String.format("关闭麦克风失败!, ErrorCode=%d", info.nErrCode), Toast.LENGTH_SHORT).show();
            }
        }
        else if (ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_ENABLE_SPEAKER == type)
        {
            //接入步骤13： 收到扬声器事件
            TMGCallbackHelper.ParamsAudioDeviceInfo info = TMGCallbackHelper.ParseAudioDeviceInfoIntent(data);
            if (info.nErrCode == AVError.AV_OK)
            {
                Toast.makeText(this, String.format("开启扬声器成功!"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, String.format("开启扬声器失败!, ErrorCode=%d", info.nErrCode), Toast.LENGTH_SHORT).show();
            }
        }
        else if (ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_DISABLE_SPEAKER == type)
        {
            //接入步骤13： 收到扬声器事件
            TMGCallbackHelper.ParamsAudioDeviceInfo info = TMGCallbackHelper.ParseAudioDeviceInfoIntent(data);
            if (info.nErrCode == AVError.AV_OK)
            {
                Toast.makeText(this, String.format("关闭扬声器成功!"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, String.format("关闭扬声器失败!, ErrorCode=%d", info.nErrCode), Toast.LENGTH_SHORT).show();
            }
        }
        else if (ITMGContext.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_EXIT_ROOM == type)
        {
            //接入步骤15： 收到退房成功事件
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            finish();
        }
    }
}
