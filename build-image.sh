#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-crypto")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-crypto
docker push ezzefiohez/finance-crypto
