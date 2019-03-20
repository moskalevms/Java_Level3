import java.util.Arrays;

/**
 * 1.	Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
 *   Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
 *   идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
 *   иначе в методе необходимо выбросить RuntimeException.
 *   Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 *   Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
 * 2.	Написать метод, который проверяет состав массива из чисел 1 и 4.
 * Если в нем нет хоть одной четверки или единицы, то метод вернет false;
 * Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 * [ 1 1 1 4 4 1 4 4 ] -> true
 * [ 1 1 1 1 1 1 ] -> false
 * [ 4 4 4 4 ] -> false
 * [ 1 4 4 1 1 4 3 ] -> false
 */
public class JUnitTest {



    public static void main(String[] args) {

       int[] arr1 = {1, 2, 4, 4, 2, 3, 4, 1, 7};

       int[] arr2 = {1, 1, 1, 4, 4, 1, 4, 4 };

       int[] arr3 = {1, 1, 1, 1, 1, 1 };


       int[] arr4 = {2, 6, 7, 8, 2, 5 };



       System.out.println(Arrays.toString(arrTestForTestOne(arr1)));
       System.out.println(arrTestForTestTwo(arr2));
       System.out.println(arrTestForTestTwo(arr3));
       System.out.println(arrTestForTestTwo(arr4));
    }


    public static int[] arrTestForTestOne(int[] arr) throws RuntimeException {
        int n = 0;
        String str = Arrays.toString(arr);
        if (str.indexOf(String.valueOf(4)) != -1) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 4) n = i;
            }
            int[] arr1 = new int[arr.length - n - 1];
            System.arraycopy(arr, n + 1, arr1, 0, arr1.length);
            return arr1;
        } else {
            throw new RuntimeException();
        }
    }


    public static boolean arrTestForTestTwo(int[] arr){
        boolean result = false;

        for(int i =0; i < arr.length; i++){

            if(arr[i] == 1 && arr[i] == 4 ){
                result = true;
            }
            else result = false;
        }


        return result;

    }
}

