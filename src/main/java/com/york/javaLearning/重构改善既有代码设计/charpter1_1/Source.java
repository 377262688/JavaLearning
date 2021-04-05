package com.york.javaLearning.重构改善既有代码设计.charpter1_1;

/**
 * @author york
 * @create 2020-12-30 18:03
 **/
public class Source {

    private String playJson = "{\n" +
            "\"hamlet\": {\"name\": \"Hamlet\", \"type\": \"tragedy\"},\n" +
            "\"as-like\": {\"name\": \"As You Like It\", \"type\": \"comedy\"},\n" +
            "\"othello\": {\"name\": \"Othello\", \"type\": \"tragedy\"}\n" +
            "}";
    private String invoiceJson = "[\n" +
            "{\n" +
            "\"customer\": \"BigCo\",\n" +
            "\"performances\": [\n" +
            "{\n" +
            "\"playID\": \"hamlet\",\n" +
            "\"audience\": 55\n" +
            "},\n" +
            "{\n" +
            "\"playID\": \"as-like\",\n" +
            "\"audience\": 35\n" +
            "},\n" +
            "{\n" +
            "\"playID\": \"othello\",\n" +
            "\"audience\": 40\n" +
            "}\n" +
            "]\n" +
            "}\n" +
            "]";


    public String statement(String invoice, String plays) {
        String result = "Statement for %s\n";

        return String.format(result,1);
    }

    public static void main(String[] args) {
        Source source = new Source();
        System.out.println(source.statement("",""));

    }
}
