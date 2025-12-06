#!/bin/bash

echo "========================================="
echo "Waiting for Kafka to be ready..."
echo "========================================="

for i in {1..30}; do
  if kafka-topics --bootstrap-server kafka:9092 --list > /dev/null 2>&1; then
    echo "Kafka is ready!"
    break
  fi
  echo "Attempt $i/30: Kafka not ready yet, waiting..."
  sleep 2
done

echo "========================================="
echo "Creating topics..."
echo "========================================="

kafka-topics --bootstrap-server kafka:9092 --create --if-not-exists \
  --topic stock_tracking_request --partitions 1 --replication-factor 1

kafka-topics --bootstrap-server kafka:9092 --create --if-not-exists \
  --topic stock_tracking_response --partitions 1 --replication-factor 1

kafka-topics --bootstrap-server kafka:9092 --create --if-not-exists \
  --topic stock_valuation_response --partitions 1 --replication-factor 1

echo "========================================="
echo "Topics created successfully:"
echo "========================================="
kafka-topics --bootstrap-server kafka:9092 --list

echo "========================================="
echo "Init complete!"
echo "========================================="
