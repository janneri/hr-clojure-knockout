#!/bin/sh

curl -d "{\"firstname\": \"$1\", \"lastname\": \"$2\", \"department\": $3}" --header "Content-Type: application/json" http://localhost:8080/api/add-employee

echo "\n"

