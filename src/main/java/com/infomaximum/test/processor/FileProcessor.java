package com.infomaximum.test.processor;

import com.infomaximum.test.exception.ApplicationException;
import com.infomaximum.test.model.AddressEntry;

import java.io.File;
import java.util.*;

public abstract class FileProcessor {

    private Set<AddressEntry> uniqueItems = new HashSet<>();
    private Map<AddressEntry, Integer> duplicatesMap = new HashMap<>(); // Мапа дублирующихся записей

    private Integer itemsCount = 0; // Общее количество записей в файле (необязательный счетчик)

    /**
     * Мапа этажности зданий по городам, ключом является {@code "ГОРОД ЭТАЖНОСТЬ"},
     * например, {@code "Саранск 5"} или {@code "Нальчик 4"}.<br/>
     * TreeMap использован (не HashMap) для автоматической сортировки.
     */
    private Map<String, Integer> citiesFloorsMap = new TreeMap<>();

    /**
     * Метод для чтения и обработки (заполнения полей) файла, конкретные реализации зависит от типа файла.
     *
     * @param file файл для процессинга
     * @throws ApplicationException если возникли программные ошибки (ошибка чтения, формата и т.д.)
     * @see CSVFileProcessor CSVFileProcessor
     */
    public abstract void process(File file) throws ApplicationException;

    public final void processEntry(AddressEntry entry) {
        itemsCount++;
        if (uniqueItems.contains(entry)) {
            if (duplicatesMap.containsKey(entry)) {
                duplicatesMap.put(entry, duplicatesMap.get(entry) + 1);
            } else {
                duplicatesMap.put(entry, 2);
            }
        } else {
            uniqueItems.add(entry);
            processFloorsFor(entry);
        }
    }

    public final Map<String, Integer> processFloorsFor(AddressEntry entry) {
        String cityFloorKey = entry.getCity() + " " + entry.getFloor();
        if (!citiesFloorsMap.containsKey(cityFloorKey)) {
            for (Integer i = 1; i <= 5; i++) {
                citiesFloorsMap.put(entry.getCity() + " " + i, 0);
            }
        }
        citiesFloorsMap.put(cityFloorKey, citiesFloorsMap.get(cityFloorKey) + 1);
        return citiesFloorsMap;
    }

    public final void printResults() {
        System.out.println("=============================");

        if (citiesFloorsMap.size() == 0) {
            System.out.println("Записей в файле нет");
        } else {
            for (Map.Entry<String, Integer> entry : citiesFloorsMap.entrySet()) {
                System.out.println("В городе " + entry.getKey() + "-этажных зданий: " + entry.getValue());
            }
            System.out.println("=============================");

            if (duplicatesMap.size() == 0) {
                System.out.println("Повторяющихся записей нет");
            } else {
                for (Map.Entry<AddressEntry, Integer> entry : duplicatesMap.entrySet()) {
                    System.out.println("Запись " + entry.getKey() + " встречается " + entry.getValue() + " раз(а)");
                }
            }
            System.out.println("=============================");
            System.out.println("Уникальных записей (зданий) в файле: " + uniqueItems.size()); // необязательная строка, можно удалить
            System.out.println("Всего записей в файле: " + itemsCount); // необязательный счетчик, можно удалить

            uniqueItems.clear();
            duplicatesMap.clear();
            citiesFloorsMap.clear();
        }
    }

    public Set<AddressEntry> getUniqueItems() {
        return uniqueItems;
    }

    public Map<AddressEntry, Integer> getDuplicatesMap() {
        return duplicatesMap;
    }

    public Map<String, Integer> getCitiesFloorsMap() {
        return citiesFloorsMap;
    }

}
