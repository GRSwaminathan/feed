package com.sample.feed.schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.log4j.Logger;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;

import com.day.cq.commons.jcr.JcrUtil;
import com.sample.feed.feeds.AtomFeed;
import com.sample.feed.feeds.FeedHandler;

@Component
public class MyScheduler {

	final String FEED_PATH = "http://www.theverge.com/rss/index.xml"; 
	final Logger log = Logger.getLogger(MyScheduler.class);
	
	final String BASE_PATH = "/etc/feeds/philips";
	
	@Reference
	Scheduler scheduler;
	
	@Reference
	protected SlingRepository repositoy;
	
	FeedHandler handler = new FeedHandler();
	
	protected void activate(ComponentContext componentContext) throws Exception{
		log.info("INSIDE ACTIVATE METHOD");
		String schedulingExpression = "0 * * * * ?";
    	String jobName1 = "case1";
    	Map<String, Serializable> config1 = new HashMap<String, Serializable>();
    	boolean canRunConcurrently = true;
       
        try {
        	scheduler.addJob(jobName1, job1, config1, schedulingExpression, canRunConcurrently);
        } catch (Exception e) {
        	 	log.info("inside exception");
        	e.printStackTrace();
        }   
	}
	
	 final Runnable job1 = new Runnable() {
         public void run() {
         	log.info("SUCCESS:: Executing job123");
         	readFeed();
         }
     };
	
	private void readFeed(){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			URL url = new URL(FEED_PATH); 
            setProxy(); 
            InputStream urlInputStream = url.openConnection().getInputStream();			
			saxParser.parse(urlInputStream, handler);
			log.info("SIZE!!! "+handler.getEntries().size());
//			log.info(handler.getEntries());
			storeFeed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void storeFeed(){
		Session session = null;
		log.info("INSIDE STORE FEED");
		
		try {
			session = repositoy.loginAdministrative(repositoy.getDefaultWorkspace());
			log.info("PATH: "+BASE_PATH);
			log.info("PATH EXIST:: "+session.itemExists(BASE_PATH));
				if (session != null && session.itemExists(BASE_PATH)) {
					Node baseNode = session.getNode(BASE_PATH);
//					Resource resource = resourceResolver.getResource(resourcePath);
//					Node node = resource.adaptTo(Node.class);
//					Session ses = node.getSession();
					log.info("INSIDE IF: "+baseNode.getPath());
					for(String temp:handler.getEntries().keySet()){
						log.info("INSIDE FOR: "+BASE_PATH.concat("/").concat(JcrUtil.createValidName(temp))+" :EXIST: "+session.nodeExists(BASE_PATH.concat("/").concat(JcrUtil.createValidName(temp))));
						if(!session.nodeExists(BASE_PATH.concat("/").concat(JcrUtil.createValidName(temp)))){
							log.info("ADDING NODE: "+JcrUtil.createValidName(temp));
							Node newNode = baseNode.addNode(JcrUtil.createValidName(temp));
							setNodeProperties(newNode,handler.getEntries().get(temp));
							session.save();					
						}
					}
//					ses.save();
				}				
		} catch (RepositoryException e) {
			log.error("Repository Exception");
			e.printStackTrace();
		}finally{
//			resourceResolver.close();
			session.logout();
		}
	}
	
	private void setNodeProperties(Node newNode, AtomFeed atomFeed) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException {
		// TODO Auto-generated method stub
		log.info("INSIDE Node PROPERTIES:: ");
		newNode.setProperty("category", atomFeed.getCategory());
		newNode.setProperty("content", atomFeed.getContent());
		newNode.setProperty("contributor", atomFeed.getContributor());
		newNode.setProperty("generator", atomFeed.getGenerator());
		newNode.setProperty("icon", atomFeed.getIcon());
		newNode.setProperty("id", atomFeed.getId());
		newNode.setProperty("link", atomFeed.getLink());
		newNode.setProperty("logo", atomFeed.getLogo());
		newNode.setProperty("published", atomFeed.getPublished());
		newNode.setProperty("rights", atomFeed.getRights());
		newNode.setProperty("subtitle", atomFeed.getSubtitle());
		newNode.setProperty("title", atomFeed.getTitle());
		newNode.setProperty("updated", atomFeed.getUpdated());
		newNode.setProperty("author_name", atomFeed.getAuthor().getName());	
	}

	private static void setProxy() throws IOException 
    { 
        Properties sysProperties = System.getProperties(); 
        sysProperties.put("proxyHost", "proxy1.wipro.com"); 
        sysProperties.put("proxyPort", "8080"); 
        System.setProperties(sysProperties); 
    } 
}
