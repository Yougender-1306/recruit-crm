
## MySQL Configuration

To set up the MySQL database for this application, follow these steps:

1. **Install MySQL**:
   - Ensure MySQL Server is installed and running on your system.

2. **Configure MySQL Root Password**:
   - Open `src/main/resources/application.properties` and set the MySQL root credentials:
     ```properties
     spring.datasource.password=your_root_password
