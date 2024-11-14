package com.library.library.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authorization_codes", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorization_code_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(name = "random_code")
    private String randomCode;
}
