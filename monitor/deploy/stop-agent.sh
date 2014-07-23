WORK_HOME=`pwd`
kill `cat $WORK_HOME/agent.pid`  
rm -rf $WORK_HOME/agent.pid  
echo agent stop successful.
