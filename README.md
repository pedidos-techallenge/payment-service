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
         -d '{"idOrder": "12345"}'
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
         -d '{"idOrder": "12345"}'
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
         -d '{"idOrder": "12345"}'
    ```

# Running staging environment on AWS:
1. Deploy staging image to dockerhub
    ```
    docker buildx build . -t jubzzz/payment-service:staging
    docker push jubzzz/payment-service:staging
    ```
2. Setup env variables with aws credentials and MYSQL login
    ```
    export AWS_ACCESS_KEY_ID=...
    export AWS_SECRET_ACCESS_KEY=...
    export AWS_SESSION_TOKEN=...
    export AWS_REGION=...
    export MYSQL_USERNAME=...
    export MYSQL_PASSWORD=...
    ```
3. Deploy terraform infra to AWS  

    3.1 Deploy Shared infra (VPC, S3 and SQS)  
    `terraform -chdir=./staging/shared init`  
    `terraform -chdir=./staging/shared apply`  
    3.2 Deploy the main infra (RDS and EKS)  
    `terraform -chdir=./staging/clusters init`  
    `terraform -chdir=./staging/clusters apply -var "MYSQL_USERNAME=${MYSQL_USERNAME}" -var "MYSQL_PASSWORD=${MYSQL_PASSWORD}"`  
    3.3 Deploy and destroy the lambda for DB initialization  
    `terraform -chdir=./staging/lambda-initdb init`  
    `terraform -chdir=./staging/lambda-initdb apply -var "MYSQL_USERNAME=${MYSQL_USERNAME}" -var "MYSQL_PASSWORD=${MYSQL_PASSWORD}"`  
    `terraform -chdir=./staging/lambda-initdb destroy -var "MYSQL_USERNAME=" -var "MYSQL_PASSWORD="`  

4. Deploy the Kubernetes application  
    
    4.1 Setup EKS config  
    `aws eks update-kubeconfig --region us-east-1 --name orderpayments-eks`  
    4.2 Retrieve AWS endpoints for RDS and SQS  
    ```
    export MYSQL_HOST=$(aws rds describe-db-instances \
        --region=us-east-1 \
        --db-instance-identifier \
        techchallenge-payment-service \
        --query 'DBInstances[0].Endpoint.Address' \
        --output text)
    export AWS_SQS_URL=$(aws sqs get-queue-url --queue-name payment-order-main --region=us-east-1 --output text)
    ```  
    4.3 Apply Kubernetes deployment  
    `kubectl apply -f ./staging/kubernetes/deployment.yaml`  
    4.4 Set env variables for EKS deployment 
    ```
    kubectl set env deployment/payment-deployment \
        SPRING_PROFILE=prd \
        MYSQL_HOST=$MYSQL_HOST \
        MYSQL_PORT=3306 \
        MYSQL_DATABSE=orderpayments \
        MYSQL_USER=$MYSQL_USERNAME \
        MYSQL_PASSWORD=$MYSQL_PASSWORD \
        AWS_SQS_URL=$AWS_SQS_URL \
        AWS_REGION=$AWS_REGION \
        AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
        AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
        AWS_SESSION_TOKEN=$AWS_SESSION_TOKEN
    ```

5. Deploy the API Gateway  
`terraform -chdir=./staging/api-gateway init`  
`terraform -chdir=./staging/api-gateway apply`  

6. Clean Up
    
    6.1 Destroy the api gateway  
    `terraform -chdir=./staging/api-gateway destroy`  
    6.2 Destroy the RDS and EKS clusters  
    `terraform -chdir=./staging/clusters destroy -var "MYSQL_USERNAME=" -var "MYSQL_PASSWORD="`  
    6.3 Destroy the shared infra  
    `terraform -chdir=./staging/shared destroy`  
    6.4 Destroy the Load Balancer via AWS console