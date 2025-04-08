resource "opensearch_dashboard_object" "raw-ghsa-vuln-data" {
  tenant_name = local.default_tenant_name
  body        = <<EOF
  [
        {
            "_id": "search:raw-ghsa-vuln-data",
            "_source": {
                "type": "search",
                "search": {
                    "columns": [
                        "_source"
                    ],
                    "kibanaSavedObjectMeta": {
                        "searchSourceJSON": "{\"query\":{\"query\":\"\",\"language\":\"kuery\"},\"highlightAll\":true,\"version\":true,\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"published\",\"calendar_interval\":\"1y\",\"time_zone\":\"Asia/Saigon\",\"min_doc_count\":1}}},\"filter\":[],\"indexRefName\":\"kibanaSavedObjectMeta.searchSourceJSON.index\"}"
                    },
                    "title": "Raw GHSA vulnerabilities"
                },
                "references": [
                    {
                        "id": "github-security-advisory-database",
                        "name": "kibanaSavedObjectMeta.searchSourceJSON.index",
                        "type": "index-pattern"
                    }
                ]
            }
        }
    ]
EOF
}

resource "opensearch_dashboard_object" "unfixed-ghsa-reviewd-vuln-data" {
  tenant_name = local.default_tenant_name
  body        = <<EOF
  [
        {
            "_id": "search:unfixed-ghsa-reviewed-vuln-data",
            "_source": {
                "type": "search",
                "search": {
                    "columns": [
                        "database_specific.severity",
                        "summary",
                        "affected.package.ecosystem",
                        "affected.package.name"
                    ],
                    "kibanaSavedObjectMeta": {
                        "searchSourceJSON": "{\"query\":{\"query\":\"database_specific.github_reviewed: true AND NOT affected.ranges.events.fixed:*\",\"language\":\"kuery\"},\"highlightAll\":true,\"version\":true,\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"published\",\"calendar_interval\":\"1y\",\"time_zone\":\"Asia/Saigon\",\"min_doc_count\":1}}},\"filter\":[],\"indexRefName\":\"kibanaSavedObjectMeta.searchSourceJSON.index\"}"
                    },
                    "title": "Unfixed GHSA-reviewed vulnerabilities"
                },
                "references": [
                    {
                        "id": "github-security-advisory-database",
                        "name": "kibanaSavedObjectMeta.searchSourceJSON.index",
                        "type": "index-pattern"
                    }
                ]
            }
        }
    ]
EOF
}
