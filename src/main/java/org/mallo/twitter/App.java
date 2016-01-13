package org.mallo.twitter;

import java.util.List;
import java.util.Scanner;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Mathius Girik Allo
 */
public class App
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
	    
		ConfigurationBuilder confBuilder = new ConfigurationBuilder();
	    confBuilder.setDebugEnabled(true);
	    confBuilder.setOAuthConsumerKey(Util.getOAuthConsumerKey());
	    confBuilder.setOAuthConsumerSecret(Util.getOAuthConsumerSecret());
	    confBuilder.setOAuthAccessToken(Util.getOAuthAccessToken());
	    confBuilder.setOAuthAccessTokenSecret(Util.getOAuthAccessTokenSecret());

	    TwitterFactory factory = new TwitterFactory(confBuilder.build());
	    Twitter twitter = factory.getInstance();
	    
	    //To prompt user to enter username 
	    System.out.printf("Please enter a twitter username: ");
	  	String username  = sc.next();
	    
	  	try 
	    {
	  		//To display the last 5 tweets for the username
	  		List<Status> statuses = twitter.getUserTimeline(username);
            
	  		System.out.println("The latest 5 tweets by user @" + username + " are:");
            int counter = 0;
            
            for ( Status status : statuses ) 
            {
            	counter ++;
                
            	if ( counter > 5 ) {
                	break;
                }
                
            	System.out.println("@" + username + " - " + status.getText());
            }
            
            if ( counter == 0 ) {
            	System.out.println("There is no tweets record found");
            }
            
            
            //To retrieve User details
            User user = twitter.showUser(username);
            System.out.printf("\nTotal number of tweets is %d\n", user.getStatusesCount());
            System.out.printf("Total number of followings is %d\n", user.getFriendsCount());
            System.out.printf("Total number of followers is %d\n", user.getFollowersCount());
        } 
	  	catch ( TwitterException ex ) {
            System.out.printf("Failed to retrieve Tweets for username: %s\n", username);
            System.out.printf("Reason: %s", ex.getMessage());
            System.exit(1);
        }
	    
	    sc.close();
	}
}
