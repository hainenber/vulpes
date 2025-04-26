# Vulpes

My personal security platform, made of various OSS products.
- [OpenSearch: An open-source, enterprise-grade search and observability suite that brings order to unstructured data at scale](https://opensearch.org/)
- [TBD] [osquery/osquery: SQL powered operating system instrumentation, monitoring, and analytics.](https://osquery.io/)
- [TBD] [apache/superset: An open-source modern data exploration and visualization platform](https://superset.apache.org/)

## Prerequisites
- Java 21+. We recommend [sdkman](https://sdkman.io/).
    ```bash
      curl -s "https://get.sdkman.io" | bash
      exec zsh
      sdk install 17
    ```
- Self-signed TLS/SSL certificate. We recommend using [mkcert](https://github.com/FiloSottile/mkcert) for domain `localhost`.
    ```bash
      brew install mkcert
      mkcert -install  # You'll be prompted for superuser password 
      mkcert -cert-file localhost.pem -key-file localhost-key.pem localhost
    ```
- [Terraform 1.11.4+](https://developer.hashicorp.com/terraform)

## Getting started
### Docker
  1. Application and dependencies
      * Run `docker-compose up -d --build` to provision OpenSearch server and the Dashboard component
      * The application has been containerized and would be built by Docker Compose.
  2. Infrastructure
      * Run `terraform apply -var-file opensearch.credentials.tfvars` in working directory `./infrastructure/opensearch`
### Kubernetes
- TBD