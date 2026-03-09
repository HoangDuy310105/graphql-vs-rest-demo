# BẢN KẾ HOẠCH & KỊCH BẢN DEMO THUYẾT TRÌNH (GraphQL vs REST API)

***Môn học:*** [Điền tên môn học]
***Đề tài:*** So sánh kiến trúc GraphQL và REST API
***Vai trò Demo:*** Trình diễn cách giải quyết vấn đề của REST bằng GraphQL.
***Thời gian Demo dự kiến:*** 5 Phút.

---

## 1. MỤC ĐÍCH CỦA BÀI DEMO
Bài Demo này không nhằm mục đích hướng dẫn code, mà để **chứng minh bằng mắt thật** cho Giảng viên thấy 2 khuyết điểm lớn nhất của REST API truyền thống và cách GraphQL giải quyết nó một cách thanh lịch như thế nào.

**Hai vấn đề cốt lõi cần làm rõ:**
1.  **Over-fetching (Thừa dữ liệu):** REST trả về quá nhiều dữ liệu không cần thiết trên 1 màn hình cụ thể, gây lãng phí băng thông mạng (đặc biệt là 3G/4G trên Mobile).
2.  **Under-fetching (Thiếu dữ liệu):** REST bắt ứng dụng phải gọi nhiều API liên tiếp mới gom đủ dữ liệu hiển thị cho 1 giao diện, gây chậm trễ (tăng độ trễ HTTP Request).

---

## 2. BỐI CẢNH (BÀI TOÁN ĐẶT RA)
Giả định chúng ta đang phát triển một Ứng dụng Mobile đọc Blog.
*   **Yêu cầu tính năng:** Ở Màn hình chính của App, chúng ta **chỉ cần hiển thị [Tên của Tác giả] và [Tiêu đề các bài viết của họ]**.
*   **Điều kiện:** Không hiển thị Email, Số điện thoại hay Nội dung chi tiết bài viết để tiết kiệm pin và dung lượng mạng.

---

## 3. CÁCH CHẠY SOURCE CODE TẠI NHÀ (CHUẨN BỊ TRƯỚC)

*Dự án đã được code bằng Spring Boot (Java).*

**Bước 1: Mở thư mục dự án**
*   Mở Terminal (Command Prompt / PowerShell).
*   Gõ lệnh di chuyển vào thư mục code:
    ```bash
    cd C:\Users\ADMIN\.gemini\antigravity\scratch\graphql-vs-rest-demo
    ```

**Bước 2: Khởi động Server Máy chủ**
*   Gõ lệnh sau để bật Server lên (Cần máy tính có kết nối mạng ở lần đầu tĩnh tiến):
    ```bash
    .\mvnw spring-boot:run
    ```
*   *Lưu ý:* Khi thấy dòng chữ `Started DemoApplication...` nghĩa là máy chủ đã sẵn sàng ở cổng `8080`. Không tắt cửa sổ Terminal này trong suốt quá trình thuyết trình.

---

## 4. KỊCH BẢN THUYẾT TRÌNH CỤ THỂ TRÊN LỚP (TỪNG BƯỚC)

*(Mở sẵn trình duyệt Web ra màn hình máy chiếu)*.

### Giai đoạn 1: Demo lỗi OVER-FETCHING của REST (Phút 12:00)
> **(Lời thoại):** *"Thưa thầy cô, quay lại bài toán trên App Mobile. Nhu cầu của em là lấy **Tên Tác giả** số 1. Với thiết kế REST truyền thống, em sẽ gọi đường dẫn lấy thông tin user như sau:"*

👉 **(Hành động):** Mở Tab 1, gõ URL: `http://localhost:8080/api/authors/1`

> **(Lời thoại chốt lỗi):** *"Thầy cô thấy không ạ? Màn hình điện thoại bé xíu chỉ cần mỗi chữ 'Nguyen Van A', nhưng API trả về cho em nguyên một cục JSON chứa đủ combo: Tuổi, Điện thoại, Email. Đây là Over-fetching, rất lãng phí băng thông 4G của người dùng tải App!"*

### Giai đoạn 2: Demo lỗi UNDER-FETCHING của REST (Phút 13:00)
> **(Lời thoại):** *"Tiếp theo, ứng dụng cần lấy danh sách Tiêu đề bài viết để kẹp chung vào với cái Tên đó. Nhưng cái API ban nãy không đáp ứng đủ. Em **bắt buộc phải móc sang một cổng API thứ 2**."*

👉 **(Hành động):** Mở Tab 2 (Giữ nguyên Tab 1), gõ URL: `http://localhost:8080/api/authors/1/posts`

> **(Lời thoại chốt lỗi):** *"Đây là lỗi Under-fetching (Gửi 1 Request không đủ data). Điều này bắt máy tính của người dùng phải chọc vào Server tận 2 lần, chờ phản hồi 2 vòng, làm App load chậm hơn rất nhiều."*

### Giai đoạn 3: "Biểu diễn phép màu" của GraphQL (Phút 14:00 - ĂN ĐIỂM)
> **(Lời thoại):** *"Nhưng với công nghệ GraphQL, mọi thứ khác biệt hoàn toàn. Dù dữ liệu phức tạp đến mấy, em chỉ giao tiếp với đúng **1 Endpoint (đường dẫn) duy nhất**."*

👉 **(Hành động):** Mở Tab 3, gõ URL: `http://localhost:8080/graphiql`

> **(Lời thoại):** *"Đây là giao diện Playground. Em sẽ gửi một yêu cầu (Query) với cú pháp giống hệt cấu trúc JSON. Em bảo GraphQL: Này, vào cơ sở dữ liệu lấy cho tôi đúng **Name** (Tên), và gọi luôn cho tôi **Title** (Tiêu đề bài) ra đây."*

👉 **(Hành động):** Copy & Paste đoạn code này vào ô trống bên trái và bấm nút ▶️ (Play/Run) màu kem ở giữa:

```graphql
query {
  author(id: "1") {
    name
    posts {
      title
    }
  }
}
```

### Giai đoạn 4: Phân tích kết quả & Chốt bài (Phút 15:00)
*(Chỉ tay vào cột Kết quả trả về bên phải màn hình)*

> **(Lời thoại chốt hạ):** *"Thầy cô cùng xem kết quả trả về ạ!
> Thứ 1: Nó trả về chính xác [Tên] và [Tiêu đề]. Không có số điện thoại hay email thừa thãi nào cả (Giải quyết triệt để **Over-fetching**).
> Thứ 2: Dữ liệu của Tác giả và Bài viết được móc nối lồng vào nhau, xuất hiện đầy đủ chỉ trong **ĐÚNG 1 LẦN GỌI DUY NHẤT** lên máy chủ (Giải quyết triệt để **Under-fetching**).
> 
> Nhờ tính linh hoạt này, Client (người làm UI App) được toàn quyền kiểm soát dữ liệu mình muốn mà không cần nhờ Backend tạo thêm API mới. GraphQL đặc biệt phù hợp cho các hệ thống phức tạp, đa nền tảng. Em xin kết thúc bài Demo ạ!"*




BẢN KẾ HOẠCH & KỊCH BẢN DEMO THUYẾT TRÌNH (GraphQL vs REST API)
Môn học: [Điền tên môn học] Đề tài: So sánh kiến trúc GraphQL và REST API Vai trò Demo: Trình diễn cách giải quyết vấn đề của REST bằng GraphQL. Thời gian Demo dự kiến: 5 Phút.

1. MỤC ĐÍCH CỦA BÀI DEMO
Bài Demo này không nhằm mục đích hướng dẫn code, mà để chứng minh bằng mắt thật cho Giảng viên thấy 2 khuyết điểm lớn nhất của REST API truyền thống và cách GraphQL giải quyết nó một cách thanh lịch như thế nào.

Hai vấn đề cốt lõi cần làm rõ:

Over-fetching (Thừa dữ liệu): REST trả về quá nhiều dữ liệu không cần thiết trên 1 màn hình cụ thể, gây lãng phí băng thông mạng (đặc biệt là 3G/4G trên Mobile).
Under-fetching (Thiếu dữ liệu): REST bắt ứng dụng phải gọi nhiều API liên tiếp mới gom đủ dữ liệu hiển thị cho 1 giao diện, gây chậm trễ (tăng độ trễ HTTP Request).
2. BỐI CẢNH (BÀI TOÁN ĐẶT RA)
Giả định chúng ta đang phát triển một Ứng dụng Mobile đọc Blog.

Yêu cầu tính năng: Ở Màn hình chính của App, chúng ta chỉ cần hiển thị [Tên của Tác giả] và [Tiêu đề các bài viết của họ].
Điều kiện: Không hiển thị Email, Số điện thoại hay Nội dung chi tiết bài viết để tiết kiệm pin và dung lượng mạng.
3. CÁCH CHẠY SOURCE CODE TẠI NHÀ (CHUẨN BỊ TRƯỚC)
Dự án đã được code bằng Spring Boot (Java).

Bước 1: Mở thư mục dự án

Mở Terminal (Command Prompt / PowerShell).
Gõ lệnh di chuyển vào thư mục code:
bash
cd C:\Users\ADMIN\.gemini\antigravity\scratch\graphql-vs-rest-demo
Bước 2: Khởi động Server Máy chủ

Gõ lệnh sau để bật Server lên (Cần máy tính có kết nối mạng ở lần đầu tĩnh tiến):
bash
.\mvnw spring-boot:run
Lưu ý: Khi thấy dòng chữ Started DemoApplication... nghĩa là máy chủ đã sẵn sàng ở cổng 8080. Không tắt cửa sổ Terminal này trong suốt quá trình thuyết trình.
4. KỊCH BẢN THUYẾT TRÌNH CỤ THỂ TRÊN LỚP (TỪNG BƯỚC)
(Mở sẵn trình duyệt Web ra màn hình máy chiếu).

Giai đoạn 1: Demo lỗi OVER-FETCHING của REST (Phút 12:00)
(Lời thoại): "Thưa thầy cô, quay lại bài toán trên App Mobile. Nhu cầu của em là lấy Tên Tác giả số 1. Với thiết kế REST truyền thống, em sẽ gọi đường dẫn lấy thông tin user như sau:"

👉 (Hành động): Mở Tab 1, gõ URL: http://localhost:8080/api/authors/1

(Lời thoại chốt lỗi): "Thầy cô thấy không ạ? Màn hình điện thoại bé xíu chỉ cần mỗi chữ 'Nguyen Van A', nhưng API trả về cho em nguyên một cục JSON chứa đủ combo: Tuổi, Điện thoại, Email. Đây là Over-fetching, rất lãng phí băng thông 4G của người dùng tải App!"

Giai đoạn 2: Demo lỗi UNDER-FETCHING của REST (Phút 13:00)
(Lời thoại): "Tiếp theo, ứng dụng cần lấy danh sách Tiêu đề bài viết để kẹp chung vào với cái Tên đó. Nhưng cái API ban nãy không đáp ứng đủ. Em bắt buộc phải móc sang một cổng API thứ 2."

👉 (Hành động): Mở Tab 2 (Giữ nguyên Tab 1), gõ URL: http://localhost:8080/api/authors/1/posts

(Lời thoại chốt lỗi): "Đây là lỗi Under-fetching (Gửi 1 Request không đủ data). Điều này bắt máy tính của người dùng phải chọc vào Server tận 2 lần, chờ phản hồi 2 vòng, làm App load chậm hơn rất nhiều."

Giai đoạn 3: "Biểu diễn phép màu" của GraphQL (Phút 14:00 - ĂN ĐIỂM)
(Lời thoại): "Nhưng với công nghệ GraphQL, mọi thứ khác biệt hoàn toàn. Dù dữ liệu phức tạp đến mấy, em chỉ giao tiếp với đúng 1 Endpoint (đường dẫn) duy nhất."

👉 (Hành động): Mở Tab 3, gõ URL: http://localhost:8080/graphiql

(Lời thoại): "Đây là giao diện Playground. Em sẽ gửi một yêu cầu (Query) với cú pháp giống hệt cấu trúc JSON. Em bảo GraphQL: Này, vào cơ sở dữ liệu lấy cho tôi đúng Name (Tên), và gọi luôn cho tôi Title (Tiêu đề bài) ra đây."

👉 (Hành động): Copy & Paste đoạn code này vào ô trống bên trái và bấm nút ▶️ (Play/Run) màu kem ở giữa:

graphql
query {
  author(id: "1") {
    name
    posts {
      title
    }
  }
}
Giai đoạn 4: Phân tích kết quả & Chốt bài (Phút 15:00)
(Chỉ tay vào cột Kết quả trả về bên phải màn hình)

(Lời thoại chốt hạ): *"Thầy cô cùng xem kết quả trả về ạ! Thứ 1: Nó trả về chính xác [Tên] và [Tiêu đề]. Không có số điện thoại hay email thừa thãi nào cả (Giải quyết triệt để Over-fetching). Thứ 2: Dữ liệu của Tác giả và Bài viết được móc nối lồng vào nhau, xuất hiện đầy đủ chỉ trong ĐÚNG 1 LẦN GỌI DUY NHẤT lên máy chủ (Giải quyết triệt để Under-fetching).

Nhờ tính linh hoạt này, Client (người làm UI App) được toàn quyền kiểm soát dữ liệu mình muốn mà không cần nhờ Backend tạo thêm API mới. GraphQL đặc biệt phù hợp cho các hệ thống phức tạp, đa nền tảng. Em xin kết thúc bài Demo ạ!"*


Comment
Ctrl+Alt+M
