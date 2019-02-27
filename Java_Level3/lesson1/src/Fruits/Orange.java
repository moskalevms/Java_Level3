package Fruits;

public class Orange extends Fruit {
    private String name;
    private float weight;

    public Orange(String name, float weight){
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
