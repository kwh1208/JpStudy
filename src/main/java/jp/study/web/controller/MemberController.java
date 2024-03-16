package jp.study.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jp.study.domain.Member;
import jp.study.web.repository.MemberRepository;
import jp.study.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/join")
    public String join(){
        return "/member/join";
    }

    @PostMapping("/join")
    public String join_post(@ModelAttribute @Validated Member member, BindingResult bindingResult, HttpServletResponse response, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/member/join";
        }

        AtomicInteger result = memberRepository.join(member);

        if (result.get()==0){
            redirectAttributes.addAttribute("error", "회원가입에 실패했습니다. 잠시후에 다시 시도해주세요");
            redirectAttributes.addAttribute("member", member);
            return "/member/join";
        }

        //성공하면 로그인
        makeLoginCookie(member.getLoginId(), response, new AtomicInteger(1800));

        return "redirect:main/home";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @PostMapping("/login")
    public String login_post(@ModelAttribute @Validated Member member, BindingResult bindingResult, HttpServletResponse response, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/login";
        }

        AtomicBoolean result = memberService.login(member);


        //로그인 성공
        if (result.get()){
            makeLoginCookie(member.getLoginId(), response, new AtomicInteger(1800));

            return "redirect:main/home";
        }

        //로그인 실패
        redirectAttributes.addAttribute("error", "아이디와 비밀번호를 확인해주세요");
        return "member/login";
    }

    @PostMapping("/logout")
    public String logout(@CookieValue String loginId, HttpServletResponse response){

        makeLoginCookie(loginId, response, new AtomicInteger(0));

        return "redirect:main/home";
    }



    @GetMapping("/findId")
    public String findId(){
        return "member/findId";
    }



    @PostMapping("/findId")
    public String findId_post(@ModelAttribute @Validated Member member, RedirectAttributes redirectAttributes){

        String result = memberService.findId(member);

        if (result==null){
            redirectAttributes.addAttribute("error", "회원정보가 없습니다. 다시 확인해주세요.");
            return "member/findId";
        }

        return "member/findId_result";
    }



    @GetMapping("/findPassword")
    public String findPassword(){
        return "member/findPassword";
    }



    @PostMapping("/findPassword")
    public String findPassword_post(@ModelAttribute @Validated Member member, RedirectAttributes redirectAttributes){

        String result = memberService.findPassword(member);

        if (result==null){
            redirectAttributes.addAttribute("error", "회원정보가 없습니다. 다시 확인해주세요.");
            return "member/findPassword";
        }

        return "member/findPassword_result";
    }


    private static void makeLoginCookie(String loginId, HttpServletResponse response, AtomicInteger time) {
        Cookie loginCookie = new Cookie("loginId", loginId);
        loginCookie.setDomain("localhost");
        loginCookie.setPath("/");
        loginCookie.setMaxAge(time.get());
        response.addCookie(loginCookie);
    }

}