package Fruits;

public class Apple extends Fruit {
    private String name;
    private float weight;

    public Apple(String name, float weight){
        this.name = name;
        this.weight = weight;
    }

    public String getName(){
        return name;
    }

    public float getWeight(){
        return weight;
    }
}

