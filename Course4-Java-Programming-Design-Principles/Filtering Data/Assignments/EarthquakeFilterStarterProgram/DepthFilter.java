/**
 * @author Benny Sun
 */
public class DepthFilter implements Filter
{
    private double max, min;
    
    public DepthFilter(double min, double max)
    {
        this.max = max;
        this.min = min;
    }
    
    public boolean satisfies(QuakeEntry qe)
    {
        return max >= qe.getDepth()
            && min <= qe.getDepth();
    }
    
    public String getName()
    {
        return this.getClass().getSimpleName();
    }
}
