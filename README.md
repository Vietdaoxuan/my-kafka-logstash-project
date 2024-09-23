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

### I. Zookeeper
Zookeeper quản lý thông tin cấu hình và cung cấp dịch vụ đồng bộ cho các node trong hệ thống phân tán. Trong dự án này, Zookeeper được sử dụng để quản lý Kafka broker.

### II. Kafka
Kafka là một hệ thống messaging phân tán. Nó cho phép publish và subscribe các luồng record, lưu trữ các record một cách tin cậy và xử lý các luồng record. Trong dự án này, Kafka được sử dụng để nhận log từ Producer và cung cấp cho Consumer và Logstash.

### III. Elasticsearch
Elasticsearch là một công cụ tìm kiếm và phân tích mạnh mẽ, cho phép lưu trữ, tìm kiếm và phân tích khối lượng lớn dữ liệu một cách nhanh chóng. Trong dự án này, Elasticsearch được sử dụng để lưu trữ và tìm kiếm các log được gửi từ Logstash.

### IV. Kibana
Kibana là một công cụ trực quan hóa dữ liệu cho Elasticsearch, cung cấp giao diện để tìm kiếm và xem dữ liệu được lưu trữ trong Elasticsearch. Trong dự án này, Kibana được sử dụng để trực quan hóa các log được lưu trữ trong Elasticsearch.

### V. Logstash
Logstash là một công cụ thu thập, xử lý và gửi log. Trong dự án này, Logstash được cài đặt trên máy host để thu thập log từ Kafka và gửi chúng đến Elasticsearch.

### VI. Cài đặt
Logstash là một công cụ thu thập, xử lý và gửi log. Trong dự án này, Logstash được cài đặt trên máy host để thu thập log từ Kafka và gửi chúng đến Elasticsearch.

#### 1. Tạo Zookeeper, Kafka, Elasticsearch, Kibana qua Docker Compose

- Mở CMD trỏ đến thư mục 

```bash
cd .\my-kafka-logstash-project\docker\docker-compose.yml
```

- Chạy lệnh

```bash
docker compose up -d
```

- Kiểm tra thông cổng localhost port 9092 cho kafka server

```bash
telnet localhost 9092
```

- Kiểm tra thông cổng localhost port 9200 cho Elasticsearch

```bash
telnet localhost 9200
```

- Kiểm tra localhost:5601 trên browser

##### Chạy kafka trên Windows:

- Làm theo hướng dẫn tại [Link](https://www.geeksforgeeks.org/how-to-install-and-run-apache-kafka-on-windows/)
 
#### 2. Khởi chạy logstash

- Tải Logstash từ trang chủ	Elastic: [Logstash Download](https://www.elastic.co/downloads/logstash)

- Giải nén tệp tải về vào một thư mục trên máy tính

- Tạo tệp cấu hình Logstash

```config
input {
  kafka {
    bootstrap_servers => "localhost:9092"
    topics => ["my-topic"]
    group_id => "logstash-group"
    codec => "json"
  }
}

output {
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "logs-%{+YYYY.MM.dd}"
	template => "G:\logstash-8.14.3\template\ecs-logstash-template.json"
    template_name => "ecs-logstash"
    template_overwrite => true
  }
  stdout {
    codec => rubydebug
  }
}
```

- Tạo template

```config
{
  "index_patterns": ["logs-*"],
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 1
  },
  "mappings": {
    "_source": {
      "enabled": true
    },
    "properties": {
      "@timestamp": {
        "type": "date"
      },
      "message": {
        "type": "text"
      },
      "host": {
        "properties": {
          "name": {
            "type": "keyword"
          }
        }
      },
      "log": {
        "properties": {
          "level": {
            "type": "keyword"
          },
          "logger": {
            "type": "keyword"
          }
        }
      }
    }
  }
}
```

- Cài template vào Elasticsearch

```bash
curl -X PUT "http://localhost:9200/_template/ecs-logstash" -H "Content-Type: application/json" -d @path\to\your\ecs-logstash-template.json
```

- Trỏ đến thư mục bin bên trong thư mục được giải nén ở trên, chạy logstash
 
```bash
logstash -f C:\logstash\config\logstash.conf
```

#### 3. Chạy Producer & consumer

- Build Producer: Trỏ đến thư mục Producer chạy lệnh

```bash
mvn clean package
```

- Build Consumer: Trỏ đến thư mục Consumer chạy lệnh

```bash
mvn clean package
```

### VII. Unit Tests

#### 1. Tạo mới Kafka topic
- Tạo mới Topic my-topic

#### 2. Gửi API
- Sử dụng Postman, gửi bản tin theo Url

```bash
http://localhost:8080/sendOrder
```

- Cấu trúc bản tin

```bash
{
    "orderId": "2",
    "customerName": "Vietdx",
    "product": "Code",
    "quantity": 1
}
```

- Response trả ra 
"Message sent to Kafka topic: "

- Kiểm tra phía Consumer, Console trả ra: 

```bash
"Received message: 
{
    "orderId": "2",
    "customerName": "Vietdx",
    "product": "Code",
    "quantity": 1
}"
```

- Kiểm tra logstash: 
+ Tại ô search nhập Discover --> Chọn Log-* --> Truy vấn theo trường tương ứng
