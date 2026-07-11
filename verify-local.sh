#!/bin/bash
# Smoke test for smp-notification-service
set -e

BASE_URL="http://localhost:8080"
REQUEST_ID=$(uuidgen 2>/dev/null || cat /proc/sys/kernel/random/uuid)

smoke_post() {
  local name=$1
  local path=$2
  local body=$3
  echo "--- Smoke POST $name ---"
  curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL$path" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer <fill-test-token>" \
    -H "consumerId: SMP" \
    -H "sessionId: $REQUEST_ID" \
    -H "sessionAppId: SMP-LOCAL" \
    -H "customerId: <fill>" \
    -H "deviceId: <fill>" \
    -H "deviceType: AND" \
    -H "traceparent: 00-$(echo $REQUEST_ID | tr -d '-')-$(echo $REQUEST_ID | cut -c1-16)-01" \
    -H "apiVersion: v1" \
    -H "subscription-key: <fill>" \
    -H "X-Request-Id: $REQUEST_ID" \
    -d "$body"
  echo ""
}

smoke_get() {
  local name=$1
  local path=$2
  echo "--- Smoke GET $name ---"
  curl -s -o /dev/null -w "%{http_code}" -X GET "$BASE_URL$path" \
    -H "Authorization: Bearer <fill-test-token>" \
    -H "consumerId: SMP" \
    -H "sessionId: $REQUEST_ID" \
    -H "sessionAppId: SMP-LOCAL" \
    -H "customerId: <fill>" \
    -H "deviceId: <fill>" \
    -H "deviceType: AND" \
    -H "traceparent: 00-$(echo $REQUEST_ID | tr -d '-')-$(echo $REQUEST_ID | cut -c1-16)-01" \
    -H "apiVersion: v1" \
    -H "subscription-key: <fill>" \
    -H "X-Request-Id: $REQUEST_ID"
  echo ""
}

smoke_post "createNotification" "/notifications" '{"title":"Test Notification","message":"Test message body","projectId":"proj-test-001"}'
smoke_get "getNotificationById" "/notifications/mock-notification-id-001"

echo "Smoke tests complete."
