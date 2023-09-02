provider "aws"{
  region  = "us-east-1"
  access_key = "AKIA4UD4NF7AKGU2TCQO"
  secret_key = "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"
}

#VPC creation
resource "aws_vpc" "my_vpc-3" {
  cidr_block = "10.0.0.0/16"
  tags={
    Name="VPC_start"
  }
}

#creating Private and public subnets

resource "aws_subnet" "public-subnet-1" {
  vpc_id     = aws_vpc.my_vpc-3.id
  cidr_block = "10.0.1.0/24"
  availability_zone = "us-east-1a"
  map_public_ip_on_launch = true

  tags = {
    Name = "Public_us-east-1a"
  }
}

resource "aws_subnet" "private-subnet-2" {
  vpc_id     = aws_vpc.my_vpc-3.id
  cidr_block = "10.0.2.0/24"
 availability_zone = "us-east-1a"
  tags = {
    Name = "Private_us-east-1b"
  }
}

resource "aws_instance" "my-server-5" {
 ami           = "ami-053b0d53c279acc90"
instance_type = "t2.micro"
subnet_id     = aws_subnet.public-subnet-1.id
security_groups = ["aws_security_group.TF_SGGG.name"]

 tags={
   Name="my-server-5"
 }
}

#creating security-groups
resource "aws_security_group" "TF_SGGG" {
  name        = "security group using terraform"
  description = "security group using terraform"
  vpc_id = "vpc-0b1e31c3e53e40a44"

  ingress {
    description      = "HTTPS"
    from_port        = 443
    to_port          = 443
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
   
  }

    ingress {
    description      = "HTTP"
    from_port        = 80
    to_port          = 80
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
   
  }

    ingress {
    description      = "SSH"
    from_port        = 22
    to_port          = 22
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
   
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
   cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "TF_SGGG"
  }
}