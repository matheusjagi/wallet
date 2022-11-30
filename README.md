# PICPAY WALLET - Projeto desenvolvido para desafio de processo seletivo

Sistema projetado para criação de uma carteira digital para realização de operações financeiras básicas como: transferência de valores entre usuários, saques, depósitos e pagamento de contas. A solução foi construida no modelo de microservices usando as tecnologias mais usadas do Spring, contendo comunicações sincronas e assincronas se utilizando de filas de processamento.

## Arquitetura dos microservices


## Tecnologias utilizadas
- Swagger
- Rabbitmq
- Spring data JPA
- Spring data Elasticsearch
- Spring cloud Eureka

## Requisitos
Para montar o ambiente do projeto é necessário:

- Java 17
- Docker
- Maven

Para subir o ambiente AWS execute:

`$ cdk deploy --all --parameters Rds:databasePassword=[PASSWORD]`

## Configuração do Backend
Este projeto foi desenvolvido utilizando a arquitetura Spring e conta com vários módulos para seu completo funcionamento.

Para que seja devidamente preparado o ambiente para sua execução, deve-se executar o comando abaixo:

`$ mvn clean install`