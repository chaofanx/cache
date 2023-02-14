package com.chaofan.cache.util;

import com.chaofan.cache.exception.CacheRuntimeException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;

/**
 * @author 李超凡
 * @since 2022/5/30 10:18
 */
public final class FileUtil {

    public static void createFile(String path) {
        File file = new File(path);
        if (file.exists()) return;
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CacheRuntimeException(e);
        }
    }

    public static void clear(String path) {
        File file = new File(path);
        if (!file.exists()) throw new CacheRuntimeException(new FileNotFoundException());
        try (FileOutputStream o = new FileOutputStream(file)) {
            o.write(new byte[]{});
        } catch (IOException e) {
            throw new CacheRuntimeException(e);
        }
    }

    /**
     * nio写文件
     */
    public static void write(String path, String content, OpenOption... options) {
        try (FileChannel channel = FileChannel.open(Path.of(path), options)) {
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            allocate.put(content.getBytes());
            allocate.flip();
            channel.write(allocate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readAllLines(String path, Charset cs) {
        try {
            return Files.readAllLines(Path.of(path), cs);
        } catch (IOException e) {
            throw new CacheRuntimeException(e);
        }
    }
}
