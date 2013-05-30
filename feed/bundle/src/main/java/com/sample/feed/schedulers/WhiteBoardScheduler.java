package com.sample.feed.schedulers;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.log4j.Logger;
import org.osgi.service.component.ComponentContext;

@Component(metatype=true, label="MyWhiteBoardService", description="This is my Description")
@Service
@Property(name="scheduler.expression", value="0 * * * * ?")
public class WhiteBoardScheduler implements Runnable {

    private static final int DEFAULT_BUCKET_SIZE = 500;
       
    @Property(label="WhiteBoar Max Size", description="This is my property description", intValue=0)
    public static final String MAX_SIZE = "my.whiteboard.maxsize";
    
    @Property(label = "%Bucket Size", description = "%Maximum products per section before bucketing, and maximum in each bucket", intValue = DEFAULT_BUCKET_SIZE)
    public static final String BUCKET_SIZE_PROP_NAME = "cq.sample.feed.feeds.bucketsize";

	final Logger log = Logger.getLogger(WhiteBoardScheduler.class);
			
	public void run() {
		// TODO Auto-generated method stub
		log.info("FROM WHTITE BOARD SCHEDULER");
	}
	
	ComponentContext componentContext;
	
	Integer maxSize;
	@Activate
    protected void activate(ComponentContext ctx) throws Exception {
//		maxSize = (Integer)ctx.getProperties().get(MAX_SIZE);
		this.componentContext = ctx;
	}
}