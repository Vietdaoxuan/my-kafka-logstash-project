# Kafka Logstash Elasticsearch Demo Project

## Mô tả Dự Án

Dự án này bao gồm các service sau:
- Zookeeper
- Kafka
- Elasticsearch
- Kibana
- Logstash
- Kafka Producer
- Kafka Consumer

Dự án sử dụng Docker và Docker Compose để triển khai các service chính, trong khi Producer và Consumer được phát triển bằng Spring Boot và chạy trên máy host. Logstash cũng được cài đặt và chạy trên máy host để thu thập log từ Kafka và đẩy vào Elasticsearch.

## Cấu trúc thư mục
my-kafka-logstash-project/
├── docker/
│ ├── docker-compose.yml
├── logstash/
│ ├── config/
│ │ ├── logstash.conf
│ │ └── ecs-logstash-template.json
├── consumer/
│ ├── pom.xml
│ ├── src/
├── producer/
│ ├── src/
│ ├── pom.xml
└── README.md

## Các Thành Phần Chính

### 1. Zookeeper
Zookeeper quản lý thông tin cấu hình và cung cấp dịch vụ đồng bộ cho các node trong hệ thống phân tán. Trong dự án này, Zookeeper được sử dụng để quản lý Kafka broker.

### 2. Kafka
Kafka là một hệ thống messaging phân tán. Nó cho phép publish và subscribe các luồng record, lưu trữ các record một cách tin cậy và xử lý các luồng record. Trong dự án này, Kafka được sử dụng để nhận log từ Producer và cung cấp cho Consumer và Logstash.

### 3. Elasticsearch
Elasticsearch là một công cụ tìm kiếm và phân tích mạnh mẽ, cho phép lưu trữ, tìm kiếm và phân tích khối lượng lớn dữ liệu một cách nhanh chóng. Trong dự án này, Elasticsearch được sử dụng để lưu trữ và tìm kiếm các log được gửi từ Logstash.

### 4. Kibana
Kibana là một công cụ trực quan hóa dữ liệu cho Elasticsearch, cung cấp giao diện để tìm kiếm và xem dữ liệu được lưu trữ trong Elasticsearch. Trong dự án này, Kibana được sử dụng để trực quan hóa các log được lưu trữ trong Elasticsearch.

### 5. Logstash
Logstash là một công cụ thu thập, xử lý và gửi log. Trong dự án này, Logstash được cài đặt trên máy host để thu thập log từ Kafka và gửi chúng đến Elasticsearch.