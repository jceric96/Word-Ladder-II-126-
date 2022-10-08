import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WordLadderII {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        Set<String> dictionary = new HashSet<>(wordList);

        List<List<String>> results = new ArrayList<>();

        if (wordList.size() == 0)
            return results;
        HashMap<String, ArrayList<String>> preList = new HashMap<>();

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        while (!queue.isEmpty()) {
            Set<String> queue2 = new HashSet<>();
            boolean isFound = false;
            while (!queue.isEmpty()) {
                String preWord = queue.poll();
                Set<String> wordsWithinDistance = getWordsWithinDistance(dictionary, preWord);
                for (String word : wordsWithinDistance) {
                    if (word.equals(endWord)) {
                        isFound = true;
                    }
                    if (!visited.contains(word)) {
                        updatePreList(preList, word, preWord);
                        queue2.add(word);
                    }
                }
            }
            visited.addAll(queue2);
            if (isFound) {
                getPaths(results, preList, new ArrayList<String>(), endWord);
            }
            queue.addAll(queue2);
        }
        // remove duplicate list
        if (results.size() > 1) {
            Set<List<String>> removeDup = new HashSet<>(results);
            results.clear();
            for (List<String> list : removeDup) {
                results.add(list);
            }
        }
        return results;
    }

    private Set<String> getWordsWithinDistance(Set<String> dictionary, String current) {
        Set<String> words = new HashSet<>();
        char[] wordCharArr = current.toCharArray();
        for (int i = 0; i < current.length(); i++) {
            char originChar = wordCharArr[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == originChar) {
                    continue;
                }
                wordCharArr[i] = c;
                String newStr = new String(wordCharArr);
                if (dictionary.contains(newStr)) {
                    words.add(newStr);
                }
            }
            wordCharArr[i] = originChar;
        }
        return words;
    }

    private void updatePreList(HashMap<String, ArrayList<String>> preList, String currWord, String preWord) {
        if (!preList.containsKey(currWord)) {
            preList.put(currWord, new ArrayList<String>());
        }
        preList.get(currWord).add(preWord);
    }

    private void getPaths(List<List<String>> paths, HashMap<String, ArrayList<String>> preList, List<String> currPath,
            String endWord) {
        if (!preList.containsKey(endWord)) {
            currPath.add(endWord);
            Collections.reverse(currPath);
            paths.add(currPath);
            return;
        }
        for (String pre : preList.get(endWord)) {
            List<String> newPath = new ArrayList<String>(currPath);
            newPath.add(endWord);
            getPaths(paths, preList, newPath, pre);
        }
    }

    public static void main(String[] args) {
        WordLadderII wl = new WordLadderII();
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new ArrayList<String>();
        String[] ss = { "hot", "dot", "dog", "lot", "log", "cog" };
        for (String s : ss) {
            wordList.add(s);
        }
        System.out.println(wl.findLadders(beginWord, endWord, wordList));
    }
}
