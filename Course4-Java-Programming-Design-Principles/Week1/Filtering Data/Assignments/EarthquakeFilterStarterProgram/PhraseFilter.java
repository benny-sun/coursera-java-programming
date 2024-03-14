/**
 * @author Benny Sun
 */
public class PhraseFilter implements Filter
{
    private String where;
    private String phrase;
    
    public PhraseFilter(String where, String phrase)
    {
        this.where = where;
        this.phrase = phrase;
    }
    
    public boolean satisfies(QuakeEntry qe)
    {
        String title = qe.getInfo();
        
        return (where.equals("start") && title.startsWith(phrase))
            || (where.equals("end") && title.endsWith(phrase))
            || (where.equals("any") && title.contains(phrase));
    }
    
    public String getName()
    {
        return this.getClass().getSimpleName();
    }
}
