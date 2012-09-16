#!/bin/sh

curl -d "{\"name\": \"$1\"}" --header "Content-Type: application/json" http://localhost:8080/api/add-department
echo "\n"
