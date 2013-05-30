package com.sample.feed.feeds;

public class AtomFeed {
	
	private String id;
	private String title;
	private String subtitle;
	private String updated;
	private Author author;
	private String link;
	private String category;
	private String contributor;
	private String generator;
	private String icon;
	private String logo;
	private String rights;
	private String published;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("PUBLISHED: ");
		builder.append(published);
		builder.append(",");
		builder.append("UPDATED: ");
		builder.append(updated);
		builder.append(",");
		builder.append("TITLE: ");
		builder.append(title);
		builder.append(",");
		builder.append("CONTENT: ");
		builder.append(content);
		builder.append(",");
		builder.append("LINK: ");
		builder.append(link);
		builder.append(",");
		builder.append("ID: ");
		builder.append(id);
		builder.append(",");
		builder.append("AUTHOR: ");
		builder.append(author);
		return builder.toString();
	}
}