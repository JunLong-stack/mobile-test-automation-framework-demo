#!/usr/bin/env bash
#
# Runs the Cucumber E2E suite against a booted Android emulator in CI.
# Invoked from .github/workflows/e2e.yml inside the android-emulator-runner.
#
set -euo pipefail

# Start Appium with its stdio redirected to a file. If it inherited the CI
# step's stdout/stderr, the step would hang waiting for the pipe to close.
appium --log-timestamp > appium.log 2>&1 &
APPIUM_PID=$!

# Wait for the Appium server to accept connections (max ~60s).
for i in $(seq 1 30); do
  if curl -sf http://127.0.0.1:4723/status > /dev/null; then
    echo "Appium is up"
    break
  fi
  echo "Waiting for Appium... ($i)"
  sleep 2
done

# Run the @e2e UI suite; capture the result so Appium is always cleaned up
# afterwards. The @api suite is exercised by a separate, emulator-free CI job.
set +e
mvn -B test -Dcucumber.filter.tags=@e2e
TEST_EXIT=$?
set -e

kill "$APPIUM_PID" 2>/dev/null || true

exit "$TEST_EXIT"
