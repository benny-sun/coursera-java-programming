/**
 * @author Duke Software Team 
 */
public interface Filter
{
    public boolean satisfies(QuakeEntry qe);
    public String getName();
}
