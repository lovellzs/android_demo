
#include <jni.h>
#include "agora_api.h"

#include <android/log.h>

using namespace agora_sdk;

static IAgoraAPI * agora = NULL;
static jobject java_callback = NULL;
static JNIEnv* java_env = NULL;
static JavaVM *g_jvm = NULL;

#define LOG(...) __android_log_print(ANDROID_LOG_DEBUG, "sdk2",__VA_ARGS__)
//#define LOG(...) ;

jmethodID g_events_ids[37];


class AttachThreadScoped {
 public:
  explicit AttachThreadScoped(JavaVM* jvm)
      : attached_(false), jvm_(jvm), env_(NULL) {
    jint ret_val = jvm->GetEnv(reinterpret_cast<void**>(&env_),
                               JNI_VERSION_1_6);
    //LOG("jvm->GetEnv = %d", ret_val);
    if (ret_val == JNI_EDETACHED) {
      // Attach the thread to the Java VM.
      ret_val = jvm_->AttachCurrentThread(&env_, NULL);
      LOG("AttachCurrentThread = %d", ret_val);
      attached_ = ret_val >= 0;
      //assert(attached_);
    }
  }
  ~AttachThreadScoped() {
    if (attached_ && (jvm_->DetachCurrentThread() < 0)) {
      //assert(false);
    }
  }

  JNIEnv* env() { return env_; }

 private:
  bool attached_;
  JavaVM* jvm_;
  JNIEnv* env_;
};


extern "C" jint JNICALL JNI_OnLoad(JavaVM* jvm, void* reserved) 
{
    //g_jvm = jvm;
    return JNI_VERSION_1_6;
}

extern "C" void JNICALL JNI_OnUnload(JavaVM* jvm, void* reserved) 
{
    //MediaMgr::getInstance()->destroy(NULL);
}

static jstring cstring2javautf8(JNIEnv *env, std::string const &s){
    // new bytes[]

    jbyteArray bytes = env->NewByteArray(s.size()); 
    //LOG("11"); 
    if (bytes==NULL) return NULL;
    env->SetByteArrayRegion(bytes, 0, s.size(), (jbyte*)s.data());  

    // utf-8
    jchar encoding_name[] = { 'U', 'T', 'F', '-', '8', ' ' };  
    jstring encoding = env->NewString(encoding_name, 5);  
    //LOG("22");
    if (encoding==NULL){
        (env)->DeleteLocalRef(bytes);
        return NULL;
    } 

    // new string
    jclass theStringClass = env->FindClass("java/lang/String"); 
    //LOG("33"); 
    if (theStringClass==NULL){
        (env)->DeleteLocalRef(bytes);
        (env)->DeleteLocalRef(encoding);
        return NULL;
    }

    jmethodID mid = env->GetMethodID(theStringClass, "<init>", "([BLjava/lang/String;)V");  
    //LOG("44");
    if (mid==NULL) {
        (env)->DeleteLocalRef(bytes);
        (env)->DeleteLocalRef(encoding);
        (env)->DeleteLocalRef(theStringClass);
        return NULL;
    }

    jobject result = env->NewObject(theStringClass, mid, bytes, encoding);  
    (env)->DeleteLocalRef(bytes);
    (env)->DeleteLocalRef(encoding);
    (env)->DeleteLocalRef(theStringClass);

    //LOG("55");
    return (jstring)result;  
}

static uint32_t g_uid;

class CallBack : public ICallBack{

        virtual void onReconnecting(uint32_t nretry){
            
            //LOG("onReconnecting %p", java_env);
            //getEnv();
            //LOG("onReconnecting %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onReconnecting env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onReconnecting", 
                        "(I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onReconnecting env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[0], mid, g_events_ids[0]);
            if (java_callback!=NULL && java_env!=NULL){

                
                java_env->CallVoidMethod(java_callback,g_events_ids[0],nretry);
                
            }
        }
            
        virtual void onReconnected(int fd){
            
            //LOG("onReconnected %p", java_env);
            //getEnv();
            //LOG("onReconnected %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onReconnected env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onReconnected", 
                        "(I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onReconnected env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[1], mid, g_events_ids[1]);
            if (java_callback!=NULL && java_env!=NULL){

                
                java_env->CallVoidMethod(java_callback,g_events_ids[1],fd);
                
            }
        }
            
        virtual void onLoginSuccess(uint32_t uid,int fd){
            g_uid = uid;
            //LOG("onLoginSuccess %p", java_env);
            //getEnv();
            //LOG("onLoginSuccess %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onLoginSuccess env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onLoginSuccess", 
                        "(II)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onLoginSuccess env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[2], mid, g_events_ids[2]);
            if (java_callback!=NULL && java_env!=NULL){

                
                java_env->CallVoidMethod(java_callback,g_events_ids[2],uid,fd);
                
            }
        }
            
        virtual void onLogout(int ecode){
            
            //LOG("onLogout %p", java_env);
            //getEnv();
            //LOG("onLogout %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onLogout env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onLogout", 
                        "(I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onLogout env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[3], mid, g_events_ids[3]);
            if (java_callback!=NULL && java_env!=NULL){

                
                java_env->CallVoidMethod(java_callback,g_events_ids[3],ecode);
                
            }
        }
            
        virtual void onLoginFailed(int ecode){
            
            //LOG("onLoginFailed %p", java_env);
            //getEnv();
            //LOG("onLoginFailed %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onLoginFailed env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onLoginFailed", 
                        "(I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onLoginFailed env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[4], mid, g_events_ids[4]);
            if (java_callback!=NULL && java_env!=NULL){

                
                java_env->CallVoidMethod(java_callback,g_events_ids[4],ecode);
                
            }
        }
            
        virtual void onChannelJoined(std::string channelID){
            
            //LOG("onChannelJoined %p", java_env);
            //getEnv();
            //LOG("onChannelJoined %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onChannelJoined env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onChannelJoined", 
                        "(Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onChannelJoined env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[5], mid, g_events_ids[5]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);
                java_env->CallVoidMethod(java_callback,g_events_ids[5],_channelID);
                java_env->DeleteLocalRef(_channelID);
            }
        }
            
        virtual void onChannelJoinFailed(std::string channelID,int ecode){
            
            //LOG("onChannelJoinFailed %p", java_env);
            //getEnv();
            //LOG("onChannelJoinFailed %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onChannelJoinFailed env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onChannelJoinFailed", 
                        "(Ljava/lang/String;I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onChannelJoinFailed env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[6], mid, g_events_ids[6]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);
                java_env->CallVoidMethod(java_callback,g_events_ids[6],_channelID,ecode);
                java_env->DeleteLocalRef(_channelID);
            }
        }
            
        virtual void onChannelLeaved(std::string channelID,int ecode){
            
            //LOG("onChannelLeaved %p", java_env);
            //getEnv();
            //LOG("onChannelLeaved %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onChannelLeaved env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onChannelLeaved", 
                        "(Ljava/lang/String;I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onChannelLeaved env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[7], mid, g_events_ids[7]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);
                java_env->CallVoidMethod(java_callback,g_events_ids[7],_channelID,ecode);
                java_env->DeleteLocalRef(_channelID);
            }
        }
            
        virtual void onChannelUserJoined(std::string account,uint32_t uid){
            
            //LOG("onChannelUserJoined %p", java_env);
            //getEnv();
            //LOG("onChannelUserJoined %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onChannelUserJoined env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onChannelUserJoined", 
                        "(Ljava/lang/String;I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onChannelUserJoined env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[8], mid, g_events_ids[8]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _account = cstring2javautf8(java_env, account);
                java_env->CallVoidMethod(java_callback,g_events_ids[8],_account,uid);
                java_env->DeleteLocalRef(_account);
            }
        }
            
        virtual void onChannelUserLeaved(std::string account,uint32_t uid){
            
            //LOG("onChannelUserLeaved %p", java_env);
            //getEnv();
            //LOG("onChannelUserLeaved %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onChannelUserLeaved env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onChannelUserLeaved", 
                        "(Ljava/lang/String;I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onChannelUserLeaved env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[9], mid, g_events_ids[9]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _account = cstring2javautf8(java_env, account);
                java_env->CallVoidMethod(java_callback,g_events_ids[9],_account,uid);
                java_env->DeleteLocalRef(_account);
            }
        }
            
    virtual void onChannelUserList(int n, char **accounts, uint32_t* uids){
        //LOG("onChannelUserList");
        //AttachThreadScoped ats(g_jvm);
        if (java_callback!=NULL && java_env!=NULL){

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                    "onChannelUserList", 
                    "([Ljava/lang/String;[I)V"
                );
            java_env->DeleteLocalRef(cls);
            //if(!mid) return;  // whoops method does not exist

            jclass cls2 = java_env->FindClass("java/lang/String");
            jobjectArray _accounts = java_env->NewObjectArray(
                n, 
                cls2,
                0
            );
            java_env->DeleteLocalRef(cls2);
            
            jintArray _uids = java_env->NewIntArray(n);

            for(int i=0;i<n;i++){
                //LOG("onChannelUserList %d %s", i, accounts[i]);
                jstring account = java_env->NewStringUTF(accounts[i]);
                java_env->SetObjectArrayElement(_accounts, i, account);
                java_env->DeleteLocalRef(account);

                jboolean copy = false;

                //java_env->SetObjectArrayElement(_accounts, i, cstring2javautf8(java_env, accounts[i]));
                //java_env->GetIntArrayElements(_uids, &copy)[i] = uids[i];
            }
            java_env->SetIntArrayRegion(_uids, 0, n, (const jint*)uids);

            java_env->CallVoidMethod(java_callback,mid,_accounts,_uids);

            java_env->DeleteLocalRef(_accounts);
            java_env->DeleteLocalRef(_uids);
        }
    }
            
        virtual void onChannelQueryUserNumResult(std::string channelID,int ecode,int num){
            
            //LOG("onChannelQueryUserNumResult %p", java_env);
            //getEnv();
            //LOG("onChannelQueryUserNumResult %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onChannelQueryUserNumResult env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onChannelQueryUserNumResult", 
                        "(Ljava/lang/String;II)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onChannelQueryUserNumResult env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[11], mid, g_events_ids[11]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);
                java_env->CallVoidMethod(java_callback,g_events_ids[11],_channelID,ecode,num);
                java_env->DeleteLocalRef(_channelID);
            }
        }
            
        virtual void onChannelQueryUserIsIn(std::string channelID,std::string account,int isIn){
            
            //LOG("onChannelQueryUserIsIn %p", java_env);
            //getEnv();
            //LOG("onChannelQueryUserIsIn %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onChannelQueryUserIsIn env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onChannelQueryUserIsIn", 
                        "(Ljava/lang/String;Ljava/lang/String;I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onChannelQueryUserIsIn env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[12], mid, g_events_ids[12]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);
                java_env->CallVoidMethod(java_callback,g_events_ids[12],_channelID,_account,isIn);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);
            }
        }
            
        virtual void onChannelAttrUpdated(std::string channelID,std::string name,std::string value,std::string type){
            
            //LOG("onChannelAttrUpdated %p", java_env);
            //getEnv();
            //LOG("onChannelAttrUpdated %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onChannelAttrUpdated env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onChannelAttrUpdated", 
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onChannelAttrUpdated env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[13], mid, g_events_ids[13]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _name = cstring2javautf8(java_env, name);jstring _value = cstring2javautf8(java_env, value);jstring _type = cstring2javautf8(java_env, type);
                java_env->CallVoidMethod(java_callback,g_events_ids[13],_channelID,_name,_value,_type);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_name);java_env->DeleteLocalRef(_value);java_env->DeleteLocalRef(_type);
            }
        }
            
        virtual void onInviteReceived(std::string channelID,std::string account,uint32_t uid,std::string extra){
            
            //LOG("onInviteReceived %p", java_env);
            //getEnv();
            //LOG("onInviteReceived %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInviteReceived env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInviteReceived", 
                        "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInviteReceived env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[14], mid, g_events_ids[14]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);jstring _extra = cstring2javautf8(java_env, extra);
                java_env->CallVoidMethod(java_callback,g_events_ids[14],_channelID,_account,uid,_extra);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_extra);
            }
        }
            
        virtual void onInviteReceivedByPeer(std::string channelID,std::string account,uint32_t uid){
            
            //LOG("onInviteReceivedByPeer %p", java_env);
            //getEnv();
            //LOG("onInviteReceivedByPeer %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInviteReceivedByPeer env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInviteReceivedByPeer", 
                        "(Ljava/lang/String;Ljava/lang/String;I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInviteReceivedByPeer env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[15], mid, g_events_ids[15]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);
                java_env->CallVoidMethod(java_callback,g_events_ids[15],_channelID,_account,uid);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);
            }
        }
            
        virtual void onInviteAcceptedByPeer(std::string channelID,std::string account,uint32_t uid,std::string extra){
            
            //LOG("onInviteAcceptedByPeer %p", java_env);
            //getEnv();
            //LOG("onInviteAcceptedByPeer %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInviteAcceptedByPeer env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInviteAcceptedByPeer", 
                        "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInviteAcceptedByPeer env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[16], mid, g_events_ids[16]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);jstring _extra = cstring2javautf8(java_env, extra);
                java_env->CallVoidMethod(java_callback,g_events_ids[16],_channelID,_account,uid,_extra);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_extra);
            }
        }
            
        virtual void onInviteRefusedByPeer(std::string channelID,std::string account,uint32_t uid,std::string extra){
            
            //LOG("onInviteRefusedByPeer %p", java_env);
            //getEnv();
            //LOG("onInviteRefusedByPeer %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInviteRefusedByPeer env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInviteRefusedByPeer", 
                        "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInviteRefusedByPeer env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[17], mid, g_events_ids[17]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);jstring _extra = cstring2javautf8(java_env, extra);
                java_env->CallVoidMethod(java_callback,g_events_ids[17],_channelID,_account,uid,_extra);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_extra);
            }
        }
            
        virtual void onInviteFailed(std::string channelID,std::string account,uint32_t uid,int ecode,std::string extra){
            
            //LOG("onInviteFailed %p", java_env);
            //getEnv();
            //LOG("onInviteFailed %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInviteFailed env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInviteFailed", 
                        "(Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInviteFailed env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[18], mid, g_events_ids[18]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);jstring _extra = cstring2javautf8(java_env, extra);
                java_env->CallVoidMethod(java_callback,g_events_ids[18],_channelID,_account,uid,ecode,_extra);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_extra);
            }
        }
            
        virtual void onInviteEndByPeer(std::string channelID,std::string account,uint32_t uid,std::string extra){
            
            //LOG("onInviteEndByPeer %p", java_env);
            //getEnv();
            //LOG("onInviteEndByPeer %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInviteEndByPeer env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInviteEndByPeer", 
                        "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInviteEndByPeer env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[19], mid, g_events_ids[19]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);jstring _extra = cstring2javautf8(java_env, extra);
                java_env->CallVoidMethod(java_callback,g_events_ids[19],_channelID,_account,uid,_extra);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_extra);
            }
        }
            
        virtual void onInviteEndByMyself(std::string channelID,std::string account,uint32_t uid){
            
            //LOG("onInviteEndByMyself %p", java_env);
            //getEnv();
            //LOG("onInviteEndByMyself %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInviteEndByMyself env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInviteEndByMyself", 
                        "(Ljava/lang/String;Ljava/lang/String;I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInviteEndByMyself env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[20], mid, g_events_ids[20]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);
                java_env->CallVoidMethod(java_callback,g_events_ids[20],_channelID,_account,uid);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);
            }
        }
            
        virtual void onInviteMsg(std::string channelID,std::string account,uint32_t uid,std::string msgType,std::string msgData,std::string extra){
            
            //LOG("onInviteMsg %p", java_env);
            //getEnv();
            //LOG("onInviteMsg %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInviteMsg env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInviteMsg", 
                        "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInviteMsg env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[21], mid, g_events_ids[21]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);jstring _msgType = cstring2javautf8(java_env, msgType);jstring _msgData = cstring2javautf8(java_env, msgData);jstring _extra = cstring2javautf8(java_env, extra);
                java_env->CallVoidMethod(java_callback,g_events_ids[21],_channelID,_account,uid,_msgType,_msgData,_extra);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_msgType);java_env->DeleteLocalRef(_msgData);java_env->DeleteLocalRef(_extra);
            }
        }
            
        virtual void onMessageSendError(std::string messageID,int ecode){
            
            //LOG("onMessageSendError %p", java_env);
            //getEnv();
            //LOG("onMessageSendError %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onMessageSendError env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onMessageSendError", 
                        "(Ljava/lang/String;I)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onMessageSendError env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[22], mid, g_events_ids[22]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _messageID = cstring2javautf8(java_env, messageID);
                java_env->CallVoidMethod(java_callback,g_events_ids[22],_messageID,ecode);
                java_env->DeleteLocalRef(_messageID);
            }
        }
            
        virtual void onMessageSendProgress(std::string account,std::string messageID,std::string type,std::string info){
            
            //LOG("onMessageSendProgress %p", java_env);
            //getEnv();
            //LOG("onMessageSendProgress %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onMessageSendProgress env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onMessageSendProgress", 
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onMessageSendProgress env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[23], mid, g_events_ids[23]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _account = cstring2javautf8(java_env, account);jstring _messageID = cstring2javautf8(java_env, messageID);jstring _type = cstring2javautf8(java_env, type);jstring _info = cstring2javautf8(java_env, info);
                java_env->CallVoidMethod(java_callback,g_events_ids[23],_account,_messageID,_type,_info);
                java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_messageID);java_env->DeleteLocalRef(_type);java_env->DeleteLocalRef(_info);
            }
        }
            
        virtual void onMessageSendSuccess(std::string messageID){
            
            //LOG("onMessageSendSuccess %p", java_env);
            //getEnv();
            //LOG("onMessageSendSuccess %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onMessageSendSuccess env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onMessageSendSuccess", 
                        "(Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onMessageSendSuccess env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[24], mid, g_events_ids[24]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _messageID = cstring2javautf8(java_env, messageID);
                java_env->CallVoidMethod(java_callback,g_events_ids[24],_messageID);
                java_env->DeleteLocalRef(_messageID);
            }
        }
            
        virtual void onMessageAppReceived(std::string msg){
            
            //LOG("onMessageAppReceived %p", java_env);
            //getEnv();
            //LOG("onMessageAppReceived %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onMessageAppReceived env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onMessageAppReceived", 
                        "(Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onMessageAppReceived env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[25], mid, g_events_ids[25]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _msg = cstring2javautf8(java_env, msg);
                java_env->CallVoidMethod(java_callback,g_events_ids[25],_msg);
                java_env->DeleteLocalRef(_msg);
            }
        }
            
        virtual void onMessageInstantReceive(std::string account,uint32_t uid,std::string msg){
            
            //LOG("onMessageInstantReceive %p", java_env);
            //getEnv();
            //LOG("onMessageInstantReceive %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onMessageInstantReceive env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onMessageInstantReceive", 
                        "(Ljava/lang/String;ILjava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onMessageInstantReceive env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[26], mid, g_events_ids[26]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _account = cstring2javautf8(java_env, account);jstring _msg = cstring2javautf8(java_env, msg);
                java_env->CallVoidMethod(java_callback,g_events_ids[26],_account,uid,_msg);
                java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_msg);
            }
        }
            
        virtual void onMessageChannelReceive(std::string channelID,std::string account,uint32_t uid,std::string msg){
            
            //LOG("onMessageChannelReceive %p", java_env);
            //getEnv();
            //LOG("onMessageChannelReceive %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onMessageChannelReceive env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onMessageChannelReceive", 
                        "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onMessageChannelReceive env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[27], mid, g_events_ids[27]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _channelID = cstring2javautf8(java_env, channelID);jstring _account = cstring2javautf8(java_env, account);jstring _msg = cstring2javautf8(java_env, msg);
                java_env->CallVoidMethod(java_callback,g_events_ids[27],_channelID,_account,uid,_msg);
                java_env->DeleteLocalRef(_channelID);java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_msg);
            }
        }
            
        virtual void onLog(std::string txt){
            
            //LOG("onLog %p", java_env);
            //getEnv();
            //LOG("onLog %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onLog env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onLog", 
                        "(Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onLog env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[28], mid, g_events_ids[28]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _txt = cstring2javautf8(java_env, txt);
                java_env->CallVoidMethod(java_callback,g_events_ids[28],_txt);
                java_env->DeleteLocalRef(_txt);
            }
        }
            
        virtual void onInvokeRet(std::string callID,std::string err,std::string resp){
            
            //LOG("onInvokeRet %p", java_env);
            //getEnv();
            //LOG("onInvokeRet %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onInvokeRet env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onInvokeRet", 
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onInvokeRet env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[29], mid, g_events_ids[29]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _callID = cstring2javautf8(java_env, callID);jstring _err = cstring2javautf8(java_env, err);jstring _resp = cstring2javautf8(java_env, resp);
                java_env->CallVoidMethod(java_callback,g_events_ids[29],_callID,_err,_resp);
                java_env->DeleteLocalRef(_callID);java_env->DeleteLocalRef(_err);java_env->DeleteLocalRef(_resp);
            }
        }
            
        virtual void onMsg(std::string from,std::string t,std::string msg){
            
            //LOG("onMsg %p", java_env);
            //getEnv();
            //LOG("onMsg %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onMsg env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onMsg", 
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onMsg env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[30], mid, g_events_ids[30]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _from = cstring2javautf8(java_env, from);jstring _t = cstring2javautf8(java_env, t);jstring _msg = cstring2javautf8(java_env, msg);
                java_env->CallVoidMethod(java_callback,g_events_ids[30],_from,_t,_msg);
                java_env->DeleteLocalRef(_from);java_env->DeleteLocalRef(_t);java_env->DeleteLocalRef(_msg);
            }
        }
            
        virtual void onUserAttrResult(std::string account,std::string name,std::string value){
            
            //LOG("onUserAttrResult %p", java_env);
            //getEnv();
            //LOG("onUserAttrResult %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onUserAttrResult env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onUserAttrResult", 
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onUserAttrResult env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[31], mid, g_events_ids[31]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _account = cstring2javautf8(java_env, account);jstring _name = cstring2javautf8(java_env, name);jstring _value = cstring2javautf8(java_env, value);
                java_env->CallVoidMethod(java_callback,g_events_ids[31],_account,_name,_value);
                java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_name);java_env->DeleteLocalRef(_value);
            }
        }
            
        virtual void onUserAttrAllResult(std::string account,std::string value){
            
            //LOG("onUserAttrAllResult %p", java_env);
            //getEnv();
            //LOG("onUserAttrAllResult %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onUserAttrAllResult env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onUserAttrAllResult", 
                        "(Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onUserAttrAllResult env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[32], mid, g_events_ids[32]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _account = cstring2javautf8(java_env, account);jstring _value = cstring2javautf8(java_env, value);
                java_env->CallVoidMethod(java_callback,g_events_ids[32],_account,_value);
                java_env->DeleteLocalRef(_account);java_env->DeleteLocalRef(_value);
            }
        }
            
        virtual void onError(std::string name,int ecode,std::string desc){
            
            //LOG("onError %p", java_env);
            //getEnv();
            //LOG("onError %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onError env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onError", 
                        "(Ljava/lang/String;ILjava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onError env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[33], mid, g_events_ids[33]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _name = cstring2javautf8(java_env, name);jstring _desc = cstring2javautf8(java_env, desc);
                java_env->CallVoidMethod(java_callback,g_events_ids[33],_name,ecode,_desc);
                java_env->DeleteLocalRef(_name);java_env->DeleteLocalRef(_desc);
            }
        }
            
        virtual void onQueryUserStatusResult(std::string name,std::string status){
            
            //LOG("onQueryUserStatusResult %p", java_env);
            //getEnv();
            //LOG("onQueryUserStatusResult %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onQueryUserStatusResult env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onQueryUserStatusResult", 
                        "(Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onQueryUserStatusResult env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[34], mid, g_events_ids[34]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _name = cstring2javautf8(java_env, name);jstring _status = cstring2javautf8(java_env, status);
                java_env->CallVoidMethod(java_callback,g_events_ids[34],_name,_status);
                java_env->DeleteLocalRef(_name);java_env->DeleteLocalRef(_status);
            }
        }
            
        virtual void onDbg(std::string a,std::string b){
            
            //LOG("onDbg %p", java_env);
            //getEnv();
            //LOG("onDbg %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onDbg env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onDbg", 
                        "(Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onDbg env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[35], mid, g_events_ids[35]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _a = cstring2javautf8(java_env, a);jstring _b = cstring2javautf8(java_env, b);
                java_env->CallVoidMethod(java_callback,g_events_ids[35],_a,_b);
                java_env->DeleteLocalRef(_a);java_env->DeleteLocalRef(_b);
            }
        }
            
        virtual void onBCCall_result(std::string reason,std::string json_ret,std::string callID){
            
            //LOG("onBCCall_result %p", java_env);
            //getEnv();
            //LOG("onBCCall_result %p", java_env);
            AttachThreadScoped ats(g_jvm);
            java_env = ats.env();

            //LOG("onBCCall_result env=%p java_callback=%p",  java_env, java_callback);

            jclass cls = java_env->GetObjectClass(java_callback);
            jmethodID mid = java_env->GetMethodID(
                    cls, 
                        "onBCCall_result", 
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"
                    );
            java_env->DeleteLocalRef(cls);


            //LOG("onBCCall_result env=%p java_callback=%p method=%p mid=%p g_mid==%p", java_env, java_callback, g_events_ids[36], mid, g_events_ids[36]);
            if (java_callback!=NULL && java_env!=NULL){

                jstring _reason = cstring2javautf8(java_env, reason);jstring _json_ret = cstring2javautf8(java_env, json_ret);jstring _callID = cstring2javautf8(java_env, callID);
                java_env->CallVoidMethod(java_callback,g_events_ids[36],_reason,_json_ret,_callID);
                java_env->DeleteLocalRef(_reason);java_env->DeleteLocalRef(_json_ret);java_env->DeleteLocalRef(_callID);
            }
        }
            
};

static CallBack * agora_cb = NULL;

extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_jniInit( JNIEnv * env, jobject obj){

    __android_log_write(ANDROID_LOG_FATAL, "sdk2", "jniInit");
    
    if (agora==NULL){
        agora = getAgoraSDKInstance();
        
    }
}

extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_callbackSet( JNIEnv * env, jobject obj, jobject handler){
    java_env = env;
    jclass cls = NULL;

    LOG("GetJavaVM result : %d", env->GetJavaVM(&g_jvm));

    if (agora_cb==NULL){
        agora_cb = new CallBack();
        agora->callbackSet(agora_cb);
    }

    //if (java_callback!=NULL) env->DeleteLocalRef(java_callback);
    java_callback = env->NewGlobalRef(handler);

    LOG("cls %p, env=%p java_callback=%p, handler=%p", cls, env, java_callback, handler);

    cls = env->GetObjectClass(handler);

    LOG("cls %p", cls);
    
    
            LOG("GetMethodID onReconnecting");
            g_events_ids[0] = env->GetMethodID(cls, "onReconnecting", "(I)V");
            LOG("onReconnecting = %p", g_events_ids[0]);
            

            LOG("GetMethodID onReconnected");
            g_events_ids[1] = env->GetMethodID(cls, "onReconnected", "(I)V");
            LOG("onReconnected = %p", g_events_ids[1]);
            

            LOG("GetMethodID onLoginSuccess");
            g_events_ids[2] = env->GetMethodID(cls, "onLoginSuccess", "(II)V");
            LOG("onLoginSuccess = %p", g_events_ids[2]);
            

            LOG("GetMethodID onLogout");
            g_events_ids[3] = env->GetMethodID(cls, "onLogout", "(I)V");
            LOG("onLogout = %p", g_events_ids[3]);
            

            LOG("GetMethodID onLoginFailed");
            g_events_ids[4] = env->GetMethodID(cls, "onLoginFailed", "(I)V");
            LOG("onLoginFailed = %p", g_events_ids[4]);
            

            LOG("GetMethodID onChannelJoined");
            g_events_ids[5] = env->GetMethodID(cls, "onChannelJoined", "(Ljava/lang/String;)V");
            LOG("onChannelJoined = %p", g_events_ids[5]);
            

            LOG("GetMethodID onChannelJoinFailed");
            g_events_ids[6] = env->GetMethodID(cls, "onChannelJoinFailed", "(Ljava/lang/String;I)V");
            LOG("onChannelJoinFailed = %p", g_events_ids[6]);
            

            LOG("GetMethodID onChannelLeaved");
            g_events_ids[7] = env->GetMethodID(cls, "onChannelLeaved", "(Ljava/lang/String;I)V");
            LOG("onChannelLeaved = %p", g_events_ids[7]);
            

            LOG("GetMethodID onChannelUserJoined");
            g_events_ids[8] = env->GetMethodID(cls, "onChannelUserJoined", "(Ljava/lang/String;I)V");
            LOG("onChannelUserJoined = %p", g_events_ids[8]);
            

            LOG("GetMethodID onChannelUserLeaved");
            g_events_ids[9] = env->GetMethodID(cls, "onChannelUserLeaved", "(Ljava/lang/String;I)V");
            LOG("onChannelUserLeaved = %p", g_events_ids[9]);
            

            LOG("GetMethodID onChannelUserList");
            g_events_ids[10] = env->GetMethodID(cls, "onChannelUserList", "([Ljava/lang/String;[I)V");
            LOG("onChannelUserList = %p", g_events_ids[10]);
            

            LOG("GetMethodID onChannelQueryUserNumResult");
            g_events_ids[11] = env->GetMethodID(cls, "onChannelQueryUserNumResult", "(Ljava/lang/String;II)V");
            LOG("onChannelQueryUserNumResult = %p", g_events_ids[11]);
            

            LOG("GetMethodID onChannelQueryUserIsIn");
            g_events_ids[12] = env->GetMethodID(cls, "onChannelQueryUserIsIn", "(Ljava/lang/String;Ljava/lang/String;I)V");
            LOG("onChannelQueryUserIsIn = %p", g_events_ids[12]);
            

            LOG("GetMethodID onChannelAttrUpdated");
            g_events_ids[13] = env->GetMethodID(cls, "onChannelAttrUpdated", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onChannelAttrUpdated = %p", g_events_ids[13]);
            

            LOG("GetMethodID onInviteReceived");
            g_events_ids[14] = env->GetMethodID(cls, "onInviteReceived", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V");
            LOG("onInviteReceived = %p", g_events_ids[14]);
            

            LOG("GetMethodID onInviteReceivedByPeer");
            g_events_ids[15] = env->GetMethodID(cls, "onInviteReceivedByPeer", "(Ljava/lang/String;Ljava/lang/String;I)V");
            LOG("onInviteReceivedByPeer = %p", g_events_ids[15]);
            

            LOG("GetMethodID onInviteAcceptedByPeer");
            g_events_ids[16] = env->GetMethodID(cls, "onInviteAcceptedByPeer", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V");
            LOG("onInviteAcceptedByPeer = %p", g_events_ids[16]);
            

            LOG("GetMethodID onInviteRefusedByPeer");
            g_events_ids[17] = env->GetMethodID(cls, "onInviteRefusedByPeer", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V");
            LOG("onInviteRefusedByPeer = %p", g_events_ids[17]);
            

            LOG("GetMethodID onInviteFailed");
            g_events_ids[18] = env->GetMethodID(cls, "onInviteFailed", "(Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;)V");
            LOG("onInviteFailed = %p", g_events_ids[18]);
            

            LOG("GetMethodID onInviteEndByPeer");
            g_events_ids[19] = env->GetMethodID(cls, "onInviteEndByPeer", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V");
            LOG("onInviteEndByPeer = %p", g_events_ids[19]);
            

            LOG("GetMethodID onInviteEndByMyself");
            g_events_ids[20] = env->GetMethodID(cls, "onInviteEndByMyself", "(Ljava/lang/String;Ljava/lang/String;I)V");
            LOG("onInviteEndByMyself = %p", g_events_ids[20]);
            

            LOG("GetMethodID onInviteMsg");
            g_events_ids[21] = env->GetMethodID(cls, "onInviteMsg", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onInviteMsg = %p", g_events_ids[21]);
            

            LOG("GetMethodID onMessageSendError");
            g_events_ids[22] = env->GetMethodID(cls, "onMessageSendError", "(Ljava/lang/String;I)V");
            LOG("onMessageSendError = %p", g_events_ids[22]);
            

            LOG("GetMethodID onMessageSendProgress");
            g_events_ids[23] = env->GetMethodID(cls, "onMessageSendProgress", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onMessageSendProgress = %p", g_events_ids[23]);
            

            LOG("GetMethodID onMessageSendSuccess");
            g_events_ids[24] = env->GetMethodID(cls, "onMessageSendSuccess", "(Ljava/lang/String;)V");
            LOG("onMessageSendSuccess = %p", g_events_ids[24]);
            

            LOG("GetMethodID onMessageAppReceived");
            g_events_ids[25] = env->GetMethodID(cls, "onMessageAppReceived", "(Ljava/lang/String;)V");
            LOG("onMessageAppReceived = %p", g_events_ids[25]);
            

            LOG("GetMethodID onMessageInstantReceive");
            g_events_ids[26] = env->GetMethodID(cls, "onMessageInstantReceive", "(Ljava/lang/String;ILjava/lang/String;)V");
            LOG("onMessageInstantReceive = %p", g_events_ids[26]);
            

            LOG("GetMethodID onMessageChannelReceive");
            g_events_ids[27] = env->GetMethodID(cls, "onMessageChannelReceive", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V");
            LOG("onMessageChannelReceive = %p", g_events_ids[27]);
            

            LOG("GetMethodID onLog");
            g_events_ids[28] = env->GetMethodID(cls, "onLog", "(Ljava/lang/String;)V");
            LOG("onLog = %p", g_events_ids[28]);
            

            LOG("GetMethodID onInvokeRet");
            g_events_ids[29] = env->GetMethodID(cls, "onInvokeRet", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onInvokeRet = %p", g_events_ids[29]);
            

            LOG("GetMethodID onMsg");
            g_events_ids[30] = env->GetMethodID(cls, "onMsg", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onMsg = %p", g_events_ids[30]);
            

            LOG("GetMethodID onUserAttrResult");
            g_events_ids[31] = env->GetMethodID(cls, "onUserAttrResult", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onUserAttrResult = %p", g_events_ids[31]);
            

            LOG("GetMethodID onUserAttrAllResult");
            g_events_ids[32] = env->GetMethodID(cls, "onUserAttrAllResult", "(Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onUserAttrAllResult = %p", g_events_ids[32]);
            

            LOG("GetMethodID onError");
            g_events_ids[33] = env->GetMethodID(cls, "onError", "(Ljava/lang/String;ILjava/lang/String;)V");
            LOG("onError = %p", g_events_ids[33]);
            

            LOG("GetMethodID onQueryUserStatusResult");
            g_events_ids[34] = env->GetMethodID(cls, "onQueryUserStatusResult", "(Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onQueryUserStatusResult = %p", g_events_ids[34]);
            

            LOG("GetMethodID onDbg");
            g_events_ids[35] = env->GetMethodID(cls, "onDbg", "(Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onDbg = %p", g_events_ids[35]);
            

            LOG("GetMethodID onBCCall_result");
            g_events_ids[36] = env->GetMethodID(cls, "onBCCall_result", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
            LOG("onBCCall_result = %p", g_events_ids[36]);
            

    java_env->DeleteLocalRef(cls);
    //((ICallBack*)agora_cb)->onLoginSuccess();
}
        
extern "C" JNIEXPORT  jobject JNICALL Java_io_agora_NativeAgoraAPI_callbackGet( JNIEnv * env, jobject obj ){
    return java_callback;
}


                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_login( JNIEnv * env,jobject obj,jstring vendorID,jstring account,jstring token,jint uid,jstring deviceID){
                    return agora->login (
                        std::string(vendorID==NULL?"":env->GetStringUTFChars(vendorID,NULL)),
        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        std::string(token==NULL?"":env->GetStringUTFChars(token,NULL)),
        uid,
        std::string(deviceID==NULL?"":env->GetStringUTFChars(deviceID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_login2( JNIEnv * env,jobject obj,jstring vendorID,jstring account,jstring token,jint uid,jstring deviceID,jint retry_time_in_s,jint retry_count){
                    return agora->login2 (
                        std::string(vendorID==NULL?"":env->GetStringUTFChars(vendorID,NULL)),
        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        std::string(token==NULL?"":env->GetStringUTFChars(token,NULL)),
        uid,
        std::string(deviceID==NULL?"":env->GetStringUTFChars(deviceID,NULL)),
        retry_time_in_s,
        retry_count
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_logout( JNIEnv * env,jobject obj){
                    return agora->logout (
                        
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelJoin( JNIEnv * env,jobject obj,jstring channelID){
                    return agora->channelJoin (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelLeave( JNIEnv * env,jobject obj,jstring channelID){
                    return agora->channelLeave (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelQueryUserNum( JNIEnv * env,jobject obj,jstring channelID){
                    return agora->channelQueryUserNum (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelQueryUserIsIn( JNIEnv * env,jobject obj,jstring channelID,jstring account){
                    return agora->channelQueryUserIsIn (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelSetAttr( JNIEnv * env,jobject obj,jstring channelID,jstring name,jstring value){
                    return agora->channelSetAttr (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(name==NULL?"":env->GetStringUTFChars(name,NULL)),
        std::string(value==NULL?"":env->GetStringUTFChars(value,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelDelAttr( JNIEnv * env,jobject obj,jstring channelID,jstring name){
                    return agora->channelDelAttr (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(name==NULL?"":env->GetStringUTFChars(name,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelClearAttr( JNIEnv * env,jobject obj,jstring channelID){
                    return agora->channelClearAttr (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInviteUser( JNIEnv * env,jobject obj,jstring channelID,jstring account,jint uid){
                    return agora->channelInviteUser (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        uid
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInviteUser2( JNIEnv * env,jobject obj,jstring channelID,jstring account,jstring extra){
                    return agora->channelInviteUser2 (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        std::string(extra==NULL?"":env->GetStringUTFChars(extra,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInvitePhone( JNIEnv * env,jobject obj,jstring channelID,jstring phoneNum,jint uid){
                    return agora->channelInvitePhone (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(phoneNum==NULL?"":env->GetStringUTFChars(phoneNum,NULL)),
        uid
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInvitePhone2( JNIEnv * env,jobject obj,jstring channelID,jstring phoneNum,jstring sourcesNum){
                    return agora->channelInvitePhone2 (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(phoneNum==NULL?"":env->GetStringUTFChars(phoneNum,NULL)),
        std::string(sourcesNum==NULL?"":env->GetStringUTFChars(sourcesNum,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInvitePhone3( JNIEnv * env,jobject obj,jstring channelID,jstring phoneNum,jstring sourcesNum,jstring extra){
                    return agora->channelInvitePhone3 (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(phoneNum==NULL?"":env->GetStringUTFChars(phoneNum,NULL)),
        std::string(sourcesNum==NULL?"":env->GetStringUTFChars(sourcesNum,NULL)),
        std::string(extra==NULL?"":env->GetStringUTFChars(extra,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInviteDTMF( JNIEnv * env,jobject obj,jstring channelID,jstring phoneNum,jstring dtmf){
                    return agora->channelInviteDTMF (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(phoneNum==NULL?"":env->GetStringUTFChars(phoneNum,NULL)),
        std::string(dtmf==NULL?"":env->GetStringUTFChars(dtmf,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInviteAccept( JNIEnv * env,jobject obj,jstring channelID,jstring account,jint uid){
                    return agora->channelInviteAccept (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        uid
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInviteRefuse( JNIEnv * env,jobject obj,jstring channelID,jstring account,jint uid,jstring extra){
                    return agora->channelInviteRefuse (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        uid,
        std::string(extra==NULL?"":env->GetStringUTFChars(extra,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_channelInviteEnd( JNIEnv * env,jobject obj,jstring channelID,jstring account,jint uid){
                    return agora->channelInviteEnd (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        uid
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_messageAppSend( JNIEnv * env,jobject obj,jstring msg,jstring msgID){
                    return agora->messageAppSend (
                        std::string(msg==NULL?"":env->GetStringUTFChars(msg,NULL)),
        std::string(msgID==NULL?"":env->GetStringUTFChars(msgID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_messageInstantSend( JNIEnv * env,jobject obj,jstring account,jint uid,jstring msg,jstring msgID){
                    return agora->messageInstantSend (
                        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        uid,
        std::string(msg==NULL?"":env->GetStringUTFChars(msg,NULL)),
        std::string(msgID==NULL?"":env->GetStringUTFChars(msgID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_messageInstantSend2( JNIEnv * env,jobject obj,jstring account,jint uid,jstring msg,jstring msgID,jstring options){
                    return agora->messageInstantSend2 (
                        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        uid,
        std::string(msg==NULL?"":env->GetStringUTFChars(msg,NULL)),
        std::string(msgID==NULL?"":env->GetStringUTFChars(msgID,NULL)),
        std::string(options==NULL?"":env->GetStringUTFChars(options,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_messageChannelSend( JNIEnv * env,jobject obj,jstring channelID,jstring msg,jstring msgID){
                    return agora->messageChannelSend (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(msg==NULL?"":env->GetStringUTFChars(msg,NULL)),
        std::string(msgID==NULL?"":env->GetStringUTFChars(msgID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_messageChannelSendForce( JNIEnv * env,jobject obj,jstring channelID,jstring msg,jstring msgID){
                    return agora->messageChannelSendForce (
                        std::string(channelID==NULL?"":env->GetStringUTFChars(channelID,NULL)),
        std::string(msg==NULL?"":env->GetStringUTFChars(msg,NULL)),
        std::string(msgID==NULL?"":env->GetStringUTFChars(msgID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_messagePushSend( JNIEnv * env,jobject obj,jstring account,jint uid,jstring msg,jstring msgID){
                    return agora->messagePushSend (
                        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        uid,
        std::string(msg==NULL?"":env->GetStringUTFChars(msg,NULL)),
        std::string(msgID==NULL?"":env->GetStringUTFChars(msgID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_messageChatSend( JNIEnv * env,jobject obj,jstring account,jint uid,jstring msg,jstring msgID){
                    return agora->messageChatSend (
                        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        uid,
        std::string(msg==NULL?"":env->GetStringUTFChars(msg,NULL)),
        std::string(msgID==NULL?"":env->GetStringUTFChars(msgID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_messageDTMFSend( JNIEnv * env,jobject obj,jint uid,jstring msg,jstring msgID){
                    return agora->messageDTMFSend (
                        uid,
        std::string(msg==NULL?"":env->GetStringUTFChars(msg,NULL)),
        std::string(msgID==NULL?"":env->GetStringUTFChars(msgID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_setBackground( JNIEnv * env,jobject obj,jint bOut){
                    LOG("bOut = %d", bOut);
                    agora->setBackground (
                        bOut
                    );
                }
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_setNetworkStatus( JNIEnv * env,jobject obj,jint bOut){
                    return agora->setNetworkStatus (
                        bOut
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_ping( JNIEnv * env,jobject obj){
                    return agora->ping (
                        
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_setAttr( JNIEnv * env,jobject obj,jstring name,jstring value){
                    return agora->setAttr (
                        std::string(name==NULL?"":env->GetStringUTFChars(name,NULL)),
        std::string(value==NULL?"":env->GetStringUTFChars(value,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_getAttr( JNIEnv * env,jobject obj,jstring name){
                    return agora->getAttr (
                        std::string(name==NULL?"":env->GetStringUTFChars(name,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_getAttrAll( JNIEnv * env,jobject obj){
                    return agora->getAttrAll (
                        
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_getUserAttr( JNIEnv * env,jobject obj,jstring account,jstring name){
                    return agora->getUserAttr (
                        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL)),
        std::string(name==NULL?"":env->GetStringUTFChars(name,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_getUserAttrAll( JNIEnv * env,jobject obj,jstring account){
                    return agora->getUserAttrAll (
                        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_queryUserStatus( JNIEnv * env,jobject obj,jstring account){
                    return agora->queryUserStatus (
                        std::string(account==NULL?"":env->GetStringUTFChars(account,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_invoke( JNIEnv * env,jobject obj,jstring name,jstring req,jstring callID){
                    return agora->invoke (
                        std::string(name==NULL?"":env->GetStringUTFChars(name,NULL)),
        std::string(req==NULL?"":env->GetStringUTFChars(req,NULL)),
        std::string(callID==NULL?"":env->GetStringUTFChars(callID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_start( JNIEnv * env,jobject obj){
                    return agora->start (
                        
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_stop( JNIEnv * env,jobject obj){
                    return agora->stop (
                        
                    );
                }
            
                extern "C" JNIEXPORT  jint JNICALL Java_io_agora_NativeAgoraAPI_isOnline( JNIEnv * env,jobject obj){
                    return agora->isOnline (
                        
                    )?1:0;
                }
            
                extern "C" JNIEXPORT  jint JNICALL Java_io_agora_NativeAgoraAPI_getStatus( JNIEnv * env,jobject obj){
                    return agora->getStatus (
                        
                    );
                }
            
                extern "C" JNIEXPORT  jint JNICALL Java_io_agora_NativeAgoraAPI_getSdkVersion( JNIEnv * env,jobject obj){
                    return agora->getSdkVersion (
                        
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_bc_call( JNIEnv * env,jobject obj,jstring func,jstring json_args,jstring callID){
                    return agora->bc_call (
                        std::string(func==NULL?"":env->GetStringUTFChars(func,NULL)),
        std::string(json_args==NULL?"":env->GetStringUTFChars(json_args,NULL)),
        std::string(callID==NULL?"":env->GetStringUTFChars(callID,NULL))
                    );
                }
            
                extern "C" JNIEXPORT  void JNICALL Java_io_agora_NativeAgoraAPI_dbg( JNIEnv * env,jobject obj,jstring a,jstring b){
                    return agora->dbg (
                        std::string(a==NULL?"":env->GetStringUTFChars(a,NULL)),
        std::string(b==NULL?"":env->GetStringUTFChars(b,NULL))
                    );
                }
            

extern "C" JNIEXPORT  jint JNICALL Java_io_agora_NativeAgoraAPI_getuid( JNIEnv * env){
    return g_uid;
}

