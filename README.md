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
	
	docker exec -it ml bash

	exit

##Validaciones:
