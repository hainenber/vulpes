resource "opensearch_dashboard_object" "ghsa-vuln" {
  tenant_name = local.default_tenant_name
  body        = <<EOF
    [
        {
            "_id": "dashboard:ghsa-vuln",
            "_source": {
                "type": "dashboard",
                "dashboard": {
                    "kibanaSavedObjectMeta": {
                        "searchSourceJSON": "{\"query\":{\"language\":\"kuery\",\"query\":\"\"},\"filter\":[]}"
                    },
                    "optionsJSON": "{\"hidePanelTitles\":false,\"useMargins\":true}",
                    "panelsJSON": "[{\"version\":\"2.19.0\",\"gridData\":{\"h\":16,\"i\":\"bc16a4cb-2b1c-4752-b3a8-a75af2b8b7e7\",\"w\":48,\"x\":0,\"y\":28},\"panelIndex\":\"bc16a4cb-2b1c-4752-b3a8-a75af2b8b7e7\",\"embeddableConfig\":{},\"panelRefName\":\"panel_0\"},{\"version\":\"2.19.0\",\"gridData\":{\"h\":13,\"i\":\"5ff03a03-639f-4836-96b9-ec0247dcf3d4\",\"w\":48,\"x\":0,\"y\":0},\"panelIndex\":\"5ff03a03-639f-4836-96b9-ec0247dcf3d4\",\"embeddableConfig\":{},\"panelRefName\":\"panel_1\"},{\"version\":\"2.19.0\",\"gridData\":{\"h\":15,\"i\":\"e64feb59-6a6c-4e6f-8562-2b89589d80bc\",\"w\":14,\"x\":0,\"y\":13},\"panelIndex\":\"e64feb59-6a6c-4e6f-8562-2b89589d80bc\",\"embeddableConfig\":{},\"panelRefName\":\"panel_2\"},{\"version\":\"2.19.0\",\"gridData\":{\"x\":14,\"y\":13,\"w\":18,\"h\":15,\"i\":\"a26ac472-636a-4ed1-9c1d-4f3813f40ed8\"},\"panelIndex\":\"a26ac472-636a-4ed1-9c1d-4f3813f40ed8\",\"embeddableConfig\":{},\"panelRefName\":\"panel_3\"},{\"version\":\"2.19.0\",\"gridData\":{\"x\":32,\"y\":13,\"w\":16,\"h\":15,\"i\":\"3d024e76-6e7f-41b1-96ff-8b94cc01151b\"},\"panelIndex\":\"3d024e76-6e7f-41b1-96ff-8b94cc01151b\",\"embeddableConfig\":{},\"panelRefName\":\"panel_4\"}]",
                    "refreshInterval": {
                        "pause": true,
                        "value": 0
                    },
                    "timeFrom": "now-15M",
                    "timeRestore": true,
                    "timeTo": "now",
                    "title": "GHSA vulnerabilities"
                },
                "references": [
                    {
                        "id": "raw-ghsa-vuln-data",
                        "name": "panel_0",
                        "type": "search"
                    },
                    {
                        "id": "unfixed-ghsa-reviewed-vuln-data",
                        "name": "panel_1",
                        "type": "search"
                    },
                    {
                        "id": "ghsa-vuln-by-review-status",
                        "name": "panel_2",
                        "type": "visualization"
                    },
                    {
                        "id": "ghsa-vuln-by-published-time",
                        "name": "panel_3",
                        "type": "visualization"
                    },
                    {
                        "id": "ghsa-vuln-by-cwe",
                        "name": "panel_4",
                        "type": "visualization"
                    }
                ]
            }
        }
    ]
EOF
}