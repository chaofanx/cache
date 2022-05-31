package com.chaofan.cache.util;

import com.chaofan.cache.exception.CacheRuntimeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;

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
            throw new CacheRuntimeException("发生异常");
        } catch (SecurityException e) {
            throw new CacheRuntimeException("没有权限创建文件");
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
}
