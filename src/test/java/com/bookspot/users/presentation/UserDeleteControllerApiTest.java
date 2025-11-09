package com.bookspot.users.presentation;

import com.bookspot.SpringBootWithH2Test;
import com.bookspot.WebSecurityAuthHelper;
import com.bookspot.bag.domain.BagBook;
import com.bookspot.bag.domain.BagBookCreator;
import com.bookspot.bag.domain.BagBookRepository;
import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.shelfbooks.domain.ShelfBook;
import com.bookspot.shelfbooks.domain.ShelfBookRepository;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesManager;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.presentation.dto.request.ShelfCreationRequest;
import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootWithH2Test
@Transactional
class UserDeleteControllerApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BagBookRepository bagBookRepository;
    @Autowired
    private ShelvesRepository shelvesRepository;
    @Autowired
    private ShelfBookRepository shelfBookRepository;

    // 데이터 생성을 위한 의존성
    @Autowired BagBookCreator bagBookCreator;
    @Autowired ShelvesManager shelvesManager;

    private Users deletedUser;
    private Users otherUser;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() throws InterruptedException {
        // 유저 생성
        deletedUser = Users.createUser("123456789", OAuthProvider.GOOGLE, "deletedUser@example.com");
        otherUser = Users.createUser("234567890", OAuthProvider.GOOGLE, "otherUser@example.com");
        usersRepository.saveAll(List.of(deletedUser, otherUser));

        // 책 생성
        book1 = createTestBook("1234567890123");
        book2 = createTestBook("2345678901234");
        bookRepository.saveAll(List.of(book1, book2));

        // 책가방 데이터 생성
        BagBook bagBook1 = bagBookCreator.create(deletedUser, book1);
        BagBook bagBook2 = bagBookCreator.create(deletedUser, book2);
        BagBook otherBagBook = bagBookCreator.create(otherUser, book2);
        bagBookRepository.saveAll(List.of(bagBook1, bagBook2, otherBagBook));

        // 책장 생성
        Shelves filledShelf = shelvesManager.create(
                deletedUser,
                new ShelfCreationRequest("데이터 2개를 넣을 책장", true)
        );
        Shelves emptyShelf = shelvesManager.create(deletedUser, new ShelfCreationRequest("빈 책장", false));
        Shelves otherShelf = shelvesManager.create(otherUser, new ShelfCreationRequest("다른 사용자 책장", true));
        shelvesRepository.saveAll(List.of(filledShelf, emptyShelf,otherShelf));

        // 책장 속 책 생성
        ShelfBook shelfBook1 = new ShelfBook(filledShelf, book1, 1);
        ShelfBook shelfBook2 = new ShelfBook(filledShelf, book2, 2);
        ShelfBook otherShelfBook = new ShelfBook(otherShelf, book2, 2);
        shelfBookRepository.saveAll(List.of(shelfBook1, shelfBook2, otherShelfBook));
    }

    // 관련 데이터 : { 책가방, 책장 - 책장책 }
    @Test
    void 회원탈퇴시_관련_데이터_모두_삭제() throws Exception {
        assertEquals(2, bookRepository.count()); // 책 2개

        assertEquals(2, usersRepository.count()); // 사용자: 탈퇴 사용자 + 일반사용자
        assertEquals(3, bagBookRepository.count()); // 책가방: 탈퇴 사용자(2) + 일반사용자(1)
        assertEquals(3, shelvesRepository.count()); // 사용자 - 탈퇴 사용자(2) + 일반 사용자(1)
        assertEquals(3, shelfBookRepository.count()); // 사용자 - 탈퇴 사용자(2) + 일반 사용자(1)

        mockMvc.perform(
                        WebSecurityAuthHelper.apiWithAuth(
                                delete("/api/users/me"),
                                deletedUser.getId()
                        )
                )
                .andExpect(status().isNoContent());

        // 사용자와 상관없는 책 데이터는 유지
        assertEquals(2, bookRepository.count());
        // 탈퇴 사용자 데이터 모두 제거
        assertEquals(1, usersRepository.count());
        assertTrue(usersRepository.existsById(otherUser.getId()));

        assertEquals(1, bagBookRepository.count());
        assertEquals(1, bagBookRepository.findByUsersId(otherUser.getId()).size());

        assertEquals(1, shelvesRepository.count());
        assertEquals(1, shelvesRepository.findByUsersId(otherUser.getId()).size());
        assertEquals(1, shelfBookRepository.count());
    }

    private Book createTestBook(String isbn13) {
        Book created = BeanUtils.instantiateClass(Book.class);
        ReflectionTestUtils.setField(created, "isbn13", isbn13);
        ReflectionTestUtils.setField(created, "title", "테스트 책 " + isbn13);
        return created;
    }

}