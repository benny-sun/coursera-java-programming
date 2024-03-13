/**
 * @author Benny Sun
 */
public class DistanceFilter implements Filter
{
    private Location location;
    private float maxDistance;
    
    public DistanceFilter(Location location, float distance)
    {
        this.location = location;
        this.maxDistance = distance;
    }
    
    public boolean satisfies(QuakeEntry qe)
    {
        float dist = location.distanceTo(qe.getLocation());
        
        return dist < maxDistance;
    }
    
    public String getName()
    {
        return this.getClass().getSimpleName();
    }
}
