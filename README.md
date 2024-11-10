![test](https://github.com/pedidos-techallenge/payment-service/actions/workflows/test.yml/badge.svg)

![build](https://github.com/pedidos-techallenge/payment-service/actions/workflows/build.yml/badge.svg)


# Running local dev environment:

## via docker (no DB, test profile)
1. Build locally-tagged docker image:
`docker buildx build . -t payment-service:local`
2. Run docker image:
`docker run -it -e SPRING_PROFILE=test -p 8080:8080 payment-service:local`
3. Send your requests to localhost:8080
```
curl -X POST http://localhost:8080/v1/payment/new \
     -H "Content-Type: application/json" \
     -d '{"orderId": "12345"}'
```

## via docker-compose (MySQL db, local profile)
1. Start up database within local_db
`docker compose -f local_db/docker-compose.yml up`
2. Build locally-tagged docker image:
`docker buildx build . -t payment-service:local`
3. Start up the application with network=host mode and providing db credentials
```
docker run -it \
    --network=host \
    -e SPRING_PROFILE=prd \
    -e MYSQL_HOST=localhost \
    -e MYSQL_PORT=3306 \
    -e MYSQL_DATABSE=orderpayments \
    -e MYSQL_USER=payments \
    -e MYSQL_PASSWORD=pass_payments \
    payment-service:local
```
4. Send your requests to localhost:8080
```
curl -X POST http://localhost:8080/v1/payment/new \
     -H "Content-Type: application/json" \
     -d '{"orderId": "12345"}'
```

## via kubernetes kind cluster (MySQL db, local profile)
1. Build locally-tagged docker image:
`docker buildx build . -t payment-service:local`
2. Create kind cluster:
`kind create cluster`
3. Load the docker image into kind-control-plane
`kind load docker-image payment-service:local`
4. Apply kubernetes local deployment:
`kubectl apply -f ./Kubernetes_dev`
5. Port forward the load balancer to 8080
`kubectl port-forward svc/payment-service-lb 8080:8080`
6. Send your requests to localhost:8080
```
curl -X POST http://localhost:8080/v1/payment/new \
     -H "Content-Type: application/json" \
     -d '{"orderId": "12345"}'
```


