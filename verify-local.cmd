@echo off
REM Smoke test for smp-notification-service (Windows)
set BASE_URL=http://localhost:8080
set REQUEST_ID=00000000-0000-0000-0000-000000000001

echo --- Smoke POST createNotification ---
curl -s -o nul -w "%%{http_code}" -X POST "%BASE_URL%/notifications" ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer <fill-test-token>" ^
  -H "consumerId: SMP" ^
  -H "sessionId: %REQUEST_ID%" ^
  -H "sessionAppId: SMP-LOCAL" ^
  -H "customerId: <fill>" ^
  -H "deviceId: <fill>" ^
  -H "deviceType: AND" ^
  -H "apiVersion: v1" ^
  -H "subscription-key: <fill>" ^
  -H "X-Request-Id: %REQUEST_ID%" ^
  -d "{\"title\":\"Test Notification\",\"message\":\"Test message body\",\"projectId\":\"proj-test-001\"}"
echo.

echo --- Smoke GET getNotificationById ---
curl -s -o nul -w "%%{http_code}" -X GET "%BASE_URL%/notifications/mock-notification-id-001" ^
  -H "Authorization: Bearer <fill-test-token>" ^
  -H "consumerId: SMP" ^
  -H "sessionId: %REQUEST_ID%" ^
  -H "sessionAppId: SMP-LOCAL" ^
  -H "customerId: <fill>" ^
  -H "deviceId: <fill>" ^
  -H "deviceType: AND" ^
  -H "apiVersion: v1" ^
  -H "subscription-key: <fill>" ^
  -H "X-Request-Id: %REQUEST_ID%"
echo.

echo Smoke tests complete.
