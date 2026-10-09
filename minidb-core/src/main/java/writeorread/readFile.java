package writeorread;

import tools.jackson.databind.exc.InvalidFormatException;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

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

    public void validateMagic(int magic) throws InvalidFormatException {

    }

    public Record readRecord(DataInputStream in) throws IOException {

    }


}
