resource "opensearch_dashboard_object" "github-security-advisory-database" {
  tenant_name = local.default_tenant_name
  body        = file("./index_patterns/github_security_advisory_database.json")
}