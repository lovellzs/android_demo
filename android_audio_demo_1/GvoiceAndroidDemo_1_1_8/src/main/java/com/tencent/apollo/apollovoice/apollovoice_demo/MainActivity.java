package com.tencent.apollo.apollovoice.apollovoice_demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.gcloud.voice.GCloudVoiceEngine;
import com.tencent.gcloud.voice.IGCloudVoiceNotify;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // 加载库文件
    static {
        System.loadLibrary("GCloudVoice");
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Important
        GCloudVoiceEngine.getInstance().init(getApplicationContext(), this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private static String appID = "gcloud.test";
        private static String appKey = "test_key";
        private static String openID = Long.toString(System.currentTimeMillis());


        private static TextView _logTV = null;
        private static TextView _logTVSvrInfo = null;
        private static TextView _logTV_OffLine = null;

        private static String _recordingPath;
        private static String _downloadPath;
        private static String _fileID;
        private static boolean _bEngineInit = false;

        private static GCloudVoiceEngine engine = null;
        private static String _roomName = "cz-test";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            if (!_bEngineInit) {
                _bEngineInit = true;
                engine = GCloudVoiceEngine.getInstance();
                engine.SetAppInfo(appID, appKey, openID);
                engine.Init();
                engine.SetMode(0);

                Notify notify = new Notify();
                engine.SetNotify(notify);


                //timer to poll
                TimerTask task = new TimerTask() {
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                };

                File recordingFile = new File(this.getContext().getFilesDir(), "recording.dat");
                File downloadFile = new File(this.getContext().getFilesDir(), "download.dat");
                _recordingPath = recordingFile.getAbsolutePath();
                _downloadPath = downloadFile.getAbsolutePath();

                Timer timer = new Timer(true);
                timer.schedule(task, 500, 500);

            }
            View rootView = null;
            int index = getArguments().getInt(ARG_SECTION_NUMBER);
            if (index == 1)//realtimevoice
            {
                rootView = inflater.inflate(R.layout.activity_real_time_voice, container, false);
                InitRealTimeView(rootView);
            } else if (index == 2) //offline voice
            {
                rootView = inflater.inflate(R.layout.activity_offlinevoice, container, false);
                InitOffLineView(rootView);
            } /*else if (index == 3) {
                rootView = inflater.inflate(R.layout.activity_getsvrinfo, container, false);
                InitSvrInfoView(rootView);
            }*/

            return rootView;
        }

        private void InitRealTimeView(View view) {
            if (view == null) {
                android.util.Log.i("tyc", "realtime view is null");
                return;
            }
            Button btnJoinRoom = (Button) view.findViewById(R.id.btn_joinroom);
            btnJoinRoom.setOnClickListener(mBtnOnClick);
            Button btnCloseMic = (Button) view.findViewById(R.id.btn_closemic);
            btnCloseMic.setOnClickListener(mBtnOnClick);
            Button btnCloseSpeaker = (Button) view.findViewById(R.id.btn_closespeaker);
            btnCloseSpeaker.setOnClickListener(mBtnOnClick);
            Button btnOpenMic = (Button) view.findViewById(R.id.btn_openmic);
            btnOpenMic.setOnClickListener(mBtnOnClick);
            Button btnOpenSpeaker = (Button) view.findViewById(R.id.btn_openspeaker);
            btnOpenSpeaker.setOnClickListener(mBtnOnClick);
            Button btnQuitRoom = (Button) view.findViewById(R.id.btn_quitroom);
            btnQuitRoom.setOnClickListener(mBtnOnClick);

            Button btnTestApi = (Button) view.findViewById(R.id.btn_testapi);
            btnTestApi.setOnClickListener(mBtnOnClick);

            _logTV = (TextView) view.findViewById(R.id.tv_log);
        }

        private void InitOffLineView(View view) {

            Button btn;
            btn = (Button) view.findViewById(R.id.btn_ApplyMsgKey);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_SetSttMode);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_STT);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_StartRec);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_StopRecord);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_UploadVoice);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_DownLoadVoice);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_PlayVoice);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_StopPlay);
            btn.setOnClickListener(mOffLineBtnClick);
            btn = (Button) view.findViewById(R.id.btn_testinterface);
            btn.setOnClickListener(mOffLineBtnClick);

            _logTV_OffLine = (TextView) view.findViewById(R.id.tv_offline_log);

        }

        private void InitSvrInfoView(View view) {

            Button btnGetRoomInfo = (Button) view.findViewById(R.id.btn_getroominfo);
            btnGetRoomInfo.setOnClickListener(mBtnOnClick);

            _logTVSvrInfo = (TextView) view.findViewById(R.id.tv_svrinfo_log);
        }

        private View.OnClickListener mBtnOnClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int resId = v.getId();
                if (resId == R.id.btn_joinroom) {
                    _logTV.setText("_joinRoomBtnID\n");
                    int ret = engine.JoinTeamRoom(_roomName, 10000);
                    _logTV.setText("Join room with " + ret);
                } else if (resId == R.id.btn_quitroom) {
                    _logTV.setText("_quitRoomBtnID");
                    int ret = engine.QuitRoom(_roomName, 10000);
                    _logTV.setText("QuitRoom with " + ret);
                } else if (resId == R.id.btn_openmic) {
                    _logTV.setText("_openMicBtnID");
                    int ret = engine.OpenMic();
                    _logTV.setText("OpenMic with " + ret);
                } else if (resId == R.id.btn_openspeaker) {
                    _logTV.setText("_openSpeakerBtnID");
                    int ret = engine.OpenSpeaker();
                    _logTV.setText("OpenSpeaker with " + ret);
                } else if (resId == R.id.btn_closemic) {
                    _logTV.setText("_closemicbtnid");
                    int ret = engine.CloseMic();
                    _logTV.setText("CloseMic with " + ret);
                } else if (resId == R.id.btn_closespeaker) {
                    _logTV.setText("_closeSpeakerBtnID");
                    int ret = engine.CloseSpeaker();
                    _logTV.setText("CloseSpeaker with " + ret);
                } else if (resId == R.id.btn_getroominfo) {
                } else if (resId == R.id.btn_testapi) {
                    int ret = engine.GetMicLevel();
                    int ret2 = engine.Invoke(5004, 8000, 0, null);
                    _logTV.setText("GetMicLevel ret = " + ret + " invoke ret2 = " + ret2);

                }
            }
        };

        private View.OnClickListener mOffLineBtnClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int resId = v.getId();
                int ret;
                String TAG = "[OffLine Voice]";
                if (resId == R.id.btn_ApplyMsgKey) {
                    _logTV_OffLine.setText("_ApplyMsgKey");
                    ret = engine.ApplyMessageKey(10000);
                    _logTV_OffLine.setText("ApplyMessageKey with " + ret);
                } else if (resId == R.id.btn_SetSttMode) {
                    _logTV_OffLine.setText("set mode to STT");
                    ret = engine.SetMode(2);
                    _logTV_OffLine.setText("set mode to STT with result: " + ret);
                } else if (resId == R.id.btn_STT) {
                    _logTV_OffLine.setText("start speech to text");
                    ret = engine.SpeechToText(_fileID, 10000, 0);
                    _logTV_OffLine.setText("SpeechToText with result: " + ret);
                } else if (resId == R.id.btn_StartRec) {
                    _logTV_OffLine.setText("_startRecordBtnID");
                    ret = engine.StartRecording(_recordingPath);
                    Log.i(TAG, "StartRecording with" + ret);
                    _logTV_OffLine.setText("_startRecordBtnID return " + ret);
                } else if (resId == R.id.btn_StopRecord) {
                    _logTV_OffLine.setText("_stopRecordBtnID");
                    ret = engine.StopRecording();
                    Log.i(TAG, "StopRecording with" + ret);
                    _logTV_OffLine.setText("_stopRecordBtnID return " + ret);
                } else if (resId == R.id.btn_UploadVoice) {
                    _logTV_OffLine.setText("_sendVoiceBtnID");
                    ret = engine.UploadRecordedFile(_recordingPath, 10000);
                    Log.i(TAG, "UploadRecordedFile with" + ret);
                    _logTV_OffLine.setText("_sendVoiceBtnID return " + ret);
                } else if (resId == R.id.btn_DownLoadVoice) {
                    _logTV_OffLine.setText("_downloadVoiceBtnID wit fid " + _fileID);

                    ret = engine.DownloadRecordedFile(_fileID, _downloadPath, 5000);
                    Log.i(TAG, "DownloadRecordedFile with" + ret + " and fid " + _fileID);
                    _logTV_OffLine.setText("DownloadRecordedFile return " + ret + "  fileid:" + _fileID);
                } else if (resId == R.id.btn_PlayVoice) {
                    _logTV_OffLine.setText("_playVoiceBtnID");
                    ret = engine.PlayRecordedFile(_downloadPath);
                    Log.i(TAG, "PlayRecordedFile with" + ret);
                    _logTV_OffLine.setText("PlayRecordedFile return " + ret);
                } else if (resId == R.id.btn_StopPlay) {
                    _logTV_OffLine.setText("_stopPlayBtnID");
                    ret = engine.StopPlayFile();
                    Log.i(TAG, "StopPlayFile with" + ret);
                    _logTV_OffLine.setText("StopPlayFile return " + ret);
                } else if (resId == R.id.btn_testinterface) {
                    _logTV_OffLine.setText("_testinterface");
                    Integer bytes = new Integer(12);
                    Float seconds = new Float(0.1f);
                    ret = engine.GetFileParam(_recordingPath, bytes, seconds);
                    Log.i(TAG, "GetFileParam with" + ret);
                    _logTV_OffLine.setText("1filepath:" + _recordingPath + " getfile param bytes:" + bytes + " seconds:" + seconds);
                }
            }
        };

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                String TAG = "[API]";
                switch (msg.what) {
                    case 1:
                        engine.Poll();
                        break;

                    case 2: // joinroom
                        String key = (String) msg.obj;
                        //int ret = GcloudVoiceEngine.getInstance().joinRoom(key, 5000);
                        Log.i(TAG, "Get roominfo from svr , roominfo=" + key);
                        if (_logTVSvrInfo != null) {
                            _logTVSvrInfo.setText("Get roominfo from svr success , roominfo=" + key);
                        }
                        break;
                    case 3:
                        String keyData = (String) msg.obj;
                        Log.i(TAG, "Get roominfo from svr, roominfo=  " + keyData);
                        if (_logTVSvrInfo != null) {
                            _logTVSvrInfo.setText("Get roominfo from svr Error with  " + keyData);
                        }
                        break;
                    case 4:
                        String authkey = (String) msg.obj;
                        Log.i(TAG, "Get AuthKey from svr, authkey=  " + authkey);
                        if (_logTV_OffLine != null) {
                            _logTV_OffLine.setText("Get authkey from svr, authkey:" + authkey);
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };

        class Notify implements IGCloudVoiceNotify {
            /*
            String TAG = "[cz]";
            @Override
            public void onJoinRoomComplete(int code) {
                // TODO Auto-generated method stub
                Log.i(TAG, "onJoinRoomComplete:" + code);
                _logTV.setText( "onJoinRoomComplete with "+ code);
            }*/
            public final String tag = "GCloudVoiceNotify";

            @Override
            public void OnJoinRoom(int code, String roomName, int memberID) {
                Log.i(tag, "OnJoinRoom CallBack code=" + code + " roomname:" + roomName + " memberID:" + memberID);
                _logTV.setText("OnJoinRoom CallBack code=" + code + " roomname:" + roomName + " memberID:" + memberID);
            }

            @Override
            public void OnStatusUpdate(int status, String roomName, int memberID) {
                Log.i(tag, "OnStatusUpdate CallBack code=" + status + " roomname:" + roomName + " memberID:" + memberID);
                _logTV.setText("OnStatusUpdate CallBack code=" + status + " roomname:" + roomName + " memberID:" + memberID);
            }

            @Override
            public void OnQuitRoom(int code, String roomName) {
                Log.i(tag, "OnQuitRoom CallBack code=" + code + " roomname:" + roomName);
                _logTV.setText("OnQuitRoom CallBack code=" + code + " roomname:" + roomName);
            }

            @Override
            public void OnMemberVoice(int[] members, int count) {
                Log.i(tag, "OnMemberVoice CallBack " + "count:" + count);
                String str = "OnMemberVoice Callback ";
                for (int i = 0; i < count; ++i) {
                    str += " memberid:" + members[2 * i];
                    str += " state:" + members[2 * i + 1];
                }
                _logTV.setText("OnMemberVoice CallBack " + "count:" + count);
            }

            @Override
            public void OnUploadFile(int code, String filePath, String fileID) {
                Log.i(tag, "OnUploadFile CallBack code=" + code + " filePath:" + filePath + " fileID:" + fileID);
                _fileID = fileID;
                _logTV_OffLine.setText("OnUploadFile CallBack code=" + code + " filePath:" + filePath + " fileID:" + fileID);
            }

            @Override
            public void OnDownloadFile(int code, String filePath, String fileID) {
                Log.i(tag, "OnDownloadFile CallBack code=" + code + " filePath:" + filePath + " fileID:" + fileID);
                _logTV_OffLine.setText("OnDownloadFile CallBack code=" + code + " filePath:" + filePath + " fileID:" + fileID);
            }

            @Override
            public void OnPlayRecordedFile(int code, String filePath) {
                Log.i(tag, "OnPlayRecordedFile CallBack code=" + code + " filePath:" + filePath);
                _logTV_OffLine.setText("OnPlayRecordedFile CallBack code=" + code + " filePath:" + filePath);
            }

            @Override
            public void OnApplyMessageKey(int code) {
                Log.i(tag, "OnApplyMessageKey CallBack code=" + code);
                _logTV_OffLine.setText("OnApplyMessageKey CallBack code=" + code);
            }

            @Override
            public void OnSpeechToText(int code, String fileID, String result) {
                Log.i(tag, "OnSpeechToText CallBack code=" + code + " fileID:" + fileID + " result:" + result);
                _logTV_OffLine.setText("OnSpeechToText CallBack code=" + code + " fileID:" + fileID + " result:" + result);
            }

            @Override
            public void OnRecording(char[] pAudioData, int nDataLength) {
                Log.i(tag, "OnRecording CallBack  nDataLength:" + nDataLength);
            }

            @Override
            public void OnStreamSpeechToText(int i, int i1, String s) {
                Log.i(tag, "OnStreamSpeechToText CallBack  result:" + s);
            }
        }
    }

    public static int smode = 3;

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (position == 0) {
                if (smode != position) {
                    GCloudVoiceEngine.getInstance().SetMode(0);
                    smode = position;
                    Log.i("CZ", "setmode 0");
                }
            } else if (position == 1) {
                if (smode != position) {
                    GCloudVoiceEngine.getInstance().SetMode(1);
                    smode = position;
                    Log.i("CZ", "semode 1");
                }
            }
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position;
            Log.i("CZ", "position is:" + Integer.toString(pos));
            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            //return 3;
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "RealTime Voice";
                case 1:
                    return "OffLine Voice";
                //case 2:
                //return "Svr Action";
            }
            return null;
        }
    }

}
