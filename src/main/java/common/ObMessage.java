package common;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class ObMessage {
    public ObMessage(MessageType type, Object content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }

    private MessageType type;
    private Object content;
}
