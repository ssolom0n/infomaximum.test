package com.infomaximum.test;

import com.infomaximum.test.exception.ApplicationException;
import com.infomaximum.test.model.AddressEntry;
import com.infomaximum.test.processor.CSVFileProcessor;
import com.infomaximum.test.processor.FileProcessor;
import com.infomaximum.test.processor.XMLFileProcessor;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileProcessorTest {
    ClassLoader classLoader = getClass().getClassLoader();
    FileProcessor fileProcessor;

    @Test
    void testUniqueItemsShouldHaveSize5() {
        fileProcessor = new XMLFileProcessor();
        File file = new File(classLoader.getResource("XML/5UniqueItemsAnd2Doubles.xml")
                .getFile());
        try {
            fileProcessor.process(file);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        assertEquals(5, fileProcessor.getUniqueItems().size());
    }

    @Test
    void testduplicatesMapShouldHaveSize2() {
        fileProcessor = new XMLFileProcessor();
        File file = new File(classLoader.getResource("XML/5UniqueItemsAnd2Doubles.xml")
                .getFile());
        try {
            fileProcessor.process(file);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        assertEquals(2, fileProcessor.getDuplicatesMap().size());
    }

    @Test
    void testExpectingExceptionWrongFile() {
        fileProcessor = new XMLFileProcessor();
        File file = new File(classLoader.getResource("XML/Empty.xml")
                .getFile());
        ApplicationException thrown = assertThrows(ApplicationException.class, () -> {
            fileProcessor.process(file);
        });
        assertEquals("Файл некорректного формата. Ожидается файл с 4 атрибутами", thrown.getMessage());
    }

    @Test
    void testProcessEntry() {
        fileProcessor = new CSVFileProcessor();
        fileProcessor.processEntry(new AddressEntry("Барнаул", "Дальняя улица", "56", "2"));
        fileProcessor.processEntry(new AddressEntry("Братск", "Большая Октябрьская улица", "65", "5"));
        fileProcessor.processEntry(new AddressEntry("Балаково", "Барыши, местечко", "67", "2"));
        fileProcessor.processEntry(new AddressEntry("Балаково", "Барыши, местечко", "67", "2"));
        assertEquals(3, fileProcessor.getUniqueItems().size());
        assertEquals(1, fileProcessor.getDuplicatesMap().size());
        assertEquals(1, fileProcessor.getCitiesFloorsMap().get("Балаково 2"));
    }
}
