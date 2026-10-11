package writeorread;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class openFile {

    public void fileChooser() {
        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(".minidb");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
        }
    }
}
