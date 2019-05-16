package task01;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  <p>Задание: Перевести одну из предыдущих работ на использование стримов и лямбда-выражений там,
 *  где это уместно (возможно, жертвуя производительностью)</p>
 *
 * Написать программу, читающую текстовый файл. Программа должна составлять отсортированный по алфавиту список слов,
 * найденных в файле и сохранять его в файл-результат. Найденные слова не должны повторяться,
 * регистр не должен учитываться. Одно слово в разных падежах – это разные слова.
 */

/**
 * Class ReadTextFile
 */
public class ReadTextFile {
    /**
     * Constructor ReadTextFile
     */
    public ReadTextFile() {
    }

    /**
     * Convert string input text and convert to Collection ArrayList
     * @param currentText Input current text
     * @return listWithoutDuplicates Collection word of input text without duplicate
     */
    private List<String> convertStringToArrayList(String currentText) {
        List<String> al;
        String[] str;
        String deleteSymbol = "1234567890!@#$%^&*()_+!№;%:?*/\\\"~.,'-";

        for (char c : deleteSymbol.toCharArray()) {
            currentText = currentText.replace(c, ' ');
        }
        str = currentText.split(" ");
        al = Arrays.asList(str);
        al.replaceAll(String::toLowerCase);
        return al.stream().distinct().collect(Collectors.<String>toList());
    }

    /**
     * Create new with sort lisct collection
     * @param listString Collection List without duplicate
     * @param pathWriteFile Path for write the file of the result
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    private void createNewFileWithSortList(List<String> listString, String pathWriteFile) throws IOException {
        FileOutputStream file = new FileOutputStream(pathWriteFile + "fileResult" + ".txt");
        DataOutputStream data = new DataOutputStream(file);
        String sb;

        Collections.sort(listString);
        sb = listString.stream().map(word -> word + "\n").collect(Collectors.joining());
        data.writeUTF(sb);
        data.flush();
        data.close();
    }

    /**
     * Read input file and create list new file with sort and without duplicate
     * @param pathReadFile Path for read the file
     * @param nameReadFile Name current read file
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void readTextFileAndCreateNew(String pathReadFile, String nameReadFile) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(pathReadFile + nameReadFile));
        BufferedReader br = new BufferedReader(new InputStreamReader(dataInputStream));
        String readTextFile;
        StringBuilder sb = new StringBuilder();
        String strLine;
        while ((strLine = br.readLine()) != null) {
            sb.append(strLine);
        }
        readTextFile = sb.toString();
        createNewFileWithSortList(convertStringToArrayList(readTextFile), pathReadFile);
        System.out.println("Создан файл-результат сортировки: " + pathReadFile + "fileResult.txt");
    }
}


