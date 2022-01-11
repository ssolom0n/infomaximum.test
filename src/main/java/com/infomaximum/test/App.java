package com.infomaximum.test;

import com.infomaximum.test.exception.ApplicationException;
import com.infomaximum.test.processor.CSVFileProcessor;
import com.infomaximum.test.processor.FileProcessor;
import com.infomaximum.test.processor.XMLFileProcessor;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String MESSAGE_WRONG_FORMAT = "Пожалуйста, укажите файл корректного расширения, поддерживаемые расширения: csv и xml.";

    private static final String EXIT_COMMAND = "exit";
    private static final String XML = "xml";
    private static final List<String> FILE_TYPES = Arrays.asList(XML, "csv");

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                System.out.println(System.lineSeparator() + "Введите путь к файлу для анализа.");
                System.out.println(MessageFormat.format("Введите команду {0} для остановки и выхода из программы.", EXIT_COMMAND));

                String command = in.nextLine();
                exit = EXIT_COMMAND.equalsIgnoreCase(command);

                if (!exit) {
                    String fileType = command.substring(command.lastIndexOf(".") + 1).toLowerCase();
                    if (FILE_TYPES.contains(fileType)) {
                        FileProcessor fileProcessor = XML.equals(fileType) ? new XMLFileProcessor() : new CSVFileProcessor();
                        try {
                            long start = System.currentTimeMillis();

                            File file = new File(command);
                            fileProcessor.process(file);
                            fileProcessor.printResults();

                            long end = System.currentTimeMillis();
                            System.out.println("Файл обработан за " + (end - start) / 1000.0 + " сек");
                        } catch (ApplicationException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                            if (e.getCause() != null) {
                                e.getCause().printStackTrace(System.out);
                            }
                        }
                    } else {
                        System.out.println(MESSAGE_WRONG_FORMAT);
                    }
                }
            }
        }
        System.out.println("Программа завершена.");
    }

}
