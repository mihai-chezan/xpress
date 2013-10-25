package xpress.graphtags;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PieGraphResponse {

    @JsonProperty
    private final List<PieGraphResponseElement> series;

    public PieGraphResponse(@JsonProperty("series") List<PieGraphResponseElement> series) {
        this.series = series;
    }

    public List<PieGraphResponseElement> getSeries() {
        return series;
    }

    public static class PieGraphResponseElement {

        private static final String TYPE = "pie";
        @JsonProperty
        private final String name;
        @JsonProperty
        private final List<List<Object>> data;

        public String getType() {
            return TYPE;
        }

        public PieGraphResponseElement(@JsonProperty("name") String name, @JsonProperty("data") List<List<Object>> data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public List<List<Object>> getData() {
            return data;
        }
    }

}
