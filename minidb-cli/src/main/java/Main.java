
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("minidb>");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        List<String> words = new ArrayList<>();
        String input = br.readLine();
        char[] c = input.toCharArray();
        boolean inString = false;
        Map<String, LinkedHashMap<String, Object>> tables = new HashMap<>();
        LinkedHashMap<String, Object> tablesInfo = new LinkedHashMap<>();

        // 괄호안에 괄호 깊이 세는거

        for (int i = 0; i < c.length; i++) {
            if (Character.isWhitespace(c[i]) && !inString) {
                if (!sb.toString().isEmpty()) {
                    words.add(sb.toString());
                }
                sb.setLength(0);
                continue;
            }
            if (c[i] == '(' && !inString) {
                if (!sb.toString().isEmpty()) {
                    words.add(sb.toString());
                }
                words.add("(");
                sb.setLength(0);
                continue;
            }
            if (c[i] == ')' && !inString) {
                if (!sb.toString().isEmpty()) {
                    words.add(sb.toString());
                }
                words.add(")");
                sb.setLength(0);
                continue;
            }
            if (c[i] == ';' && !inString) {
                if (!sb.toString().isEmpty()) {
                    words.add(sb.toString());
                }
                sb.setLength(0);
                break;
            }
            if (i < c.length - 1 && c[i] == '\'' && c[i + 1] == '\'' && inString) {
                sb.append('\'');
                i++;
                continue;
            }
            else if (c[i] == '\'') {
                if (!inString) {
                    inString = true;
                } else {
                    inString = false;
                }
            }
            if (c[i] == ',' && !inString) {
                if (!sb.toString().isEmpty()) {
                    words.add(sb.toString());
                }
                words.add(",");
                sb.setLength(0);
                continue;
            }
            sb.append(c[i]);
        }
        if (!sb.toString().isEmpty()) {
            words.add(sb.toString());
        }

        ParserObject parserObject = new ParserObject();
        int position = 0;
        int depth = 0;
        while (position < words.size()) {
            if (words.get(position).equalsIgnoreCase("IF")) {
                position += 3;
                parserObject.setIfNotExists(true);
                continue;
            }
            if (words.get(position).equalsIgnoreCase("CREATE")) {

            }
            if (words.get(position).equalsIgnoreCase("TABLE")) {
                parserObject.setCommand("CREATE_TABLE");
                position++;
            }
            if (words.get(position).equals("(") && depth >= 1) {

            }
            else if (words.get(position).equals("(")) {
                depth++;
                position++;
            }


            position++;
        }

    }
}
