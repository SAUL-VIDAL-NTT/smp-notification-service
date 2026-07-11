#!/bin/bash
# Run smp-notification-service locally
set -e

# Load local env vars
if [ -f .env.local ]; then
  export $(grep -v '^#' .env.local | xargs)
fi

echo "Starting smp-notification-service..."
mvn spring-boot:run \
  -Dspring-boot.run.jvmArguments="-DPROFILE=${PROFILE} -DMOCKS=${MOCKS}"
