package site.l524l.imageresearcherbot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import site.l524l.imageresearcherbot.TgBot;

@Controller
public class MainControiler {
  @Autowired
  TgBot t;
  
  @PostMapping({"/"})
  @ResponseBody
  public Object getBook(@RequestBody Update update) {
    BotApiMethod<?> result = this.t.onWebhookUpdateReceived(update);
    return (result == null) ? "ok" : result;
  }
  
  @GetMapping({"/"})
  @ResponseBody
  public Object getBook() {
    try {
      Files.createFile((new File("/opt/tomcat/webapps/temp/f" + UUID.randomUUID() + ".mp4")).toPath(), (FileAttribute<?>[])new FileAttribute[0]);
    } catch (IOException e) {
      return e.toString();
    } 
    return "ok";
  }
  
  @GetMapping({"/test"})
  public Object getTest() {

	    return "ok";
  }  
}
