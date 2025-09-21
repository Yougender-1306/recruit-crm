# Recruit CRM Assessment

## MySQL Configuration

- Install MySQL and ensure the server is running.
- Configure `src/main/resources/application.properties` with root credentials:
  ```properties
  spring.datasource.password=your_root_password

Replace your_root_password with the MySQL root user password

Run the application:
textmvn clean install
mvn spring-boot:run
Flyway will apply migrations. Check console for logs (e.g., "Successfully applied 1 migration").

GET Endpoints
Departments

GET /departments

Retrieve all departments.
Example Response: [{"id": 1, "name": "Human Resources", "budget": 150000.0}, ...]



Employees

GET /employees

Retrieve all employees with optional pagination.
Example URL: http://localhost:8080/employees?page=0&size=10
Example Response: Paginated EmployeeDTO list.


GET /employees/{id}

Retrieve detailed employee by ID.
Example URL: http://localhost:8080/employees/1
Example Response: {"id": 1, "name": "Alice Johnson", "email": "alice.johnson@example.com", ...}



GET with Filters

GET /employees

Apply filters to retrieve specific employees.
Query Parameters:

score (Integer, optional): Filter by performance review score.
reviewDate (String, e.g., 2025-09-01, optional): Filter by review date.
departments (List<String>, e.g., Human Resources,Information Technology, optional): Filter by department names.
projects (List<String>, e.g., HR System Upgrade, optional): Filter by project names.
page (Integer, default 0): Page number.
size (Integer, default 10): Page size.


Example URL: http://localhost:8080/employees?score=85&reviewDate=2025-09-01&departments=Human Resources&page=0&size=10
Example Response: Filtered and paginated EmployeeDTO list matching criteria.
Additional Examples:

Filter by score: http://localhost:8080/employees?score=90
Filter by department: http://localhost:8080/employees?departments=Information Technology
Combined filters: http://localhost:8080/employees?score=85&projects=HR System Upgrade





Testing

Use Postman or curl to test endpoints.
Verify application at http://localhost:8080.
Example: curl http://localhost:8080/departments
