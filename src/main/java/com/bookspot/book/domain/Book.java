package com.bookspot.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Batch서버에서 책 추가는 하지만 삭제는 하지 않는다.
 * 만약 책을 삭제를 한다면 User의 bag_size를 관리하며 삭제해야 한다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn13;
    
    private Integer subjectCode; // 005.115
    private String author;
    private Integer publicationYear;
    private String publisher;
    private int loanCount;
    private int monthlyLoanIncrease;
}
