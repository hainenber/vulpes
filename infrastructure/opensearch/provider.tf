terraform {
  required_providers {
    opensearch = {
      source  = "opensearch-project/opensearch"
      version = "2.3.1"
    }
  }
}

locals {
  default_tenant_name = "admin"
}

provider "opensearch" {
  url      = "https://0.0.0.0:9200"
  insecure = true
  username = var.opensearch_cluster_username
  password = var.opensearch_cluster_password
}