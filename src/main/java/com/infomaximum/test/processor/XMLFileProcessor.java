package com.infomaximum.test.processor;

import com.infomaximum.test.exception.ApplicationException;
import com.infomaximum.test.model.AddressEntry;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Реализация {@link FileProcessor} для XML.
 * <p>
 * Чтение файла происходит поэлементное для разгрузки используемой памяти, т.к. файлы довольно большие.
 */
public class XMLFileProcessor extends FileProcessor {

    @Override
    public void process(File file) throws ApplicationException {
        AddressEntry currentAddress;
        try {
            XMLStreamReader xmlReader = XMLInputFactory.newInstance().
                    createXMLStreamReader(new InputStreamReader(new FileInputStream(file)));

            while (xmlReader.hasNext()) {
                xmlReader.next();
                if (xmlReader.isStartElement() && xmlReader.getLocalName().equals("item")) {
                    currentAddress = new AddressEntry(xmlReader.getAttributeValue(0),
                            xmlReader.getAttributeValue(1),
                            xmlReader.getAttributeValue(2),
                            xmlReader.getAttributeValue(3));
                    processEntry(currentAddress);
                }
            }
        } catch (XMLStreamException e) {
            throw new ApplicationException("Файл некорректного формата. Ожидается файл с 4 атрибутами");
        } catch (FileNotFoundException e) {
            throw new ApplicationException("Произошла ошибка чтения файла");
        }
    }

}
