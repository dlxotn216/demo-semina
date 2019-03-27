package app.livecoding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by taesu at : 2019-03-27
 *
 * 여기에 DateTimeFormatterConcurrencyTest 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTimeFormatterConcurrencyTest {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Test
    public void DateTimeFormatter_동시성_테스트() {
        runAsyncTask(this::parseByDateTimeFormatter);
    }

    @Test
    public void SimpleDateFormatter_동시성_테스트() {
        runAsyncTask(this::parseBySimpleFormatter);
    }

    private void runAsyncTask(Consumer<List<String>> dateParser) {
        final int fixedThreadPoolSize = 10;
        final int startOfRange = 0;
        final int endOfRange = 10000;

        final List<String> dateStrings = Collections.unmodifiableList(Arrays.asList(
                "2019-01-30", "2019-04-21", "2019-05-17", "2019-11-14", "2019-03-02"));

        ExecutorService executor = Executors.newFixedThreadPool(fixedThreadPoolSize);

        List<CompletableFuture<Void>> collect = IntStream.rangeClosed(startOfRange, endOfRange)
                .mapToObj(operand -> CompletableFuture.runAsync(() -> dateParser.accept(dateStrings), executor))
                .collect(Collectors.toList());

        collect.forEach(CompletableFuture::join);
        System.out.println("Finished " + dateParser.toString());
    }

    private void parseByDateTimeFormatter(List<String> dateStrings) {
        String source = dateStrings.get(ThreadLocalRandom.current().nextInt(dateStrings.size()));
        System.out.println(String.format("%s -> %s : %s", Thread.currentThread().getName(), source, LocalDate.parse(source, dateTimeFormatter)));
    }

    private void parseBySimpleFormatter(List<String> dateStrings) {
        try {
            String source = dateStrings.get(ThreadLocalRandom.current().nextInt(dateStrings.size()));
            System.out.println(String.format("%s -> [%s : %s]", Thread.currentThread().getName(), source, simpleDateFormat.parse(source)));
        } catch (Exception e) {
            System.out.println(String.format("Error !  %s : %s", Thread.currentThread().getName(), e.getMessage()));
        }
    }
}
