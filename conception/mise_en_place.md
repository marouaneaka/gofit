cd backend/services-api/shared-models
mvn clean package
mvn install

mvn clean package pour chaque service

lancer kafka
créer les TOPICS spécifiés 
lancer glasfish
lancer une instance psql
créer un pool jdbc et une connexion jdbc

deployer les .war sur glassfish

lancer le front end