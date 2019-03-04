package Fruits;

public class TestBox {
    public static void main(String[] args) {
        Box<Apple> appleBox = new Box<>();
        Box<Apple> appleBox2 = new Box<>();

        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());

        appleBox.add(new Apple());
        appleBox.add(new Apple());
        System.out.println("appleBox weight is: " + appleBox.getWeight());

        appleBox2.add(new Apple());
        System.out.println("appleBox2 weight is: " + appleBox.getWeight());

        appleBox.moveToAll(appleBox2);
        System.out.println("appleBox wieght is: " + appleBox.getWeight());
        System.out.println("appleBox2 wieght is: " + appleBox2.getWeight());

        System.out.println("Compare oranges and apples: " + appleBox.compareTo(orangeBox));
        System.out.println("Compare oranges and apples2: " + appleBox2.compareTo(orangeBox));
    }

}
