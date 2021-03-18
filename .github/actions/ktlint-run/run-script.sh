#!/bin/sh

set -o pipefail

echo Using KtLint version: "$(ktlint --version)"

ktlint --android "**/src/**/*.kt" --reporter=plain --reporter=checkstyle,output=ktlint-report-in-checkstyle-format.xml