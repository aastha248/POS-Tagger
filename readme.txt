Name : Aastha Gupta

Programming Language: java

Description: Designed a Hybrid Model for POS Tagging. 

•	It is a hybrid model of Multi-Domain web based algorithm and Memory based learning algorithm for POS tagging, developed in Java framework.
•	If the word 'Unknown' to the lexicon, it is searched over the web. The result of the web search contains all possible tags for the word. Then the POS rules are applied for tagging
•	If the word is 'Known' to the lexicon, its lexical representation is retrieved and then POS tagging rules are applied.
•	The results of the web search are stored into the lexicon for future use.
•	Saving results in the lexicon, effectively deals with the problem of sparse data in Memory based learning algorithm and the problem of runtime overhead in Multi-Domain web based algorithm.

For detailed information, refer 

http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=6954244&url=http%3A%2F%2Fieeexplore.ieee.org%2Fstamp%2Fstamp.jsp%3Ftp%3D%26arnumber%3D6954244


Compilation Instruction: 

Requires web connection and database connection with tables initialized in the "Wordlist" folder.
The Entry point of the code is in pos_tagger.java file. Use standard java commands to compile and run the code.