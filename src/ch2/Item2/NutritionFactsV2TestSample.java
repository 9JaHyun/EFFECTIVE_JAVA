package ch2.Item2;

public class NutritionFactsV2TestSample {
    public static void main(String[] args) {
        NutritionFactsV2 pizza = new NutritionFactsV2.Builder(1000, 8)
                .calories(2000)
                .sodium(40)
                .carbohydreate(180)
                .build();
    }
}
