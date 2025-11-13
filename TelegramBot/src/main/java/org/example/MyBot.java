package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyBot extends TelegramLongPollingBot {

    private final MyBotService service = new MyBotService();

    @Override
    public String getBotUsername() {
        return "@USERNAME";
    }

    @Override
    public String getBotToken() {
        return "BOT TOKEN"; // Bu yerga o'z tokeningni qo'y
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message message = update.getMessage();
                String text = message.getText();
                Long chatId = message.getChatId();

                System.out.println("Message keldi: " + text); // DEBUG

                // Commandalar va tugmalar
                if (text.equals("/start")) {
                    service.handleStart(this, chatId, message.getFrom());
                }
                else if (text.equals("🛍 Buyurtma berish")) {
                    service.handleBuyurtma(this, chatId);
                }
                else if (text.equals("🛒 Buyurtmalarim")) {
                    service.handleSavat(this, chatId);
                }
                else if (text.equals("ℹ️ Bot haqida")) {
                    service.handleBotHaqida(this, chatId);
                }
                else if (text.equals("🤝 Hamkorlik")) {
                    service.handleHamkorlik(this, chatId);
                }
            }
            else if (update.hasCallbackQuery()) {
                CallbackQuery callback = update.getCallbackQuery();
                String data = callback.getData();
                Long chatId = callback.getMessage().getChatId();
                Integer messageId = callback.getMessage().getMessageId();

                System.out.println("Callback keldi: " + data); // DEBUG

                // Callbackni darhol answer qilish KERAK!
                AnswerCallbackQuery answer = new AnswerCallbackQuery();
                answer.setCallbackQueryId(callback.getId());
                execute(answer);

                // Callback handlerlari
                if (data.startsWith("BRAND:")) {
                    String brand = data.substring(6);
                    service.handleBrandSelect(this, chatId, brand);
                }
                else if (data.startsWith("ADD:")) {
                    int productId = Integer.parseInt(data.substring(4));
                    service.handleAddToCart(this, chatId, callback.getId(), productId, messageId);
                }
                else if (data.equals("CHECKOUT")) {
                    service.handleCheckout(this, chatId);
                }
                else if (data.equals("CLEAR")) {
                    service.handleClearCart(this, chatId, callback.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Xatolik: " + e.getMessage());
        }
    }
}
