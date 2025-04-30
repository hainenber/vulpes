# Vulpes

My personal security platform, made of various OSS products.
- [Spring: A framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications.](https://spring.io/)
- [OpenSearch: An open-source, enterprise-grade search and observability suite that brings order to unstructured data at scale.](https://opensearch.org/)
- [PostgreSQL: A powerful, open source object-relational database system with over 35 years of active development that has earned it a strong reputation for reliability, feature robustness, and performance.](https://www.postgresql.org/)
- [TBD] [osquery: SQL powered operating system instrumentation, monitoring, and analytics.](https://osquery.io/)
- [TBD] [Flyway: helps you version your database changes and automate your database deployments safely and easily.](https://github.com/flyway/flyway)

## Prerequisites
- Java 21+. We recommend [sdkman](https://sdkman.io/).
    ```bash
      curl -s "https://get.sdkman.io" | bash
      exec zsh
      sdk install 21
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