package site.markeep.bookmark.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.markeep.bookmark.util.MailService;
import site.markeep.bookmark.user.dto.request.LoginRequestDTO;
import site.markeep.bookmark.user.dto.response.LoginResponseDTO;
import site.markeep.bookmark.user.service.UserService;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {


    private final UserService userService;

    private final MailService mailService;

    @GetMapping("/login")
    public ResponseEntity<?> login(LoginRequestDTO dto){

        try {
            LoginResponseDTO responseDTO = userService.login(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("관리자의 이메일로 문의해주세요!");
        }

    }

    // 이메일 중복 확인 요청 처리
    // GET: /user/check?email=zzzz@xxx.com
    @GetMapping("/check")
    public ResponseEntity<?> check(String email) {
        //이메일을 입력하지 않은 경우 빈 문자열 반환-400 오류
        if(email.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("");
        }
        log.info("{} 중복?? - {}", email, userService.isDuplicate(email));
        // 400 오류
        if(!userService.isDuplicate(email)) {
            return ResponseEntity.badRequest()
                    .body("이미 가입된 이메일 입니다.");
        }
        //인증번호 반환 : - 200 ok
        return ResponseEntity.ok().body(mailService.sendMail(email));
    }

}
