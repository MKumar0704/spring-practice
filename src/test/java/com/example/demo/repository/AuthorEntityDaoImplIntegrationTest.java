//package com.example.demo.repository;
//
//import com.example.demo.TestDataUtil;
//import com.example.demo.domain.entity.AuthorEntity;
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
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class AuthorEntityDaoImplIntegrationTest {
//
//
//    private AuthorRepository underTest;
//
//    @Autowired
//    public AuthorEntityDaoImplIntegrationTest(AuthorRepository underTest){
//        this.underTest=underTest;
//    }
//
//    @Test
//    public void testThatAuthorCanBeCreatedAndRecalled(){
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
//        underTest.save(authorEntity);
//        Optional<AuthorEntity> finded=underTest.findById(authorEntity.getId());
//        assertThat(finded).isPresent();
//        assertThat(finded.get()).isEqualTo(authorEntity);
//    }
//
//    @Test
//    public void testThatMultipleAuthorCanBeCreatedAndRecalled(){
//        AuthorEntity authorEntityA =TestDataUtil.createTestAuthorA();
//        underTest.save(authorEntityA);
//        AuthorEntity authorEntityB =TestDataUtil.createTestAuthorB();
//        underTest.save(authorEntityB);
//        AuthorEntity authorEntityC =TestDataUtil.createTestAuthorC();
//        underTest.save(authorEntityC);
//        Iterable<AuthorEntity> result=underTest.findAll();
//        assertThat(result).hasSize(3)
//                          .containsExactly(authorEntityA, authorEntityB, authorEntityC);
//    }
//
//    @Test
//    public void testThatAuthorCanBeUpdated(){
//        AuthorEntity authorEntity =TestDataUtil.createTestAuthorB();
//        underTest.save(authorEntity);
//        authorEntity.setName("UPDATED");
//        underTest.save(authorEntity);
//        Optional<AuthorEntity> result= underTest.findById(authorEntity.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(authorEntity);
//    }
//
//    @Test
//    public void testThatAuthorCanBeDeleted(){
//        AuthorEntity authorEntity =TestDataUtil.createTestAuthorA();
//        underTest.save(authorEntity);
//        underTest.deleteById(authorEntity.getId());
//        Optional<AuthorEntity> result=underTest.findById(authorEntity.getId());
//        assertThat(result).isEmpty();
//    }
//
//
//    @Test
//    public void testThatGetAuthorWithAgeLessThan(){
//        AuthorEntity authorEntity =TestDataUtil.createTestAuthorA();
//        underTest.save(authorEntity);
//        AuthorEntity authorEntityB =TestDataUtil.createTestAuthorB();
//        underTest.save(authorEntityB);
//        AuthorEntity authorEntityC =TestDataUtil.createTestAuthorC();
//        underTest.save(authorEntityC);
//        Iterable<AuthorEntity> finded=underTest.ageLessThan(32);
//        assertThat(finded).containsExactly(authorEntityB, authorEntityC);
//    }
//    @Test
//    public void testThatGetThatAuthorWithAgeGreaterThan(){
//        AuthorEntity authorEntity =TestDataUtil.createTestAuthorA();
//        underTest.save(authorEntity);
//        AuthorEntity authorEntityB =TestDataUtil.createTestAuthorB();
//        underTest.save(authorEntityB);
//        AuthorEntity authorEntityC =TestDataUtil.createTestAuthorC();
//        underTest.save(authorEntityC);
//
//        Iterable<AuthorEntity> finded=underTest.findTheAuthorAgeGreaterThan(32);
//        assertThat(finded).containsExactly(authorEntity);
//    }
//}
