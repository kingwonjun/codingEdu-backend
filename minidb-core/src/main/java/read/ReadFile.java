package read;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.READ;

public class ReadFile {


    /*
    rbc선언 바이트버퍼를 힙에 할당 1024byte를
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
                    throw new IOException("minidb 파일이 아닙니다.");
                }
            }
            System.out.println("mini 파일입니다.");
            fileInfo.setVersion(byteBuffer.getInt(6));
            fileInfo.setRecordCount(byteBuffer.getInt(10));
            System.out.println("version : " + fileInfo.getVersion() + " " + fileInfo.getRecordCount());
            byteBuffer.compact();
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

        Font font = new Font("맑은 고딕", Font.PLAIN, 20);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("List.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("TextField.font", font);

        JFileChooser chooser = new JFileChooser();

        // 2. 화면 크기 설정
        chooser.setPreferredSize(new Dimension(800, 600));

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


