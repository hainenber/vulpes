resource "opensearch_dashboard_object" "ghsa-vuln-by-review-status" {
  tenant_name = local.default_tenant_name
  body        = <<EOF
  [
    {
      "_id": "visualization:ghsa-vuln-by-review-status",
      "_source": {
        "type": "visualization",
        "visualization": {
          "title": "GHSA vulnerabilities by review status",
          "visState": "{\"title\":\"GHSA vulnerabilities by review status\",\"type\":\"metric\",\"aggs\":[{\"id\":\"1\",\"enabled\":true,\"type\":\"count\",\"params\":{\"customLabel\":\" \"},\"schema\":\"metric\"},{\"id\":\"2\",\"enabled\":true,\"type\":\"terms\",\"params\":{\"field\":\"database_specific.github_reviewed\",\"orderBy\":\"1\",\"order\":\"desc\",\"size\":5,\"otherBucket\":true,\"otherBucketLabel\":\"Other\",\"missingBucket\":false,\"missingBucketLabel\":\"Missing\",\"customLabel\":\"GitHub Reviewed\"},\"schema\":\"group\"}],\"params\":{\"addTooltip\":true,\"addLegend\":false,\"type\":\"metric\",\"metric\":{\"percentageMode\":false,\"useRanges\":false,\"colorSchema\":\"Green to Red\",\"metricColorMode\":\"None\",\"colorsRange\":[{\"from\":0,\"to\":10000}],\"labels\":{\"show\":true},\"invertColors\":false,\"style\":{\"bgFill\":\"#000\",\"bgColor\":false,\"labelColor\":false,\"subText\":\"\",\"fontSize\":60}}}}",
          "uiStateJSON": "{}",
          "description": "",
          "version": 1,
          "kibanaSavedObjectMeta": {
            "searchSourceJSON": "{\"query\":{\"query\":\"\",\"language\":\"kuery\"},\"filter\":[],\"indexRefName\":\"kibanaSavedObjectMeta.searchSourceJSON.index\"}"
          }
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

resource "opensearch_dashboard_object" "ghsa-vuln-by-published-time" {
  tenant_name = local.default_tenant_name
  body        = <<EOF
  [
    {
      "_id": "visualization:ghsa-vuln-by-published-time",
      "_source": {
        "type": "visualization",
        "visualization": {
          "title": "GHSA vulnerabilities by published time",
          "visState": "{\"title\": \"GHSA vulnerability by published time\",\"type\": \"histogram\",\"aggs\": [ { \"id\": \"1\", \"enabled\": true, \"type\": \"count\", \"params\": { \"customLabel\": \"\" }, \"schema\": \"metric\" }, { \"id\": \"2\", \"enabled\": true, \"type\": \"date_histogram\", \"params\": { \"field\": \"published\", \"timeRange\": { \"from\": \"now-1y\", \"to\": \"now\" }, \"useNormalizedOpenSearchInterval\": true, \"scaleMetricValues\": false, \"interval\": \"M\", \"drop_partials\": false, \"min_doc_count\": 1, \"extended_bounds\": {}, \"customLabel\": \"\" }, \"schema\": \"segment\" }],\"params\": { \"type\": \"histogram\", \"grid\": { \"categoryLines\": false, \"valueAxis\": \"\" }, \"categoryAxes\": [ { \"id\": \"CategoryAxis-1\", \"type\": \"category\", \"position\": \"bottom\", \"show\": true, \"style\": {}, \"scale\": { \"type\": \"linear\" }, \"labels\": { \"show\": true, \"filter\": true, \"truncate\": 100 }, \"title\": {} } ], \"valueAxes\": [ { \"id\": \"ValueAxis-1\", \"name\": \"LeftAxis-1\", \"type\": \"value\", \"position\": \"left\", \"show\": true, \"style\": {}, \"scale\": { \"type\": \"linear\", \"mode\": \"normal\" }, \"labels\": { \"show\": true, \"rotate\": 0, \"filter\": false, \"truncate\": 100 }, \"title\": { \"text\": \"Count\" } } ], \"seriesParams\": [ { \"show\": true, \"type\": \"histogram\", \"mode\": \"stacked\", \"data\": { \"label\": \"Count\", \"id\": \"1\" }, \"valueAxis\": \"ValueAxis-1\", \"drawLinesBetweenPoints\": true, \"lineWidth\": 2, \"showCircles\": true } ], \"addTooltip\": true, \"addLegend\": true, \"legendPosition\": \"right\", \"times\": [], \"addTimeMarker\": false, \"labels\": { \"show\": true }, \"thresholdLine\": { \"show\": false, \"value\": 10, \"width\": 1, \"style\": \"full\", \"color\": \"#E7664C\" }}}",
          "uiStateJSON": "{}",
          "description": "",
          "version": 1,
          "kibanaSavedObjectMeta": {
            "searchSourceJSON": "{\"query\":{\"query\":\"\",\"language\":\"kuery\"},\"filter\":[],\"indexRefName\":\"kibanaSavedObjectMeta.searchSourceJSON.index\"}"
          }
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

resource "opensearch_dashboard_object" "ghsa-vuln-by-cwe" {
  tenant_name = local.default_tenant_name
  body        = <<EOF
  [
    {
      "_id": "visualization:ghsa-vuln-by-cwe",
      "_source": {
        "type": "visualization",
        "visualization": {
          "title": "GHSA vulnerabilities by CWE",
          "visState": "{\"title\": \"GHSA vulnerabilities by CWE\",\"type\": \"table\",\"aggs\": [  {    \"id\": \"1\",    \"enabled\": true,    \"type\": \"count\",    \"params\": {},    \"schema\": \"metric\"  },  {    \"id\": \"2\",    \"enabled\": true,    \"type\": \"terms\",    \"params\": {      \"field\": \"database_specific.cwe_ids.keyword\",      \"orderBy\": \"1\",      \"order\": \"desc\",      \"size\": 10,      \"otherBucket\": false,      \"otherBucketLabel\": \"Other\",      \"missingBucket\": false,      \"missingBucketLabel\": \"Missing\",      \"customLabel\": \"GHSA vulnerabilities by CWE\"    },    \"schema\": \"bucket\"  }],\"params\": {  \"perPage\": 10,  \"showPartialRows\": false,  \"showMetricsAtAllLevels\": false,  \"showTotal\": false,  \"totalFunc\": \"sum\",  \"percentageCol\": \"\"}\n}",
          "uiStateJSON": "{}",
          "description": "",
          "version": 1,
          "kibanaSavedObjectMeta": {
            "searchSourceJSON": "{\"query\":{\"query\":\"\",\"language\":\"kuery\"},\"filter\":[],\"indexRefName\":\"kibanaSavedObjectMeta.searchSourceJSON.index\"}"
          }
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