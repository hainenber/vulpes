resource "opensearch_dashboard_object" "raw-ghsa-vuln-data" {
  tenant_name = local.default_tenant_name
  body = templatefile("./saved_searches/raw_ghsa_vuln_data.tftpl", {
    searchSourceJSON = jsonencode(file("./saved_searches/raw_ghsa_vuln_data.searchSource.json"))
  })
}

resource "opensearch_dashboard_object" "unfixed-ghsa-reviewd-vuln-data" {
  tenant_name = local.default_tenant_name
  body = templatefile("./saved_searches/unfixed_ghsa_reviewed_vuln_data.tftpl", {
    searchSourceJSON = jsonencode(file("./saved_searches/unfixed_ghsa_reviewed_vuln_data.searchSource.json"))
  })
}
