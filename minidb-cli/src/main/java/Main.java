import command.CommandParser;
import command.CommandProcessor;
import engine.Database;
import read.FileInfo;
import read.ReadFile;
import write.SaveFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Database database = new Database();
        CommandParser commandParser = new CommandParser();
        CommandProcessor commandProcessor = new CommandProcessor(database);
        SaveFile saveFile = new SaveFile();
        Path path = Path.of("data.minidb");
        FileInfo fileInfo = new FileInfo();

        if (!Files.exists(path)) {
            System.out.println("파일이 없습니다. 새로 만듭니다.");
            saveFile.newSave(path);
        } else {
            System.out.println("파일이 있습니다.");
        }
        while(true) {
            System.out.print("MiniDB>");
            String input = br.readLine();
            if (input == null) {
                break;
            }
            String[] tokens = commandParser.parser(input);
            String result = commandProcessor.execute(tokens);
            if (tokens[0].equals("set")) {
                try (SeekableByteChannel channel = Files.newByteChannel(
                        path,
                        StandardOpenOption.READ,
                        StandardOpenOption.WRITE
                )) {
                    int capacity = 4 + tokens[1].getBytes(StandardCharsets.UTF_8).length + 4 + tokens[2].getBytes(StandardCharsets.UTF_8).length;
                    ByteBuffer content = ByteBuffer.allocate(capacity);
                    content.putInt(tokens[1].getBytes(StandardCharsets.UTF_8).length);
                    content.put(tokens[1].getBytes(StandardCharsets.UTF_8));
                    content.putInt(tokens[2].getBytes(StandardCharsets.UTF_8).length);
                    content.put(tokens[2].getBytes(StandardCharsets.UTF_8));
                    content.flip();
                    channel.position(channel.size());
                    channel.write(content);
                }
            } else if(tokens[0].equals("seedb")) {
                try (SeekableByteChannel channel = Files.newByteChannel(path)) {
                    ByteBuffer header = ByteBuffer.allocate(14);
                    channel.read(header);
                    header.flip();
                    for (int i = 0; i < 6; i++) {
                        if (header.get(i) != fileInfo.getMagicNumber()[i]) {
                            throw new IOException("minidb 파일이 아닙니다.");
                        }
                    }
                    System.out.println("mini 파일입니다.");
                    fileInfo.setVersion(header.getInt(6));
                    fileInfo.setRecordCount(header.getInt(10));
                    ByteBuffer content = ByteBuffer.allocate(4);
                    while (true) {
                        int bytesRead = channel.read(content);
                        if (bytesRead == -1) {
                            break;
                        }
                        content.flip();
                        int keyLength = content.getInt();
                        content.compact();
                        channel.position(channel.position() + keyLength);
                        bytesRead = channel.read(content);
                        if (bytesRead == -1) {
                            break;
                        }
                        content.flip();
                        int valueLength = content.getInt();
                        int finalLength = 8 + keyLength + valueLength;
                        content = ByteBuffer.allocate(finalLength);
                        content.clear();
                        channel.position(channel.position() - finalLength);

                    }
                }
            }
            System.out.println(result);
        }

    }
}
