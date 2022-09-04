# taass-bibliotech

### Creazione immagine docker da dockerfile
docker build -t api-gateway:api-gateway ./api-gateway
docker build -t auth-service:auth-service ./auth-service
docker build -t book-service:book-service ./book-service
docker build -t catalog-service:catalog-service ./catalog-service
docker build -t studyhall-service:studyhall-service ./studyhall-service
docker build -t eureka-server:eureka-server ./eureka-server
docker build -t user-service:user-service ./user-service
docker build -t client:client ./client

### Tag immagine per repo
docker tag api-gateway:api-gateway lorenzofavaro/bibliotech:api-gateway
docker tag auth-service:auth-service lorenzofavaro/bibliotech:auth-service
docker tag book-service:book-service lorenzofavaro/bibliotech:book-service
docker tag catalog-service:catalog-service lorenzofavaro/bibliotech:catalog-service
docker tag studyhall-service:studyhall-service lorenzofavaro/bibliotech:studyhall-service
docker tag eureka-server:eureka-server lorenzofavaro/bibliotech:eureka-server
docker tag user-service:user-service lorenzofavaro/bibliotech:user-service
docker tag client:client lorenzofavaro/bibliotech:client

### Push immagine in repo
docker push lorenzofavaro/bibliotech:api-gateway
docker push lorenzofavaro/bibliotech:auth-service
docker push lorenzofavaro/bibliotech:book-service
docker push lorenzofavaro/bibliotech:catalog-service
docker push lorenzofavaro/bibliotech:studyhall-service
docker push lorenzofavaro/bibliotech:eureka-server
docker push lorenzofavaro/bibliotech:user-service
docker push lorenzofavaro/bibliotech:client

### Eliminazione immagine
docker rmi lorenzofavaro/bibliotech:auth-service

### Esempio installazione jar nella repo locale di maven
mvn install:install-file -Dfile=common-dto-0.0.1-SNAPSHOT.jar -DgroupId=taass.bibliotech -DartifactId=common-dto -Dpackaging=jar -Dversion=0.0.1-SNAPSHOT

### Installare angular --
npm install --legacy-peer-deps