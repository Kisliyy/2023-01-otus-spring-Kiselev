package ru.otus.book_storage_batch.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellBatchCommandImpl implements ShellBatchCommand {

    private final JobLauncher jobLauncher;
    private final Job importBookStorageJob;


    @SneakyThrows
    @Override
    @ShellMethod(key = "start", value = "Start migration data")
    public void startBatch() {
        JobExecution run = jobLauncher.run(importBookStorageJob, new JobParameters());
    }

}
