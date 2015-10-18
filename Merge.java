
package pos_tagger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Merge {
    
    void get_count(String word, String tags)
    {
        try
        {
            int count = 0;
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Worlist", "wordnet", "*");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(WORD_LIST) from Wordnet.TAGGER");
            if(rs.next())
            {
                count = rs.getInt(1);
            }
            con.close();
            if(count == 50000)
                delete();
            insert(word,tags);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    void delete()
    {
        try
       {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Worlist", "wordnet", "*");
            Statement st = con.createStatement();
            st.executeUpdate("DELETE from Wordnet.TAGGER WHERE WORD_LIST IN(select WORD_LIST from Wordnet.TAGGER order by ACCESS_DATE,FREQ FETCH FIRST 1 ROWS ONLY)");
            con.close();
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
        }
    }
    
    void insert(String word, String tags)
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Worlist", "wordnet", "*");
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO WORDNET.TAGGER (WORD_LIST, TAG_LIST) VALUES ('" + word.toLowerCase() + "', '" + tags +"')");
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
}
