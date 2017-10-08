package esworkshop.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Simple {

    public static final String CONTENT_FIELD_NAME = "content";

    public static void main(String[] args) throws IOException, ParseException {
        new Simple().run();

    }

    private void run() throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();

        // Store the index in memory:
        Directory directory = new RAMDirectory();
        // To store an index on disk, use this instead:
        //Directory directory = FSDirectory.open("/tmp/testindex");
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        List<Document> docs = createDocs();
        iwriter.addDocuments(docs);
        iwriter.close();

        // Now search the index:
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser(CONTENT_FIELD_NAME, analyzer);
        Query query = parser.parse("ala");
        ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
        System.out.println(String.format("Found %d documents", hits.length));
        // Iterate through the results:
        for (ScoreDoc hit : hits) {
            Document hitDoc = isearcher.doc(hit.doc);
            System.out.println(
                    String.format(
                            "Hit: %s %s",
                            hit,
                            hitDoc));
        }
        ireader.close();
        directory.close();
    }

    private List<Document> createDocs() throws IOException {
        FieldsData.Collection read = FieldsData.read();
        return read.getDocs().stream().map(fields -> {
            Document doc = new Document();
            doc.add(new Field(CONTENT_FIELD_NAME, fields.getFields().get("content"), TextField.TYPE_STORED));
            return doc;
        }).collect(Collectors.toList());
    }
}
