package writeorread;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
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

    /*rbc선언 바이트버퍼를 힙에 할당 1024byte를
    바이트버퍼를 rbc.read로 읽고
    내용이 없다면 bytesRead가 -1을 출력하고 종료
    rbc.read에 대한 개념이 부족하다
    내 생각에는 ByteBuffer.get으로 읽거나 getInt로 계속 읽다가 10을 발견하면 종료 하는게 맞나 싶다.

     */
    public void fileReading(Path path) {
        try (ReadableByteChannel rbc = Files.newByteChannel(path, READ)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            FileInfo fileInfo = new FileInfo();
            int bytesRead = rbc.read(byteBuffer);
            if (bytesRead == -1) {
                throw new EOFException("파일에 데이터가 없습니다.");
            }
            byteBuffer.flip();
            for (int i = 0; i < 6; i++) {
                if (byteBuffer.get(i) != fileInfo.getMagicNumber()[i]) {
                    throw new IOException("minidb 파일이 아닙니다.")
                }
            }
            if (byteBuffer.hasRemaining()) {
                byteBuffer.getInt();
                byteBuffer.getInt();
            }
            while (true) {
                bytesRead = rbc.read(byteBuffer);
                if (bytesRead == -1) {
                    break;
                }

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


