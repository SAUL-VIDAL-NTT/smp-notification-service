@echo off
REM run-local.cmd — boots smp-notification-service locally using profile=test + mocks

for /f "tokens=1,2 delims==" %%a in (.env.local) do set %%a=%%b

echo =^> Starting smp-notification-service locally (profile=%PROFILE%, mocks=%MOCKS%)...

set PROFILE=%PROFILE%
set MOCKS=%MOCKS%
set LOG_LEVEL=DEBUG

mvn spring-boot:run ^
  -Dspring-boot.run.jvmArguments="-DPROFILE=%PROFILE% -DMOCKS=%MOCKS% -DLOG_LEVEL=DEBUG" ^
  --settings settings.xml ^
  -Durl_artifactory=%url_artifactory% ^
  -Duser_artifactory=%user_artifactory% ^
  -Dpassword_artifactory=%password_artifactory%
