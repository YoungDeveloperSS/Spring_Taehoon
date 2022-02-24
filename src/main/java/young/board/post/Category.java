package young.board.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Category {
    QUESTION("질문"), CHAT("잡담"), NOTICE("공지사항"); //질문, 잡담, 공지사항

    private final String category;
}