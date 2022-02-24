package young.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
	// TODO ResponseEntity에서 created 메서드를 쓰면 url이 필수. 왜일까? 그리고 어떤 url이 들어가야 할까?

}
