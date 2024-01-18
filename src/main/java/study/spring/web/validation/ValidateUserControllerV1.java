package study.spring.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.spring.domain.User;
import study.spring.domain.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/user/v1")
@RequiredArgsConstructor
public class ValidateUserControllerV1 {

    // @RequiredArgsConstructor 로 final이 붙은 필드 생성자 생성, 생성자가 하나면 @Autowired 생략가능
    private final UserRepository userRepository;

    @GetMapping("/add")
    public String addForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "validation/v1/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model){

        // 검증 오류 보관
        Map<String,String> errors = new HashMap<>();

        if(!StringUtils.hasText(user.getName())){
            errors.put("userName","성명을 입력해주세요.");
        }
        if(user.getAge() == null || user.getAge()<1 || user.getAge() > 100){
            errors.put("userAge","나이는 1~100살 사이로 입력해주세요.");
        }

        // 특정 필드가 아닌 복합 룰
        if(!StringUtils.hasText(user.getName()) && user.getAge() == null){
            errors.put("globalError","성명과 나이를 입력해주세요");
        }

        // 검증 실패되면 다시 입력폼으로 보내기
        if(!errors.isEmpty()){
            model.addAttribute("errors",errors);
            log.info("errors={}",errors);
            return "validation/v1/addForm";
        }

        // 성공 로직
        User saveUser = userRepository.save(user);
        redirectAttributes.addAttribute("userId",saveUser.getUserId());
        redirectAttributes.addAttribute("status",true);

        return "redirect:/validation/v1/user/{userId}";
    }

}
