package com.york.javaLearning.io;

import java.io.*;

/**
 * @author york
 * @create 2020-06-15 15:35
 **/
public class IOTest {

    public static void main(String[] args) throws IOException {
//        listAllFiles(new File("/Users/york/jiuju/JavaLearning/src/main/java"));
//        copyFile("/Users/york/jiuju/JavaLearning/src/main/java/com/york/javaLearning/io/io.md","/Users/york/jiuju/JavaLearning/src/main/java/com/york/javaLearning/io/io.mdcopy");
        StringTest();
//        readFileContent("/Users/york/jiuju/JavaLearning/src/main/java/com/york/javaLearning/io/io.md");
    }

    public static void readFileContent(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        bufferedReader.close();
    }

    public static void StringTest() throws UnsupportedEncodingException {
        String str1 = "中文";
        byte[] bytes = str1.getBytes("UTF-8");
        String str2 = new String(bytes,"UTF-8");
        System.out.println(str2);
        String str3 = "i am 君山";
        byte[] bytes1 = str3.getBytes("ISO-8859-1");

        System.out.println(bytes1);
        String str4 = new String(bytes1,"ISO-8859-1");
        String hex = toHex(bytes1);
        System.out.println(hex);
        System.out.println(str4);
    }

    private static String toHex(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes)
        {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }

        return sb.toString();
    }

    public static void copyFile(String src,String dist) throws IOException {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dist);
        byte[] buffer = new byte[20 * 1024];
        int cnt;
        while ((cnt = in.read(buffer,0,buffer.length)) != -1) {
            out.write(buffer,0,cnt);
        }
        in.close();
        out.close();
    }

    // 递归列出目录下的文件
    public static void listAllFiles(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            System.out.println(dir.getName());
            return;
        }
        for (File file : dir.listFiles()) {
            listAllFiles(file);
        }
    }
}
