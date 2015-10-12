/**
 * Main class
 * @author Pankaj Yadav (NEU)
 *
 */
public class Main 
{
	public static void main(String[] args)
	{
		
		String seed;
		String searchString;
		
		Crawler crawl=new Crawler();
		if(args.length==1)
		{
			seed=args[0];
			crawl.beginCrawl(seed);
		}
		
		else if(args.length==2)
		{
			seed=args[0];
			searchString=args[1];
			crawl.beginCrawl(seed,searchString);
		}
		else
		{
			System.out.print("Parameters required");
		}	
		
		
	}
}
