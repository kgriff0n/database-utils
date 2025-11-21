# Database Utils

---

## 🔎 Explanations

This mod includes some utilities for working with databases.   
The most popular connectors are included directly in the mod JAR, which avoids having to import them into each mod and helps reduce the overall size of your projects.  
## 🖥️ Usage

First, you need to define your own class `MyCustomDB`, which extends `DatabaseQueries`.  
This class is intended to contain all SQL related methods.
In this context, you have access to some method from `DatabaseQueries`, such as `getType`, which help you create an implementation compatible with multiple databases.

```java
public class MyCustomDB extends DatabaseQueries {

    @Override
    public void init() {
        try (Connection connection = getConnection()) {
            /* ... */
        } catch (SQLException | DatabaseLinkException e) {
            LOGGER.error("Unable to initialize: {}", e.getMessage());
        }
    }
    
    /* other methods */
}
```

You will also need to create a database instance. Here is a little code snippet that creates a database based on a string value. This value can be obtained, for example, from a configuration file.
```java
String name = /* ... */;
Type type = DatabaseUtils.getType(name);
Database db;
switch (type) {
    case MYSQL -> db = new MySQL(/* ... */);
    case MARIADB -> db = new MariaDB(/* ... */);
    case POSTGRESQL -> db = new PostgreSQL(/* ... */);
    case SQLITE -> db = new SQLite(/* ... */);
    case TEST -> db = new TestDB(/* ... */);
}
```
Currently, this mod supports four databases:
- MySQL
- MariaDB
- PostgreSQL
- SQLite

A test database is also provided if you want to do some tests using stub values.  
Once your database has been initialized, you need to link it to your `MyCustomDB` implementation.

```java
MyCustomDB custom = new MyCustomDB();
custom.link(db);

// You can now use methods that contain SQL
custom.init();
/* ... */
```
