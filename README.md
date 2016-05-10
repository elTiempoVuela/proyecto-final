##Configuración proyecto final:


1. Clonar repositorio
   git clone https://github.com/mati-arq-new-g01/proyecto-final.git

2. Ir a la raiz del directorio donde se descargaron los fuentes y ejecutar y ejecutar el comando
   vagrant up

3. Descarcar imagenes

	docker pull matiang01/java
	
4. Subir instancias (mosquitto = 172.17.0.2, machinelearner = 172.17.0.3 , kafka = 172.17.0.4)
		
	docker run --name ml -p 9443:9443 -v /home/vagrant/machinelearner:/home/ml -h ml -itd matiang01/java
	
	docker run --name kafka -p 2181:2181 -p 8080:8080 -p 9092:9092 -v /home/vagrant/kafka:/home/kafka -itd matiang01/java
	
4. Configurar máquinas
	
	Configurar machinelearner	

		docker exec -it ml bash
		
		cd /home/ml/wso2ml-1.1.0/
		
		bin/wso2server.sh &

		exit
	
	Configurar storm
	
		docker exec -it kafka bash
	 
		cd /home/kafka/zookeeper-3.3.6
			
		bin/zkServer.sh start conf/zoo.cfg 
		
		cd /home/kafka/apache-storm-0.10.0/
	
		bin/storm nimbus &
		
		bin/storm supervisor &
		
		cd /home/kafka/standalone/
		
		java -jar iot-framework.jar --storm twitter default_config.properties

	
	
	exit

##Validaciones:
