package write;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SaveFile {

    public void newSave(Path path) throws IOException {
        try (SeekableByteChannel wbc = Files.newByteChannel(path,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            ByteBuffer header = ByteBuffer.allocate(14);
            header.put("minidb".getBytes(StandardCharsets.UTF_8));
            header.putInt(1);
            header.putInt(0);
            header.flip();

            wbc.write(header);
        }
    }

    public Path firstSaveModal(Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("다른 이름으로 저장");
        chooser.setPreferredSize(new Dimension(800, 600));
        chooser.setFileFilter(new FileNameExtensionFilter("MiniDB 파일 (*.minidb)", "minidb"));

        while (true) {
            int result = chooser.showSaveDialog(parent);
            if (result != JFileChooser.APPROVE_OPTION) {
                return null; // 취소
            }

            Path path = chooser.getSelectedFile().toPath();
            if (!path.getFileName().toString().toLowerCase().endsWith(".minidb")) {
                path = path.resolveSibling(path.getFileName() + ".minidb");
            }

            if (Files.exists(path)) {
                int answer = JOptionPane.showConfirmDialog(parent,
                        path.getFileName() + " 파일이 이미 있습니다.\n바꾸시겠습니까?",
                        "다른 이름으로 저장", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (answer != JOptionPane.YES_OPTION) {
                    continue; // 다시 고르게
                }
            }
            return path;
        }
    }
}
