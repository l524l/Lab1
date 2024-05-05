package site.l524l.imageresearcherbot;


import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TgBot extends TelegramWebhookBot {
  public TgBot() {
   
  }
  
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
	java.io.File f = null;
    if (update.getMessage().hasVideo()) {
      try {
        String name = "/opt/tomcat/webapps/temp/f" + UUID.randomUUID() + ".mp4";
        String p = ((File)execute((BotApiMethod)GetFile.builder().fileId(update.getMessage().getVideo().getFileId()).build())).getFilePath();
        f = new java.io.File(name);
        downloadFile(p, f);
        if (!f.exists())
          Files.createFile(f.toPath(), (FileAttribute<?>[])new FileAttribute[0]); 
        SendVideoNote n = SendVideoNote.builder().chatId(update.getMessage().getChatId()).videoNote(new InputFile(f)).build();
        execute(n);
        Files.deleteIfExists(f.toPath());
      } catch (TelegramApiException|java.io.IOException e) {
        SendMessage m = SendMessage.builder().chatId(update.getMessage().getChatId()).text("").build();
        return (BotApiMethod<?>)m;
      } 
      return null;
    } 
    return null;
  }
  
  public String getBotPath() {
    return "https://89.19.209.199:8443/bot/";
  }
  
  public String getBotUsername() {
    return "ImageResearcherBot";
  }
  
  public String getBotToken() {
    return "token";
  }
}
