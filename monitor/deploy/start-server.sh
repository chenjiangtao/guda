if [ ! -n "$JAVA_HOME" ]; then
  echo "JAVA_HOME IS NULL,use default  java_home"
  JAVA_HOME=/home/admin/java
 else
  echo "NOT NULL"
fi 
_RUNJAVA="$JAVA_HOME"/bin/java
WORK_HOME=`pwd`
MAIN="com.foodoon.monitor.server.start.Main"

lib='.'
for jar in `ls $WORK_HOME/WEB-INF/lib/*.jar`
do
lib=$lib:$jar
done

nohup $_RUNJAVA -classpath $lib $MAIN >$WORK_HOME/nohup.log 2>&1 &
echo $! > $WORK_HOME/server.pid
echo server start successful.
