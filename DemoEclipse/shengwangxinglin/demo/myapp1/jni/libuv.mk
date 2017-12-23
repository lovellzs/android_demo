
LOCAL_PATH:= $(call my-dir)

##################################################
# libuv
# 
LIBUVPATH := $(abspath ../libuv)
include $(CLEAR_VARS)

LOCAL_CFLAGS += -D_LARGEFILE_SOURCE=1 -D_FILE_OFFSET_BITS=64
LOCAL_MODULE    := libuv
LOCAL_C_INCLUDES:= \
	$(LIBUVPATH)/include \
	$(LIBUVPATH)/src \
	$(LIBUVPATH)/unix \
	../src/sdk2/agorasdk \

LOCAL_SRC_FILES := \
	$(LIBUVPATH)/src/fs-poll.c\
	$(LIBUVPATH)/src/inet.c\
	$(LIBUVPATH)/src/uv-common.c\
	$(LIBUVPATH)/src/version.c\
	$(LIBUVPATH)/src/threadpool.c\
	\
	$(LIBUVPATH)/src/unix/async.c\
	$(LIBUVPATH)/src/unix/core.c\
	$(LIBUVPATH)/src/unix/dl.c\
	$(LIBUVPATH)/src/unix/fs.c\
	$(LIBUVPATH)/src/unix/getaddrinfo.c\
	$(LIBUVPATH)/src/unix/getnameinfo.c\
	$(LIBUVPATH)/src/unix/loop-watcher.c\
	$(LIBUVPATH)/src/unix/loop.c\
	$(LIBUVPATH)/src/unix/pipe.c\
	$(LIBUVPATH)/src/unix/poll.c\
	$(LIBUVPATH)/src/unix/process.c\
	$(LIBUVPATH)/src/unix/signal.c\
	$(LIBUVPATH)/src/unix/stream.c\
	$(LIBUVPATH)/src/unix/tcp.c\
	$(LIBUVPATH)/src/unix/thread.c\
	$(LIBUVPATH)/src/unix/timer.c\
	$(LIBUVPATH)/src/unix/tty.c\
	$(LIBUVPATH)/src/unix/udp.c\
	\
	$(LIBUVPATH)/src/unix/linux-core.c\
	$(LIBUVPATH)/src/unix/linux-inotify.c\
	$(LIBUVPATH)/src/unix/linux-syscalls.c\
	$(LIBUVPATH)/src/unix/pthread-fixes.c\
	$(LIBUVPATH)/src/unix/android-ifaddrs.c\

aaaaaa=	$(LIBUVPATH)/src/unix/proctitle.c\



#	$(LIBUVPATH)/src/unix/kqueue.c\
#	$(LIBUVPATH)/src/unix/fsevents.c\
#	$(LIBUVPATH)/src/unix/sunos.c\
#	$(LIBUVPATH)/src/unix/netbsd.c\
#	$(LIBUVPATH)/src/unix/openbsd.c\
#	$(LIBUVPATH)/src/unix/darwin-proctitle.c\
#	$(LIBUVPATH)/src/unix/darwin.c\
#	$(LIBUVPATH)/src/unix/freebsd.c\
#	$(LIBUVPATH)/src/unix/aix.c\

include $(BUILD_STATIC_LIBRARY)

