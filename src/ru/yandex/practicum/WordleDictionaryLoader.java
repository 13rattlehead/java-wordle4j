package ru.yandex.practicum;

import ru.yandex.practicum.exception.DictionaryFileException;
import ru.yandex.practicum.exception.EmptyDictionaryException;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public WordleDictionary load(String fileName) throws DictionaryFileException, EmptyDictionaryException, IOException {

        List<String> words = new ArrayList<>();

        Path path = Paths.get(fileName);
        File file = path.toFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            reader.lines();
            words.addAll(reader.lines().map(WordleDictionaryLoader::normalize).filter(this::checkLength).toList());
            return new WordleDictionary(words);
        } catch (IOException e) {
            throw new DictionaryFileException("Ошибка чтения файла", e);
        }
    }

    public static String normalize(String word) {
        return word = word.trim().toLowerCase().replaceAll("ё", "е");
    }

    public boolean checkLength(String word) {
        return word.length() == 5;
    }
}
