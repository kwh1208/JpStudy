package jp.study.web.service;

import jp.study.domain.Member;
import jp.study.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public AtomicBoolean login(Member member) {
        AtomicBoolean result = new AtomicBoolean(false);
        Member findMember = memberRepository.findById(member);

        if (findMember.getPassword().equals(member.getPassword())){
            result.set(true);
            return result;
        }

        return result;
    }

    public String findId(Member member){
        Member result = memberRepository.findId(member);

        if (result!=null){
            return result.getLoginId();
        }

        return null;
    }

    public String findPassword(Member member){
        Member result = memberRepository.findPassword(member);

        if (result!=null){
            return result.getPassword();
        }

        return null;
    }
}
