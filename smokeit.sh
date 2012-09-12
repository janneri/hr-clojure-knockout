curl -d "{\"foo\": \"bar\"}" --header "Content-Type: application/json" http://localhost:8080/bar
echo
curl -d "{\"name\": \"osasto1\"}" --header "Content-Type: application/json" http://localhost:8080/api/add-department
echo
curl --header "Content-Type: application/json" http://localhost:8080/api/departments
echo

