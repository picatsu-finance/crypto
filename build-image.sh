#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-crypto")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-crypto
docker push ezzefiohez/finance-crypto

echo " ######## BUILD CRYPTO DONE ######## "

curl  -X POST http://94.239.109.172:9000/api/webhooks/e2950929-7f5c-402f-aa41-2f6e43b1c67a
