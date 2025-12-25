@echo off
chcp 65001 > nul

if "%1"=="" (
    echo 未传递参数
    rem goto:eof
)

echo %1
set AppName=%1
set JVM_OPTS=-Dspring.profiles.active=%2
echo %2
set url=%3
set outputFileName=%AppName%

:: 获取当前脚本所在的目录
for %%I in (%~dp0) do set "currentDirectory=%%~fI"
set outputPath=%currentDirectory%\%outputFileName%

echo 开始下载 %outputFileName% ...
powershell -Command "& {Invoke-WebRequest -Uri %url% -OutFile %outputPath%}"



set "id=%4"
IF "%id%"=="1" GOTO start
IF "%id%"=="2" GOTO stop
IF "%id%"=="3" GOTO restart
IF "%id%"=="4" GOTO status
IF "%id%"=="5" EXIT

:start
    for /f "usebackq tokens=1-2" %%a in (`jps -l ^| findstr %AppName%`) do (
        set pid=%%a
        set image_name=%%b
    )
    if defined pid (
        echo %AppName% is already running
    ) else (
        echo Starting: java -jar %outputPath% %JVM_OPTS%
        start C:\Program Files\Java\jdk-1.8\bin\java.exe -jar %outputPath% %JVM_OPTS%
        echo Start %AppName% success...
    )
    goto:eof

:stop
    call :killPort104
    for /f "usebackq tokens=1-2" %%a in (`jps -l ^| findstr %AppName%`) do (
        set pid=%%a
        set image_name=%%b
    )
    if not defined pid (
        echo Process %AppName% does not exist
    ) else (
        echo Prepare to kill %image_name%
        echo Start killing %pid% ...
        taskkill /f /pid %pid%
    )
    goto:eof

:restart
    call :stop
    call :start
    goto:eof

:status
    for /f "usebackq tokens=1-2" %%a in (`jps -l ^| findstr %AppName%`) do (
        set pid=%%a
        set image_name=%%b
    )
    if not defined pid (
        echo Process %AppName% is dead
    ) else (
        echo %image_name% is running
    )
    goto:eof

:killPort104
    echo Killing processes on port 104...
    for /f "tokens=5" %%a in ('netstat -a -n -o ^| find "104"') do (
        taskkill /f /pid %%a
    )
    echo Processes on port 104 killed.
    goto:eof
