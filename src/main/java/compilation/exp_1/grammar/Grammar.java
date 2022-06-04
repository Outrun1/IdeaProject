package compilation.exp_1.grammar;

import java.io.*;
import java.util.*;

public class Grammar {
    private ArrayList<String> product = new ArrayList<>();//产生式集合
    private Set<Character> vn = new HashSet<>();//非终结符集
    private Set<Character> vt = new HashSet<>();//终结符集
    private int type = 3;


    void loadFrom(String fileName) throws IOException {

        FileReader frText = new FileReader(fileName);
        BufferedReader input = new BufferedReader(frText);
        String rexLeft = "^[A-Z][a-zA-Z]*$", //用于匹配产生式左边（必须包含一个大写字母）
                rexRight = "^[a-zA-Z]*$"; //用于匹配产生式右边（可以匹配空字符串）

        String s;
        while ((s = input.readLine()) != null) {
            int index = s.indexOf('→');
            if (index > 0) {
                s = s + " ";//解决输入"S→"无法分成两个字符串数组的问题
                String[] split = s.split("→");
                if (split.length == 2) {
                    split[1] = split[1].trim();
                    if (split[0].matches(rexLeft) && split[1].matches(rexRight)) {
                        product.add(s);
                        parseProduction(split[0] + split[1]);
                        analysisType(split[0], split[1]);
                    }
                }
            }
        }
    }

    boolean writeTo(String fileName) throws IOException {
        if(product.isEmpty())
            return false;
        FileWriter fwText = new FileWriter(fileName);
        BufferedWriter outputText = new BufferedWriter(fwText);
        for (String s : product) {
            outputText.write(s);
            outputText.newLine();
        }
        outputText.flush();
        outputText.close();
        fwText.close();
        return true;
    }

    private void analysisType(String s1, String s2) {//分析是几型文法
        if (type > 0) {//如果当前是0型，则不会进入if
            if (type > 1 && s1.length() == 1 && s1.charAt(0) < 97 && s2.length() > 0) {//左边是只有一个大写字母,右边长度大于0
                String r = "^[a-z][A-Z]?$";//匹配3型文法右边,第一个是小写字母，可以有第二个，必须是大写字母
                if (!s2.matches(r)) {//不能匹配3型文法右边，则是2型文法
                    type = 2;
                }
            } else if (s2.length() == 0 || s2.length() >= s1.length()) {
                type = 1;
            } else {
                type = 0;
            }
        }
    }

    private void parseProduction(String s) {//分解产生式
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) < 97) {//大写字母，非终结符
                vn.add(s.charAt(i));
            } else {
                vt.add(s.charAt(i));
            }
        }
    }

    public void print() {//打印非终结符，终结符和产生式
        System.out.print("非终结符：");
        for (char c : vn) {
            System.out.print(c + " ");
        }
        System.out.print("\n终结符：");
        for (char c : vt) {
            System.out.print(c + " ");
        }
        System.out.println("\n产生式：");
        for (String s : product) {
            System.out.println(s);
        }
        if (product.isEmpty()) {//产生式为空
            System.out.println("请先输入产生式");
        } else {
            System.out.println("该文法是 " + type + " 型文法");
        }
    }

    boolean isVT(char symbol) {
        return vt.contains(symbol);
    }

    boolean isVN(char symbol) {
        return vn.contains(symbol);
    }

    int getGrammarType() {
        return type;
    }

    public static void main(String[] args) throws IOException {
        String GrammarRead = "C:\\Users\\hp\\Desktop\\课件\\大三下\\编译原理\\Exp_1\\Grammar.txt";
        String GrammarWrite = "C:\\Users\\hp\\Desktop\\课件\\大三下\\编译原理\\Exp_1\\GrammarWrite.txt";
        Grammar grammar = new Grammar();
        grammar.loadFrom(GrammarRead);
        grammar.writeTo(GrammarWrite);
        grammar.print();
        System.out.println("S是终结符吗：" + grammar.isVN('S'));
        System.out.println("A是终结符吗：" + grammar.isVN('A'));
        System.out.println("a是非终结符吗：" + grammar.isVT('a'));
        System.out.println("s是非终结符吗：" + grammar.isVT('s'));
    }

}

