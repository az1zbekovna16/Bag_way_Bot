package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.*;

public class MyBotService {

    private final DatabaseHelper db = new DatabaseHelper();
    private final KeyBoardFactory keyboard = new KeyBoardFactory();
    private final Map<Long, List<CartItem>> userCarts = new HashMap<>();

    // /start command
    public void handleStart(MyBot bot, Long chatId, User user) {
        db.registerUser(chatId, user.getFirstName(), user.getLastName(), user.getUserName());

        String welcome = "🛍 Assalomu alaykum, " + user.getFirstName() + "!\n\n" +
                "BAGWAY Online Shop botiga xush kelibsiz!\n" +
                "Bizda eng yaxshi sumkalar mavjud! 👜";

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(welcome);
        message.setReplyMarkup(keyboard.getMainMenu());

        execute(bot, message);
    }

    // Buyurtma berish - brendlar ro'yxati
    public void handleBuyurtma(MyBot bot, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("👜 Brendni tanlang:");
        message.setReplyMarkup(keyboard.getBrandKeyboard());

        execute(bot, message);
    }

    // Brend tanlanganda - mahsulotlarni ko'rsatish
    public void handleBrandSelect(MyBot bot, Long chatId, String brand) {
        List<Product> products = db.getProductsByBrand(brand);

        if (products.isEmpty()) {
            sendText(bot, chatId, "❌ Bu brend uchun mahsulotlar topilmadi.");
            return;
        }

        for (Product p : products) {
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId.toString());
            photo.setPhoto(new InputFile(p.getImageUrl()));
            photo.setCaption(String.format("%s – %,d so'm", p.getName(), p.getPrice()));
            photo.setReplyMarkup(keyboard.getProductKeyboard(p.getId()));

            try {
                bot.execute(photo);
                Thread.sleep(300); // Spam oldini olish
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Savatga qo'shish
    public void handleAddToCart(MyBot bot, Long chatId, String callbackId, int productId, Integer messageId) {
        Product product = db.getProductById(productId);

        if (product == null) {
            answerCallback(bot, callbackId, "❌ Mahsulot topilmadi!");
            return;
        }

        // Savatni olish yoki yaratish
        List<CartItem> cart = userCarts.getOrDefault(chatId, new ArrayList<>());

        // Mahsulot allaqachon savatdami?
        boolean exists = false;
        for (CartItem item : cart) {
            if (item.getProductId() == productId) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            cart.add(new CartItem(product));
            userCarts.put(chatId, cart);
            answerCallback(bot, callbackId, "✅ Savatga qo'shildi!");

            // Tugmani o'zgartirish
            EditMessageReplyMarkup edit = new EditMessageReplyMarkup();
            edit.setChatId(chatId.toString());
            edit.setMessageId(messageId);
            edit.setReplyMarkup(keyboard.getProductKeyboardAdded(productId));

            try {
                bot.execute(edit);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            answerCallback(bot, callbackId, "ℹ️ Bu mahsulot allaqachon savatda!");
        }
    }

    // Savatni ko'rish
    public void handleSavat(MyBot bot, Long chatId) {
        List<CartItem> cart = userCarts.get(chatId);

        if (cart == null || cart.isEmpty()) {
            sendText(bot, chatId, "🛒 Sizning savatingiz bo'sh.");
            return;
        }

        StringBuilder sb = new StringBuilder("🛒 Sizning savatingiz:\n\n");
        int total = 0;

        for (CartItem item : cart) {
            sb.append(String.format("• %s – %,d so'm\n", item.getName(), item.getPrice()));
            total += item.getPrice();
        }

        sb.append(String.format("\n💰 Jami: %,d so'm", total));

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(sb.toString());
        message.setReplyMarkup(keyboard.getCartKeyboard());

        execute(bot, message);
    }

    // To'lov qilish
    public void handleCheckout(MyBot bot, Long chatId) {
        List<CartItem> cart = userCarts.get(chatId);

        if (cart == null || cart.isEmpty()) {
            sendText(bot, chatId, "🛒 Savatingiz bo'sh! Avval mahsulot qo'shing.");
            return;
        }

        String payment = "💳 To'lov uchun karta ma'lumotlari:\n\n" +
                "Karta raqami: 8600 1234 5678 9012\n" +
                "Karta egasi: BAGWAY SHOP\n\n" +
                "📝 To'lovni amalga oshirgandan so'ng:\n" +
                "1. Skrinshot yuboring\n" +
                "2. Yoki @bagway_admin ga murojaat qiling\n\n" +
                "⏰ Sizning buyurtmangiz 24 soat ichida qayta ko'rib chiqiladi.";

        sendText(bot, chatId, payment);

        // Buyurtmani saqlash
        db.saveOrder(chatId, cart);

        // Savatni tozalash
        userCarts.remove(chatId);

        sendText(bot, chatId, "✅ Buyurtmangiz qabul qilindi! Tez orada aloqaga chiqamiz.");
    }

    // Savatni tozalash
    public void handleClearCart(MyBot bot, Long chatId, String callbackId) {
        userCarts.remove(chatId);
        answerCallback(bot, callbackId, "🗑 Savat tozalandi!");
        sendText(bot, chatId, "🛒 Savatingiz tozalandi.");
    }

    // Bot haqida
    public void handleBotHaqida(MyBot bot, Long chatId) {
        String info = "ℹ️ BAGWAY Online Shop Bot\n\n" +
                "Biz orqali siz eng yaxshi brend sumkalarini xarid qilishingiz mumkin!\n\n" +
                "📦 Yetkazib berish: 1-3 kun\n" +
                "💳 To'lov: Karta orqali\n" +
                "📞 Aloqa: @bagway_admin";

        sendText(bot, chatId, info);
    }

    // Hamkorlik
    public void handleHamkorlik(MyBot bot, Long chatId) {
        String partner = "🤝 Hamkorlik takliflari\n\n" +
                "Agar siz ham bizning hamkorimiz bo'lishni istasangiz,\n" +
                "@bagway_admin ga murojaat qiling!\n\n" +
                "📈 Yuqori komissiya\n" +
                "🎁 Maxsus chegirmalar\n" +
                "🚀 Marketing yordami";

        sendText(bot, chatId, partner);
    }

    // Helper metodlar
    private void sendText(MyBot bot, Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        execute(bot, message);
    }

    private void execute(MyBot bot, SendMessage message) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void answerCallback(MyBot bot, String callbackId, String text) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(text);
        answer.setShowAlert(false);

        try {
            bot.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}