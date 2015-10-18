
package pos_tagger;

public class Words {
    public  String word = null, tag = null;
    Words prev = null,next = null;
    
    public void initializeword(String word)
    {
        this.word = word;
    }
    
    public void initializetag(String tag)
    {
        this.tag = tag;
    }
    
    public String get_word()
    {
        return this.word;
    }
    
    public String get_tag()
    {
        System.out.println(this.tag);
        if(this.tag != null && this.tag.contains("|"))
        {
            if(this.tag.contains("JJR") && this.word.endsWith("er")) 
                return this.tag = "JJR";
            if(this.tag.contains("JJS") && this.word.endsWith("est")) 
                return this.tag = "JJS";
            if(this.tag.contains("RB") && (this.word.endsWith("ly") || this.word.endsWith("n't")))
                return this.tag = "RB";
            if(this.tag.contains("RBR") && this.word.endsWith("er")) 
                return this.tag = "RBR";
            if(this.tag.contains("NN"))
            {
                if(this.tag.contains("PRP"))
                    return this.tag = "PRP";
                else if(Character.isUpperCase(this.word.charAt(0)) && !this.word.equals("I"))
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                        return this.tag = "NN";
                }
            }
            if(this.tag.contains("IN") && this.tag.contains("CC"))
            {
                Words ref1= this.prev;
                Words ref2 = this.next;
                if((ref1 != null && ref1.word.equalsIgnoreCase(",")) || (ref2 != null && ref2.word.equalsIgnoreCase(",")))
                    return this.tag = "CC";
                else
                    return this.tag = "IN";
            }
            if(this.tag.contains("IN"))
            {
                Words ref2 = this.next;
                Words ref1 = this.prev;
                if((ref2 != null && ref2.tag.contains("PRP")) || (ref1 != null && (ref1.tag.contains("NN") || ref1.tag.contains("VB"))))
                    return this.tag = "IN";
            }
            if(this.tag.contains("JJ") && this.tag.contains("RB"))
            {
               Words ref2 = this.next;
               Words ref1 = this.prev;
               if(ref2 != null && (ref2.tag.contains("VB") || ref2.tag.contains("JJ")))
                    return this.tag = "RB";
               else if((ref2 != null && ref2.tag.contains("NN")) || (ref1 != null && ref1.tag.contains("VB")) || (ref2 != null && ref2.tag.equals("") && ref1.tag.contains("NN")))
                        return this.tag = "JJ";
            }
            if(this.tag.contains("DT") && this.tag.contains("PRP"))
            {
                Words ref2 = this.next;
                Words ref1 = this.prev;
                if((ref2 != null && ref2.tag.contains("NN")) || this.tag.contains("JJ"))
                    return this.tag = "DT";
                else if((ref1 != null) && (ref1.tag.contains("IN") || ref1.tag.contains("VB")))
                    return this.tag = "PRP";
            }
            if(this.tag.contains("DT") && this.tag.contains("RB"))
            {
                Words ref2 = this.next;
                Words ref1 = this.prev;
                if((ref2 != null && ref2.tag.contains("RBR")) || (ref2 != null && ref2.tag.contains("JJ") && ((ref1 != null) && (ref1.tag.contains("PRP") ||ref1.tag.contains("VB")))))
                    return this.tag = "RB";
                else
                    return this.tag = "DT";
            }
            if(this.tag.contains("JJ") && this.tag.contains("NNP"))
            {
                Words ref1 = this.next;
                Words ref2 = this.prev;
                Words ref3 = ref2.prev;
                if(ref1 != null && ref1.tag.contains("NN"))
                    return this.tag = "JJ";
                else if(ref1 != null && ref1.tag.contains("VB"))
                {
                    if((ref2 != null) && (ref2.word.equalsIgnoreCase("a") || ref2.tag.equalsIgnoreCase("an")))
                        return this.tag = "NNP";
                    else if(ref2 != null && ref2.word.equalsIgnoreCase("the"))
                        return this.tag = "NNPS";
                }
                else if((ref2 != null && (ref2.word.equalsIgnoreCase("a") || ref2.word.equalsIgnoreCase("an"))) && (ref3 != null && (ref3.word.equalsIgnoreCase("is") || ref3.word.equalsIgnoreCase("'s")))) 
                    return this.tag = "NNP";
                else if(ref2 != null && ref2.tag.contains("NNP"))
                    return this.tag = ref2.tag;
                else if((ref2 != null && ref2.word.equalsIgnoreCase("the")) && ((ref3 != null ) && (ref3.word.equalsIgnoreCase("is") || ref3.word.equalsIgnoreCase("'s")))) 
                    return this.tag = "NNPS";
            }
            if(this.tag.contains("JJ") && this.tag.contains("NN"))
            {
                Words ref2 = this.next;
                Words ref1 = this.prev;
                if((ref2 != null && ref2.tag.contains("NN") && ref1 != null && !ref1.tag.contains("JJ")) || (ref2.tag.equals("") && ref1 != null && (ref1.tag.contains("RB") || ref1.tag.contains("VB"))))
                    return this.tag = "JJ";
                else if(((ref1 != null) && ref1.tag.contains("DT") || ref1.tag.contains("NN")) ||(ref2 != null && ref2.tag.contains("VB")))
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                        return this.tag = "NN";
                }
            }
            if(this.tag.contains("JJ") && this.tag.contains("VBN"))
            {
                Words ref1 = this.next;
                Words ref2 = this.prev;
                if(ref1 != null && (ref1.tag.contains("NN") || ref1.word.equalsIgnoreCase("by")))
                    return this.tag = "VBN";
                else if(ref2 != null &&(ref2.tag.contains("RB") || ref2.tag.contains("VBN") || ref2.tag.contains("VBD")))
                    return this.tag = "JJ";
            }
            if(this.tag.contains("NN") && this.tag.contains("RB"))
            {
                Words ref2 = this.prev;
                Words ref1 = this.next;
                if(ref2 != null && ref2.tag.contains("VBP") || (ref1 != null && ref1.tag.contains("VBZ")))
                    return this.tag = "RB";
                else if(ref2 != null && (ref2.tag.contains("IN") || ref2.tag.contains("PRP") || ref2.tag.contains("DT")))
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                       return this.tag = "NN";
                }
            }
            if(this.tag.contains("VBP") && this.tag.contains("VB"))
            {
                Words ref2 = this.prev;
                Words ref1 = this.next;
                if(ref1 != null && ref1.tag.contains("PRP") || ref2 != null && (ref2.word.equalsIgnoreCase("I") || ref2.tag.contains("PRP") || ref2.tag.contains("NN")))
                    return this.tag = "VBP";
                else if((ref2 != null && (ref2.tag.contains("RB") || ref2.tag.contains("VB") || ref2.tag.contains("TO")))||(ref2 == null && !this.tag.contains("NN")))
                    return this.tag = "VB";
            }
            if(this.tag.contains("VBP") && this.tag.contains("NN"))
            {
                Words ref2 = this.prev;
                Words ref1 = this.next;
                if(ref1 != null && ref1.tag.contains("PRP") || ref2 != null && (ref2.word.equalsIgnoreCase("I") || ref2.tag.contains("PRP")))
                    return this.tag = "VBP";
                else if(ref2 != null && (ref2.tag.contains("DT") || ref2.tag.contains("NN")))
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                        return this.tag = "NN";
                }
            }
            if(this.tag.contains("NN") && this.tag.contains("VBG"))
            {
                Words ref2 = this.prev;
                Words ref1 = this.next;
                if((ref2 != null && ref2.tag.contains("JJ")) || (ref1 != null && ref1.tag.contains("VB")))
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                        return this.tag = "NN";
                }
                else if(ref2 != null && (ref2.tag.contains("VB") || ref2.tag.contains("IN")) || ref1 != null && ref1.word.equals(","))
                    return this.tag = "VBG";
            }
            if(this.tag.contains("VBD") && this.tag.contains("NN"))
            {
                Words ref2 = this.next;
                Words ref1 = this.prev;
                if(ref2 != null && (ref2.tag.contains("PRP") || ref2.tag.contains("DT") || ref2.tag.contains("CC") || ref2.tag.contains("RB")))
                    return this.tag = "VBD";
                else if(ref1 != null && (ref1.tag.contains("DT") || ref1.tag.contains("NN") || ref1.tag.contains("JJ") || ref1.tag.contains("IN")))
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                        return this.tag = "NN";
                }
            }
            if(this.tag.contains("VBD") && this.tag.contains("VBN"))
            {
               Words ref1 = this.next;
               Words ref2 = this.prev;
               if(ref2 != null && (ref2 == null || ref2.tag.contains("DT") || ref2.tag.contains("IN")) && ref1 != null && ref1.tag.contains("NN"))
                   return this.tag = "VBN";
               else
                   return this.tag = "VBD";
            }
            if(this.tag.contains("VBZ") && this.tag.contains("NN"))
            {
                Words ref2 = this.prev;
                Words ref1 = this.next;
                if(ref2 != null && (ref2.tag.equals("DT") || ref2.tag.contains("IN") || ref2.tag.contains("TO") || (ref2.tag.contains("NN") && !ref1.tag.contains("TO")) || ref2.tag.contains("JJ")))
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                        return this.tag = "NN";
                }
                else
                    return this.tag = "VBZ";
            }
            if(this.tag.contains("NN") && this.tag.contains("VB"))
            {
                Words ref2 = this.prev;
                if(ref2 != null && (ref2.tag.contains("DT") || ref2.tag.contains("IN") || ref2.tag.contains("NN") || ref2.tag.contains("JJ")))
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                        return this.tag = "NN";
                }
                else
                    return this.tag = "VB";
            }
            if(this.tag.contains("NN") && this.tag.contains("UH"))
            {
                Words ref2 = this.next;
                if(ref2 != null && ref2.word.equals(","))
                    return this.tag = "UH";
                else
                {
                    if(this.tag.contains("NNS"))
                        return this.tag = "NNS";
                    else 
                        return this.tag = "NN";
                }
            }
            if(this.tag.contains("RB"))
            {
                Words ref2 = this.next;
                if(ref2 != null && ref2.tag.contains("JJ"))
                    return this.tag = "RB";
            }
            if(this.tag.contains("CC"))
            {
                Words ref2 = this.prev;
                if(ref2 != null && (ref2.tag.contains("NN") || ref2.word.equals(",") || ref2.tag.contains("RB")))
                    return this.tag = "CC";
            }
            if(this.tag.contains("PDT"))
            {
                Words ref2 = this.next;
                if(ref2 != null && (ref2.tag.contains("PRP$") || ref2.word.equalsIgnoreCase("a") || ref2.word.equalsIgnoreCase("an") || ref2.word.equalsIgnoreCase("the")))
                    return this.tag = "PDT";
                else 
                    return this.tag = "JJ";
            }
            if(this.word.equalsIgnoreCase("when"))
            {
                Words ref2 = this.next;
                while(ref2.next != null)
                {
                    ref2 = ref2.next;
                }
                if(this.word.equalsIgnoreCase("?"))
                    return this.tag = "IN";
                else
                   return this.tag = "WRB";
            }
            if(this.word.equalsIgnoreCase("either") || this.word.equalsIgnoreCase("both") || this.word.equalsIgnoreCase("niether"))
            {
                Words ref2 = this.next;
                if(ref2 != null && (ref2.tag.contains("JJ") || ref2.tag.contains("VB")))
                    return this.tag = "CC";
                else 
                   return this.tag = "DT";
            }
            if(this.word.equalsIgnoreCase("twice") || this.word.equalsIgnoreCase("thrice"))
            {
                Words ref2 = this.next;
                if(ref2 != null && ref2.word.equalsIgnoreCase("the"))
                    return this.tag = "RB";
                else 
                    return this.tag = "JJ";
            }   
            if(this.word.equalsIgnoreCase("one") || this.word.equalsIgnoreCase("ones"))
            {
                Words ref2 = this.prev;
                if(ref2 != null && ref2.tag.contains("JJ"))
                {
                    if(this.word.equalsIgnoreCase("one"))
                        return this.tag = "NN";
                    else
                        return this.tag = "NNS";
                }
                else 
                    return this.tag = "CD";
            }
            if(this.word.equalsIgnoreCase("there"))
            {
                if(this.prev == null)
                {
                   Words ref2 = this.next;
                    if(ref2 != null && ref2.word.equalsIgnoreCase(","))
                        return this.tag = "RB";
                    else
                        return this.tag = "EX";
                }
                else
                    return this.tag = "RB";
            }
            for(Color col : Color.values())
            {   
                if(this.word.equalsIgnoreCase(col.name()))
                {
                    Words ref1 = this.prev;
                    if(ref1 != null && ref1.word.equalsIgnoreCase("dark"))
                    {
                        ref1 = ref1.prev;
                        if(ref1 != null && (ref1.word.equalsIgnoreCase("a") || ref1.word.equalsIgnoreCase("the") || ref1.tag.contains("JJ")))
                        {
                            if(this.word.endsWith("s"))
                                return this.tag = "NNS";
                            else
                                return this.tag = "NN";
                        }
                        else if(ref1 != null && ref1.tag.contains("VB"))
                            return this.tag = "JJ";
                    }
                    else if(ref1 != null && ref1.tag.contains("JJ"))
                    {
                        if(this.word.endsWith("s"))
                            return this.tag = "NNS";
                        else
                            return this.tag = "NN";
                    }
                    break;
                }
            }
        }
        if(this.tag != null && this.word.contains("-"))
        {
            for(Number num : Number.values())
            {   
                if(this.word.equalsIgnoreCase(num.name()))
                    return this.tag = "RB";
                else
                    return this.tag = "JJ";
            }
        }
        if(this.tag.contains("|"))
        {
            int index = this.tag.indexOf("|");
            this.tag = this.tag.substring(0, index);
        }   
        return this.tag;
    }
    
}
