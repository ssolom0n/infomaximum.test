package com.infomaximum.test.processor;

import com.infomaximum.test.exception.ApplicationException;
import com.infomaximum.test.model.AddressEntry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Реализация {@link FileProcessor} для CSV.
 * <p>
 * Чтение файла происходит построчно, т.к. файлы довольно большие.
 */
public class CSVFileProcessor extends FileProcessor {

    private static final String SEPARATOR = ";";

    @Override
    public void process(File file) throws ApplicationException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";
            String header = bufferedReader.readLine();
            if (header.equals("\"city\";\"street\";\"house\";\"floor\"")) {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] items = line.split(SEPARATOR);
                    if (items.length != 4) {
                        throw new ApplicationException("Файл некорректного формата. Ожидается файл с 4 колонками");
                    }
                    AddressEntry currentAddress = new AddressEntry(trimQoutes(items[0]), trimQoutes(items[1]), items[2], items[3]);
                    processEntry(currentAddress);
                }
            } else throw new ApplicationException("Файл некорректного формата. Ожидается файл с 4 колонками");
        } catch (IOException e) {
            throw new ApplicationException("Произошла ошибка чтения файла", e);
        } catch (NullPointerException e) {
            throw new ApplicationException("Файл некорректного формата. Ожидается файл с 4 колонками", e);
        }
    }

    private String trimQoutes(String value) {
        return value.replaceAll("^\"|\"$", "");
    }

}
