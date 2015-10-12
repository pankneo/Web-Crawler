import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author Pankaj Yadav (NEU)
 * Crawler class representing the actual crawler
 *
 */
public class Crawler 
{

	final String PREFIX = "//en.wikipedia.org/wiki/";
	final String MAIN_PAGE = "//en.wikipedia.org/wiki/Main_Page";

	private Set<WebLink> visitedLookup = new HashSet<WebLink>();
	private Queue<WebLink> unvisitedUrls = new LinkedList<WebLink>();
	private Set<WebLink> crawledUrls=new LinkedHashSet<WebLink>();
	int counter=0;
	

	/**
	 * beginCrawl initiates crawling with only url parameter
	 * 
	 * @param url
	 */
	public void beginCrawl(String url) 
	{
		beginCrawl(url, "");
	}

	/**
	 * beginCrawl with both url and search string
	 * @param url
	 * @param searchString
	 */
	public void beginCrawl(String url, String searchString)
	{

		WebLink seed = new WebLink(url, 1);
		unvisitedUrls.add(seed);

		while (!unvisitedUrls.isEmpty() && crawledUrls.size() < 1000)
		{
			WebLink document = unvisitedUrls.remove();
			if(document.getDepth()<6)
			{
				visitedLookup.add(document);			
				extractLinks(document, searchString);
				
				
				try				 
				{
					//Delay of 1 second
					Thread.sleep(1000L);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(searchString.isEmpty())
		  writeToFile("Simple_Crawler_URLs.txt");
		else
			writeToFile("Focused_Crawler_URLs.txt");

	}

	
	/**
	 * extractLinks performs link crawling and populates the crawled links
	 * @param weblink
	 * @param searchString
	 */
	private void extractLinks(WebLink weblink, String searchString) 
	{
		try
		{
			//Adding timeout to prevent Read Timed out
			Document doc = Jsoup.connect(weblink.getUrl()).timeout(10*1000).get();
			String docText = doc.text();
			if (weblink.getDepth() <= 5 && weblink.getDepth() > 1)
			{
				if (!searchString.isEmpty()
				&& !docText.toLowerCase().contains(searchString.toLowerCase())) 
					return;				
			}
			crawledUrls.add(weblink);
			counter++;
			System.out.println(counter+"."+weblink.getUrl() + "\tDepth=" + weblink.getDepth());
			Elements links = doc.select("a[href]");
			
			for (Element link : links)
			{
				if (isFilterPassed(link.attr("abs:href"), link.attr("href")))
				{

					WebLink newWebLink = new WebLink(link.attr(("abs:href")),
							weblink.getDepth() + 1);
					if (!visitedLookup.contains(newWebLink) 
					&& !unvisitedUrls.contains(newWebLink))
					{
						unvisitedUrls.add(newWebLink);
					}
					
				}
			}

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * isFilterPassed filters the links 
	 * @param absLink
	 * @param relLink
	 * @return
	 */
	private boolean isFilterPassed(String absLink, String relLink)
	{
		return !absLink.contains("#")
				&& !relLink.contains(":")
				&& !absLink.contains(MAIN_PAGE)
				&& absLink.contains(PREFIX);

	}

	/**
	 * writeToFile writes the list of crawled urls to text file
	 * @param filename
	 */
	void writeToFile(String filename)
	{
		FileWriter writer;
		try 
		{
			writer = new FileWriter(filename, true);
		    Iterator<WebLink> setIterator=crawledUrls.iterator();
		    while(setIterator.hasNext()) 
		    {			
              writer.write(setIterator.next().getUrl()+ "\n");  
		    }
		writer.close();
		if(filename.equals("Focused_Crawler_URLs.txt"))
			{
			     System.out.print("\nRetrived Documents:"+visitedLookup.size());
			     System.out.print("\nFocused Crawl Urls:"+crawledUrls.size());
			     float proportion=(float)(crawledUrls.size())/visitedLookup.size();
			     System.out.print("\nRetrival Proportion:"+proportion);
			}
		
		} 
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
