package com.sample.feed.feeds;

public class Author {
	
	private String name;
	private String email;
	private String uri;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append(",");
		builder.append(email);
		builder.append(",");
		builder.append(uri);
		return builder.toString();
	}
}