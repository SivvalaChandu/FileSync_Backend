package com.Files.DataSync.util;

import java.util.HashMap;
import java.util.Map;

public class MimeTypeUtil {
    private static final Map<String, String> MIME_TYPE_MAP = new HashMap<>();

    static {
        MIME_TYPE_MAP.put("txt", "text/plain");
        MIME_TYPE_MAP.put("jpg", "image/jpeg");
        MIME_TYPE_MAP.put("jpeg", "image/jpeg");
        MIME_TYPE_MAP.put("png", "image/png");
        MIME_TYPE_MAP.put("pdf", "application/pdf");
        MIME_TYPE_MAP.put("txt", "text/plain");
        MIME_TYPE_MAP.put("xls", "application/vnd.ms-excel"); // Old Excel files
        MIME_TYPE_MAP.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // New Excel files
        MIME_TYPE_MAP.put("doc", "application/msword"); // Old Word files
        MIME_TYPE_MAP.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"); // New Word files
        MIME_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint"); // Old PowerPoint files
        MIME_TYPE_MAP.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"); // New PowerPoint files
        MIME_TYPE_MAP.put("mp3", "audio/mpeg");
        MIME_TYPE_MAP.put("wav", "audio/wav");
        MIME_TYPE_MAP.put("mp4", "video/mp4");
        MIME_TYPE_MAP.put("mkv", "video/x-matroska");
    }

    public static String getMimeType(String fileExtension) {
        return MIME_TYPE_MAP.getOrDefault(fileExtension.toLowerCase(), "application/octet-stream");
    }
}
