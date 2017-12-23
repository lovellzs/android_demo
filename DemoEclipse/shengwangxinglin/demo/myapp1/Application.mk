## APP_STL := stlport_static
#APP_STL := gnustl_static
#APP_PLATFORM := android-22

## no armeabi x86 so far
#APP_ABI := armeabi-v7a
APP_CPPFLAGS := --std=c++11

STLPORT_FORCE_REBUILD := false
APP_STL := stlport_static
APP_STL := gnustl_static
#APP_CPPFLAGS += -fno-exceptions
APP_CPPFLAGS += -fno-rtti -fPIC -fpic
APP_CPPFLAGS += -fvisibility=hidden
APP_CPPFLAGS += -DNOMEDIA=1 -DANDROID=1
#APP_CPPFLAGS += -DNOSDKLOG=1
APP_CFLAGS += -fvisibility=hidden

HAVEVIDEO = 1
ENABLE_EAAC_ENC = 0
ENABLE_MP3_DEC = 1
ENABLE_SLES_PLAYER = 0
APP_ABI := armeabi-v7a x86 arm64-v8a
APP_PLATFORM := android-9

