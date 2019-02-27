package Arrays;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * 1.	Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
 * 2.	Написать метод, который преобразует массив в ArrayList;
 */

public class ArrChangePlace {

    //Задача 2.
    public static Integer[] intArr = {1,2,3,4};
    public static String[] strArr = {"один", "два", "три", "четыре"};


    public static void main(String[] args) {

        //Задача 1.
        Integer[] ints = {1,2,3,4,5};
        Array<Integer> iArr = new Array<Integer>(ints);
        iArr.avg(ints, 1, 4);

        String[] str = {"привет", "как", "дела"};
        Array<String> strArray = new Array<String>(str);
        strArray.avg(str, 0,2);

        //Задача 2.
        arrToarrayList(intArr);
        arrToarrayList(strArr);


    }


    //Метод для преобразования массива в ArrayList. На входе - массив
      public static <T> void arrToarrayList(T[] arr){
          List<T> destList = new ArrayList<>();
          Collections.addAll(destList, arr);
          for(int i =0; i < destList.size(); i++){
              System.out.println(destList.get(i));
          }
     }

}
