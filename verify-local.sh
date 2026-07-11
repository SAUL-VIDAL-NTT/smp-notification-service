#!/bin/bash
# verify-local.sh — smoke test for smp-notification-service running locally

set -e

BASE_URL="${1:-http://localhost:8080}"
BEARER_TOKEN="${BEARER_TOKEN:-Bearer test-local-token}"
REQUEST_ID="req-$(uuidgen 2>/dev/null || date +%s)"
CONSUMER_ID="SMP"
SESSION_ID="$(uuidgen 2>/dev/null || echo session-local-001)"
DEVICE_ID="device-local-001"
DEVICE_TYPE="AND"
API_VERSION="v1"
SUBSCRIPTION_KEY="test-subscription-key"

echo "==> Smoke test: smp-notification-service @ $BASE_URL"
echo ""

smoke_post() {
  local desc="$1"
  local path="$2"
  local body="$3"
  echo "--- $desc ---"
  curl -s -o /tmp/smp_resp.json -w "HTTP %{http_code}\n" \
    -X POST "$BASE_URL$path" \
    -H "Content-Type: application/json" \
    -H "consumerId: $CONSUMER_ID" \
    -H "sessionId: $SESSION_ID" \
    -H "sessionAppId: APP_SMP" \
    -H "customerId: customer-001" \
    -H "deviceId: $DEVICE_ID" \
    -H "deviceType: $DEVICE_TYPE" \
    -H "traceparent: 00-trace-parent-local" \
    -H "Authorization: $BEARER_TOKEN" \
    -H "apiVersion: $API_VERSION" \
    -H "subscription-key: $SUBSCRIPTION_KEY" \
    -H "X-Request-Id: $REQUEST_ID" \
    -d "$body"
  cat /tmp/smp_resp.json | python3 -m json.tool 2>/dev/null || cat /tmp/smp_resp.json
  echo ""
}

smoke_get() {
  local desc="$1"
  local path="$2"
  echo "--- $desc ---"
  curl -s -o /tmp/smp_resp.json -w "HTTP %{http_code}\n" \
    -X GET "$BASE_URL$path" \
    -H "consumerId: $CONSUMER_ID" \
    -H "sessionId: $SESSION_ID" \
    -H "sessionAppId: APP_SMP" \
    -H "customerId: customer-001" \
    -H "deviceId: $DEVICE_ID" \
    -H "deviceType: $DEVICE_TYPE" \
    -H "traceparent: 00-trace-parent-local" \
    -H "Authorization: $BEARER_TOKEN" \
    -H "apiVersion: $API_VERSION" \
    -H "subscription-key: $SUBSCRIPTION_KEY" \
    -H "X-Request-Id: $REQUEST_ID"
  cat /tmp/smp_resp.json | python3 -m json.tool 2>/dev/null || cat /tmp/smp_resp.json
  echo ""
}

# POST /notifications
smoke_post "Create Notification" "/notifications" \
  '{"title":"Project milestone updated","message":"The project moved to In Progress","projectId":"KAN-100","severity":"medium"}'

# GET /notifications/{id} — use 'mock-notif-001' for mock profile
smoke_get "Get Notification by ID (mock)" "/notifications/mock-notif-001"

echo "==> Smoke test complete."
