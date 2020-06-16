package com.york.javaLearning.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author york
 * @create 2020-06-16 10:05
 **/
public class FileChannelTest {

    public static void main(String[] args) {
        fastCopy("PassSerializableTest.obj","PassSerializableTest.obj.copy");
    }

    private static void fastCopy(String src,String dist) {
        try {
            FileInputStream in = new FileInputStream(src);
            FileChannel inFileChannel = in.getChannel();
            FileOutputStream out = new FileOutputStream(dist);
            FileChannel outFileChannel = out.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                int r = inFileChannel.read(buffer);
                if (r == -1) {
                    break;
                }
                buffer.flip();

                outFileChannel.write(buffer);

                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
