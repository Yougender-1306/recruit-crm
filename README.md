
## MySQL Configuration

To set up the MySQL database for this application, follow these steps:

1. **Install MySQL**:
   - Ensure MySQL Server is installed and running on your system.

2. **Configure MySQL Root Password**:
   - Open `src/main/resources/application.properties` and set the MySQL root credentials:
     ```properties
     spring.datasource.password=your_root_password


Replace your_root_password with the password for the MySQL root user. If no password is set, leave it as an empty string (spring.datasource.password=).


Run the Application:

Navigate to the project directory (D:\Downloads\Recruit CRM assessment) in a terminal.
Run:
textmvn clean install
mvn spring-boot:run

Flyway will automatically apply migrations and populate the database.


Verify Connection:

Check the console for Flyway logs (e.g., "Successfully applied 1 migration") and ensure no database connection errors occur.



GET Endpoints
The application provides the following GET endpoints to retrieve data. The database is pre-populated via Flyway migrations, so no POST requests are needed.
Departments

GET /departments

Description: Retrieve all departments.
Example Response: [{"id": 1, "name": "Human Resources", "budget": 150000.0}, ...]



Employees

GET /employees

Description: Retrieve employees with optional filters and pagination.
Query Parameters:

score (Integer, optional): Filter by performance review score.
reviewDate (String, optional, e.g., 2025-09-01): Filter by review date.
departments (List<String>, optional, e.g., Human Resources,Information Technology): Filter by department names.
projects (List<String>, optional, e.g., HR System Upgrade): Filter by project names.
page (Integer, default 0): Page number.
size (Integer, default 10): Page size.


Example URL: http://localhost:8080/employees?score=85&reviewDate=2025-09-01&departments=Human Resources&page=0&size=10
Example Response: Paginated list of EmployeeDTOs.


GET /employees/{id}

Description: Retrieve detailed employee data by ID.
Example URL: http://localhost:8080/employees/1
Example Response: {"id": 1, "name": "Alice Johnson", "email": "alice.johnson@example.com", ...}



Testing

Use a tool like Postman or curl to test the endpoints.
Start the application and access http://localhost:8080 to verify it’s running.
Example curl command for departments:
textcurl http://localhost:8080/departments


Notes

Ensure the MySQL server is running before starting the application.
The database is pre-populated via V1__initial_data.sql in src/main/resources/db/migration/.
For troubleshooting, check the console for errors or enable spring.jpa.show-sql=true for SQL logs.

text### Changes Made
- Removed the `CREATE DATABASE epms_db` step since Flyway and the application configuration will handle database initialization.
- Kept the focus on configuring the MySQL root password and providing `GET` endpoint details.

### Instructions
- Replace the existing `README.md` in `D:\Downloads\Recruit CRM assessment\` with this content.
- Update `application.properties` with your MySQL root password.
- Proceed with running the application once your network issue is resolved and you’ve pushed to GitHub.

Let me know if you need further refinements!
