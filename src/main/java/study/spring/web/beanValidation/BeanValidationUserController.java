package study.spring.web.beanValidation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.spring.domain.User;
import study.spring.domain.UserEditForm;
import study.spring.domain.UserRepository;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/beanValidation/user")
@RequiredArgsConstructor
public class BeanValidationUserController {

    private UserRepository userRepository;

    @GetMapping("/add")
    public String addForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "beanValidation/addForm";
    }

    @PostMapping("/add")
    public String validationV5(@Validated @ModelAttribute User user, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Model model){

        // 검증 실패시 다시 addForm 으로
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "beanValidation/addForm";
        }

        // 성공 로직
        User saveUser = userRepository.save(user);
        redirectAttributes.addAttribute("userId",saveUser.getUserId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/beanValidation/users/{userId}";

    }
    @GetMapping("/list")
    public String listForm(){
        List<User> list = userRepository.findAll();
        log.info("list={}",list);
        return "";
    }

    @GetMapping ("/edit/{userId}")
    public String editForm(@PathVariable Long userId, Model model){

        User user = userRepository.findById(userId);
        model.addAttribute("user",user);
        return "beanValidation/editForm";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user,
                           @ModelAttribute("user")UserEditForm editForm,
                           BindingResult bindingResult){
        // 검증
//        if(bindingResult.hasErrors()){
//            log.info("errors={}",bindingResult);
//            return "redirect:/beanValidation/editForm";
//        }
//
//        UserEditForm userParam = new UserEditForm();
//        userParam.setUserId(user.getUserId());
//        userParam.setName(user.getName());
//        userParam.setAge(user.getAge());
//
//        userRepository.save(User);
        return "";
    }

}
