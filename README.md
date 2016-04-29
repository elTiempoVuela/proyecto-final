##Configuración proyecto final:


1. Clonar repositorio
   git clone https://github.com/mati-arq-new-g01/proyecto-final.git

2. Ir a la raiz del directorio donde se descargaron los fuentes y ejecutar y ejecutar el comando
   vagrant up


3. Subir instancia de Machine Learner

	docker run -p 9443:9443 --name ml  -v /home/vagrant/machinelearner:/home/ml -h ml -itd matiang01/java
	
	docker exec -it ml bash

	exit

##Validaciones:
