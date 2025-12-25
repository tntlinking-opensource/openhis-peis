@echo off
echo.
echo [信息] 以jar形式启动运行项目
echo.

cd %~dp0
cd ../mc-admin/target

set JAVA_OPTS=-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -jar %JAVA_OPTS% mc-admin.jar

cd bin
pause