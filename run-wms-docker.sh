sudo docker stop $(sudo docker ps -a -q)

sudo docker rm $(sudo docker ps -a -q)

sudo docker run -d --net=host --rm --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres

sudo docker run -d --net=host --name=zookeeper -p 32181:32181 -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 confluentinc/cp-zookeeper:5.0.0

sudo docker run -d --net=host --name=kafka -p 29092:29092 -e KAFKA_ZOOKEEPER_CONNECT=localhost:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://137.117.102.146:29092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 confluentinc/cp-kafka:5.0.0

sudo docker run -d --net=host --name=schema-registry -e SCHEMA_REGISTRY_KAFKASTORE_CONNECTION_URL=localhost:32181 -e SCHEMA_REGISTRY_HOST_NAME=localhost -e SCHEMA_REGISTRY_LISTENERS=http://10.0.0.4:28081 confluentinc/cp-schema-registry:5.0.0

sudo docker run -d --net=host --name=picking -p 9093:9093 -e SPRING_PROFILES_ACTIVE=azure  wmscontainerregistry.azurecr.io/picking:v1

sudo docker login wmscontainerregistry.azurecr.io -u wmsContainerRegistry -p ZIzBXuvHQ0QGKIU=32CZfaue80byfOPh