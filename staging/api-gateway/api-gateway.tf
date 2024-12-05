resource "aws_apigatewayv2_api" "main" {
  name          = "main"
  protocol_type = "HTTP"
}

resource "aws_apigatewayv2_stage" "v1" {
  api_id = aws_apigatewayv2_api.main.id

  name        = "v1"
  auto_deploy = true
}



resource "aws_security_group" "vpc_link" {
  name   = "vpc-link-payment"
  vpc_id = data.aws_vpc.techchallenge-vpc.id

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
  }
}

resource "aws_apigatewayv2_vpc_link" "eks" {
  name               = "eks"
  security_group_ids = [aws_security_group.vpc_link.id]
  subnet_ids = data.aws_subnets.private-subnets.ids
}

data "aws_lb" "k8-payment-service-lb" {
    tags = {
        "kubernetes.io/service-name": "default/payment-service-lb"
    }
}


data "aws_lb_listener" "k8-payment-service-lb-listener" {
  load_balancer_arn = data.aws_lb.k8-payment-service-lb.arn
  port = 8080
}

resource "aws_apigatewayv2_integration" "eks" {
  api_id = aws_apigatewayv2_api.main.id

  integration_uri = data.aws_lb_listener.k8-payment-service-lb-listener.arn
  integration_type   = "HTTP_PROXY"
  integration_method = "ANY"
  connection_type    = "VPC_LINK"
  connection_id      = aws_apigatewayv2_vpc_link.eks.id
}


resource "aws_apigatewayv2_route" "get_echo" {
  api_id = aws_apigatewayv2_api.main.id

  route_key = "ANY /{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.eks.id}"
}