
package pos_tagger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class irregular_verbs {
    
    String get_tag(char tagcount)
    {
        String tags = "";
        switch(tagcount)
        {
            case '1': tags = "VB|VBP";
                break;
            case '2': tags = "VBD";
                break;
            case '3': tags = "VBN";
                break;
            case '4': tags = "VBZ";
                break;
            case '5': tags = "VBG";
                break;
        }
        return tags;
    }
    String verbs(String word )
    {
        String tags = ""; 
        try
        {
            char first = word.charAt(0);
            word = word.substring(1);
            word = Character.toUpperCase(first) + word;
            Scanner tx = new Scanner(new File("C:\\Users\\Naresh Gupta\\Documents\\NetBeansProjects\\Pos_Tagger\\irregular-verb-list.txt"));
            while(tx.hasNextLine())
            {
                String str = tx.nextLine();
                if(str.contains(word))
                {
                   int index = str.indexOf(word, 0);
                   char aa = str.charAt(index + word.length() + 1);
                   while(index != -1 && aa == ' ')
                   {
                        int index1 = str.indexOf(" ", index);
                        if(tags.equals(""))
                            tags = get_tag(str.charAt(index1 - 1));
                        else
                            tags = tags + "|" + get_tag(str.charAt(index1 - 1));
                        index = str.indexOf(word, index1);
                        aa = str.charAt(index + word.length() + 1);
                    }
                    break;
                }
            } 
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e);
        }
        return tags;
    }
}
