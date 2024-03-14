/**
 * @author Benny Sun
 */
public class MagnitudeFilter implements Filter
{
    private double max, min;
    
    public MagnitudeFilter(double min, double max)
    {
        this.max = max;
        this.min = min;
    }
    
    public boolean satisfies(QuakeEntry qe)
    {
        return max >= qe.getMagnitude()
            && min <= qe.getMagnitude();
    }
    
    public String getName()
    {
        return this.getClass().getSimpleName();
    }
}
