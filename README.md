# Kafka Order Producer - Spring Boot

This project demonstrates a **Spring Boot Kafka Producer** that sends `Order` objects to a Kafka topic. It supports scheduled sending, partitioned messages, and key-based message routing.  


## Features

- Sends `Order` objects as JSON messages to Kafka.  
- Uses **KafkaTemplate** with a `ProducerFactory` for sending messages.  
- Supports partitioned messages (by key or specified partition).  
- Scheduled sending of random orders.  
- Handles asynchronous sending with callbacks.  

---

## Prerequisites

- Java 19  
- Spring Boot 3.x  
- Apache Kafka (running locally or accessible cluster)  
- Maven  

---

