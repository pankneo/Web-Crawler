/**
 * Custom class to support data structure with url and depth.
 * It contains corresponding setters and getters along with common function overrides 
 * @author Pankaj Yadav (NEU)
 *
 */

public class WebLink
{
	private String url;
	private int depth;

	public String getUrl()
	{
		return url;
	}

	public WebLink(String url, int depth)
	{
		super();
		this.url = url;
		this.depth = depth;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getDepth()
	{
		return depth;
	}

	public void setDepth(int depth)
	{
		this.depth = depth;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public String toString() 
	{
		return "WebLink [url=" + url + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebLink other = (WebLink) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
