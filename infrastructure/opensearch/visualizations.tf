resource "opensearch_dashboard_object" "ghsa-vuln-by-review-status" {
  tenant_name = local.default_tenant_name
  body = templatefile("./visualizations/ghsa_vuln_by_review_status.tftpl", {
    visState         = jsonencode(file("./visualizations/ghsa_vuln_by_review_status.visState.json")),
    searchSourceJSON = jsonencode(file("./visualizations/ghsa_vuln_by_review_status.searchSource.json"))
  })
}

resource "opensearch_dashboard_object" "ghsa-vuln-by-published-time" {
  tenant_name = local.default_tenant_name
  body = templatefile("./visualizations/ghsa_vuln_by_published_time.tftpl", {
    visState         = jsonencode(file("./visualizations/ghsa_vuln_by_published_status.visState.json")),
    searchSourceJSON = jsonencode(file("./visualizations/ghsa_vuln_by_published_status.searchSource.json"))
  })
}

resource "opensearch_dashboard_object" "ghsa-vuln-by-cwe" {
  tenant_name = local.default_tenant_name
  body = templatefile("./visualizations/ghsa_vuln_by_cwe.tftpl", {
    visState         = jsonencode(file("./visualizations/ghsa_vuln_by_cwe.visState.json")),
    searchSourceJSON = jsonencode(file("./visualizations/ghsa_vuln_by_cwe.searchSource.json"))
  })
}