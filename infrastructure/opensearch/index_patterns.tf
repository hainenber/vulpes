resource "opensearch_dashboard_object" "github-security-advisory-database" {
  tenant_name = local.default_tenant_name
  body        = <<EOF
  [
    {
      "_id": "index-pattern:github-security-advisory-database",
      "_source": {
        "type": "index-pattern",
        "index-pattern": {
          "title": "github-security-advisory-database",
          "timeFieldName": "published"
        }
      }
    }
  ]
EOF
}