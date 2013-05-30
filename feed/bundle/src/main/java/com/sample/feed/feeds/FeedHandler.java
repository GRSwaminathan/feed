package com.sample.feed.feeds;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FeedHandler extends DefaultHandler {

	final Logger log = Logger.getLogger(FeedHandler.class);
	Map<String,AtomFeed> feedEntries = new HashMap<String,AtomFeed>();
	AtomFeed atomFeed;
	Author author;
	StringBuilder temp=new StringBuilder();

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if(qName.equalsIgnoreCase("entry")){			
			atomFeed = new AtomFeed();
		}		
		if(qName.equalsIgnoreCase("published") || qName.equalsIgnoreCase("updated") || qName.equalsIgnoreCase("title") ||
				qName.equalsIgnoreCase("content")|| qName.equalsIgnoreCase("link") || qName.equalsIgnoreCase("id") || qName.equalsIgnoreCase("author")){
			temp=new StringBuilder();
		}		
		if(atomFeed!=null && qName.equalsIgnoreCase("author")){
			author = new Author();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(atomFeed!=null && temp !=null){
			if(qName.equalsIgnoreCase("entry")){			
				feedEntries.put(atomFeed.getId(), atomFeed);
				log.info("ENTRY ENDS ++++++++++++++");
//				log.info(atomFeed);
			}	
			if(qName.equalsIgnoreCase("published")){
				atomFeed.setPublished(temp.toString());
			}		
			if(qName.equalsIgnoreCase("updated")){
				atomFeed.setUpdated(temp.toString());
			}
			if(qName.equalsIgnoreCase("title")){
				atomFeed.setTitle(temp.toString());
			}
			if(qName.equalsIgnoreCase("content")){
				atomFeed.setContent(temp.toString());
			}
			if(qName.equalsIgnoreCase("link")){
				atomFeed.setLink(temp.toString());
			}
			if(qName.equalsIgnoreCase("id")){
				atomFeed.setId(temp.toString());
			}
			if(qName.equalsIgnoreCase("name")){
				author.setName(temp.toString());
				atomFeed.setAuthor(author);
			}					
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		String strtemp = new String(ch,start,length);
		if(temp!=null)
		temp = temp.append(strtemp);
	}
	
	public Map<String, AtomFeed> getEntries(){
		return feedEntries;
	}
}