package main.java;

import java.util.*;

public class LexicalPermutation {
  static Integer MAX = 1000000007;

  public static void main(String[] args) {
    System.out.println(get_ranks("bac") + " == 2");
    System.out.println(get_ranks("acb") + " == 1");
    System.out.println(get_ranks("cba") + " == 5");

    System.out.println(get_ranks("aaa") + " == 0");
    System.out.println(get_ranks("abba") + " == 2");

    System.out.println(get_ranks("caabbc") + " == 60");

    System.out.println(get_ranks("axaelixedhtshsixbuzouqtjrkpyafthezfuehcovcqlbvmkbrwxhzrxymricmehktxepyxomxcx") + " == 1000000007");
  }

  static int get_ranks(String word)
  {
    String[] lettersArray = word.split("");

    List<String> listOfSortedLetters = new LinkedList<>(Arrays.asList(lettersArray));
    Collections.sort(listOfSortedLetters);

    Set<String> set = new HashSet<>(listOfSortedLetters);

    boolean hasDuplicates = set.size() != listOfSortedLetters.size();

    return hasDuplicates ? duplicates(word, listOfSortedLetters) : noDuplicates(word, listOfSortedLetters);

  }

  static int duplicates(String word, List<String> listOfSortedLetters) {
    int wordLength = word.length();

    Map<String, Integer> map = getUniqueLetters(word);

    Integer startIndex = 0;
    Integer endIndex = combinationComplex(map);
    if (endIndex.equals(MAX)) return MAX;
    endIndex -= 1;

    String[] lettersArr = word.split("");
    for (int i = 0; i < wordLength; i++) {
      String letter = lettersArr[i];
      Integer mapLength = map.size();
      for(int j = 0; j < mapLength; j++) {
        String sortedLetter = map.keySet().toArray(new String[map.size()])[j];
        if(sortedLetter.equals(letter)) {
          listOfSortedLetters.remove(sortedLetter);
          String tempWord = listAsString(listOfSortedLetters);

          map = getUniqueLetters(tempWord);
          endIndex = startIndex + combinationComplex(map) - 1;
          break;
        } else {
          String tempWord = listAsString(listOfSortedLetters).substring(1);
          Map<String, Integer> tempMap = getUniqueLetters(tempWord);
          startIndex += combinationComplex(tempMap);
        }
      }
      if(startIndex.equals(endIndex)) {
        break;
      }
    }

    return startIndex;
  }

  static int noDuplicates(String word, List<String> listOfSortedLetters)
  {
    int wordLength = word.length();

    Integer startIndex = 0;
    Integer endIndex = permutation(wordLength);

    String[] lettersArr = word.split("");
    for (int i = 0; i < wordLength; i++) {
      String letter = lettersArr[i];
      for(int j = 0; j < wordLength - i; j++) {
        String sortedLetter = listOfSortedLetters.get(j);
        if(sortedLetter.equals(letter)) {
          endIndex = startIndex + permutation(wordLength - i - 1) - 1;
          listOfSortedLetters.remove(sortedLetter);
          break;
        } else {
          startIndex += permutation(wordLength - i - 1);
        }
      }
      if(startIndex.equals(endIndex)) {
        break;
      }
    }

    return startIndex;

  }

  // (n+m)! / n!m!
  private static Integer combinationComplex(Map<String, Integer> map) {
    Integer divideBy = 1;
    Integer total = 0;
    for(Integer value : map.values()) {
      total += value;
      divideBy = divideBy * permutation(value);
      if (divideBy.equals(0)) break;
    }

    Integer permTotal = permutation(total);
    Integer num = divideBy.equals(0) || permTotal.equals(0) ? MAX : permTotal / divideBy;

    return num;
  }

  //n!
  private static Integer permutation(Integer n) {
    Integer nE = 1;

    for (int i = n; i > 1; i--) {
      nE = nE * i;
    }

    return nE;
  }

  private static Map<String, Integer> getUniqueLetters(String input) {
    String[] letters = input.split("");
    Map<String, Integer> map = new HashMap<>();

    for (String letter : letters) {
      Integer num = map.get(letter);
      num = num == null ? 0 : num;
      map.put(letter, num+1);
    }

    return map;
  }

  static void printPermutations(String word) {
    Set<String> set = permutations(word);
    List<String> list = new ArrayList<>(set);
    Collections.sort(list);
    int i = 0;
    for(String s : list) {
      System.out.println(i + "-" + s);
      i++;
    }
  }

  static Set<String> permutations(String str)
  {
    Set<String> set = new HashSet<>();
    if (str.isEmpty())
      return set;

    Character a = str.charAt(0);

    if (str.length() > 1)
    {
      str = str.substring(1);

      Set<String> permSet = permutations(str);

      for (String x : permSet)
      {
        for (int i = 0; i <= x.length(); i++)
        {
          set.add(x.substring(0, i) + a + x.substring(i));
        }
      }
    }
    else
    {
      set.add(a + "");
    }
    return set;
  }

  static String listAsString(List<String> arr)
  {
    String str = "";
    for(String a : arr) {
      str += a;
    }
    return str;
  }
}
