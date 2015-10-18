package pos_tagger;

import java.util.StringTokenizer;
import java.sql.*;

public class Pos_Tagger
{
    public Words wordlist = null;
    void tokenize(String source)
    {
        int count = 0;
        Words startlist = new Words();
        StringTokenizer st1 = new StringTokenizer(source,".");
        while(st1.hasMoreTokens())
        {
            String sentence = st1.nextToken();
            sentence = sentence.replaceAll("\\s+", " ");
            StringTokenizer st = new StringTokenizer(sentence," ");
            while(st.hasMoreTokens())
            {
                String word = st.nextToken();
                int comma = 0;
                String pos = null;
                if(word.contains(","))
                {
                    comma = 1;
                    word = word.substring(0, word.lastIndexOf(","));
                }
                else if(word.endsWith("n’t"))
                {
                    comma = 4;
                    pos = word.substring(word.indexOf("n’t"));
                    word = word.substring(0, word.indexOf("n’t"));
                }
                else if(word.contains("'"))
                {
                    comma = 2;
                    pos = word.substring(word.lastIndexOf("'"));
                    word = word.substring(0, word.lastIndexOf("'"));
                }
                else if(word.contains("?"))
                {
                    comma = 3;
                    word = word.substring(0, word.lastIndexOf("?"));
                }
                Words newlink = new Words();
                newlink.initializeword(word);
                if (count ==0)
                {
                    newlink.next = wordlist;
                    wordlist = newlink;
                    startlist = wordlist;
                    startlist.next = null;
                    startlist.prev = null;
                    count++;
                }
                 else
                {
                    newlink.prev = startlist;
                    startlist.next = newlink;
                    startlist = newlink;
                }
                newlink = null;
                if(comma != 0)
                {
                    if(comma == 1)
                        word = ",";
                    else if(comma == 3)
                        word = "?";
                    else
                        word = pos;
                    
                    comma = 0;
                    newlink = new Words();
                    newlink.initializeword(word);
                    newlink.prev = startlist;
                    startlist.next = newlink;
                    startlist = newlink;
                }
            }
           Words newlink = new Words();
        newlink.initializeword(".");
        newlink.prev = startlist;
        startlist.next = newlink;
        startlist = newlink;
        startlist.next = null;
        }
    }
    
    public void get_tag()
    {
        try
        {
            boolean result_found = false;
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Worlist", "wordnet", "*");
            Statement st = con.createStatement();
            for(Words list = wordlist; list.word != null ;)
            {
                if(!list.word.equals(".") && !list.word.equals(","))
                {
                    ResultSet rs = st.executeQuery("select Tag_List from Wordnet.TAGGER where Word_List = '" + list.get_word().toLowerCase() + "'");
                    if(rs.next())
                    {
                        String Tags = rs.getString(1);
                        list.initializetag(Tags);
                        result_found = true;
                        try
                        {
                            Class.forName("org.apache.derby.jdbc.ClientDriver");
                            Connection con1 = DriverManager.getConnection("jdbc:derby://localhost:1527/Worlist", "wordnet", "*");
                            Statement st1 = con1.createStatement();
                            st1.executeUpdate("UPDATE Wordnet.TAGGER SET ACCESS_DATE = CURRENT DATE , FREQ = FREQ + 1 where Word_List = '" + list.get_word().toLowerCase() + "'");
                        }
                        catch(Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                    if(result_found == false)
                    {
                        irregular_verbs iv = new irregular_verbs();
                        String tag = iv.verbs(list.word);
                        multi_domain md = new multi_domain();
                        String p_tags = md.googlesearch(list.word, tag);
                        if(p_tags.equals(""))
                        {
                            p_tags =  "NN";
                        }
                        list.initializetag(p_tags);
                        Merge mg = new Merge();
                        mg.get_count(list.word, list.tag);
                    }
                }
                else
                    list.initializetag("");
                result_found = false;
                if(list.next != null)
                {
                    list = list.next;
                }
                else
                    break;
            }
        }
        
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
        }
         
    }
    
    public Words get_start_list()
    {
        return wordlist;
    }
    
    public static void main(String[] args) {
        GUI g = new GUI();
        g.setVisible(true);
    }
}
