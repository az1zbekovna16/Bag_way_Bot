package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.*;

public class KeyBoardFactory {

    // Asosiy menyu
    public ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("🛍 Buyurtma berish");
        row1.add("🛒 Buyurtmalarim");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("ℹ️ Bot haqida");
        row2.add("🤝 Hamkorlik");

        keyboard.setKeyboard(Arrays.asList(row1, row2));
        return keyboard;
    }

    // Brendlar klaviaturasi
    public InlineKeyboardMarkup getBrandKeyboard() {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        String[] brands = {"HERMES", "BAGCO", "min-min", "PRADA", "LACOSTE", "VALENTINO", "ZARA", "JACQUEMUS"};

        for (int i = 0; i < brands.length; i += 3) {
            List<InlineKeyboardButton> row = new ArrayList<>();

            for (int j = i; j < i + 3 && j < brands.length; j++) {
                InlineKeyboardButton btn = new InlineKeyboardButton();
                btn.setText(brands[j]);
                btn.setCallbackData("BRAND:" + brands[j]);
                row.add(btn);
            }

            rows.add(row);
        }

        keyboard.setKeyboard(rows);
        return keyboard;
    }

    // Mahsulot klaviaturasi
    public InlineKeyboardMarkup getProductKeyboard(int productId) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText("🛒 Savatga qo'shish");
        btn.setCallbackData("ADD:" + productId);

        keyboard.setKeyboard(Collections.singletonList(Collections.singletonList(btn)));
        return keyboard;
    }

    // Savatga qo'shilgan mahsulot klaviaturasi
    public InlineKeyboardMarkup getProductKeyboardAdded(int productId) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText("✅ Savatda");
        btn.setCallbackData("ADDED:" + productId);

        keyboard.setKeyboard(Collections.singletonList(Collections.singletonList(btn)));
        return keyboard;
    }

    // Savat klaviaturasi
    public InlineKeyboardMarkup getCartKeyboard() {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton checkoutBtn = new InlineKeyboardButton();
        checkoutBtn.setText("💳 To'lov qilish");
        checkoutBtn.setCallbackData("CHECKOUT");

        InlineKeyboardButton clearBtn = new InlineKeyboardButton();
        clearBtn.setText("🗑 Tozalash");
        clearBtn.setCallbackData("CLEAR");

        List<List<InlineKeyboardButton>> rows = Arrays.asList(
                Collections.singletonList(checkoutBtn),
                Collections.singletonList(clearBtn)
        );

        keyboard.setKeyboard(rows);
        return keyboard;
    }
}

