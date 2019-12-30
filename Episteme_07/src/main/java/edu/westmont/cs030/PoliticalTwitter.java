/**
 * @author Nathan Young
 * Episteme 7
 * PoliticalTwitter.java
 * 3/8/2019
 */
package edu.westmont.cs030;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Program that asks the user to input the twitter handle of a profile, and then searches their most recent 100 tweets
 * for a search term (user input), and then displays those tweets to the console in alphabetical order
 */
public class PoliticalTwitter {

	// Various keys from https://dev.twitter.com app registration
	private static final String CONSUMER_KEY = "9ljVj1KoKgLjsJOYDBwX6FZfV";
	private static final String CONSUMER_SECRET = "dVuRzyQQ9Fp94wVDQvQ0U6Z5WQgQJHbJmVJtigFJfbbLKIclvG";
	private static final String OAUTH_ACCESS_TOKEN = "1102297130486513664-uTllMxgeg7hzuXg6oLo9XoTzgXWXrG";
	private static final String OAUTH_ACCESS_TOKEN_SECRET = "BNEqfDqhvDgTFhrXtqpwlZbfyAIBnIjLwGcitlMFuHwC6";

	public static void main(String[] args) throws TwitterException {
		
		// Create an object that has configuration settings in it.
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		// Set the configuration
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_SECRET);
		cb.setOAuthAccessToken(OAUTH_ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);

		// Set up a factory to manage Twitter connections with the configurations
		TwitterFactory tf = new TwitterFactory(cb.build());

		// Get the twitter connection from the factory
		Twitter twitter = tf.getInstance();

	
		List<Status> statuses = new ArrayList<Status>();
		
		// Asks the user for the target (twitter account to be searched), and the search term
		String TWITTER_HANDLE;
		String SEARCHTERM;
		
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter the twitter handle of the intended target (include \"@\"): ");
			TWITTER_HANDLE=in.next();
		System.out.print("Enter the word you want to search for in the tweets of " + TWITTER_HANDLE + ": ");	
			SEARCHTERM=in.next();
		in.close();
		

		try {
			Paging page = new Paging(1,100); // Get 100 tweets starting from the first page of 100
			statuses = twitter.getUserTimeline(TWITTER_HANDLE,page);
		} catch (TwitterException e) {
		}

		// Creates an array of all tweets that contain the search term
		ArrayList<String> searchTweets=new ArrayList<String>();
		for(Status tweet:statuses) {
			String message=tweet.getText();
			if(message.toLowerCase().contains(SEARCHTERM.toLowerCase())) {
				searchTweets.add(message);
			}
		}
		
		String targetName = statuses.get(0).getUser().getName();// Gets the listed name of the user
		System.out.println();
		
		// Sorts the tweets in alphabetical order and prints them out
		// with "A" being at the top of the console and "Z" being at the bottom
		Collections.sort(searchTweets); 
		for(String tweet:searchTweets) {
			System.out.println(targetName + ": " + tweet);
		}

	}

}
