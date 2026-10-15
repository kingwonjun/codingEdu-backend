

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("minidb>");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        boolean inString = false;
        List<String> words = new ArrayList<>();

        StringBuilder inputSb = new StringBuilder();
        int checkPosition = 0;
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            inputSb.append(line);
            while (checkPosition < inputSb.length()) {
                if (inputSb.charAt(checkPosition) == '\'' && !inString) {
                    inString = true;
                }
                else if (inputSb.charAt(checkPosition) == '\'' && inString) {
                    inString = false;
                }
                if (inputSb.charAt(checkPosition) == ';' && !inString) {
                    checkPosition++;
                    break;
                }
                checkPosition++;
            }
            if (checkPosition >= 1 && inputSb.charAt(checkPosition - 1) == ';' && !inString) {
                break;
            }
        }

        String input = inputSb.toString();
        char[] c = input.toCharArray();
        Map<String, LinkedHashMap<String, Object>> tables = new HashMap<>();
        LinkedHashMap<String, Object> tablesInfo = new LinkedHashMap<>();

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
        ColumnInfo columninfo = new ColumnInfo();
        List<ColumnInfo> columnList = new ArrayList<>();

        int position = 0;
        int depth = 0;
        while (position < words.size()) {

            if (words.get(position).equalsIgnoreCase("CREATE")) {

            }
            if (words.get(position).equalsIgnoreCase("TABLE")) {
                parserObject.setCommand("CREATE_TABLE");
                position++;
                if (words.get(position).equalsIgnoreCase("IF")) {
                    position += 3;
                    parserObject.setIfNotExists(true);
                    continue;
                } else {
                    parserObject.setTable(words.get(position));
                }
            }
            // 처음 시작할 때 depth 0
            // maxLength가 필요한지 안필요한지 분기문으로
            if (words.get(position).equals("(") && depth == 0) {
                depth++;
                position++;
                if (position >= words.size()) {
                    throw new IllegalArgumentException("SQL syntax error: Unexpected end of input");
                }
                columninfo.setName(words.get(position));
                position++;
                if (words.get(position).equalsIgnoreCase("VARCHAR")
                        || words.get(position).equalsIgnoreCase("CHAR")
                        || words.get(position).equalsIgnoreCase("BIT")
                        || words.get(position).equalsIgnoreCase("VARBIT")) {
                    columninfo.setType(words.get(position));
                    depth++;
                    position += 2;
                    columninfo.setMaxLength(Integer.valueOf(words.get(position)));
                    position++;
                    depth--;
                } else {
                    columninfo.setType(words.get(position));
                }
                parserObject.getColumns().add(columninfo);
                columninfo = new ColumnInfo();
                // 이 코드는 처음을 계산한뒤 , 가
            }
           else if (words.get(position).equals(",") && depth >= 1) {
                position++;
                if (words.get(position).equals(")")) {
                    depth--;
                }
                else if (position >= words.size()) {
                    throw new IllegalArgumentException("SQL syntax error: Unexpected end of input");
                }
                columninfo.setName(words.get(position));
                position++;

                if (words.get(position).equalsIgnoreCase("VARCHAR")
                        || words.get(position).equalsIgnoreCase("CHAR")
                        || words.get(position).equalsIgnoreCase("BIT")
                        || words.get(position).equalsIgnoreCase("VARBIT")) {
                    columninfo.setType(words.get(position));
                    depth++;
                    position += 2;
                    columninfo.setMaxLength(Integer.valueOf(words.get(position)));
                    position++;
                    depth--;
                } else {
                    columninfo.setType(words.get(position));
                }
                parserObject.getColumns().add(columninfo);
                columninfo = new ColumnInfo();
            }
            position++;
        }

    }
}
