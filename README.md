# Order API - Spring Boot REST API với PostgreSQL

Đây là một ứng dụng Spring Boot cung cấp CRUD REST API để quản lý đơn hàng (orders) với cơ sở dữ liệu PostgreSQL.

## Yêu cầu hệ thống

- Java 17+
- Maven 3.6+
- PostgreSQL 12+

## Cấu trúc cơ sở dữ liệu

```sql
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    order_id INT,
    user_id INT,
    total NUMERIC(10,2),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Cài đặt và chạy ứng dụng

### 🐳 Chạy với Docker Compose (Khuyến nghị)

#### Yêu cầu:
- Docker và Docker Compose
- Make (tùy chọn, để sử dụng Makefile)

#### Cách 1: Sử dụng Makefile (Đơn giản nhất)
```bash
# Xem tất cả lệnh có sẵn
make help

# Build và khởi động tất cả services
make build
make up

# Hoặc khởi động với pgAdmin (công cụ quản lý database)
make up-tools

# Xem logs
make logs

# Dừng services
make down

# Dọn dẹp hoàn toàn
make clean
```

#### Cách 2: Sử dụng Docker Compose trực tiếp
```bash
# Build images
docker-compose build

# Khởi động services
docker-compose up -d

# Khởi động với pgAdmin
docker-compose --profile tools up -d

# Xem logs
docker-compose logs -f

# Dừng services
docker-compose down

# Dọn dẹp volumes
docker-compose down -v
```

### 📱 Truy cập ứng dụng

- **API**: http://localhost:8089
- **pgAdmin** (nếu khởi động với tools): http://localhost:5050
  - Email: admin@example.com
  - Password: admin

### 🔧 Chạy thủ công (không Docker)

#### 1. Chuẩn bị cơ sở dữ liệu
```sql
CREATE DATABASE orderdb;
```

#### 2. Cấu hình database
Cập nhật file `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:54329/orderdb
spring.datasource.username=postgres
spring.datasource.password=password
```

#### 3. Chạy ứng dụng
```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### 1. Tạo đơn hàng mới
```http
POST /api/orders
Content-Type: application/json

{
    "orderId": 1001,
    "userId": 1,
    "total": 150.50,
    "status": "PENDING"
}
```

### 2. Lấy tất cả đơn hàng
```http
GET /api/orders
```

### 3. Lấy đơn hàng theo ID
```http
GET /api/orders/{id}
```

### 4. Lấy đơn hàng theo Order ID
```http
GET /api/orders/order-id/{orderId}
```

### 5. Lấy đơn hàng theo User ID
```http
GET /api/orders/user/{userId}
```

### 6. Lấy đơn hàng theo trạng thái
```http
GET /api/orders/status/{status}
```

### 7. Lấy đơn hàng theo User ID và trạng thái
```http
GET /api/orders/user/{userId}/status/{status}
```

### 8. Cập nhật đơn hàng (toàn bộ)
```http
PUT /api/orders/{id}
Content-Type: application/json

{
    "orderId": 1001,
    "userId": 1,
    "total": 200.00,
    "status": "COMPLETED"
}
```

### 9. Cập nhật đơn hàng (một phần)
```http
PATCH /api/orders/{id}
Content-Type: application/json

{
    "status": "COMPLETED"
}
```

### 10. Xóa đơn hàng
```http
DELETE /api/orders/{id}
```

### 11. Kiểm tra đơn hàng có tồn tại
```http
GET /api/orders/{id}/exists
```

## Ví dụ sử dụng với curl

### Tạo đơn hàng mới
```bash
curl -X POST http://localhost:8089/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 1006,
    "userId": 4,
    "total": 250.75,
    "status": "PENDING"
  }'
```

### Lấy tất cả đơn hàng
```bash
curl http://localhost:8089/api/orders
```

### Lấy đơn hàng theo ID
```bash
curl http://localhost:8089/api/orders/1
```

### Cập nhật trạng thái đơn hàng
```bash
curl -X PATCH http://localhost:8089/api/orders/1 \
  -H "Content-Type: application/json" \
  -d '{"status": "COMPLETED"}'
```

### Xóa đơn hàng
```bash
curl -X DELETE http://localhost:8089/api/orders/1
```

## 🐳 Docker Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Spring Boot   │    │   PostgreSQL    │    │    pgAdmin      │
│   Application   │    │    Database     │    │   (Optional)    │
│   Port: 8089    │────│   Port: 54329   │    │   Port: 5050    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Docker Services:

1. **order-api**: Spring Boot application
2. **postgres**: PostgreSQL database với dữ liệu mẫu
3. **pgadmin**: Web-based PostgreSQL administration (optional)

### Docker Features:

- ✅ Multi-stage build để tối ưu image size
- ✅ Health checks cho tất cả services
- ✅ Volume persistence cho database
- ✅ Network isolation
- ✅ Graceful shutdown
- ✅ Non-root user trong container

## Cấu trúc dự án

```
├── src/
│   ├── main/
│   │   ├── java/com/example/orderapi/
│   │   │   ├── controller/
│   │   │   │   └── OrderController.java
│   │   │   ├── entity/
│   │   │   │   └── Order.java
│   │   │   ├── repository/
│   │   │   │   └── OrderRepository.java
│   │   │   ├── service/
│   │   │   │   └── OrderService.java
│   │   │   └── OrderApiApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-docker.properties
│   │       └── schema.sql
│   └── test/
│       └── java/
├── docker-compose.yml
├── Dockerfile
├── Makefile
├── .dockerignore
├── pom.xml
└── README.md
```

## Tính năng

- ✅ CRUD operations đầy đủ
- ✅ Validation dữ liệu đầu vào
- ✅ Xử lý lỗi và response thống nhất
- ✅ Tìm kiếm theo nhiều tiêu chí
- ✅ Indexes cho hiệu suất tốt
- ✅ Transaction management
- ✅ Cross-origin support

## 🚀 Quick Start với Docker

```bash
# Clone repository
git clone <repository-url>
cd spring-boot-example

# Khởi động với Docker Compose
make build
make up

# Kiểm tra services đang chạy
make status

# Test API
curl http://localhost:8089/api/orders

# Xem logs
make logs-app
```

## 🛠️ Development

### Chạy trong môi trường development
```bash
# Chỉ khởi động PostgreSQL
docker-compose up -d postgres

# Chạy Spring Boot app locally
mvn spring-boot:run
```

### Debugging
```bash
# Xem logs chi tiết
make logs

# Truy cập database qua pgAdmin
make up-tools
# Mở http://localhost:5050
```

## Ghi chú

- ✅ **Docker Support**: Containerized với PostgreSQL
- ✅ **Health Checks**: Monitoring cho tất cả services  
- ✅ **Data Persistence**: PostgreSQL data được lưu trong Docker volume
- ✅ **Auto Schema**: Hibernate tự động tạo/cập nhật schema
- ✅ **Sample Data**: Dữ liệu mẫu được chèn tự động khi khởi động
- ✅ **pgAdmin**: Web interface để quản lý database
- ✅ **Makefile**: Các lệnh tiện ích cho Docker operations
- ✅ **Multi-stage Build**: Optimized Docker images
- ✅ **CORS Support**: API hỗ trợ CORS cho frontend