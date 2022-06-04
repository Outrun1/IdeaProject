package Translate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import Translate.com.baidu.translate.demo.TransApi;
import Translate.com.youdao.translate.demo.HttpRequest;

public class Main extends JFrame {
	private JPanel contentPane;
    private JButton search;
    private JTextArea words;
    private JTextArea youdao_meaning;
    private JTextArea baidu_meaning;
    private JTextArea comp;
    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20211205001019629";
    private static final String SECURITY_KEY = "qVQKUvWawyCHBzWw7FGE";
    public Main(){
    	this.setTitle("句子翻译");//设置标题
        this.setSize(450, 500);   //设置窗口大小
        this.setLocationRelativeTo(null);//居中显示
        
        this.contentPane = new JPanel();//初始化面板
        this.contentPane.setLayout(null);//设置布局NULL
        words = new JTextArea(20, 4);//设置查询框
        search = new JButton("翻译");//查询按钮
        youdao_meaning = new JTextArea(20, 4);//有道翻译句子意思显示
        baidu_meaning = new JTextArea(20, 4);//百度翻译句子意思显示
        comp = new JTextArea(20, 4);//比较显示
        
        words.setBounds(40, 20, 280, 100);
        search.setBounds(330, 20, 80, 28);
        youdao_meaning.setBounds(40, 130, 370, 100);
        youdao_meaning.setLineWrap(true);
        baidu_meaning.setBounds(40, 240, 370, 100);
        baidu_meaning.setLineWrap(true);
        comp.setBounds(40, 350, 370, 100);
        words.setLineWrap(true);
        comp.setLineWrap(true);
        this.contentPane.add(words);
        this.contentPane.add(search);
        this.contentPane.add(youdao_meaning);
        this.contentPane.add(baidu_meaning);
        this.contentPane.add(comp);
        this.add(this.contentPane);
        
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = HttpRequest.searchWord(words.getText());	//中文翻译英文
                JSONObject jsonObject = JSONObject.parseObject(result);	//解析json字段

                com.alibaba.fastjson2.JSONArray jsonArray = (com.alibaba.fastjson2.JSONArray) jsonObject.get("translation");	//解析json数组字段
                String content1 = new String();
                content1 += jsonArray.get(0);	//jsonArray数组中只有一个元素，所以直接取第一个元素 
                //有道翻译完成，写入content1中
                
                TransApi api = new TransApi(APP_ID, SECURITY_KEY);
                String query = words.getText();
                String str = api.getTransResult(query, "auto", "en");    //中文翻译英文
                JSONObject  jsonObj = JSONObject.parseObject(str);    //解析json字段
                com.alibaba.fastjson2.JSONArray  js = (com.alibaba.fastjson2.JSONArray) jsonObj.get("trans_result");	//解析json数组字段
                jsonObj = (JSONObject) js.get(0);	//js数组中只有一个元素，所以直接取第一个元素
                String content2= new String();
                content2 += jsonObj.get("dst");
                //百度翻译完成，写入content2中
                
                String[] arr1 = content1.split(" ");
        		String[] arr2 = content2.split(" ");
        		String content3 = new String();
        		int num=0;
        		for (int i = 0; i < arr2.length; i++)
        			for (int j = 0; j < arr1.length; j++)
        				if (arr1[j].equals(arr2[i])){
        					content3 += (arr1[j] + " ");
        					num++;
        				}
        		//翻译结果比较完成，写入content3中
                youdao_meaning.setText("有道翻译结果：\n"+content1);
                baidu_meaning.setText("百度翻译结果：\n"+content2);//得到dst字段，即译文，并输出
                comp.setText("共同单词个数："+ num +"\n分别是："+content3);
            }
        });
        
        this.setVisible(true);//设置可见
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//设置关闭
    }
    public static void main(String[] args) {
    	new Main();
    }
}
