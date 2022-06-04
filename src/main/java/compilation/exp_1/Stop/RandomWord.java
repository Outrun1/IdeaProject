package compilation.exp_1.Stop;

import java.util.*;

public class RandomWord {

	private  int wordMinLen = 0;
	private  int wordMaxLen = 0;
	RandomWord(int wordMinLen, int wordMaxLen) {
		this.wordMinLen = wordMinLen;
		this.wordMaxLen = wordMaxLen;
	}


	private String randomWordWithFixedLength() {

		StringBuffer sb = new StringBuffer();
		Random myRand = new Random();
		//[wordMinLen, wordMaxLen + 1)
		int len = myRand.nextInt(wordMaxLen - wordMinLen + 1) + wordMinLen;
		for(int i = 0; i < len; ++i) {
			//[0, 26)
			int shift = myRand.nextInt(26);
			char c = (char) ('a' + shift);
			sb.append(c);
		}
		return sb.toString();
	}

	//create a word randomly



	//[minLength=2, maxLength=6]
	public String randomWord() {

		// decide the length of a new word

		// randomly get a word
		return randomWordWithFixedLength();
	}

}
