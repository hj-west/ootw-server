package com.responseor.ootw.entity;

import com.responseor.ootw.dto.Role;
import lombok.*;

import javax.persistence.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ootw_member")
public class Member extends BaseEntity{
     /**
      * UUID : UUID
      */
     @Id
     @Column(name = "uuid")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long uuid;

     /**
      * EMAIL : 이메일
      */
     @Column(name = "email", length = 45, unique = true)
     private String email;

     /**
      * PASSWORD : 비밀번호
      */
     @Column(name = "password", length = 100)
     private String password;


     /**
      * TEL_NO : 전화번호
      */
     @Column(name = "tel_no", length = 50)
     private String telNo;


     @Enumerated(EnumType.STRING)
     private Role role;

}
