# Hướng dẫn chạy Spring Boot Project trên Visual Studio Code

## 1. Cài đặt Extension Pack cần thiết

### 1.1. Extension Pack for Java

Cài đặt Extension Pack for Java từ Microsoft (bao gồm tất cả extension Java cần thiết):

- **Extension Pack for Java** (ms-vscode.vscode-java-pack)
- Bao gồm: Language Support for Java, Debugger for Java, Test Runner for Java, Maven for Java, Project Manager for Java, Visual Studio IntelliCode

### 1.2. Spring Boot Extension Pack

Cài đặt Spring Boot Extension Pack:

- **Spring Boot Extension Pack** (pivotal.vscode-spring-boot)
- Bao gồm: Spring Boot Tools, Spring Initializr Java Support, Spring Boot Dashboard

### 1.3. Lombok Annotations Support

- **Lombok Annotations Support for VS Code** (vscjava.vscode-lombok)

## 2. Cấu hình môi trường

### 2.1. Kiểm tra Java Version

```powershell
java -version
javac -version
```

- Đảm bảo Java 17+ đã được cài đặt (dự án đang dùng Java 24)
- Nếu chưa có, tải từ: https://adoptium.net/

### 2.2. Kiểm tra Maven

```powershell
mvn -version
```

- Nếu chưa có Maven, tải từ: https://maven.apache.org/download.cgi
- Hoặc sử dụng Maven Wrapper có sẵn trong dự án (mvnw.cmd)

### 2.3. Kiểm tra SQL Server

- Đảm bảo SQL Server đang chạy trên localhost:1433
- Database "noithat" đã tồn tại
- User: sa, Password: 123 (như trong application.properties)

## 3. Mở dự án trong VS Code

### 3.1. Mở thư mục dự án

1. Mở VS Code
2. File → Open Folder
3. Chọn thư mục: `c:\D\Study\DTN\noithat`

### 3.2. Đợi VS Code khởi tạo

- VS Code sẽ tự động detect Maven project
- Đợi Extension Pack for Java khởi tạo workspace
- Kiểm tra thanh status bar phía dưới có hiển thị "Java" và "Spring Boot"

## 4. Build và chạy dự án

### 4.1. Clean và compile project

Mở Terminal trong VS Code (Ctrl + `):

```powershell
# Sử dụng Maven Wrapper
.\mvnw clean compile

# Hoặc nếu đã có Maven global
mvn clean compile
```

### 4.2. Chạy ứng dụng

#### Cách 1: Sử dụng VS Code Task (Khuyên dùng)

1. Ctrl + Shift + P → "Tasks: Run Task"
2. Chọn "Spring Boot: Run Application"

#### Cách 2: Sử dụng Terminal

```powershell
# Sử dụng Maven Wrapper
.\mvnw spring-boot:run

# Hoặc nếu đã có Maven global
mvn spring-boot:run
```

#### Cách 3: Chạy từ main class

1. Mở file `src/main/java/com/noithat/backend/NoithatApplication.java`
2. Click vào biểu tượng "Run" hoặc "Debug" phía trên main method
3. Hoặc nhấn F5 để debug

### 4.3. Kiểm tra ứng dụng đã chạy

- Ứng dụng sẽ chạy trên port 8080
- Kiểm tra console log trong VS Code Terminal
- Tìm dòng: "Started NoithatApplication in X.XXX seconds"

## 5. Truy cập các endpoint

### 5.1. Swagger UI

Mở trình duyệt và truy cập:

```
http://localhost:8080/swagger-ui.html
```

### 5.2. API Documentation

```
http://localhost:8080/api-docs
```

### 5.3. Test API endpoints

Ví dụ:

```
GET http://localhost:8080/api/danh-muc
GET http://localhost:8080/api/san-pham
GET http://localhost:8080/api/khach-hang
```

## 6. Debug và Development

### 6.1. Debug mode

1. Đặt breakpoint bằng cách click vào margin bên trái số dòng
2. Nhấn F5 để start debug
3. Hoặc click "Debug" trên main method của NoithatApplication

### 6.2. Hot reload/Live reload

- VS Code sẽ tự động compile khi save file
- Spring Boot DevTools sẽ tự động restart ứng dụng khi có thay đổi

### 6.3. View logs

- Console output hiển thị trong Terminal tab
- Log level được set là DEBUG cho package com.noithat.backend

## 7. Troubleshooting

### 7.1. Lỗi compilation

```powershell
.\mvnw clean compile -X
```

### 7.2. Lỗi dependency

```powershell
.\mvnw dependency:resolve
```

### 7.3. Lỗi database connection

- Kiểm tra SQL Server đang chạy
- Kiểm tra connection string trong application.properties
- Kiểm tra user/password: sa/123

### 7.4. Lỗi port 8080 đã được sử dụng

Tìm và kill process đang sử dụng port 8080:

```powershell
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

### 7.5. Làm mới workspace

1. Ctrl + Shift + P
2. "Java: Restart Projects"

## 8. Cấu trúc dự án

```
noithat/
├── src/main/java/com/noithat/backend/
│   ├── NoithatApplication.java          # Main class
│   ├── controller/                      # REST Controllers
│   ├── service/                         # Business Logic Services
│   ├── repository/                      # Data Access Layer
│   ├── entity/                          # JPA Entities
│   ├── dto/                            # Data Transfer Objects
│   ├── config/                         # Configuration classes
│   └── exception/                      # Exception handling
├── src/main/resources/
│   ├── application.properties          # Configuration
│   ├── static/                         # Static resources
│   └── templates/                      # Templates
└── pom.xml                            # Maven configuration
```

## 9. Tính năng chính đã implement

- ✅ REST API với chuẩn response format
- ✅ Swagger/OpenAPI documentation
- ✅ Request validation
- ✅ Exception handling
- ✅ Logging
- ✅ CRUD operations cho các entity
- ✅ Search và filter endpoints
- ✅ Pagination support

## 10. Next Steps

1. Cấu hình database với dữ liệu mẫu
2. Thêm authentication/authorization
3. Tối ưu performance
4. Viết unit tests
5. Deploy lên production environment

---

**Lưu ý:** Đảm bảo đã cài đặt đầy đủ các extension và môi trường trước khi chạy dự án.
