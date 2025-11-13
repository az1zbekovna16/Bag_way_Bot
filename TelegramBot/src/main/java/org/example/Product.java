package org.example;
class Product {
    private int id;
    private String brand;
    private String name;
    private int price;
    private String imageUrl;

    public Product(int id, String brand, String name, int price, String imageUrl) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}