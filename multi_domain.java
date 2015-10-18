
package pos_tagger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class multi_domain {
    
   String getresult(String temp, String key)
    {
        String result = "";
        if(temp.contains("#noun"))
        {
            if(!result.equals("") && !result.contains("NN"))
            {
                if((key.endsWith("s") && !key.endsWith("ss")) || key.endsWith("es"))
                    result = result + "|NNS";
                else
                    result = "|NN";
            }
            else
            {
                if((key.endsWith("s")&& !key.endsWith("ss")) || key.endsWith("es"))
                    result = result + "NNS";
                else
                    result = "NN";
            }
        }
        if(temp.contains("#pronoun"))
        {
            if(key.startsWith("wh"))
            {
                if(!result.equals("") && !result.contains("WP"))
                        result = result + "|WP";
                else
                    result = "WP";
            }
            else
            {
                if(!result.equals("") && !result.contains("PRP"))
                        result = result + "|PRP";
                else
                    result = "PRP";
            }
        }
        if(temp.contains("#verb"))
        {
            if(key.endsWith("ed"))
            {
                if(!result.equals("") && !result.contains("VB"))
                        result = result + "|VBD|VBN";
                else
                    result = "VBD|VBN";
            }
            else if(key.endsWith("ies") || key.endsWith("es") || key.endsWith("s"))
            {
                if(!result.equals("") && !result.contains("VB"))
                        result = result + "|VBZ";
                else
                    result = "VBZ";
            }
            else if(key.endsWith("ing"))
            {
                if(!result.equals("") && !result.contains("VB"))
                        result = result + "|VBG";
                else
                    result = "VBG";
            }
            else
            {
                if(!result.equals("") && !result.contains("VB"))
                        result = result + "|VB|VBP";
                else
                    result = "VB|VBP";
            }
        }
        if(temp.contains("#adjective"))
        {
            if(key.endsWith("er"))
            {
                if(!result.equals("") && !result.contains("JJ"))
                        result = result + "|JJR";
                else
                    result = "JJR";
            }
            else if(key.endsWith("est"))
            {
                if(!result.equals("") && !result.contains("JJ"))
                        result = result + "|JJS";
                else
                    result = "JJS";
            }
            else
            {
                if(!result.equals("") && !result.contains("JJ"))
                        result = result + "|JJ";
                else
                    result = "JJ";
            }
        }
        if(temp.contains("#preposition"))
        {
            if(!result.equals("") && !result.contains("IN"))
                    result = result + "|IN";
            else
                result = "IN";
        }
        if(temp.contains("#adverb"))
        {
            if(key.endsWith("er"))
            {
                if(!result.equals("") && !result.contains("RB"))
                        result = result + "|RBR";
                else
                    result = "RBR";
            }
            else if(key.startsWith("wh"))
            {
                if(!result.equals("") && !result.contains("RB"))
                        result = result + "|WRB";
                else
                    result = "WRB";
            }
            else
            {
                if(!result.equals("") && !result.contains("RB"))
                        result = result + "|RB";
                else
                    result = "RB";
            }
        }
        if(temp.contains("#conjunction"))
        {
            if(!result.equals("") && !result.contains("CC"))
                    result = result + "|CC";
            else
                result = "CC";
        }
        if(temp.contains("#determiner") || temp.contains("article"))
        {
            if(key.startsWith("wh"))
            {
                if(!result.equals("") && !result.contains("DT"))
                        result = result + "|WDT";
                else
                    result = "WDT";
            }
            else
            {
                if(!result.equals("") && !result.contains("DT"))
                        result = result + "|DT";
                else
                    result = "DT";
            }   
        }
        if(temp.contains("#interjection"))
        {
            if(!result.equals("") && !result.contains("UH"))
                    result = result + "|UH";
            else
                result = "UH";
        }
        if(temp.contains("#modal"))
        {
            if(!result.equals("") && !result.contains("MD"))
                    result = result + "|MD";
            else
                result = "MD";
        }
        return result;
    }
    String googlesearch(String key , String result)
    {
        String inputLine,cresult = "", resultset = "";
        try
        {
            String url = "http://www.ldoceonline.com/search/?q="; 
            String charset="UTF-8";
            String query = String.format("%s",URLEncoder.encode(key, charset));
            URLConnection con = new URL(url+ query).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((inputLine = in.readLine()) != null)
            {
                if(inputLine.contains("class=\"POS\""))
                {
                    cresult = inputLine;
                    cresult = cresult.replace("pronoun", "#pronoun");
                    cresult = cresult.replace("adverb", "#adverb");
                    cresult = cresult.replace(">noun", ">#noun");
                    cresult = cresult.replace(">verb", ">#verb");
                    cresult = cresult.replace("adjective", "#adjective");
                    cresult = cresult.replace("preposition", "#preposition");
                    cresult = cresult.replace("conjunction", "#conjunction");
                    cresult = cresult.replace("determiner", "#determiner");
                    cresult = cresult.replace("interjection", "#interjection");
                    cresult = cresult.replace("modal", "#modal");
                    break;
                }
                else if(inputLine.contains("a href=\"/dictionary/"))
                {
                    int index = inputLine.lastIndexOf(">");
                    int index1 = inputLine.indexOf("<");
                    int index2 = index1;
                    boolean newline = false;
                    while(index2 != index)
                    {
                        index2 = inputLine.indexOf(">", index2 + 1);
                        String temp = inputLine.substring(index1, index2);
                        if (temp.contains("/dictionary/"))
                            newline = true;
                        index1 = inputLine.indexOf("<", index1 + 1);
                        if(index1 != -1 && index1 - index2 > 1)
                        {
                            if(newline == true)
                                resultset = resultset + "|";
                            String results = inputLine.substring(index2 + 1, index1);
                            results = results.replaceAll("\\s+", " ");
                            if(results.length() > 1)
                                results = results.trim();
                            if(Character.isAlphabetic(results.charAt(0)) || Character.isDigit(results.charAt(0)))
                                resultset = resultset + " #" + results;
                            else
                                resultset = resultset + " " + results;
                            newline = false;
                        }
                    }
                    break;
                }
            }
            in.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        resultset = resultset.replaceAll("\\s+", " ");
        if(!resultset.equals(""))
        {
            int index = resultset.indexOf("|");
            System.out.println(resultset);
            while(index != -1)
            {
                int index1 = resultset.indexOf("|",index + 1);
                int index2 = resultset.indexOf(" ", index + 2);
                String temps = resultset.substring(index + 3, index2);
                String temp;
                if(index1 != -1)
                    temp = resultset.substring(index, index1);
                else 
                {
                    int length = resultset.length();
                    temp = resultset.substring(index, length);
                }
                if(temp.contains("#verb"))
                    temps = temps.substring(0,temps.length() - 1);
                if((temps.equals(key) || key.contains(temps)) && resultset.charAt(index2 + 1) == '#')
                {
                    String tem = getresult(temp , key);
                    if(!result.contains("VB") || (result.contains("VB") && !tem.contains("VB")))
                    {
                        if(!result.equals("") && !result.contains(tem) && !tem.equals(""))
                            result = result + "|";
                        if(!result.contains(tem))
                            result = result + tem;
                    }
                }
                index = index1;
            }
        }
        else
            result = getresult(cresult,key);
        return result;
    }
}