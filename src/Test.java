import task01.ReadTextFile;
import task02.GeneratorTextFile;

import java.io.IOException;

/**
 * Задание: Перевести одну из предыдущих работ на использование стримов и лямбда-выражений там,
 * где это уместно (возможно, жертвуя производительностью)
 */
public class Test {
    public static void main(String[] args) {
        /**
         * First task
         */
        System.out.println("Первое задание:");
        String pathTextFile = ".\\taskOneExample\\";
        String nameTextFile = "Five ways you could become a memory champion.txt";
        ReadTextFile readTextFile = new ReadTextFile();

        try {
            readTextFile.readTextFileAndCreateNew(pathTextFile, nameTextFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Second task
         */
        System.out.println("Второе задание:");
        GeneratorTextFile generatorTextFile = new GeneratorTextFile();
        String somePath = ".\\taskTwoExample\\";
        int someN = 5;
        int someSize = 3;
        String[] someWords = new String[]{"Cat", "Dog", "Horse"};
        int someProbability = 50;

        try {
            generatorTextFile.getFiles(somePath, someN, someSize, someWords, someProbability);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
