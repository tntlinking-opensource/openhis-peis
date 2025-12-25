package com.center.medical.report.utils;

import com.center.medical.common.exception.ServiceException;
import com.center.medical.report.bean.param.BatchDownloadParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.center.medical.framework.datasource.DynamicDataSourceContextHolder.log;

public class UrlFilesDownloadUtils {
    /**
     * 同步下载（适用于小文件数量）
     */
    public static void downloadSynchronously(List<BatchDownloadParam> param, String prefix, HttpServletResponse response) throws Exception {
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            zos.setLevel(6);
            for (BatchDownloadParam zipstream : param) {
                try {
                    String filename = zipstream.getNamingMethod() + ".pdf";
                    String url = prefix + zipstream.getUrlPdf();

                    zos.putNextEntry(new ZipEntry(filename));
                    downloadAndWriteToZip(url, zos);
                    zos.closeEntry();
                } catch (Exception e) {
                    log.error("下载文件失败: {}", zipstream.getNamingMethod(), e);
                    // 继续下载其他文件，不中断整个流程
                }
            }
        }
    }

    /**
     * 异步并发下载（适用于大文件数量）
     */
    public static void downloadAsynchronously(List<BatchDownloadParam> param, String prefix, HttpServletResponse response) throws Exception {
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            zos.setLevel(6);

            // 创建线程池，控制并发数量
            int maxConcurrency = Math.min(param.size(), 10); // 最多10个并发
            ExecutorService executor = Executors.newFixedThreadPool(maxConcurrency);

            try {
                // 并发下载文件，使用CompletableFuture
                List<CompletableFuture<Void>> futures = new ArrayList<>();

                for (BatchDownloadParam zipstream : param) {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        try {
                            String filename = zipstream.getNamingMethod() + ".pdf";
                            String url = prefix + zipstream.getUrlPdf();

                            // 同步写入zip文件
                            synchronized (zos) {
                                zos.putNextEntry(new ZipEntry(filename));
                                downloadAndWriteToZip(url, zos);
                                zos.closeEntry();
                            }
                        } catch (Exception e) {
                            log.error("下载文件失败: {}", zipstream.getNamingMethod(), e);
                            throw new CompletionException(e);
                        }
                    }, executor);

                    futures.add(future);
                }

                // 等待所有下载完成
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            } finally {
                executor.shutdown();
                try {
                    if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }

        } catch (Exception e) {
            log.error("批量下载失败", e);
            throw new ServiceException("批量下载失败: " + e.getMessage());
        }
    }

    /**
     * 下载文件并写入zip流
     */
    private static void downloadAndWriteToZip(String url, ZipOutputStream zos) throws IOException {
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            HttpURLConnection conn = null;
            InputStream is = null;
            try {
                URL urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoInput(true);
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(15000); // 增加连接超时
                conn.setReadTimeout(60000);    // 增加读取超时
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
                conn.setRequestProperty("Accept", "application/pdf,application/octet-stream,*/*");
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
                conn.setRequestProperty("Connection", "keep-alive");

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    is = conn.getInputStream();
                    byte[] buffer = new byte[16384]; // 增加缓冲区大小到16KB
                    int bytesRead;
                    long totalBytes = 0;

                    while ((bytesRead = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, bytesRead);
                        totalBytes += bytesRead;

                        // 如果文件太大，记录警告
                        if (totalBytes > 100 * 1024 * 1024) { // 100MB
                            log.warn("文件过大: {} bytes", totalBytes);
                            break;
                        }
                    }
                    return; // 成功下载，退出重试循环
                } else if (responseCode == 404) {
                    throw new IOException("文件不存在: " + url);
                } else {
                    throw new IOException("HTTP错误: " + responseCode + " - " + url);
                }
            } catch (IOException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    throw e; // 重试次数用完，抛出异常
                }
                log.warn("下载失败，第{}次重试: {}", retryCount, url);
                try {
                    Thread.sleep(1000 * retryCount); // 递增延迟重试
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new IOException("下载被中断", ie);
                }
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        log.warn("关闭输入流失败", e);
                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }
}
