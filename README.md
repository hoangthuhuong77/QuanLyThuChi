# 📱 QUẢN LÝ THU CHI CÁ NHÂN

## 1. Giới thiệu đề tài

### Bài toán

Trong cuộc sống hiện đại, việc quản lý các khoản thu nhập và chi tiêu cá nhân bằng sổ tay hoặc ghi chú thông thường thường gây khó khăn trong việc theo dõi tài chính. Người dùng dễ quên các khoản chi, khó thống kê số tiền đã sử dụng và không đánh giá được tình hình tài chính hiện tại.

### Mục tiêu

Xây dựng ứng dụng Android hỗ trợ:

* Quản lý các khoản thu nhập và chi tiêu.
* Theo dõi lịch sử giao dịch.
* Thống kê tổng thu, tổng chi và số dư.
* Hỗ trợ người dùng kiểm soát tài chính cá nhân hiệu quả hơn.

---

## 2. Dataset

### Nguồn dữ liệu

Dữ liệu được người dùng nhập trực tiếp trên ứng dụng và được lưu trữ cục bộ bằng SQLite Database.

### Bảng dữ liệu chính

### GiaoDich

| Tên cột | Kiểu dữ liệu | Mô tả                  |
| ------- | ------------ | ---------------------- |
| id      | INTEGER      | Mã giao dịch           |
| ghiChu  | TEXT         | Nội dung giao dịch     |
| soTien  | INTEGER      | Số tiền                |
| loai    | TEXT         | Thu nhập hoặc Chi tiêu |
| danhMuc | TEXT         | Danh mục giao dịch     |
| ngay    | TEXT         | Ngày giao dịch         |

---

## 3. Pipeline & Luồng xử lý

Hệ thống hoạt động theo quy trình:

Người dùng nhập giao dịch

↓

Kiểm tra dữ liệu đầu vào

↓

Lưu vào SQLite

↓

Cập nhật lịch sử giao dịch

↓

Tính tổng thu

↓

Tính tổng chi

↓

Tính số dư

↓

Hiển thị báo cáo thống kê

---

## 4. Mô hình & Công nghệ sử dụng

### Ngôn ngữ

* Java
* XML

### Cơ sở dữ liệu

* SQLite

### Công cụ phát triển

* Android Studio

### Thư viện hỗ trợ

* SQLiteOpenHelper
* ListView
* AlertDialog
* Intent
* DecimalFormat

### Lý do lựa chọn

* Java dễ phát triển ứng dụng Android.
* SQLite hoạt động offline, không cần Internet.
* Dữ liệu được lưu trực tiếp trên thiết bị giúp truy xuất nhanh và bảo mật.

---

## 5. Kết quả đạt được

### Tính năng

* Quản lý thu nhập.
* Quản lý chi tiêu.
* Xem lịch sử giao dịch.
* Chỉnh sửa giao dịch.
* Xóa giao dịch.
* Thống kê tổng thu.
* Thống kê tổng chi.
* Tính số dư tự động.
* Hiển thị giao dịch gần đây trên trang chủ.

### Hiệu năng

Ứng dụng hoạt động ổn định trên các thiết bị Android API 24 trở lên.

---

## 6. Hướng dẫn chạy dự án

### Cài đặt môi trường

* Android Studio
* JDK 17
* Android SDK 35+

### Chạy ứng dụng

Clone dự án:

```bash
git clone https://github.com/hoangthuhuong77/QuanLyThuChi.git
```

Mở bằng Android Studio

Chờ Gradle Sync hoàn tất

Kết nối điện thoại Android hoặc Emulator

Nhấn Run (Shift + F10)

---

## 7. Cấu trúc thư mục

```text
QuanLyThuChi
│
├── app
│   ├── java
│   │   ├── MainActivity
│   │   ├── HomeActivity
│   │   ├── LichSuActivity
│   │   ├── ThongKeActivity
│   │   ├── DanhMucActivity
│   │   └── DatabaseHelper
│   │
│   └── res
│       ├── layout
│       ├── drawable
│       └── values
│
├── README.md
└── .gitignore
```

---

## 8. Tác giả

Họ tên: Hoàng Thu Hương
Mã sinh viên: 10123178
Lớp:12523W.2

Họ tên: Phạm Văn Hiệu
Mã sinh viên: 10123135
Lớp: 12523W.2




