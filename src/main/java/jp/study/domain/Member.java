package jp.study.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Member {

        AtomicLong id;

        String loginId;

        String password;

        String name;

        String email;
}