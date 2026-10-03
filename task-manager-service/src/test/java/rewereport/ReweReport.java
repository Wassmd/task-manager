import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ReweReport {
  record Product(int id, String name, BigDecimal price, int quantity) {
  }

  record Promotion(int id, String name, String startCalendarWeek, String endCalendarWeek) {
  }

  record PromotionItem(int id, BigDecimal promotionPrice, int productId, int promotionId) {
  }

  private final List<Product> products = List.of(
      new Product(1, "Milka", new BigDecimal("1.99"), 1),
      new Product(2, "Milch", new BigDecimal("1.19"), 1),
      new Product(3, "Butter", new BigDecimal("2.00"), 1),
      new Product(4, "Wasser", new BigDecimal("3.80"), 6),
      new Product(5, "Bier", new BigDecimal("5.70"), 6),
      new Product(6, "Kaffee", new BigDecimal("7.99"), 1),
      new Product(7, "Nudeln", new BigDecimal("1.49"), 1),
      new Product(8, "Apfelsaft", new BigDecimal("1.89"), 1)
  );

  private final List<Promotion> promotions = List.of(
      new Promotion(1, "Sommeraktion", "2026/38", "2026/38"),
      new Promotion(2, "Herbstangebote", "2026/39", "2026/39"),
      new Promotion(3, "Getränkewoche", "2026/40", "2026/40"),
      new Promotion(4, "Jahreswechsel", "2026/52", "2026/52")
  );

  private final List<PromotionItem> promotionItems = List.of(
      new PromotionItem(1, new BigDecimal("1.49"), 1, 1),
      new PromotionItem(2, new BigDecimal("0.99"), 2, 1),
      new PromotionItem(3, new BigDecimal("1.69"), 3, 1),
      new PromotionItem(4, new BigDecimal("5.99"), 6, 2),
      new PromotionItem(5, new BigDecimal("0.99"), 7, 2),
      new PromotionItem(6, new BigDecimal("1.59"), 8, 2),
      new PromotionItem(7, new BigDecimal("2.99"), 4, 3),
      new PromotionItem(8, new BigDecimal("4.49"), 5, 3),
      new PromotionItem(9, new BigDecimal("1.39"), 8, 3),
      new PromotionItem(10, new BigDecimal("1.29"), 1, 4),
      new PromotionItem(11, new BigDecimal("4.99"), 5, 4)
  );

  @Test
  void promotionOverviewByPeriod() {
    List<Promotion> filteredPromotion = promotions.stream()
        .filter(promotion -> promotion.startCalendarWeek().equals("2026/38") && promotion.endCalendarWeek().equals("2026/38"))
        .toList();

    assertThat(filteredPromotion.isEmpty()).isFalse();
    assertThat(filteredPromotion.size()).isEqualTo(1);
  }

  @Test
  void testSavingPerArticleInPromotion() {
    // product price - promotionItemPrice
    // In promotion 1 i.e "Sommeraktion" i.e "2026/38" we have 3 products with promotion prices
    Promotion promotion = promotions.stream()
        .filter(promo -> promo.name.equals("Sommeraktion") && promo.startCalendarWeek.equals("2026/38"))
        .findFirst()
        .orElseThrow();

    List<PromotionItem> items = promotionItems.stream()
        .filter(item -> item.promotionId == promotion.id)
        .toList();

    // Product name, promotion name and promotion price and product price
    // product

    Map<Integer, Product> productById = products.stream().collect(toMap(p -> p.id, p -> p));

    Map<String, BigDecimal> productAndSaving = items.stream().collect(
        toMap(item -> productById.get(item.productId).name,
            item -> productById.get(item.productId).price.subtract(item.promotionPrice))
    );

    IO.println(productAndSaving);

    assertThat(productAndSaving.get("Milka")).isEqualTo(new BigDecimal("0.50"));
    assertThat(productAndSaving.get("Milch")).isEqualTo(new BigDecimal("0.20"));
    assertThat(productAndSaving.get("Butter")).isEqualTo(new BigDecimal("0.31"));
  }

  //Rabatt in Prozent — (price − promoPrice) / price × 100 je Artikel
  @Test
  void discountInPercentagePerPromotionItem() {
    record ItemPerDiscountPercentage(String name, BigDecimal discountPercentage) {
    }

    Map<Integer, Product> productById = products.stream().collect(toMap(p -> p.id, p -> p));
    List<ItemPerDiscountPercentage> discountPercentage = promotionItems.stream()
        .map(item -> {
              BigDecimal priceDifference = productById.get(item.productId).price.subtract(item.promotionPrice);
              BigDecimal ds = priceDifference.multiply(BigDecimal.valueOf(100))
                  .divide(productById.get(item.productId).price, 0, RoundingMode.HALF_UP);
              return new ItemPerDiscountPercentage(productById.get(item.productId).name, ds);
            }
        ).toList();

    assertThat(discountPercentage.getFirst().name).isEqualTo("Milka");
    assertThat(discountPercentage.getFirst().discountPercentage).isEqualTo(new BigDecimal("25"));

    discountPercentage.forEach(IO::println);
  }

  // ===== KOMPLEXERE AUFGABEN MIT ZWEI STREAMS =====
  @Test
  void testCalculateTotalSavingPerPromotion() {
    // Get all the products per promotion
    Map<Integer, Set<PromotionItem>> itemsPerPromotion = promotionItems
        .stream()
        .collect(groupingBy(PromotionItem::promotionId, toSet()));

    //Get the sum of PromotionItems Price in a Promotion
    Map<Integer, BigDecimal> sumOfPricesInAPromotion = itemsPerPromotion
        .entrySet()
        .stream()
        .collect(toMap(Map.Entry::getKey, entry -> entry
            .getValue()
            .stream()
            .map(PromotionItem::promotionPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
        ));

    //Get all sum of all normal price of the Item in a Promotion
    BigDecimal normalAmount;

    var sumOfPromotionItemsWithNormalPrice = itemsPerPromotion
        .entrySet()
        .stream()
        .collect(toMap(Map.Entry::getKey, entry ->
            entry
                .getValue()
                .stream()
                .flatMap(p ->
                    products
                        .stream()
                        .filter(pr -> pr.id == p.id)

                )
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add)

            ));

    IO.println(itemsPerPromotion);
    IO.println(sumOfPricesInAPromotion);
    IO.println(sumOfPromotionItemsWithNormalPrice);

    // saving per promotion
    var savings = sumOfPromotionItemsWithNormalPrice
        .entrySet()
        .stream()
        .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().subtract(sumOfPricesInAPromotion.get(entry.getKey()))));

    IO.println(savings);
  }

}
