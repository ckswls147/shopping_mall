package proj.dev_proj.member;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImp implements MemberService{
    private final MemberRepositoryImpl memberRepositoryImpl = new MemberRepositoryImpl();

    @Override
    public Long join(Member member, String password_check) {
        validateDuplicateMember(member);
        validatePasswordCheck(member.getPassword(), password_check);// 중복회원 검증
        memberRepositoryImpl.save(member);
        return member.getId();
    }

    private void validatePasswordCheck(String password, String password_check) {
        if (!password.equals(password_check)) {
            throw new IllegalStateException("비밀번호가 다릅니다. 1 = " + password + " 2 = " + password_check);
        }
    }
    private void validateDuplicateMember(Member member) {
        memberRepositoryImpl.findByName(member.getUsername())
                .ifPresent(m -> { // 값이 있으면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        memberRepositoryImpl.findByName(member.getNickname())
                .ifPresent(m -> { // 값이 있으면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 회원 아이디 조회
    public Member findOne(Long memberId) {
        return memberRepositoryImpl.findById(memberId);
    }

}