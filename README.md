# Session16 - Spring Boot Profile Logging

Project mẫu hoàn chỉnh để thực hành cấu hình Log Level theo Spring Profiles.

## Yêu cầu

- JDK 17 trở lên
- Maven 3.6+ (hoặc dùng Maven Wrapper nếu bổ sung)

## Cấu trúc chính

```text
Session16/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/example/session16/
    │   │   ├── Session16Application.java
    │   │   ├── LoggingController.java
    │   │   └── LoggingTestController.java
    │   └── resources/
    │       ├── application.yml
    │       ├── application-dev.yml
    │       └── application-prod.yml
    └── test/java/com/example/session16/
        └── Session16ApplicationTests.java
```

## Chạy môi trường Dev

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Windows PowerShell/CMD:

```bat
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

## Chạy môi trường Prod

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Windows PowerShell/CMD:

```bat
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Khi chạy `prod`, root logger và package `com.example` đều ở mức INFO. Vì vậy DEBUG và TRACE sẽ không xuất hiện; INFO, WARN và ERROR vẫn được hiển thị.

## Kiểm chứng

Sau khi ứng dụng khởi động, mở:

```text
http://localhost:8080/
```

Endpoint này phát sinh log ở các mức TRACE, DEBUG, INFO, WARN và ERROR. Khi chạy profile `prod`, console chỉ hiển thị INFO trở lên. Khi chạy profile `dev`, các mức DEBUG trở lên sẽ hiển thị (TRACE chỉ hiển thị nếu logger tương ứng được cấu hình TRACE; hiện tại cấu hình package là DEBUG nên TRACE bị ẩn).

Dừng ứng dụng bằng `Ctrl+C`.

## Ghi chú

- `application.yml` chỉ chứa cấu hình chung, không cố định log level.
- `application-dev.yml` cấu hình `root: DEBUG` và `com.example: DEBUG`.
- `application-prod.yml` cấu hình `root: INFO` và `com.example: INFO`.
- Project dùng Spring Boot 3.5.7 và Java 17.

## Rolling log file

`src/main/resources/logback-spring.xml` ghi log ra console và `logs/app.log`.
Logback xoay file theo ngày và khi file đạt 10 MB, với tên archive dạng
`logs/app-yyyy-MM-dd.N.log`. Chính sách giữ tối đa 30 ngày và giới hạn tổng
dung lượng archive ở 1 GB. Mức root và `com.example` được đọc từ cấu hình
`logging.level` của Spring, do đó các giá trị của profile `dev`/`prod` vẫn
được áp dụng. Thư mục runtime `logs/` và thư mục build `target/` được ignore
bởi Git.

Log console và file được xuất dưới dạng một JSON object trên mỗi dòng bằng
Logstash Logback Encoder. Endpoint kiểm thử `/test-log` và `/test-exception`
chỉ được bật khi chạy với `--app.logging-test.enabled=true`; bật tùy chọn này
chỉ trong môi trường kiểm thử.
