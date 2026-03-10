# GraphQL vs REST API — Demo Project

Dự án Spring Boot minh họa trực quan sự khác biệt giữa **REST API** và **GraphQL** thông qua một giao diện demo tương tác.

---

## Công nghệ sử dụng

- **Java 17** + **Spring Boot 4.0**
- **Spring for GraphQL** — GraphQL endpoint
- **Spring Web MVC** — REST endpoints
- **In-memory data store** — không dùng database
- **Vanilla HTML/CSS/JS** — frontend tĩnh

---

## Cài đặt & Chạy

```bash
# Clone về máy
git clone <repo-url>
cd graphql-vs-rest-demo

# Khởi động server (cần Java 17+)
.\mvnw spring-boot:run        # Windows
./mvnw spring-boot:run        # macOS / Linux
```

Server khởi động tại `http://localhost:8080`. Khi thấy `Started DemoApplication` trong console là sẵn sàng.

---

## Các endpoint

| Loại | Endpoint | Mô tả |
|---|---|---|
| REST | `GET /api/authors/{id}` | Lấy thông tin tác giả (có `Cache-Control`) |
| REST | `GET /api/authors/{id}/posts` | Lấy danh sách bài viết (có `Cache-Control`) |
| REST | `POST /api/authors/{id}/posts` | Tạo bài viết mới (trả về `201 Created`) |
| GraphQL | `POST /graphql` | GraphQL endpoint duy nhất |
| GraphQL | `GET /graphiql` | GraphiQL Playground (UI trực quan) |
| Static | `GET /` | Giao diện demo |

---

## Tính năng demo

### So sánh trực tiếp REST vs GraphQL
- **Over-fetching:** REST trả về toàn bộ fields dù client chỉ cần một vài field — GraphQL trả về chính xác những gì được yêu cầu.
- **Under-fetching:** REST cần 2 request tuần tự để lấy tác giả + bài viết — GraphQL gộp chung trong 1 request duy nhất.

### RACE — Cuộc đua có cache
- **Lần 1 (không cache):** Cả hai đều fetch từ network. GraphQL thắng nhờ ít request hơn.
- **Lần 2 (có cache):** REST tận dụng `Cache-Control` → browser cache → ~0ms. GraphQL dùng POST nên không cache được, vẫn phải round-trip lên server.

### Điểm mạnh riêng của REST
- **HTTP Status Code:** REST trả `404` khi không tìm thấy. GraphQL luôn trả `200 OK` kể cả khi có lỗi.
- **HTTP Caching:** REST GET tích hợp tự nhiên với browser cache và CDN. GraphQL POST thì không.
- **URL rõ nghĩa:** REST URL có thể bookmark, share, mở thẳng trên browser.

---

## Cấu trúc project

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── DemoApplication.java          # Entry point
│   │   ├── DataStore.java                # In-memory data
│   │   ├── Author.java / Post.java       # Models
│   │   ├── AuthorRestController.java     # REST controllers
│   │   ├── AuthorGraphqlController.java  # GraphQL resolvers
│   │   └── PostRequestBody.java          # Request DTO
│   └── resources/
│       ├── graphql/schema.graphqls       # GraphQL schema
│       ├── static/index.html             # Giao diện demo
│       └── application.properties
```

