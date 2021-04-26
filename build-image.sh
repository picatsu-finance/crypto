#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-crypto")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-crypto
docker push ezzefiohez/finance-crypto

echo " ######## BUILD CRYPTO DONE ######## "

curl  -X POST http://146.59.195.214:9000/api/webhooks/102fbc39-e65a-42e3-b614-ca2fc2585396
