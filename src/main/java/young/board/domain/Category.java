package young.board.domain;

import lombok.Getter;

@Getter
public enum Category {
    QUESTION("질문"), CHAT("잡담"), NOTICE("공지사항"); //질문, 잡담, 공지사항

    private String category;

    Category(String category) {
        this.category = category;
    }
}