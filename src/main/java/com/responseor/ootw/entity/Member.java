package com.responseor.ootw.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OOTW_MEMBER")
public class Member extends BaseEntity {
    /**
     * UUID : UUID
     */
    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    /**
     * EMAIL : 이메일
     */
    @Column(name = "EMAIL", length = 45, unique = true)
    private String email;

    /**
     * PASSWORD : 비밀번호
     */
    @Column(name = "PASSWORD", length = 100)
    private String password;


    /**
     * TEL_NO : 전화번호
     */
    @Column(name = "TEL_NO", length = 50)
    private String telNo;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
