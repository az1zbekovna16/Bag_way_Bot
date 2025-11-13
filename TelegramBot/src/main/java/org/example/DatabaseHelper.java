package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class DatabaseHelper {

    private static final String DB_FILE = "database.xlsx";
    private Workbook workbook;

    public DatabaseHelper() {
        try {
            File file = new File(DB_FILE);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                fis.close();
            } else {
                createNewDatabase();
            }
        } catch (IOException e) {
            e.printStackTrace();
            createNewDatabase();
        }
    }

    // Yangi database yaratish
    private void createNewDatabase() {
        workbook = new XSSFWorkbook();

        // Products sheet
        Sheet productsSheet = workbook.createSheet("Products");
        Row header = productsSheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Brand");
        header.createCell(2).setCellValue("Name");
        header.createCell(3).setCellValue("Price");
        header.createCell(4).setCellValue("ImageURL");

        // Sample data
        addSampleProducts(productsSheet);

        // Users sheet
        Sheet usersSheet = workbook.createSheet("Users");
        Row userHeader = usersSheet.createRow(0);
        userHeader.createCell(0).setCellValue("ChatID");
        userHeader.createCell(1).setCellValue("FirstName");
        userHeader.createCell(2).setCellValue("LastName");
        userHeader.createCell(3).setCellValue("Username");
        userHeader.createCell(4).setCellValue("RegisterDate");

        // Orders sheet
        Sheet ordersSheet = workbook.createSheet("Orders");
        Row orderHeader = ordersSheet.createRow(0);
        orderHeader.createCell(0).setCellValue("OrderID");
        orderHeader.createCell(1).setCellValue("ChatID");
        orderHeader.createCell(2).setCellValue("Products");
        orderHeader.createCell(3).setCellValue("TotalPrice");
        orderHeader.createCell(4).setCellValue("OrderDate");
        orderHeader.createCell(5).setCellValue("Status");

        saveDatabase();
    }

    // Sample mahsulotlar qo'shish
    private void addSampleProducts(Sheet sheet) {
        String[][] products = {
                // HERMES
                {"1", "HERMES", "Birkin 30", "190000", "C:\\Users\\az1zbekovna_16\\Desktop\\java\\TelegramBot\\src\\main\\resources\\imgs\\hermes1.png"},
                {"2", "HERMES", "Kelly 25", "180000", "C:\\Users\\az1zbekovna_16\\Desktop\\java\\TelegramBot\\src\\main\\resources\\imgs\\hermes2.png"},
                {"3", "HERMES", "Constance 24", "12000000", "https://picsum.photos/400/400?random=3"},
                {"4", "HERMES", "Evelyne III", "4500000", "https://picsum.photos/400/400?random=4"},
                {"5", "HERMES", "Garden Party", "3800000", "https://picsum.photos/400/400?random=5"},

                // BAGCO
                {"6", "BAGCO", "Classic Tote", "325000", "https://picsum.photos/400/400?random=6"},
                {"7", "BAGCO", "Leather Satchel", "450000", "https://picsum.photos/400/400?random=7"},
                {"8", "BAGCO", "Mini Crossbody", "280000", "https://picsum.photos/400/400?random=8"},
                {"9", "BAGCO", "Business Bag", "550000", "https://picsum.photos/400/400?random=9"},
                {"10", "BAGCO", "Weekend Duffle", "680000", "https://picsum.photos/400/400?random=10"},

                // min-min
                {"11", "min-min", "Mini Shoulder Bag", "150000", "https://picsum.photos/400/400?random=11"},
                {"12", "min-min", "Cute Clutch", "120000", "https://picsum.photos/400/400?random=12"},
                {"13", "min-min", "Chain Bag", "180000", "https://picsum.photos/400/400?random=13"},
                {"14", "min-min", "Evening Bag", "200000", "https://picsum.photos/400/400?random=14"},
                {"15", "min-min", "Crossbody Mini", "160000", "https://picsum.photos/400/400?random=15"},

                // PRADA
                {"16", "PRADA", "Galleria Bag", "8500000", "https://picsum.photos/400/400?random=16"},
                {"17", "PRADA", "Cahier Bag", "7200000", "https://picsum.photos/400/400?random=17"},
                {"18", "PRADA", "Cleo Bag", "6800000", "https://picsum.photos/400/400?random=18"},
                {"19", "PRADA", "Sidonie Bag", "5900000", "https://picsum.photos/400/400?random=19"},
                {"20", "PRADA", "Re-Edition 2005", "4500000", "https://picsum.photos/400/400?random=20"},

                // LACOSTE
                {"21", "LACOSTE", "L.12.12 Concept", "450000", "https://picsum.photos/400/400?random=21"},
                {"22", "LACOSTE", "Chantaco Bag", "680000", "https://picsum.photos/400/400?random=22"},
                {"23", "LACOSTE", "Daily Classic", "520000", "https://picsum.photos/400/400?random=23"},
                {"24", "LACOSTE", "Crossover Bag", "390000", "https://picsum.photos/400/400?random=24"},
                {"25", "LACOSTE", "Zip Tote", "580000", "https://picsum.photos/400/400?random=25"},

                // VALENTINO
                {"26", "VALENTINO", "Rockstud Spike", "9200000", "https://picsum.photos/400/400?random=26"},
                {"27", "VALENTINO", "VSLING Bag", "8800000", "https://picsum.photos/400/400?random=27"},
                {"28", "VALENTINO", "Roman Stud", "7500000", "https://picsum.photos/400/400?random=28"},
                {"29", "VALENTINO", "Supervee Bag", "6900000", "https://picsum.photos/400/400?random=29"},
                {"30", "VALENTINO", "One Stud Bag", "8200000", "https://picsum.photos/400/400?random=30"},

                // ZARA
                {"31", "ZARA", "City Bag", "180000", "https://picsum.photos/400/400?random=31"},
                {"32", "ZARA", "Tote Shopper", "220000", "https://picsum.photos/400/400?random=32"},
                {"33", "ZARA", "Crossbody Bag", "150000", "https://picsum.photos/400/400?random=33"},
                {"34", "ZARA", "Bucket Bag", "190000", "https://picsum.photos/400/400?random=34"},
                {"35", "ZARA", "Mini Bag", "140000", "https://picsum.photos/400/400?random=35"},

                // JACQUEMUS
                {"36", "JACQUEMUS", "Le Chiquito", "2800000", "https://picsum.photos/400/400?random=36"},
                {"37", "JACQUEMUS", "Le Bambino", "3200000", "https://picsum.photos/400/400?random=37"},
                {"38", "JACQUEMUS", "Le Grand Bambino", "3800000", "https://picsum.photos/400/400?random=38"},
                {"39", "JACQUEMUS", "Le Sac Riviera", "4200000", "https://picsum.photos/400/400?random=39"},
                {"40", "JACQUEMUS", "Le Petit Bambino", "2900000", "https://picsum.photos/400/400?random=40"}
        };

        for (int i = 0; i < products.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < products[i].length; j++) {
                row.createCell(j).setCellValue(products[i][j]);
            }
        }
    }

    // Brend bo'yicha mahsulotlar
    public List<Product> getProductsByBrand(String brand) {
        List<Product> products = new ArrayList<>();
        Sheet sheet = workbook.getSheet("Products");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                try {
                    String rowBrand = getCellValueAsString(row.getCell(1));
                    if (rowBrand.equalsIgnoreCase(brand)) {
                        products.add(new Product(
                                (int) getCellValueAsNumber(row.getCell(0)),
                                getCellValueAsString(row.getCell(1)),
                                getCellValueAsString(row.getCell(2)),
                                (int) getCellValueAsNumber(row.getCell(3)),
                                getCellValueAsString(row.getCell(4))
                        ));
                    }
                } catch (Exception e) {
                    System.out.println("Row " + i + " o'qishda xatolik: " + e.getMessage());
                }
            }
        }

        return products;
    }

    // ID bo'yicha mahsulot
    public Product getProductById(int id) {
        Sheet sheet = workbook.getSheet("Products");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                try {
                    int rowId = (int) getCellValueAsNumber(row.getCell(0));
                    if (rowId == id) {
                        return new Product(
                                rowId,
                                getCellValueAsString(row.getCell(1)),
                                getCellValueAsString(row.getCell(2)),
                                (int) getCellValueAsNumber(row.getCell(3)),
                                getCellValueAsString(row.getCell(4))
                        );
                    }
                } catch (Exception e) {
                    System.out.println("Row " + i + " o'qishda xatolik: " + e.getMessage());
                }
            }
        }

        return null;
    }

    // Helper: Cell qiymatini String sifatida olish
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    // Helper: Cell qiymatini Number sifatida olish
    private double getCellValueAsNumber(Cell cell) {
        if (cell == null) return 0;

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0;
                }
            default:
                return 0;
        }
    }

    // Foydalanuvchini ro'yxatdan o'tkazish
    public void registerUser(Long chatId, String firstName, String lastName, String username) {
        Sheet sheet = workbook.getSheet("Users");

        // Tekshirish - user bormi
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null && (long) row.getCell(0).getNumericCellValue() == chatId) {
                return; // Allaqachon bor
            }
        }

        // Yangi user qo'shish
        Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);
        newRow.createCell(0).setCellValue(chatId);
        newRow.createCell(1).setCellValue(firstName);
        newRow.createCell(2).setCellValue(lastName != null ? lastName : "");
        newRow.createCell(3).setCellValue(username != null ? username : "");
        newRow.createCell(4).setCellValue(new Date().toString());

        saveDatabase();
    }

    // Buyurtmani saqlash
    public void saveOrder(Long chatId, List<CartItem> cart) {
        Sheet sheet = workbook.getSheet("Orders");

        StringBuilder products = new StringBuilder();
        int total = 0;

        for (CartItem item : cart) {
            products.append(item.getName()).append(", ");
            total += item.getPrice();
        }

        Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);
        newRow.createCell(0).setCellValue(sheet.getLastRowNum()); // OrderID
        newRow.createCell(1).setCellValue(chatId);
        newRow.createCell(2).setCellValue(products.toString());
        newRow.createCell(3).setCellValue(total);
        newRow.createCell(4).setCellValue(new Date().toString());
        newRow.createCell(5).setCellValue("pending");

        saveDatabase();
    }

    // Database saqlash
    private void saveDatabase() {
        try {
            FileOutputStream fos = new FileOutputStream(DB_FILE);
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}