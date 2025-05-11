# Vulpes
[![Java CI with Gradle](https://github.com/hainenber/vulpes/actions/workflows/backend-build.yml/badge.svg?branch=main)](https://github.com/hainenber/vulpes/actions/workflows/backend-build.yml)
[![Node.js CI](https://github.com/hainenber/vulpes/actions/workflows/frontend-build.yml/badge.svg)](https://github.com/hainenber/vulpes/actions/workflows/frontend-build.yml)

My overly-engineered personal security platform, made of various OSS products.

<table>
    <tr>
        <th>Logo</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td><img width="32" src="https://spring.io/img/favicon.ico"></td>
        <td><a href="https://spring.io/">Spring</a></td>
        <td>A framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications.</td>
    </tr>
    <tr>
        <td><img width="32" src="https://opensearch.org/wp-content/uploads/2025/01/opensearch_mark_default.png"></td>
        <td><a href="https://opensearch.org/">OpenSearch</a></td>
        <td>An open-source, enterprise-grade search and observability suite that brings order to unstructured data at scale.</td>
    </tr>
    <tr>
        <td><img width="32" src="https://wiki.postgresql.org/images/3/30/PostgreSQL_logo.3colors.120x120.png"></td>
        <td><a href="https://www.postgresql.org">PostgreSQL</a></td>
        <td>A powerful, open source object-relational database system with over 35 years of active development that has earned it a strong reputation for reliability, feature robustness, and performance</td>
    </tr>
    <tr>
        <td><img width="32" src="https://upload.wikimedia.org/wikipedia/commons/e/e1/Flyway_logo.svg"></td>
        <td><a href="https://www.red-gate.com/products/flyway/">Flyway</a></td>
        <td>Helps you version your database changes and automate your database deployments safely and easily.</td>
    </tr>
    <tr>
        <td><img width="32" src="https://github.com/osquery/osquery/raw/master/docs/img/logo-2x-dark.png"></td>
        <td><a href="https://www.osquery.io/">osquery</a></td>
        <td>TBA: SQL powered operating system instrumentation, monitoring, and analytics</td>
    </tr>
    <tr>
        <td><img width="32" src="https://superset.apache.org/img/favicon.ico"></td>
        <td><a href="https://superset.apache.org/">Apache Superset</a></td>
        <td>TBA: A modern, enterprise-ready business intelligence web application</td>
    </tr>
</table>

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
  1. Infrastructure
     * Run `terraform apply -var-file opensearch.credentials.tfvars` in working directory `./infrastructure/opensearch`
  2. Application and dependencies
     * Run `docker-compose up -d --build` to provision OpenSearch server and the Dashboard component
     * The application has been containerized and would be built by Docker Compose.
### Kubernetes
- TBD