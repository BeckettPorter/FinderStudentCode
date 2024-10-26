import java.io.BufferedReader;
import java.io.IOException;
/**
 * Finder
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Beckett Porter
 **/

public class Finder {

    private static final String INVALID = "INVALID KEY";
    private static final int p = 999983;

    public Finder() {}

    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException
    {
        // TODO: Complete the buildTable() function!
        // Make array of like 1 mil (near p value) of arraylists, then store each match in that arraylist and if
        // there are multiple entries (ie, collisions) then go through and check each thing individually.
        br.close();
    }

    public String query(String key){
        // TODO: Complete the query() function!
        return INVALID;
    }
}