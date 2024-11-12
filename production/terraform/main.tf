terraform {
  backend "s3" {
    bucket = "techchallenge-orderpayments-bucket"    
    region = "us-east-1"
    key    = "mysql.tfstate"
  }
}

provider "aws" {
  region = "us-east-1"
}

### General data sources
data "aws_vpc" "techchallenge-vpc" {
  filter {
    name   = "tag:Name"
    values = ["techchallenge-vpc"]
  }
}