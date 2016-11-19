package cdv.plugins.telegram;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Mojo for sending Telegram messages
 *
 * @author Dmitry Coolga
 *         19.11.2016 16:35
 */
@Mojo(name = "send-message", threadSafe = true, requiresOnline = true)
public class TelegramNotificationMojo extends AbstractMojo {

    @Parameter(required = true)
    private String botToken;

    @Parameter(required = true)
    private String chatId;

    @Parameter(required = true)
    private String text;

    public void execute() {
        ClientBuilder.newClient()
                .target("https://api.telegram.org")
                .path("bot" + botToken)
                .path("sendMessage")
                .queryParam("chat_id", chatId)
                .queryParam("text", text)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
    }

}
