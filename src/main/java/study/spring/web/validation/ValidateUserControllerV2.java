package study.spring.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.spring.domain.User;
import study.spring.domain.UserRepository;
import study.spring.web.validator.userValidator;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/user/v2")
@RequiredArgsConstructor
public class ValidateUserControllerV2 {

    // @RequiredArgsConstructor 로 final이 붙은 필드 생성자 생성, 생성자가 하나면 @Autowired 생략가능
    private final UserRepository userRepository;
    private final userValidator validator;

    @InitBinder
    public void init(WebDataBinder dataBinder){ // 이 컨트롤러가 호출될때마다 검증기가 작동한다.
        dataBinder.addValidators(validator);
    }

    @GetMapping("/add")
    public String addForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "validation/v2/addForm";
    }
    //@PostMapping("/add")
    public String validationV1(@ModelAttribute User user, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,Model model){
        log.info("V2진입");
        if(!StringUtils.hasText(user.getName())){
            bindingResult.addError(new FieldError("user","name","성명을 입력해주세요."));
        }
        if(user.getAge() == null || user.getAge() < 1 || user.getAge() >100){
            bindingResult.addError(new FieldError("user","age","나이는 1~100살 사이로 입력해주세요."));
        }

        // 특정 필드가 아닌 복합룰
        // -> new FieldError가 아닌 new ObjectError를 쓴다.
        if(!StringUtils.hasText(user.getName()) && user.getAge() == null){
            // ObjectName, Default Message
            bindingResult.addError(new ObjectError("user","성명과 나이를 입력해주세요."));
        }

        // 검증 실패시 다시 addForm 으로
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        User saveUser = userRepository.save(user);
        redirectAttributes.addAttribute("userId",saveUser.getUserId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/validation/v2/users/{userId}";

    }
    //@PostMapping("/add")
    public String validationV2(@ModelAttribute User user, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,Model model){
        if(!StringUtils.hasText(user.getName())){
            bindingResult.addError(new FieldError("user","name",user.getName(),false,new String[]{"required.user.name"},null,null));
        }
        if(user.getAge() == null || user.getAge() < 1 || user.getAge() >100){
            bindingResult.addError(new FieldError("user","age",user.getAge(),false,new String[]{"range.user.age"},new Object[]{1,100},null));
        }

        // 특정 필드가 아닌 복합룰
        // -> new FieldError가 아닌 new ObjectError를 쓴다.
        if(!StringUtils.hasText(user.getName()) && user.getAge() == null){
            // ObjectName, Default Message
            bindingResult.addError(new ObjectError("user",new String[]{"required.user.nameAge"},null,null));
        }

        // 검증 실패시 다시 addForm 으로
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        User saveUser = userRepository.save(user);
        redirectAttributes.addAttribute("userId",saveUser.getUserId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/validation/v2/users/{userId}";

    }

    //@PostMapping("/add")
    public String validationV3(@ModelAttribute User user, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,Model model){
        if(!StringUtils.hasText(user.getName())){
           bindingResult.rejectValue("name","required.user.name");
        }
        if(user.getAge() == null || user.getAge() < 1 || user.getAge() >100){
            bindingResult.rejectValue("age","range.user.age",new Object[]{1,100},null);
        }

        // 특정 필드가 아닌 복합룰
        // -> new FieldError가 아닌 new ObjectError를 쓴다.
        if(!StringUtils.hasText(user.getName()) && user.getAge() == null){
            // ObjectName, Default Message
            bindingResult.reject("required.user.nameAge");
        }

        // 검증 실패시 다시 addForm 으로
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        User saveUser = userRepository.save(user);
        redirectAttributes.addAttribute("userId",saveUser.getUserId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/validation/v2/users/{userId}";

    }

    //@PostMapping("/add")
    public String validationV4(@ModelAttribute User user, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,Model model){

        // 검증하는 부분을 검증 클래스 userValidator에 작성하였다.
        if(validator.supports(User.class)){
            validator.validate(user,bindingResult);
        }

        // 검증 실패시 다시 addForm 으로
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        User saveUser = userRepository.save(user);
        redirectAttributes.addAttribute("userId",saveUser.getUserId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/validation/v2/users/{userId}";

    }
    @PostMapping("/add")
    public String validationV5(@Validated @ModelAttribute User user, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Model model){
        // @Validated를 쓰므로써 검증기가 알아서 검증을 돌려주고 에러들을 bindingResult에 담아놓는다.
        // @Validated를 안썻을시 검증하는 부분을 검증 클래스 userValidator에 작성하였고, 그걸 호출해주었다.
        /*if(validator.supports(User.class)){
            validator.validate(user,bindingResult);
        }*/

        // 검증 실패시 다시 addForm 으로
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        User saveUser = userRepository.save(user);
        redirectAttributes.addAttribute("userId",saveUser.getUserId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/validation/v2/users/{userId}";

    }
}
