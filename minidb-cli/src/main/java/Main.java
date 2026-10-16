

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
                } else if (inputSb.charAt(checkPosition) == '\'' && inString) {
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
                words.add(";");
                sb.setLength(0);
                break;
            }
            if (i < c.length - 1 && c[i] == '\'' && c[i + 1] == '\'' && inString) {
                sb.append('\'');
                i++;
                continue;
            } else if (c[i] == '\'') {
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
        TableConstraint tableConstraint = new TableConstraint();
        List<ColumnInfo> columnList = new ArrayList<>();
        List<List<String>> uniqueList = new ArrayList<>();
        List<String> pkList = new ArrayList<>();
        int position = 0;
        int depth = 0;

        while (position < words.size()) {
            if (isToken(position, words, "CREATE", 0)) {
                position++;
            }

            if (isToken(position, words, "TABLE", 0)) {
                parserObject.setCommand("CREATE_TABLE");
                position++;

                if (isToken(position, words, "IF", 0)) {
                    position += 3;
                    parserObject.setIfNotExists(true);
                    continue;
                } else {
                    parserObject.setTable(words.get(position));
                    position++;
                }
            }

            if (isToken(position, words, "(", 0) || isToken(position, words, ",", 0)) {
                depth++;
                position++;
                if (position >= words.size()) {
                    throw new IllegalArgumentException(
                            "SQL syntax error: Unexpected end of input"
                    );
                }
                String columnName = words.get(position);
                if (isToken(position, words,"PRIMARY", 0) && isToken(position, words, "KEY", 1)) {
                    if (isToken(position, words, "(", 2)) {
                        position += 2;
                        while (!isToken(position, words, ")", 0)) {
                            position++;
                            pkList.add(words.get(position));
                            position++;
                        }
                        tableConstraint.setPk(pkList);
                        position++;
                        if (isToken(position, words, ";", 1)) {
                            break;
                        }
                        if (isToken(position, words, ",", 0)) {
                            continue;
                        }
                    }
                }
                else if (isToken(position, words,"UNIQUE", 0)) {
                    if (isToken(position, words, "(", 2)) {
                        position += 2;
                        List<String> list = new ArrayList<>();
                        while (!isToken(position, words, ")", 0)) {
                            position++;
                            list.add(words.get(position));
                            position++;
                        }
                        uniqueList.add(list);
                        tableConstraint.setUnique(uniqueList);
                        position++;
                        if (isToken(position, words, ";", 1)) {
                            break;
                        }
                        if (isToken(position, words, ",", 0)) {
                            continue;
                        }
                    }
                }
                else {
                    columninfo.setName(columnName);
                    position++;
                }
                if (isToken(position, words, "VARCHAR", 0)
                        || isToken(position, words, "CHAR", 0)
                        || isToken(position, words, "BIT", 0)
                        || isToken(position, words, "VARBIT", 0)) {

                    columninfo.setType(words.get(position));
                    depth++;
                    position += 2;
                    columninfo.setMaxLength(Integer.valueOf(words.get(position)));
                    position+= 2;
                    depth--;
                } else {
                    columninfo.setType(words.get(position));
                    position++;
                }
                // isToken이 아니라 !isToken 이다
                while (!isToken(position, words, ",", 0)) {
                    if (isToken(position, words, "NOT", 0)
                            && isToken(position, words, "NULL", 1)) {
                        columninfo.setNullable(false);
                        position += 2;
                    }
                    // UNIQUE 조건은 기존 코드가 미완성 상태
                    if (isToken(position, words, "UNIQUE", 0)) {
                        List<String> list = new ArrayList<>();
                        list.add(columnName);
                        uniqueList.add(list);
                        tableConstraint.setUnique(uniqueList);
                        position++;
                    }
                    if (isToken(position, words, "PRIMARY", 0) && isToken(position, words, "KEY", 1)) {
                        pkList.add(columnName);
                        tableConstraint.setPk(pkList);
                        position += 2;
                    }

                    if (isToken(position, words, ")", 0)) {
                        depth--;
                        break;
                    }
                    // if문 3개에는 position 증가가 있고, 해당하지않는 명령어가 있을때는 무한루프가 된다. 이거는 나중에 수정해야된다.
                }
                if (columninfo.getName() != null) {
                    parserObject.getTableSchema().getColumnInfo().add(columninfo);
                }
                columninfo = new ColumnInfo();
            }
            if (isToken(position, words, ")", 0) && isToken(position, words, ";", 1)) {
                break;
            }
        }
        if (!tableConstraint.getPk().isEmpty()
                || !tableConstraint.getUnique().isEmpty()) {
            parserObject.getTableSchema()
                    .getTableConstraints()
                    .add(tableConstraint);
        }
    }

    private static boolean isToken(int position, List<String> words, String token, int offset) {
        if (position >= words.size()) {
            throw new ArrayIndexOutOfBoundsException("position이 words 사이즈에서 벗어났습니다.");
        }
        if (position + offset >= words.size()) {
            throw new ArrayIndexOutOfBoundsException("position + offset이 words 사이즈에서 벗어났습니다.");
        }
        if (position + offset < 0) {
            throw new ArrayIndexOutOfBoundsException("위치값이 -1 미만입니다.");
        }
        return words.get(position + offset).equalsIgnoreCase(token);
    }
}






