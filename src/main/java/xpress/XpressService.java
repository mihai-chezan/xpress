/**
 * 
 */
package xpress;

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
        environment.addResource(new VoteResource());
        environment.addResource(new MoodGraphResource());
        environment.addResource(new GraphsTagsResource());
    }

}
