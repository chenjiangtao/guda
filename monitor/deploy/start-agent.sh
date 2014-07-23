if [ ! -n "$JAVA_HOME" ]; then
  echo "JAVA_HOME IS NULL,use default  JAVA_HOME"
  JAVA_HOME=/home/admin/jre
 else
  echo "NOT NULL"
fi 
_RUNJAVA="$JAVA_HOME"/bin/java
WORK_HOME=`pwd`
MAIN="com.foodoon.monitor.agent.start.Main"



nohup $_RUNJAVA -Djava.ext.dirs=$WORK_HOME/sigar-bin/lib:$WORK_HOME/lib  $MAIN >$WORK_HOME/nohup.log 2>&1 &
echo $! > $WORK_HOME/agent.pid
echo agent start successful.

