# Database Utils

---

## 🔎 Explanations

This mod includes some utilities for working with databases.   
The most popular connectors are included directly in the mod JAR, which avoids having to import them into each mod and helps reduce the overall size of your projects.  

To import this library, you must add the following to your build.gradle file.
```groovy
repositories {
    maven { url "https://api.modrinth.com/maven" }
}

dependencies {
    implementation "maven.modrinth:database-utils:1.0.0"
}
```

## 🖥️ Usage

Configuration is managed by this library. By using the following line, a `.properties` file is automatically created (if it does not already exist) and loaded from the `config` folder.

```java
Config config = new Config(MOD_ID);
```

In this example,  the file will be named `MOD_ID.properties`.  
Here is an example configuration  file.

```properties
storage=postgres
host=127.0.0.1
port=5432
database=minecraft_db
user=user
password=password
```

| Key      | Description                                                                         |
|----------|-------------------------------------------------------------------------------------|
| storage  | Database type. Must be one of: `postgres`, `mysql`, `mariadb`, `sqlite`, or `test`. |
| host     | IP address or hostname of the database server                                       |
| port     | Port used by the database server.                                                   |
| database | Name of the database.                                                               |
| user     | User used to connect to the database.                                               |
| password | Password of the user.                                                               |


Then, you need to define your own class `MyCustomDB`, which extends `DatabaseQueries`.  
This class is intended to contain all SQL related methods.
In this context, you have access to some method from `DatabaseQueries`, such as `getType`, which help you create an implementation compatible with multiple databases.

```java
public class MyCustomDB extends DatabaseQueries {

    public MyCustomDB(Database db) {
        super(db);
    }
    
    @Override
    public void init() {
        try (Connection connection = getConnection()) {
            /* ... */
        } catch (SQLException | DatabaseLinkException e) {
            LOGGER.error("Unable to initialize: {}", e.getMessage());
        }
    }
    
    /* other methods */
    
    public void someMethod() {
        /* ... */
    }
}
```

You will also need to create a database instance. This is where the `Config` object is used. The following code snippet retrieves the database defined in the configuration and initializes your `DatabaseQueries` implementation.

```java
Database database = DatabaseUtils.getDatabase(config);
MyCustomDB custom = new MyCustomDB(database);
```

You can now call any query defined in your `DatabaseQueries` implementation through your `custom` instance.  

```java
custom.someMethod();
```