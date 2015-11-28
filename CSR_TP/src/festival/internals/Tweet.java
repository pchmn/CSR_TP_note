package festival.internals;

import java.util.Collection;

public class Tweet 
{

	/** Content message of the tweet.*/
	private String content_;
	
	/**
	 * 
	 * Creates a new tweet.
	 * 
	 * @param content_
	 */
	public Tweet(String content_) {
		super();
		this.content_ = content_;
	}




	public String getContent() {
		return content_;
	}

}
