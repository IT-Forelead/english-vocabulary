#!/bin/bash

RED='\033[0;31m'
BLUE='\033[0;34m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

printf "${BLUE}\nIs your user able to run docker? (sudo usermod -aG docker $USER)${NC}\n\n"

printf "${RED}stop postgresql_test_database docker image...${NC}\n"
cd server/src/test/resources/docker/ || exit
docker-compose down
printf "${GREEN}start postgresql_test_database docker image...${NC}\n"
docker-compose up -d
cd ../../../../../
sleep 5
printf "${GREEN}testing...${NC}\n"
sbt -mem 3000 test
printf "${RED}stop postgresql_test_database docker image...${NC}\n"
cd server/src/test/resources/docker/ || exit
docker-compose down