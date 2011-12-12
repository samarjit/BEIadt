package com.ycs.ezlink.scheduler.cmd;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.FileChannel;

class StreamCopier implements Runnable {
    private InputStream in;
    private OutputStream out;

    public StreamCopier(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        try {
            int n;
            byte[] buffer = new byte[4096];
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                out.flush();
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}

class InputCopier implements Runnable {
    private FileChannel in;
    private OutputStream out;

    public InputCopier(FileChannel in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        try {
            int n;
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer.array(), 0, n);
                out.flush();
            }
            out.close();
        }
        catch (AsynchronousCloseException e) {}
        catch (IOException e) {
            System.out.println(e);
        }
    }
}

public class Test {
    private static FileChannel getChannel(InputStream in)
            throws NoSuchFieldException, IllegalAccessException {
        Field f = FilterInputStream.class.getDeclaredField("in");
        f.setAccessible(true);
        while (in instanceof FilterInputStream)
            in = (InputStream)f.get((FilterInputStream)in);
        return ((FileInputStream)in).getChannel();
    }

    public static void main(String[] args)
            throws IOException, InterruptedException,
                   NoSuchFieldException, IllegalAccessException {
        Process process = Runtime.getRuntime().exec("cmd");
        Thread outThread = new Thread(new StreamCopier(
                process.getInputStream(), System.out));
        outThread.start();
        Thread errThread = new Thread(new StreamCopier(
                process.getErrorStream(), System.err));
        errThread.start();
        Thread inThread = new Thread(new InputCopier(
                getChannel(System.in), process.getOutputStream()));
        inThread.start();
        process.waitFor();
        System.in.close();
        outThread.join();
        errThread.join();
        inThread.join();
    }
}