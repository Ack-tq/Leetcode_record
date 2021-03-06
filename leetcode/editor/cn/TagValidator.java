import java.util.ArrayDeque;

/**
Given a string representing a code snippet, implement a tag validator to parse 
the code and return whether it is valid. 

 A code snippet is valid if all the following rules hold: 

 
 The code must be wrapped in a valid closed tag. Otherwise, the code is invalid.
 
 A closed tag (not necessarily valid) has exactly the following format : <TAG_
NAME>TAG_CONTENT</TAG_NAME>. Among them, <TAG_NAME> is the start tag, and </TAG_
NAME> is the end tag. The TAG_NAME in start and end tags should be the same. A 
closed tag is valid if and only if the TAG_NAME and TAG_CONTENT are valid. 
 A valid TAG_NAME only contain upper-case letters, and has length in range [1,9]
. Otherwise, the TAG_NAME is invalid. 
 A valid TAG_CONTENT may contain other valid closed tags, cdata and any 
characters (see note1) EXCEPT unmatched <, unmatched start and end tag, and unmatched 
or closed tags with invalid TAG_NAME. Otherwise, the TAG_CONTENT is invalid. 
 A start tag is unmatched if no end tag exists with the same TAG_NAME, and vice 
versa. However, you also need to consider the issue of unbalanced when tags are 
nested. 
 A < is unmatched if you cannot find a subsequent >. And when you find a < or </
, all the subsequent characters until the next > should be parsed as TAG_NAME (
not necessarily valid). 
 The cdata has the following format : <![CDATA[CDATA_CONTENT]]>. The range of 
CDATA_CONTENT is defined as the characters between <![CDATA[ and the first 
subsequent ]]>. 
 CDATA_CONTENT may contain any characters. The function of cdata is to forbid 
the validator to parse CDATA_CONTENT, so even it has some characters that can be 
parsed as tag (no matter valid or invalid), you should treat it as regular 
characters. 
 

 
 Example 1: 

 
Input: code = "<DIV>This is the first line <![CDATA[<div>]]></DIV>"
Output: true
Explanation: 
The code is wrapped in a closed tag : <DIV> and </DIV>. 
The TAG_NAME is valid, the TAG_CONTENT consists of some characters and cdata. 
Although CDATA_CONTENT has an unmatched start tag with invalid TAG_NAME, it 
should be considered as plain text, not parsed as a tag.
So TAG_CONTENT is valid, and then the code is valid. Thus return true.
 

 Example 2: 

 
Input: code = "<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>"
Output: true
Explanation:
We first separate the code into : start_tag|tag_content|end_tag.
start_tag -> "<DIV>"
end_tag -> "</DIV>"
tag_content could also be separated into : text1|cdata|text2.
text1 -> ">>  ![cdata[]] "
cdata -> "<![CDATA[<div>]>]]>", where the CDATA_CONTENT is "<div>]>"
text2 -> "]]>>]"
The reason why start_tag is NOT "<DIV>>>" is because of the rule 6.
The reason why cdata is NOT "<![CDATA[<div>]>]]>]]>" is because of the rule 7.
 

 Example 3: 

 
Input: code = "<A>  <B> </A>   </B>"
Output: false
Explanation: Unbalanced. If "<A>" is closed, then "<B>" must be unmatched, and 
vice versa.
 

 
 Constraints: 

 
 1 <= code.length <= 500 
 code consists of English letters, digits, '<', '>', '/', '!', '[', ']', '.', 
and ' '. 
 
 Related Topics ??? ????????? ???? 39 ???? 0

*/
  
  public class TagValidator{
      public static void main(String[] args) {
           Solution solution = new TagValidator().new Solution();
	      System.out.println(solution.isValid("<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>"));
      }
      //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isValid(String code) {
        int n = code.length();
	    ArrayDeque<String> tags = new ArrayDeque<>();

		int i = 0;
	    while (i < n) {
		    if (code.charAt(i) == '<') {
				//?????????????????????????????????????????????
				if (i == n - 1) return false;
			    if (code.charAt(i + 1) == '/') {
				    //??????????????????
				    int j = code.indexOf('>', i);
					//???????????????????????????????????????
				    if (j < 0) return false;
					String tagName = code.substring(i + 2, j);
					if (tags.isEmpty() || !tags.peek().equals(tagName)) return false;
					tags.pop();
					i = j + 1;
					//???????????????????????????????????????????????????????????????????????????stack????????????code????????????????????????????????????????????????tags_content
				    if (tags.isEmpty() && i != n) return false;
			    } else if (code.charAt(i + 1) == '!') {
					//cdata??????
				    //cdata???????????????????????????????????????????????????????????????????????????
				    //??????cdataTag??????????????????????????????
				    if (tags.isEmpty() || i + 9 > n) return false;
				    String cdataTag = code.substring(i + 2, i + 9);
					if (!cdataTag.equals("[CDATA[")) return false;

				    int j = code.indexOf("]]>", i + 2);
					if (j < 0) return false;
					i = j + 1;
			    } else {
					//??????????????????
				    int j = code.indexOf('>', i);
					if (j < 0) return false;
				    String tagsName = code.substring(i + 1, j);
					if (tagsName.length() < 1 || tagsName.length() > 9) return false;
				    for (int k = 0; k < tagsName.length(); k++) {
						if (!Character.isUpperCase(tagsName.charAt(k))) return false;
				    }
				    tags.push(tagsName);
					i = j + 1;
			    }
		    } else {
				//?????????<???????????????tag_content???????????????stack?????????????????????????????????????????????????????????????????????
			    if (tags.isEmpty()) return false;
				i++;
		    }
	    }
		return tags.isEmpty();
    }
}
//leetcode submit region end(Prohibit modification and deletion)

  }