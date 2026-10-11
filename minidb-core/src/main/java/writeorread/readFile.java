package writeorread;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.EnumSet;

import static java.nio.file.StandardOpenOption.READ;

public class readFile {

    public void headerRead() throws IOException {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("data.minidb")))) {
            int magic = in.readInt();
            int version = in.readShort();
            int recordCount = in.readInt();

            System.out.printf("magic=0x%08X, version=%d, count=%d%n",
                    magic, version, recordCount);
        }
    }

    public void fileReading(Path path) {
        try (ReadableByteChannel rbc = Files.newByteChannel(path, READ)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (true) {
                int bytesRead = rbc.read(byteBuffer);
                if (bytesRead == -1) {
                    break;
                }
                byteBuffer.flip();
                CharBuffer chars = StandardCharsets.UTF_8.decode(byteBuffer);
                System.out.print(chars);
                byteBuffer.compact();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path fileChoose() {
        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("minidb", "minidb");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);
        Path path = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().toPath();
        }

        return path;
    }
}


