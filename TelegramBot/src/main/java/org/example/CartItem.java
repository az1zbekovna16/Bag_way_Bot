package org.example;

class CartItem {
    private int productId;
    private String name;
    private int price;

    public CartItem(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public int getPrice() { return price; }
}

