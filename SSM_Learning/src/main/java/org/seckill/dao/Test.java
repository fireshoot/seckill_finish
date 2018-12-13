package org.seckill.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author yangxin
 * @version 1.00
 * @time 2018/12/6  12:05
 */
public class Test {

    public static void main(String[] args){
        System.out.printf(ReverseSentence("I am a student."));
    }
    public static String ReverseSentence(String str) {
        if(str=="") return  " ";
        ArrayList<String> list=new ArrayList<>();
        String []strings=str.split(" ");
        String new_str="";
        for(int i=strings.length-1;i>=0;i--){
            new_str+=strings[i];
            if(i!=0)
                new_str+=" ";
        }


        return new_str;
    }

    public static String reverse(String str,int start,int end){
        String s=str.substring(0,start);
        char []str1=new char[end-start+1];
        int j=0;
        for(int i=end-1;i>=start;i--,j++){
            str1[j]=str.charAt(i);
        }
        String s1=new String(str1);
        return s+s1+str.substring(end,str.length());
    }
    /**
    * 方法实现说明:
     * 字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它
    * @author      yangxin
    * @date        2018/12/10 16:24
    */
    public static String LeftRotateString(String str,int n) {
        if(n>str.length()||n<0){
            return "";
        }
        String rotate=str.substring(0,n);
        String end=str.substring(n);
        return end+rotate;
    }
    
    /**
    * 方法实现说明 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，
     * 如果有多对数字的和等于S，输出两个数的乘积最小的
    * @author      yangxin
     * @param
    * @return      
    * @exception   
    * @date        2018/12/6 14:50
    */
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> list=new ArrayList<Integer>();
        if(array.length<1||sum==0||array==null)
            return list;
        int start=0;
        int end=array.length-1;
        while(start<end){
            int newSum=array[start]+array[end];
            if(newSum==sum){
                list.add(array[start]);
                list.add(array[end]);
                return list;
            }
            else if(newSum>sum){
                start++;
            }else
                end--;
        }
        return list;
    }




    /**方法一：
     * 输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
     *
     * @param
     * @return
     * @throws
     * @author yangxin
     * @date 2018/12/6 13:14
     */
    public static ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        if (sum < 3)
            return lists;
        int mid = (1 + sum) / 2;
        for (int i = 2; i <= mid; i++) {
            if(FindNumber(sum, i).size()!=0)
                if(!lists.contains(FindNumber(sum, i)))
                    lists.add(FindNumber(sum, i));
        }
        /*
        排序
         */
        Collections.sort(lists, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if(o1.get(0)>o2.get(0))
                    return 1;
                if(o1.get(0)<o2.get(0))
                    return -1;
                return 0;
            }
        });

        return lists;
    }
    /**
    * FindContinuousSequence的调用函数，实现找出一组存在的数据
    * @author      yangxin
     * @param
    * @return
    * @exception
    * @date        2018/12/6 14:20
    */
    public static ArrayList<Integer> FindNumber(int sum, int i) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (i % 2 == 0) {
            if (sum % i == i / 2 && (sum / i - i / 2 + 1) >= 0) {
                int j = (sum / i - i / 2 + 1);
                if(j==0)
                    j++;
                for (; j <= (sum / i + i / 2); j++)
                    list.add(j);
            }
        } else {
            if (sum % i == 0 && (sum / i - i / 2) >= 0) {
                int j = (sum / i - i / 2 );
                if(j==0)
                    j++;
                for (; j <= (sum / i + i / 2); j++) {
                    list.add(j);
                }
            }
        }
        return list;
    }

    /**
    * 方法二：输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
    * @author      yangxin
     * @param
    * @return
    * @exception
    * @date        2018/12/6 14:21
    */
    public static ArrayList<ArrayList<Integer>> FindContinuousSequence1(int sum) {
        ArrayList<ArrayList<Integer>> lists=new ArrayList<ArrayList<Integer>>();
        if(sum<3)
            return lists;
        int start=1;
        int end=2;
        int middle=(1+sum)/2;
        int newSum=start+end;
        while(start<middle){
            if(newSum==sum){
                if(addToList(start,end).size()>1)
                lists.add(addToList(start,end));
            }

            while (newSum>sum&&start<end){
                newSum-=start;
                start++;
                if(newSum==sum){
                    if(addToList(start,end).size()>1)
                    lists.add(addToList(start,end));
                }
            }
            end++;
            newSum+=end;

        }
        return lists;
    }

    public static ArrayList<Integer> addToList(int start,int end){
        ArrayList<Integer> list=new ArrayList<Integer>();
        for(int i=start;i<=end;i++)
            list.add(i);
        return list;
    }




}
