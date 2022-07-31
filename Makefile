.PHONY: mysql
mysql:
	@command docker-compose -f ./infra/mysql.yml up

.PHONY: redis
redis:
	@command docker-compose -f ./infra/redis.yml up

.PHONY: kafka
kafka:
	@command docker-compose -f ./infra/kafka.yml up

.PHONY: all
all:
	@command docker-compose -f ./infra/mysql.yml up -d
	@command docker-compose -f ./infra/redis.yml up -d
	@command docker-compose -f ./infra/kafka.yml up -d

.PHONY: clear
clear:
	@command docker-compose -f ./infra/mysql.yml -f ./infra/redis.yml -f ./infra/kafka.yml down