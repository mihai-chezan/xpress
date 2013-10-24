/**
 * 
 */
package xpress;

import xpress.storage.InMemoryRepository;

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
        environment.addResource(new TagCloudResource());
        final VoteResource voteResource = new VoteResource(InMemoryRepository.getInstance());
        voteResource.setRepository(InMemoryRepository.getInstance());
        environment.addResource(voteResource);
        environment.addResource(new MoodGraphResource(new MoodGraphGenerator(InMemoryRepository.getInstance())));
        environment.addResource(new GraphTagsResource());
    }

}
