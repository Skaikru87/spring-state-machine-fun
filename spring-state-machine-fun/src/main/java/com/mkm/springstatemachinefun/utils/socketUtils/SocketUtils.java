package com.mkm.springstatemachinefun.utils.socketUtils;

import com.mkm.springstatemachinefun.utils.Colours;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

@Slf4j
//@Component
@WithStateMachine
public class SocketUtils {

    private static final int BUFFER_SIZE = 1024;
    private static final int READ_TIMEOUT_MS = 1200;

//    @Autowired
//    MessageUtils messageUtils;

    public static void close(Socket socket) throws IOException {
        if (isConnected(socket)) {
            socket.close();
        }
    }

    public static boolean isConnected(Socket socket) {
        return socket != null && !socket.isClosed() && socket.isConnected();
    }

    public synchronized String read(Socket socket) throws IOException {
        String answer = null;

        InputStream is = socket.getInputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        read = is.read(buffer);
        if (read != -1) {
            answer = new String(buffer, 0, read);
        } else {
            close(socket);
        }
        if(answer != null) {
//            messageUtils.updateUnitPackState(answer);
        }
        return answer;
    }

    public static LinkedList<Byte> readBytes(Socket socket) throws IOException {
        LinkedList<Byte> l = new LinkedList<>();

        InputStream is = socket.getInputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        read = is.read(buffer);
        if (read != -1) {
            for (int a = 0; a < read; a++)
                l.add(buffer[a]);
        } else {
            close(socket);
        }

        return l;
    }

    public static LinkedList<Byte> readWithTimeout(Socket socket) throws IOException {

        long maxTimeMillis = System.currentTimeMillis() + READ_TIMEOUT_MS;

        LinkedList<Byte> l = new LinkedList<>();

        try {
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[BUFFER_SIZE];

            int bufferOffset = 0;
            while (System.currentTimeMillis() < maxTimeMillis && bufferOffset < BUFFER_SIZE) {

                int readLength = Math.min(is.available(), BUFFER_SIZE - bufferOffset);
                int readResult = is.read(buffer, bufferOffset, readLength);
                if (readResult == -1) {
                    close(socket);
                    break;
                }

                if (readResult != 0)
                    maxTimeMillis = System.currentTimeMillis() + READ_TIMEOUT_MS;

                bufferOffset += readResult;
            }

            for (int a = 0; a < bufferOffset; a++)
                l.add(buffer[a]);

        } catch (Exception e) {
            log.error("Read with timeout exception: {}", e.getClass().getSimpleName());
        }

        return l;
    }

    public static void write(String message, Socket socket) throws IOException {
        log.info(Colours.ANSI_YELLOW + "Send: {}" + Colours.ANSI_RESET, message );
        ByteArrayOutputStream array = new ByteArrayOutputStream(message.getBytes().length);
        array.write(message.getBytes(), 0, message.getBytes().length);
        array.writeTo(socket.getOutputStream());
        socket.getOutputStream().flush();
    }

    public static void write(byte[] bytes, Socket socket) throws IOException {
        ByteArrayOutputStream array = new ByteArrayOutputStream(bytes.length);
        array.write(bytes, 0, bytes.length);
        array.writeTo(socket.getOutputStream());
        socket.getOutputStream().flush();
    }

    public static void write(String message, Socket socket, Charset charset) throws IOException {
        ByteArrayOutputStream array = new ByteArrayOutputStream(message.getBytes().length);
        array.write(message.getBytes(charset), 0, message.getBytes().length);
        array.writeTo(socket.getOutputStream());
        socket.getOutputStream().flush();
    }

    public static void write(String message, Socket socket, String writeNewLine) throws IOException {
        if (Boolean.parseBoolean(writeNewLine))
            write(message + '\n', socket);
        else
            write(message, socket);
    }

    public static Socket connect(String host, String port, String timeout) throws IOException {
        int p = Integer.parseInt(port);
        int t = Integer.parseInt(timeout);

        Socket socket = new Socket();
        socket.setSoTimeout(t);
        socket.setKeepAlive(true);
        socket.setReuseAddress(true);
        socket.connect(new InetSocketAddress(host, p));

        return socket;
    }

    public static byte[] getByteArray(List<Byte> contents) {
        byte[] contentsPrimitive = new byte[contents.size()];
        for (int a = 0; a < contents.size(); a++) {
            contentsPrimitive[a] = contents.get(a);
        }
        return contentsPrimitive;
    }

}
