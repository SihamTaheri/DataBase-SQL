# DataBase-SQL
Project README

This project, implemented in Java, facilitates database interactions using SQL queries. It provides a set of classes for establishing connections to a MySQL database, executing SQL queries, and handling result sets.


Usage:
------

To use the project:

1. Ensure you have a MySQL database server set up and running.
2. Import the project files into your Java development environment.
3. Configure the database connection parameters in the DBConnection class constructor (`server`, `port`, `user`, `pass`, `database`).
4. Compile the Java files.
5. Use the DBConnection class methods to interact with the database:
   - Use the `connect()` method to establish a connection to the database.
   - Use the `update()` method to execute SQL update statements (e.g., INSERT, UPDATE, DELETE).
   - Use the `query()` method to execute SQL SELECT queries and retrieve result sets.
   - Use the `close()` method to close the database connection when done.
