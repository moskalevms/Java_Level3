package Fruits;

import java.util.ArrayList;
import java.util.List;

/**
 * 3.	Задача:
 * a.	Даны классы Fruit -> Apple, Orange;
 * b.	Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 * c.	Для хранения фруктов внутри коробки можно использовать ArrayList;
 * d.	Сделать метод getWeight(), который высчитывает вес коробки. Задать вес одного фрукта и их количество: вес яблока – 1.0f, апельсина – 1.5f
 *      (единицы измерения не важны);
 * e.	Внутри класса Box сделать метод Compare, который позволяет сравнить текущую коробку с той, которую подадут в Compare в качестве параметра.
 *      True – если их массы равны, False в противоположном случае. Можно сравнивать коробки с яблоками и апельсинами;
 * f.	Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую.
 *      Помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами.
 *      Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в первой;
 * g.	Не забываем про метод добавления фрукта в коробку.
 */

public class Box<E extends Fruit> { //ограничиваю сверху обощенный тип Е с помощью интерфейса Fruit

    private List<E> fruits = new ArrayList<>();

    public void add(E fruit){
      fruits.add(fruit);
    }

    public void moveToAll(Box<E> anotherBox){ //перемещаем все фрукты из текущей коробки в ту коробку, которую передали на вход
        for (E fruit : fruits){
            anotherBox.add(fruit);
        }

        fruits.clear();
    }


    public boolean compareTo(Box<?> anotherBox){
        return Math.abs(this.getWeight() - anotherBox.getWeight()) < 0.0001;
    }

    public double getWeight(){
        double sum = 0.0;
        for(E fruit : fruits){
            sum += fruit.getWeight();
        }
        return sum;
    }

}
