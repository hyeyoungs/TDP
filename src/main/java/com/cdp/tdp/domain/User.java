package com.cdp.tdp.domain;
import com.cdp.tdp.dto.TilRequestDto;
import com.cdp.tdp.dto.UserDto;
import com.cdp.tdp.dto.UserUpdateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자 만든다.
@Entity // DB 테이블 역할
public class User extends Timestamped {

    public User(String signId, String signPassword, String nickname, String githubId, String introduce,String picture,String picture_real) {
        this.username = signId;
        this.password = signPassword;
        this.nickname = nickname;
        this.github_id = githubId;
        this.introduce = introduce;
        this.picture=picture;
        this.picture_real=picture_real; // s3이미지 주소
        this.kakaoId=null;

    }

    public User(String signId, String signPassword, String nickname, String githubId, String introduce,String picture,String picture_real,Long kakaoId) {
        this.username = signId;
        this.password = signPassword;
        this.nickname = nickname;
        this.github_id = githubId;
        this.introduce = introduce;
        this.picture=picture;
        this.picture_real=picture_real; // s3이미지 주소
        this.kakaoId=kakaoId;

    }

    public User(String signId, String signPassword, String nickname, Long kakaoId) {
        this.username = signId;
        this.password = signPassword;
        this.nickname = nickname;
        this.github_id = null;
        this.introduce = null;
        this.picture= null;
        this.picture_real= null; // s3이미지 주소
        this.kakaoId=kakaoId;

    }
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String github_id;

    private String introduce;

    private String picture;

    private String picture_real;

    @Column(nullable = true)
    private Long kakaoId;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Til> til_list;


    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Comment> comments;

//    @ManyToMany
//    @JoinTable(
//            name = "user_authority",
//            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
//    private Set<Authority> authorities;

    public void updateUser(UserUpdateDto userUpdateDto){

        this.nickname = userUpdateDto.getNickname();
        this.github_id = userUpdateDto.getGithub_id();
        this.introduce = userUpdateDto.getIntroduce();
        this.picture= userUpdateDto.getPicture();


    }


}
