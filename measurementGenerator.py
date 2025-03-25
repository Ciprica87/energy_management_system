import pika
import csv
import json
import time
from datetime import datetime, timedelta

device_id = input("Please enter the device ID: ")

# Your CloudAMQP URL
cloud_amqp_url = 'amqp://anmybhpg:ktNw7zEZJ6uCNXQO7USPQanls3ikar_C@cow-01.rmq2.cloudamqp.com/anmybhpg'  

# Establish a connection to RabbitMQ server
connection_params = pika.URLParameters(cloud_amqp_url)
connection = pika.BlockingConnection(connection_params)
channel = connection.channel()

# Ensure the queue exists
channel.queue_declare(queue='measurement_queue')

def send_to_queue(time, device_id, measurement):
    message = {
        "timestamp": int(time.timestamp() * 1000),  # Current time in milliseconds
        "deviceId": device_id,
        "value": measurement
    }
    channel.basic_publish(
        exchange='',
        routing_key='measurement_queue',
        body=json.dumps(message)
    )
    print(f"Sent message: {message}")

csv_file_path = 'sensor.csv' 

try:
    with open(csv_file_path, mode='r') as csvfile:
        csvreader = csv.DictReader(csvfile)
        current_time = datetime.now()

        for row in csvreader:
            print(row)
            send_to_queue(current_time, device_id, float(row['0']))  # Assuming the column name is 'value'
            current_time += timedelta(minutes=10)
            time.sleep(1)

except FileNotFoundError:
    print("The sensor.csv file was not found.")
except Exception as e:
    print(f"An error occurred: {e}")
finally:
    connection.close()
