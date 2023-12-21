package com.example.demo.member.service;

import com.example.demo.member.model.Member;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService{
    private MemberRepository memberRepository;
    private HttpServletRequest httpServletRequest;
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, HttpServletRequest httpServletRequest,PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.httpServletRequest = httpServletRequest;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(String username, String password) {
        if(!memberRepository.findByUsername(username).isPresent()) {
            memberRepository.save(Member.builder()
                            .username(username)
                            .password(passwordEncoder.encode(password))
                            .authority("ROLE_USER")
                    .build());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUsername(username);
        Member member = null;
        if(result.isPresent()) {
            member = result.get();
            httpServletRequest.getSession().setAttribute("isLogined", true);
            // 위에 코드는 알아서 해주기 때문에 사용 안해도 됨
        }

        return member;
    }
}
