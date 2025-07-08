# JSON Dataset Query API – Spring Boot Application

This project is a Spring Boot backend application that supports dynamic insertion and querying of JSON-based records in a MySQL database. It provides REST APIs to **insert**, **group by**, and **sort** records dynamically using native SQL queries with the **EntityManager**.

---

## 🔧 Features

- 📦 Insert dynamic JSON records into datasets
- 🔍 Query records with:
  - `groupBy` (e.g., by department)
  - `sortBy` with `asc` or `desc` (e.g., by age)
- 🛢️ Data stored in MySQL using a `json` column
- 💡 Uses native SQL queries via `EntityManager`
- ✅ Clean architecture with DTOs, Services, Controllers
- 🧪 Easily testable via Postman

---

## 🚀 API Endpoints

### 📥 Insert Record

- **POST** `/api/dataset/{datasetName}/record`
- **Request Body** (JSON):

```json
{
  "id": 1,
  "name": "John Doe",
  "age": 30,
  "department": "Engineering"
}
```

- **Response**:

```json
{
  "message": "Record added successfully",
  "dataset": "employee_dataset",
  "recordId": 1
}
```

---

### 📊 Group By

- **GET** `/api/dataset/{datasetName}/query?groupBy={key}`  
- Example: `GET /api/dataset/employee_dataset/query?groupBy=department`

---

### 🔢 Sort By

- **GET** `/api/dataset/{datasetName}/query?sortBy={key}&order={asc|desc}`  
- Example: `GET /api/dataset/employee_dataset/query?sortBy=age&order=asc`

---

## 🧪 Sample Records

You can use these sample JSON payloads to insert test data:  
(See previous messages for full list.)

---

## 💾 MySQL Table DDL

```sql
CREATE TABLE json_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dataset_name VARCHAR(255),
    data JSON
);
```

---

## ⚙️ Application Properties

Configure your DB credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jsondb
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ How to Run

1. Clone the repo  
2. Create `jsondb` schema in MySQL  
3. Configure `application.properties`  
4. Run the Spring Boot app:

```bash
mvn spring-boot:run
```

---

## 🧪 Testing via Postman

- Use Postman to:
  - Insert multiple records
  - Run GET queries for `groupBy` or `sortBy`
- All endpoints follow REST conventions

---

## 📁 Project Structure

```
src/
├── controller
├── service
├── entity
├── dto
└── JsonQueryApplication.java
```

---

## 📚 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL 8.x
- Jackson (for JSON processing)

---

## ✅ Requirements

- Java 17+
- Maven 3.6+
- MySQL 8+

---
