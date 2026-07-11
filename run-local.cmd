@echo off
REM Run smp-notification-service locally (Windows)
set PROFILE=test
set MOCKS=mock, mock-notification
set LOG_LEVEL=INFO

echo Starting smp-notification-service...
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DPROFILE=%PROFILE% -DMOCKS=%MOCKS%"
