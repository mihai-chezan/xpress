/**
 * 
 */
package xpress;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import xpress.graphtags.GraphTagsResource;
import xpress.graphtags.PieChartGenerator;
import xpress.graphtags.TagRetriever;
import xpress.retrieve.TagCloudRetriever;
import xpress.storage.Repository;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * @author mcq
 * 
 */
public class XpressService extends Service<XpressConfiguration> {

    public static void main(String[] args) throws Exception {
        new XpressService().run(args);
    }

    @Override
    public void initialize(Bootstrap<XpressConfiguration> bootstrap) {
        bootstrap.setName("Xpress");
        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    }

    @Override
    public void run(XpressConfiguration configuration, Environment environment) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Repository repository = ac.getBean(Repository.class);
        TagRetriever tagRetriever = ac.getBean(TagRetriever.class);
        PieChartGenerator pieChartGenerator = ac.getBean(PieChartGenerator.class);

        environment.addResource(new TagCloudResource(new TagCloudRetriever(repository)));
        environment.addResource(new VoteResource(repository));
        environment.addResource(new MoodGraphResource(new MoodGraphGenerator(repository)));
        final GraphTagsResource resource = new GraphTagsResource();
        resource.setTagRetriever(tagRetriever);
        resource.setPieChartGenerator(pieChartGenerator);
        environment.addResource(resource);

    }

}
