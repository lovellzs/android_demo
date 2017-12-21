pwd

SRC_REL_DIR=`svn info | grep URL | awk -F '/' '{print $(NF-1)}'`
echo "*** svn rel: $SRC_REL_DIR"
SRC_SVN_VER=`svn info | grep '^Last Changed Rev' | awk -F':' '{print $2}'`
echo "*** svn vel: $SRC_SVN_VER"
SDK_SVN_INFO="$SRC_REL_DIR"-"$SRC_SVN_VER"
echo "*** svn info: $SDK_SVN_INFO"
# sed -i  's/\(^#define *SDK_SVN_VERSION_BUILD *\).*/\1'"\"$SDK_SVN_INFO\""'/g' "./client/av_version.h"
# if [ $? -eq 0 ]; then
    # echo "*** svn revision: $SDK_SVN_INFO"
# fi

export JAVA_HOME=$JDK6
export PATH=$JDK6/bin:$PATH
export SVN_INFO=$SDK_SVN_INFO
export GRADLE_HOME=${GRADLE_BASE}/gradle-4.1
export GRADLE_USER_HOME=${GRADLE_HOME}
export PATH=$GRADLE_HOME/bin:$PATH
echo $GRADLE_HOME
echo .................begin..............

# $GRADLE_HOME/bin/gradle build
ant -f build.xml
echo .................end..............

if ! [ $? = 0 ] ;then
exit 1
fi

