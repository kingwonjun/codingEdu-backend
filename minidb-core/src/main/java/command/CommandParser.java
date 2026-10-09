package command;

import java.io.BufferedReader;
import java.util.Arrays;

public class CommandParser {

    public String[] parser(String input) {
        String[] tokens = input.strip().split("\\s+");
        tokens[0] = tokens[0].toLowerCase();
        switch (tokens[0]) {
            case "set":
                if (tokens.length != 3) {
                    throw new IllegalArgumentException("ERROR wrong number of arguments for SET");
                }
                break;
            case "get":
                if (tokens.length != 2) {
                    throw new IllegalArgumentException("ERROR wrong number of arguments for GET");
                }
                break;
            case "delete":
                if (tokens.length != 2) {
                    throw new IllegalArgumentException("ERROR wrong number of arguments for DELETE");
                }
                break;
            case "exists":
                if (tokens.length != 2) {
                    throw new IllegalArgumentException("ERROR wrong number of arguments for EXISTS");
                }
                break;
            case "count":
                if (tokens.length != 1) {
                    throw new IllegalArgumentException("ERROR wrong number of arguments for COUNT");
                }
                break;
            case "clear":
                if (tokens.length != 1) {
                    throw new IllegalArgumentException("ERROR wrong number of arguments for CLEAR");
                }
                break;
            case "help":
                if (tokens.length != 1) {
                    throw new IllegalArgumentException("ERROR wrong number of arguments for HELP");
                }
                break;
            case "exit":
                if (tokens.length != 1) {
                    throw new IllegalArgumentException("ERROR wrong number of arguments for EXIT");
                }
                break;
            case "":
                break;
            default:
                throw new IllegalArgumentException("ERROR unknown command: " + tokens[0]);
        }

        return tokens;
    }
}

