package command;

import engine.Database;

import java.util.Arrays;

public class CommandProcessor {

    private final Database database;

    public CommandProcessor(Database database) {
        this.database = database;
    }

    public String execute(String[] tokens) {
        String command = tokens[0];
        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

        switch(command) {
            case "set":
                database.set(args[0], args[1]);
                return "OK";
            case "get":
                return database.get(args[0]);
            case "delete":
                if (database.delete(args[0])){
                    return "OK";
                }
                return "삭제할 값이 없습니다.";
            case "exists":
                if (database.exists(args[0])){
                    return "true";
                }
                return "false";
            case "count":
                return database.count();
            case "clear":
                database.clear();
                return "OK";
            default:
                return "ERROR: Unknown command";
        }
    }
}
