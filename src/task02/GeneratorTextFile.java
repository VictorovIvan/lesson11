package task02;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>Задание: Перевести одну из предыдущих работ на использование стримов и лямбда-выражений там,
 * где это уместно (возможно, жертвуя производительностью)</p>
 *
 * <p>Создать генератор текстовых файлов, работающий по следующим правилам:</p>
 * * <p>Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
 * Слово состоит из 1<=n2<=15 латинских букв
 * Слова разделены одним пробелом
 * Предложение начинается с заглавной буквы
 * Предложение заканчивается (.|!|?)+" "</p>
 * <p>Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений.
 * В конце абзаца стоит разрыв строки и перенос каретки.</p>
 * <p>Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива
 * в следующее предложение (1/probability).</p>
 * <p>Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability),
 * который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.</p>
 */

/**
 * Class GeneratorTextFile
 */
public class GeneratorTextFile {
    /**
     * Constructor GeneratorTextFile
     */
    public GeneratorTextFile() {
    }

    private final int maxNumberParagraph = 20;
    private final int minNumberParagraph = 1;
    private final int maxNumberProposal = 15;
    private final int minNumberProposal = 1;
    private final int maxNumberWord = 15;
    private final int minNumberWord = 1;

    private int probabilityOfOccurrenceWord = 0;

    /**
     * Generating some word
     *
     * @param len Length count of the char
     * @return sb.toString() The generated word
     */
    private String randomWord(int len) {
        final String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);

        for (int index = 0; index < len; index++) {
            sb.append(lowerAlphabet.charAt(rnd.nextInt(lowerAlphabet.length())));
        }
        return sb.toString();
    }

    /**
     * Generating some proposal
     *
     * @param len Length count of the word
     * @return sb.toString() The generated proposal
     */
    private String randomProposal(int len) {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);

        sb.append(" ");
        sb.append(upperAlphabet.charAt(rnd.nextInt(upperAlphabet.length())));
        IntStream.range(0, len).forEach(index -> {
            sb.append(randomWord(rnd.nextInt(maxNumberWord) + minNumberWord));
            if (index < len - 1) {
                sb.append(" ");
            }
        });
        return sb.toString();
    }

    /**
     * Generating some paragraph
     *
     * @param len         Length count of the proposal
     * @param words       Arrays of the word
     * @param probability Probability of occurrence of one of the array words th the proposal
     * @return sb.toString() The generated paragraph
     */
    private String randomParagraph(int len, String[] words, int probability) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        String endProposal = "(.|!|?)+\" \"";
        double percentProbability = (1 / ((double) probability * 0.01));
        int numberProposal = (int) percentProbability;

        probabilityOfOccurrenceWord++;
        IntStream.range(0, len).forEach(index -> {
            sb.append(randomProposal(rnd.nextInt(maxNumberProposal) + minNumberProposal));
            if ((probabilityOfOccurrenceWord >= numberProposal) && (index + 1) == numberProposal) {
                sb.append(" ");
                sb.append(words[rnd.nextInt(words.length)]);
                probabilityOfOccurrenceWord = 0;
            }
            sb.append(endProposal.charAt(rnd.nextInt(endProposal.length())));
        });
        return sb.toString();
    }

    /**
     * Generating some text
     *
     * @param len         Length count of the paragraphs
     * @param words       Arrays of the word
     * @param probability Probability of occurrence of one of the array words th the proposal
     * @return sb.toString() The generated Text
     */
    private String randomText(int len, String[] words, int probability) {
        SecureRandom rnd = new SecureRandom();
        String sb = IntStream.range(0, len).mapToObj(index -> randomParagraph(rnd.nextInt(maxNumberParagraph) + minNumberParagraph, words, probability) + "\n").collect(Collectors.joining());

        return sb;
    }

    /**
     * @param path        Path to writw
     * @param n           Number of files
     * @param size        Size of the text paragraph
     * @param words       Arrays of the word
     * @param probability Probability of occurrence of one of the array words th the proposal
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void getFiles(String path, int n, int size, String[] words, int probability) throws IOException {
        for (int index = 1; index < (n + 1); index++) {
            FileOutputStream file = new FileOutputStream(path + index + ".txt");
            DataOutputStream data = new DataOutputStream(file);

            data.writeUTF(randomText(size, words, probability));
            data.flush();
            data.close();
        }
        System.out.println("Файлы созданы: " + path);
    }
}
