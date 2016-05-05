##Configuración proyecto final:


1. Clonar repositorio
   git clone https://github.com/mati-arq-new-g01/proyecto-final.git

2. Ir a la raiz del directorio donde se descargaron los fuentes y ejecutar y ejecutar el comando
   vagrant up

3. Descarcar imagenes

	docker pull matiang01/java
	
	docker pull matiang01/mosquitto

4. Subir instancias (mosquitto = 172.17.0.2, machinelearner = 172.17.0.3 , kafka = 172.17.0.4)
	
	docker run --name mosquitto -p 1883:1883 -itd matiang01/mosquitto
	
	docker run --name ml -p 9443:9443 -v /home/vagrant/machinelearner:/home/ml -h ml -itd matiang01/java
	
	docker run --name kafka -p 2181:2181 -p 8080:8080 -p 9092:9092 -v /home/vagrant/kafka:/home/kafka -itd matiang01/java
	
4. Configurar máquinas
	
	Configurar storm
	
		docker exec -it kafka bash
	 
		cd /home/kafka/zookeeper-3.3.6
			
		bin/zkServer.sh start conf/zoo.cfg 
		
		#cd /home/kafka/kafka_2.9.2-0.8.2.2
		
		#bin/kafka-server-start.sh config/server.properties &
		
		#bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1  --partitions 1 --topic iotdogs
		
		cd /home/kafka/apache-storm-0.10.0/
	
		bin/storm nimbus &
		
		bin/storm supervisor &
		
		cd /home/kafka/standalone/
		
		java -jar iot-framework.jar --storm twitter default_config.properties

	
	Configurar storm	

		docker exec -it ml bash

	exit

##Validaciones:
