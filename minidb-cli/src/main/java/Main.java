import command.CommandParser;
import command.CommandProcessor;
import engine.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Database database = new Database();
        CommandParser commandParser = new CommandParser();
        CommandProcessor commandProcessor = new CommandProcessor(database);

        while(true) {
            System.out.print("MiniDB>");
            String input = br.readLine();
            if (input == null) {
                break;
            }

            String result = commandProcessor.execute(commandParser.parser(input));
            System.out.println(result);
        }
    }
}
