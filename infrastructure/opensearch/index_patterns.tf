resource "opensearch_dashboard_object" "github-security-advisory-database" {
  tenant_name = "admin"
  body        = <<EOF
  [
    {
      "_id": "index-pattern:github-security-advisory-database",
      "_type": "doc",
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