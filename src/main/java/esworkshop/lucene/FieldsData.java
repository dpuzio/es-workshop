package esworkshop.lucene;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FieldsData {
    private Map<String, String> fields;

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "FieldsData{" +
                "fields=" + fields +
                '}';
    }

    public static class Collection {
        private List<FieldsData> docs;

        public void setDocs(List<FieldsData> docs) {
            this.docs = docs;
        }

        public List<FieldsData> getDocs() {
            return docs;
        }

        @Override
        public String toString() {
            return "Collection{" +
                    "docs=" + docs +
                    '}';
        }


    }
    static Collection read() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        ObjectReader objectReader = objectMapper.readerFor(FieldsData.Collection.class);
        FieldsData.Collection collection = objectReader.readValue(new File("src/main/resources/ala.yml"));
        return collection;
    }

}
