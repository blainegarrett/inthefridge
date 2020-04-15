# Make wrapper commands to abstract out cli nuances

.PHONY: install
install:
	mvn install

.PHONY: test
test:
	mvn test

.PHONY: dev
dev:
	mvn spring-boot:run

.PHONY: deploy
deploy:
	mvn package appengine:deploy -Dapp.deploy.projectId=inthefridge