package ch2.Item2;

public class NutritionFacts {
    private final int servingSize;
    private final int getServingSize;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public NutritionFacts(int servingSize, int getServingSize, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.getServingSize = getServingSize;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
