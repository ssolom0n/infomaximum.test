package com.infomaximum.test;

import com.infomaximum.test.exception.ApplicationException;
import com.infomaximum.test.processor.FileProcessor;
import com.infomaximum.test.processor.XMLFileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XMLFileProcessorTest {
    ClassLoader classLoader = getClass().getClassLoader();
    FileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        fileProcessor = new XMLFileProcessor();
    }

    @Test
    void testUniqueItemsShouldHaveSize5() throws ApplicationException {
        File file = new File(classLoader.getResource("XML/5UniqueItemsAnd2Doubles.xml").getFile());
        fileProcessor.process(file);
        assertEquals(5, fileProcessor.getUniqueItems().size());
    }

    @Test
    void testduplicatesMapShouldHaveSize2() throws ApplicationException {
        File file = new File(classLoader.getResource("XML/5UniqueItemsAnd2Doubles.xml").getFile());
        fileProcessor.process(file);
        assertEquals(2, fileProcessor.getDuplicatesMap().size());
    }

    @Test
    void testExpectingExceptionWrongFileEmpty() {
        File file = new File(classLoader.getResource("XML/Empty.xml").getFile());
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            fileProcessor.process(file);
        });
        assertEquals("Файл некорректного формата. Ожидается файл с 4 атрибутами", thrown.getMessage());
    }

    @Test
    void testExpectingExceptionWrongFile() {
        File file = new File(classLoader.getResource("XML/Corrupted.xml").getFile());
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            fileProcessor.process(file);
        });
        assertEquals("Файл некорректного формата. Ожидается файл с 4 атрибутами", thrown.getMessage());
    }
}
