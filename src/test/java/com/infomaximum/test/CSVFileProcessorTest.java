package com.infomaximum.test;

import com.infomaximum.test.exception.ApplicationException;
import com.infomaximum.test.processor.CSVFileProcessor;
import com.infomaximum.test.processor.FileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

class CSVFileProcessorTest {
    ClassLoader classLoader = getClass().getClassLoader();
    FileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        fileProcessor = new CSVFileProcessor();
    }

    @Test
    void testUniqueItemsShouldHaveSize5() throws ApplicationException {
        File file = new File(classLoader.getResource("CSV/5UniqueItemsAnd2Doubles.csv").getFile());
        fileProcessor.process(file);
        assertEquals(5, fileProcessor.getUniqueItems().size());
    }

    @Test
    void testduplicatesMapShouldHaveSize2() throws ApplicationException {
        File file = new File(classLoader.getResource("CSV/5UniqueItemsAnd2Doubles.csv").getFile());
        fileProcessor.process(file);
        assertEquals(2, fileProcessor.getDuplicatesMap().size());
    }

    @Test
    void testExpectingExceptionWrongFileEmpty() {
        fileProcessor = new CSVFileProcessor();
        File file = new File(classLoader.getResource("CSV/Empty.csv").getFile());
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            fileProcessor.process(file);
        });
        assertEquals("Файл некорректного формата. Ожидается файл с 4 колонками", thrown.getMessage());
    }

    @Test
    void testExpectingExceptionWrongFile() {
        fileProcessor = new CSVFileProcessor();
        File file = new File(classLoader.getResource("CSV/Corrupted.csv")
                .getFile());
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            fileProcessor.process(file);
        });
        assertEquals("Файл некорректного формата. Ожидается файл с 4 колонками", thrown.getMessage());
    }

}
