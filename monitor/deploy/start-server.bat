set "CURRENT_DIR=%cd%"

set JAVA_HOME=%CURRENT_DIR%\jre
echo %JAVA_HOME%



set _RUNJAVA=%JAVA_HOME%\bin\java
%_RUNJAVA% -Djava.ext.dirs=%CURRENT_DIR%/WEB-INF/lib  com.foodoon.monitor.server.start.Main