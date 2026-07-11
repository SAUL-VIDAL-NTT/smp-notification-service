@echo off
REM verify-local.cmd — smoke test for smp-notification-service running locally

set BASE_URL=http://localhost:8080
set BEARER_TOKEN=Bearer test-local-token
set CONSUMER_ID=SMP
set SESSION_ID=session-local-001
set DEVICE_ID=device-local-001
set DEVICE_TYPE=AND
set API_VERSION=v1
set SUBSCRIPTION_KEY=test-subscription-key
set REQUEST_ID=req-local-001

echo =^> Smoke test: smp-notification-service @ %BASE_URL%
echo.

:smoke_post
echo --- Create Notification ---
curl -s -X POST "%BASE_URL%/notifications" ^
  -H "Content-Type: application/json" ^
  -H "consumerId: %CONSUMER_ID%" ^
  -H "sessionId: %SESSION_ID%" ^
  -H "sessionAppId: APP_SMP" ^
  -H "customerId: customer-001" ^
  -H "deviceId: %DEVICE_ID%" ^
  -H "deviceType: %DEVICE_TYPE%" ^
  -H "traceparent: 00-trace-parent-local" ^
  -H "Authorization: %BEARER_TOKEN%" ^
  -H "apiVersion: %API_VERSION%" ^
  -H "subscription-key: %SUBSCRIPTION_KEY%" ^
  -H "X-Request-Id: %REQUEST_ID%" ^
  -d "{\"title\":\"Project milestone updated\",\"message\":\"The project moved to In Progress\",\"projectId\":\"KAN-100\",\"severity\":\"medium\"}"
echo.

echo --- Get Notification by ID ---
curl -s -X GET "%BASE_URL%/notifications/mock-notif-001" ^
  -H "consumerId: %CONSUMER_ID%" ^
  -H "sessionId: %SESSION_ID%" ^
  -H "sessionAppId: APP_SMP" ^
  -H "customerId: customer-001" ^
  -H "deviceId: %DEVICE_ID%" ^
  -H "deviceType: %DEVICE_TYPE%" ^
  -H "traceparent: 00-trace-parent-local" ^
  -H "Authorization: %BEARER_TOKEN%" ^
  -H "apiVersion: %API_VERSION%" ^
  -H "subscription-key: %SUBSCRIPTION_KEY%" ^
  -H "X-Request-Id: %REQUEST_ID%"
echo.

echo =^> Smoke test complete.
