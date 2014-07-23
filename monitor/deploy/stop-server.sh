WORK_HOME=`pwd`
kill `cat $WORK_HOME/server.pid`  
rm -rf $WORK_HOME/server.pid  
echo server stop successful.
