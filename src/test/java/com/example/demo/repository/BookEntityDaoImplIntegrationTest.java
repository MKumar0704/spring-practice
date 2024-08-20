//package com.example.demo.repository;
//
//import com.example.demo.TestDataUtil;
//import com.example.demo.domain.entity.AuthorEntity;
//import com.example.demo.domain.entity.BookEntity;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class BookEntityDaoImplIntegrationTest {
//
//    private BookRepository underTesting;
//
//    private AuthorRepository authorTest;
//
//    @Autowired
//    public BookEntityDaoImplIntegrationTest(BookRepository underTesting, AuthorRepository authorTest){
//        this.underTesting=underTesting;
//        this.authorTest=authorTest;
//    }
//
//    @Test
//    public void testThatBookCanBeCreatedAndRecalled(){
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
//        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
//        underTesting.save(bookEntity);
//        Optional<BookEntity> finded = underTesting.findById(bookEntity.getIsbn());
//        assertThat(finded).isPresent();
//        assertThat(finded.get()).isEqualTo(bookEntity);
//    }
//
//    @Test
//    public void testThatMultipleBookCanBeCreatedAndRecalled(){
//        AuthorEntity authorEntity =TestDataUtil.createTestAuthorA();
//        AuthorEntity authorEntityB =TestDataUtil.createTestAuthorB();
//        AuthorEntity authorEntityC =TestDataUtil.createTestAuthorC();
//        BookEntity bookEntityA =TestDataUtil.createTestBookA(authorEntity);
//        underTesting.save(bookEntityA);
//        BookEntity bookEntityB =TestDataUtil.createTestBookB(authorEntityB);
//        underTesting.save(bookEntityB);
//        BookEntity bookEntityC =TestDataUtil.createTestBookC(authorEntityC);
//        underTesting.save(bookEntityC);
//
//        Iterable<BookEntity> result= underTesting.findAll();
//        assertThat(result).hasSize(3)
//                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
//
//    }
//
//    @Test
//    public void testThatBookCanBeUpdated(){
//        AuthorEntity authorEntity =TestDataUtil.createTestAuthorA();
//        authorTest.save(authorEntity);
//        BookEntity bookEntityA =TestDataUtil.createTestBookA(authorEntity);
//        underTesting.save(bookEntityA);
//        bookEntityA.setTitle("UPDATED");
//        underTesting.save(bookEntityA);
//        Optional<BookEntity> result=underTesting.findById(bookEntityA.getIsbn());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(bookEntityA);
//    }
//
//    @Test
//    public void testThatBookCanBeDeleted(){
//          AuthorEntity authorEntity =TestDataUtil.createTestAuthorA();
//        BookEntity bookEntity =TestDataUtil.createTestBookA(authorEntity);
//        underTesting.save(bookEntity);
//        underTesting.deleteById(bookEntity.getIsbn());
//        Optional<BookEntity> result=underTesting.findById(bookEntity.getIsbn());
//        assertThat(result).isEmpty();
//    }
//}
