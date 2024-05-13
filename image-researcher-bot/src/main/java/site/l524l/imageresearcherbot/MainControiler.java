package site.l524l.imageresearcherbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class MainControiler {
  @Autowired
  TgBot t;
  
  @PostMapping({"/"})
  @ResponseBody
  public Object postRoot(@RequestBody Update update) {
    BotApiMethod<?> result = this.t.onWebhookUpdateReceived(update);
    return (result == null) ? "ok" : result;
  }
  
  @GetMapping({"/"})
  @ResponseBody
  public Object getRoot() {
    return "Application is runnig";
  }
}
