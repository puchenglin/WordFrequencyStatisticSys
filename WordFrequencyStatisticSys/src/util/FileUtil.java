package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileUtil {

	/***
	 * 读取文件，解析出单词，并统计单词数量
	 * @param filePath
	 * 文件路径
	 * @return
	 * 返回Map对象，key是单词，value对应单词出现的次数
	 */
	public static Map<String,Integer> readtxtFile(String filePath) {
		Map<String, Integer> index_map = new HashMap<String, Integer>();
		BufferedReader reader = null;
		File file = new File(filePath);
		try {
			FileInputStream in = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String lineText = "";
			while((lineText=reader.readLine())!= null) {
				lineText = lineText.replaceAll("\\p{Punct}", " ").trim();//去除标点符号
//				lineText = lineText.replaceAll("\\pP", " ").trim();
//				System.out.println(lineText);
				
				String[] lineArray = lineText.split(" ");
				
				for(String word:lineArray) {
					word = word.toLowerCase().trim();
					if(word.length()>0)
						if(index_map.containsKey(word)) {
							int value = index_map.get(word)+1;
							index_map.remove(word);
							index_map.put(word, value);
						}
						else {
							index_map.put(word, 1);
						}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return index_map;
	}
	
	/**
	 * 统计两个单词的词频统计
	 * @param filePath
	 * @return
	 */
	public static Map<String,Integer> readtxtFile2(String filePath) {
		Map<String, Integer> index_map = new HashMap<String, Integer>();
		BufferedReader reader = null;
		File file = new File(filePath);
		try {
			FileInputStream in = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuilder buffer = new StringBuilder();
			String lineText = "";
			while((lineText=reader.readLine())!= null) {//读取文件
				lineText = lineText.replaceAll("\\p{Punct}", " ").trim();//去除标点符号
//				lineText = lineText.replaceAll("\\pP", " ").trim();
//				System.out.println(lineText);
				buffer.append(lineText);
				buffer.append(" ");
			}
//			System.out.println(buffer.toString().trim());
			//处理数据，针对特殊数据   如：I'm  don't...
			String[] lineArray = buffer.toString().trim().split(" ");
			ArrayList<String> token = new ArrayList<String>();
			for(String buff:lineArray)
			{
				String temp = buff.toLowerCase().trim();
				if(temp.length()>0&&temp!="m"&&temp!="t"&&temp!="ve"&&temp!="s"&&temp!="ll"&&temp!="d")
				{
					token.add(buff);
				}
			}
			
			StringBuilder twoWordArrayStr = new StringBuilder();
			for(int i=0;i<token.size()-1;i++)
			{
				twoWordArrayStr.append(token.get(i)+token.get(i+1)+"&");
			}
			/*String temp1,temp2 = "";
			for(int i = 0;i < lineArray.length-1; i++)
			{
				temp1 = lineArray[i].toLowerCase().trim();
				temp2 = lineArray[i+1].toLowerCase().trim();
				if(temp1.length()>0) {
					twoWordArrayStr.append(temp1);
				}
				else {
					continue;
				}
				
				if(temp2.length()>0) {
					twoWordArrayStr.append(temp2);
				}
				else {
					twoWordArrayStr.append(lineArray[i+2].toLowerCase().trim());
				}
				twoWordArrayStr.append("&");
			}*/
//			System.out.println(twoWordArrayStr.toString().trim());
			
			String[] newlineArray = twoWordArrayStr.toString().trim().split("&");
			for(String word:newlineArray) {
				word = word.toLowerCase().trim();
				if(word.length()>0)
					if(index_map.containsKey(word)) {
						int value = index_map.get(word)+1;
						index_map.remove(word);
						index_map.put(word, value);
					}
					else {
						index_map.put(word, 1);
					}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return index_map;
	}
	
	public static List<Map.Entry<String,Integer>> sort(Map<String,Integer> source_data) {
		List<Map.Entry<String,Integer>> sort_data = new ArrayList<Map.Entry<String,Integer>>(source_data.entrySet());
		Collections.sort(sort_data, new Comparator<Map.Entry<String,Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				if(o1.getValue()!=null&&o2.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0)
				{
					return 1;
				} else {
					return -1;
				}
			}
		});
		return sort_data;
	}
	
	public static Map<String,Integer> convert(List<Map.Entry<String,Integer>> in_data)
	{
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		for(int i=0;i<in_data.size();i++) {
			map.put(in_data.get(i).getKey(), in_data.get(i).getValue());
		}
		return map;
	}
	
	public static void main(String[] args) {
		List<Map.Entry<String,Integer>> sort_data = sort(readtxtFile("E:/安娜卡拉琳娜.txt"));
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		for(int i=0;i<5;i++) {
			System.out.println(sort_data.get(i));
			map.put(sort_data.get(i).getKey(), sort_data.get(i).getValue());
		}
//			
		
		System.out.println(map.toString());
		
	}
}
