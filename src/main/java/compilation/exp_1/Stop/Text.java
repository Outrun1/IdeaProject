package compilation.exp_1.Stop;

import java.io.*;
import java.util.*;

public class Text {
	private HashSet<String> stopWordHash = new HashSet<>();				// 5% * 2 stop words
	private ArrayList<String> stopWordList = new ArrayList<>();
	private ArrayList<String> finalWords = new ArrayList<>();

	FileWriter fwText = new FileWriter("C:\\Users\\hp\\Desktop\\课件\\大三下\\编译原理\\Exp_1\\text.txt");
	BufferedWriter outputText = new BufferedWriter(fwText);
	FileWriter fwStop = new FileWriter("C:\\Users\\hp\\Desktop\\课件\\大三下\\编译原理\\Exp_1\\StopWord.txt");
	BufferedWriter outputStop = new BufferedWriter(fwStop);

	private int textlengthInWords = 0;			// 1000  words total
	private float stopWordPercent = 0.0f;       // 5%    stop words,
	private int numStopWords = 0;
	private  int wordMinLen = 0;
	private  int wordMaxLen = 0;
	
	public Text(int textlengthInWords, float stopWordPercent, int wordMinLen, int wordMaxLen) throws IOException {
		this.textlengthInWords = textlengthInWords;
		this.stopWordPercent = stopWordPercent;
		this.wordMinLen = wordMinLen;
		this.wordMaxLen = wordMaxLen;
	}

	// 创建停用词，并存储到文件中
	private void createStopWords() throws IOException {

		numStopWords = (int) (textlengthInWords * (stopWordPercent / 100) * 2);
		System.out.println(numStopWords);
		RandomWord w = new RandomWord(wordMinLen, wordMaxLen);
		for(int i = 0; i < numStopWords; i++) {
			String word = w.randomWord();
			outputStop.write(word);
			outputStop.newLine();
			stopWordHash.add(word);
			stopWordList.add(word);
			finalWords.add(word);
		}
		outputStop.flush(); outputStop.close(); fwStop.close();
	}
	
	public void create() throws IOException {
		
		createStopWords();
		//gather norm words;
		int normWords = textlengthInWords - numStopWords;
		RandomWord w = new RandomWord(wordMinLen, wordMaxLen);
		for(int i = 0; i < normWords; i++) {
			String nWord = w.randomWord();
			if (stopWordHash.contains(nWord)) {
				i--;
				continue;
			}
			finalWords.add(nWord);
		}

		Random r =  new Random();
		//randomly write text;
		for(int i = 1; i <= textlengthInWords; ++i) {
			int index = r.nextInt(finalWords.size());
			String word = finalWords.get(index);

			// 平均每8个单词换一次行
			// index & 7 相当于对8取余
			outputText.write(word + " ");
			if ((i & 7) == 0)
				outputText.newLine();
		}
		outputText.flush();
	}

	public void filter() {
		try {
			FileReader frText = new FileReader("C:\\Users\\hp\\Desktop\\课件\\大三下\\编译原理\\Exp_1\\text.txt");
			BufferedReader input = new BufferedReader(frText);
			BufferedWriter output = new BufferedWriter(fwText);
			String str = new String();
			String s;
			while ((s = input.readLine()) != null) {
				str += s;
			}
			String[] strArr = str.split(" ");
			for (int i = 0; i < strArr.length; i++) {
				if (stopWordHash.contains(strArr[i]))
					strArr[i] = "**";
			}
			str = "";
			for (int i = 0; i < strArr.length; i++) {
				str += strArr[i] + " ";
				if ((i + 1 & 7) == 0)
					str += "\n";
			}
			output.write(str);
			output.flush(); output.close(); fwText.close();
			input.close(); frText.close();

		} catch(IOException e){
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		try {
			Text text = new Text(1000, 5, 2, 6);
			text.create();
			text.filter();
		} catch (IOException e) {
			System.out.println(e);
		}

	}
}
