# 🚀 Hướng dẫn chạy Spring Boot Project trên Visual Studio Code

## 📋 Yêu cầu hệ thống

### 1. Java Development Kit (JDK)

- **JDK 17 hoặc cao hơn** (dự án đang dùng Java 24)
- Kiểm tra: `java -version` và `javac -version`
- Download: [Eclipse Temurin](https://adoptium.net/) hoặc [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)

### 2. Maven (Tùy chọn)

- Dự án có sẵn **Maven Wrapper** (`mvnw.cmd`), không cần cài Maven global
- Nếu muốn cài Maven: [Apache Maven](https://maven.apache.org/download.cgi)

### 3. SQL Server

- **SQL Server** chạy trên `localhost:1433`
- Database: `noithat`
- User: `sa`, Password: `123`

## 🔧 Cài đặt VS Code Extensions

### Extension bắt buộc:

1. **Extension Pack for Java** (ms-vscode.vscode-java-pack)

   - Bao gồm: Language Support, Debugger, Test Runner, Maven for Java, Project Manager for Java, IntelliCode

2. **Spring Boot Extension Pack** (pivotal.vscode-spring-boot)

   - Bao gồm: Spring Boot Tools, Spring Initializr, Spring Boot Dashboard

3. **Lombok Annotations Support** (vscjava.vscode-lombok)

### Cách cài đặt:

1. Mở VS Code
2. Nhấn `Ctrl + Shift + X` để mở Extensions
3. Tìm và cài đặt từng extension ở trên

## 📂 Mở dự án trong VS Code

1. **Mở VS Code**
2. **File → Open Folder** hoặc `Ctrl + K, Ctrl + O`
3. **Chọn thư mục:** `c:\D\Study\DTN\noithat`
4. **Đợi VS Code khởi tạo:**
   - VS Code sẽ tự động detect Maven project
   - Thanh status bar sẽ hiển thị "Java" và "Spring Boot"
   - Có thể mất vài phút để download dependencies

## ▶️ Cách chạy ứng dụng

### Phương pháp 1: Sử dụng VS Code Tasks (Khuyên dùng)

1. **Mở Command Palette:** `Ctrl + Shift + P`
2. **Gõ:** "Tasks: Run Task"
3. **Chọn:** "Spring Boot: Run"

### Phương pháp 2: Chạy từ main class

1. **Mở file:** `src/main/java/com/noithat/backend/NoithatApplication.java`
2. **Click nút "Run"** phía trên method `main()`
3. **Hoặc nhấn:** `F5` để debug

### Phương pháp 3: Sử dụng Terminal

```powershell
# Mở Terminal trong VS Code: Ctrl + `

# Cách 1: Sử dụng Maven Wrapper (Khuyên dùng)
.\mvnw spring-boot:run

# Cách 2: Nếu đã cài Maven global
mvn spring-boot:run
```

## 🔍 Kiểm tra ứng dụng

### 1. Console Log

- Kiểm tra Terminal trong VS Code
- Tìm dòng: `Started NoithatApplication in X.XXX seconds`
- Port: `8080`

### 2. Swagger UI

Mở trình duyệt và truy cập:

```
http://localhost:8080/swagger-ui.html
```

### 3. API Documentation

```
http://localhost:8080/api-docs
```

### 4. Test một số endpoint

```
GET http://localhost:8080/api/danh-muc
GET http://localhost:8080/api/san-pham
GET http://localhost:8080/api/khach-hang
```

## 🐛 Debug và Development

### 1. Đặt Breakpoint

- Click vào margin bên trái số dòng để đặt breakpoint
- Nhấn `F5` để start debug mode

### 2. Hot Reload

- VS Code tự động compile khi save file
- Spring Boot DevTools tự động restart ứng dụng

### 3. View Logs

- Console output trong Terminal tab
- Log level: DEBUG cho package `com.noithat.backend`

## 🔧 Build và Package

### Build project:

```powershell
# Clean và compile
.\mvnw clean compile

# Package thành JAR file
.\mvnw package -DskipTests
```

### Sử dụng VS Code Tasks:

1. `Ctrl + Shift + P`
2. "Tasks: Run Task"
3. Chọn task phù hợp:
   - "Maven: Clean"
   - "Maven: Compile"
   - "Maven: Clean Compile"
   - "Maven: Package"

## ❗ Troubleshooting

### 1. Lỗi compilation

```powershell
.\mvnw clean compile -X
```

### 2. Lỗi dependency

```powershell
.\mvnw dependency:resolve
```

### 3. Lỗi database connection

- Kiểm tra SQL Server đang chạy
- Kiểm tra connection string trong `application.properties`
- Kiểm tra username/password: `sa/123`

### 4. Port 8080 đã được sử dụng

```powershell
# Tìm process đang dùng port 8080
netstat -ano | findstr :8080

# Kill process (thay <PID> bằng Process ID)
taskkill /PID <PID> /F
```

### 5. Refresh Java workspace

1. `Ctrl + Shift + P`
2. "Java: Restart Projects"

### 6. Rebuild Maven project

1. `Ctrl + Shift + P`
2. "Java: Reload Projects"

## 📁 Cấu trúc dự án

```
noithat/
├── .vscode/                    # VS Code configuration
│   ├── tasks.json             # Build tasks
│   ├── launch.json            # Debug configuration
│   └── settings.json          # Workspace settings
├── src/main/java/com/noithat/backend/
│   ├── NoithatApplication.java     # 🚀 Main class - START HERE
│   ├── controller/                 # 🌐 REST API Controllers
│   ├── service/                   # 💼 Business Logic
│   ├── repository/                # 💾 Data Access Layer
│   ├── entity/                    # 🗃️ Database Entities
│   ├── dto/                       # 📦 Data Transfer Objects
│   ├── config/                    # ⚙️ Configuration
│   └── exception/                 # ❌ Exception Handling
├── src/main/resources/
│   ├── application.properties     # 🔧 App Configuration
│   ├── static/                   # 📁 Static Files
│   └── templates/                # 📄 Templates
├── target/                       # 📦 Compiled Files
├── pom.xml                      # 📋 Maven Configuration
├── mvnw.cmd                     # 🔨 Maven Wrapper (Windows)
└── README.md                    # 📖 Documentation
```

## ✅ Tính năng đã implement

- ✅ **REST API** với chuẩn response format
- ✅ **Swagger/OpenAPI** documentation
- ✅ **Request validation**
- ✅ **Global exception handling**
- ✅ **Logging** với SLF4J
- ✅ **CRUD operations** cho tất cả entities
- ✅ **Search và filter** endpoints
- ✅ **Pagination** support
- ✅ **MapStruct** mapping
- ✅ **Lombok** annotations

## 🚀 Các bước tiếp theo

1. **Cấu hình database** với dữ liệu mẫu
2. **Thêm authentication/authorization**
3. **Viết unit tests**
4. **Tối ưu performance**
5. **Deploy lên production**

---

## 🆘 Cần hỗ trợ?

Nếu gặp vấn đề, hãy kiểm tra:

1. ✅ Đã cài đúng Java version?
2. ✅ Đã cài đủ VS Code extensions?
3. ✅ SQL Server đang chạy?
4. ✅ Port 8080 có trống?
5. ✅ Có lỗi trong Terminal/Console?

**Happy Coding! 🎉**
