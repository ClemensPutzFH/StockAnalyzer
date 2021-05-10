package stockanalyzer.downloader;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader extends Downloader {


    @Override
    public int process(List<String> urls) {
        int count = 0;
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService parallelExecutor = Executors.newFixedThreadPool(threads);

        List<Future<String>> parallelDownload = new ArrayList<>();

        for (String url : urls) {
            parallelDownload.add(parallelExecutor.submit(()->saveJson2File(url)));
        }
        for(Future future : parallelDownload) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();

            }
            finally {
                count++;
            }
        }
        return count;
    }
}
