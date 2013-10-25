package xpress.graphtags;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import xpress.Mood;
import xpress.graphtags.PieGraphResponse.PieGraphResponseElement;
import xpress.storage.entity.TagByMood;

import com.google.common.collect.Lists;

@Component
public class PieChartGenerator {

    public List<PieGraphResponse> buildGraphResponses(List<TagByMood> tagByMoodList) {
        List<PieGraphResponse> graphs = Lists.newArrayList();
        for (TagByMood tagByMood : tagByMoodList) {
            graphs.add(buildGraphResponse(tagByMood));
        }
        return graphs;
    }

    public PieGraphResponse buildGraphResponse(TagByMood tagByMood) {
        List<PieGraphResponseElement> series = Lists.newArrayList();
        series.addAll(generateGraphFor(tagByMood));
        return new PieGraphResponse(series);
    }

    private List<PieGraphResponseElement> generateGraphFor(TagByMood tagByMood) {
        List<List<Object>> dataEntries = Lists.newArrayList();

        for (Entry<Mood, Integer> frequencyEntry : tagByMood.getFrequency().entrySet()) {
            List<Object> dataEntry = Lists.newArrayList();
            dataEntry.add(frequencyEntry.getKey().name());
            dataEntry.add(frequencyEntry.getValue());
            dataEntries.add(dataEntry);
        }

        PieGraphResponse.PieGraphResponseElement graphElement = new PieGraphResponse.PieGraphResponseElement(tagByMood.getTag(),
                dataEntries);
        List<PieGraphResponseElement> response = Lists.newArrayList();
        response.add(graphElement);
        return response;

    }
}
