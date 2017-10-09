$ curl -XPUT 'localhost:9200/example_index?pretty' -H 'Content-Type: application/json' -d'
{
    "settings" : {
        "index" : {
            "number_of_shards" : 1, 
            "number_of_replicas" : 0 
        }
    }
}
'

$ curl -XPOST localhost:9200/example_index/doc/1/ -d '
{"content":"Ala ma kota"}'

$ curl -XPOST localhost:9200/example_index/doc/1/ -d '
{"content":"Ola ma psa"}'

$ curl -XPOST localhost:9200/example_index/doc/1/ -d '
{"content":"Ala karmi psa"}'

$ curl localhost:9200/_cat/indices
green open books Lrjr8EnRSB2z7GZbgfPLyg 1 0 1 0 4.1kb 4.1kb

$ ll /opt/elasticsearch/elasticsearch-5.6.0/data/nodes/0/indices/Lrjr8EnRSB2z7GZbgfPLyg/0/index/






$ curl -XPOST localhost:9200/books/_close

$ curl -XDELETE localhost:9200/books/


