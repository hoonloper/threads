package threads.server.domain.like;

import lombok.Getter;

@Getter
public enum LikeType {
    POST("스레드"),
    COMMENT("답글"),
    REPLY("답글답글");

    private final String label;

    LikeType(String label) {
        this.label = label;
    }
}
