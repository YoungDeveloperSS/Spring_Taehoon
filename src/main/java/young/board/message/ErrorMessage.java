package young.board.message;

public abstract class ErrorMessage {
    public static final String PARAM_FORM_ERROR = "파라미터가 제대로 입력되지 않았습니다.";
    public static final String NOT_EXIST_POST_ERROR = "삭제되었거나 존재하지 않는 게시글입니다.";
    public static final String NOT_EXIST_USER_ERROR = "삭제되었거나 존재하지 않는 사용자입니다.";

    public static final String TITLE_NOT_INPUT_ERROR = "제목을 입력해주세요.";
    public static final String TITLE_LENGTH_ERROR = "제목은 1글자 이상 20글자 이하여야 합니다.";
    public static final String WRITER_NOT_INPUT_ERROR = "작성자를 입력해주세요.";
    public static final String WRITER_LENGTH_ERROR = "작성자의 이름은 1글자 이상 8글자 이하여야 합니다.";
    public static final String CONTENT_NOT_INPUT_ERROR = "내용을 입력해주세요.";
    public static final String CATEGORY_NOT_INPUT_ERROR = "카테고리를 입력해주세요.";

    public static final String ALREADY_CLICKED_DISLIKE_ERROR = "이미 싫어요를 누른 게시물입니다.";
    public static final String ALREADY_CLICKED_LIKE_ERROR = "이미 좋아요를 누른 게시물입니다.";
}
