package site.l524l.imageresearcherbot;


import java.nio.file.Files;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TgBot extends TelegramWebhookBot {
	@Value("${bot.path}")
	private String path;
	@Value("${bot.username}")
	private String username;
	@Value("${bot.token}")
	private String token;
	@Value("${bot.tempFileFolder}")
	private String tempFileFolder;
	
	public TgBot() {
   
  }
  
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
	  String name = tempFileFolder + "f" + UUID.randomUUID() + ".mp4";
	  java.io.File tempFile = new java.io.File(name);
	
	  long chatId = update.getMessage().getChatId();
	  int messageId = update.getMessage().getMessageId();
	  
	  
	  if (update.getMessage().hasVideo()) {
    	Video tgVideo = update.getMessage().getVideo();
    	 try {
	    	if (tgVideo.getDuration() > 60) {
	    		execute(DeleteMessage.builder().chatId(chatId).messageId(messageId).build());
	    		return SendMessage.builder().chatId(chatId).text("Длительность видео привышает 60 секунд").build();
	    	}
	    	if (!tgVideo.getWidth().equals(tgVideo.getHeight()) || tgVideo.getHeight() > 384 || tgVideo.getWidth() > 384) {
	    		execute(DeleteMessage.builder().chatId(chatId).messageId(messageId).build());
	    		return SendMessage.builder().chatId(chatId).text("Видео должно иметь соотношение сторон 1 к 1, и иметь разрешение не больше 384 на 384 пиксилей").build();
	    	}
	    	
    	 	File fileInfo = execute(GetFile.builder().fileId(tgVideo.getFileId()).build());
    		
    		String fileURL = fileInfo.getFilePath();
    		downloadFile(fileURL, tempFile); 
    		
    		execute(SendVideoNote.builder().chatId(chatId).videoNote(new InputFile(tempFile)).build());
    		
    		Files.deleteIfExists(tempFile.toPath());
    		
    		return DeleteMessage.builder().chatId(chatId).messageId(messageId).build();
    	} catch (TelegramApiException|java.io.IOException e) {
    		return SendMessage.builder().chatId(chatId).text("Ошибка на нашей стороне. Попытайтесь повторить запрос позднее").build();
    	} 
	  }
	  
	  return SendMessage.builder().chatId(chatId).text("Не понял Вас) Попробуйте отправить видео").build(); 
  }
  
  public String getBotPath() {
    return path;
  }
  
  public String getBotUsername() {
    return username;
  }
  
  public String getBotToken() {
    return token;
  }
}
