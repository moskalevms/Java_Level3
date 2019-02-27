package Arrays;

import java.util.Arrays;

public class Array<T> {

    //создаю массив обобщенного типа
    private T[] arr; //массив обобщенного типа

    //конструктор класса. передаем на вход массив обощенного типа
    public Array(T[] arr){
        this.arr = arr;
    }

    //Метод перестановки элементов массива. На вход - массив ссылочного типа, номера переставляемых элементов
    public void avg(T[] arr, int n1, int n2 ){
       T sw = arr[n1];
       arr[n1] = arr[n2];
       arr[n2] = sw;
       System.out.println(Arrays.toString(arr) + "\n");

    }

}
