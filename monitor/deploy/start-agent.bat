set "CURRENT_DIR=%cd%"

set JAVA_HOME=%CURRENT_DIR%\jre
echo %JAVA_HOME%

for %%i in (%CURRENT_DIR%\sigar-bin\lib\*.jar) do call %CURRENT_DIR%\cpappend.bat %%i
echo %CURRENT_DIR%

set _RUNJAVA=%JAVA_HOME%\bin\java
%_RUNJAVA%  -Djava.ext.dirs=%CURRENT_DIR%/sigar-bin/lib;%CURRENT_DIR%/lib com.foodoon.monitor.agent.start.Main

