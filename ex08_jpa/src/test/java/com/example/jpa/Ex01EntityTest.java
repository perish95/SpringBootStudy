package com.example.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex01_entity.Product;
import com.example.jpa.ex01_entity.Role;
import com.example.jpa.ex01_entity.User;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex01EntityTest {

	// 엔티티 매니저 (영속성 컨텍스트 관리자)
	private EntityManager em;
	// 엔티티 트랜잭션
	private EntityTransaction tx;

	// 테스트 시작 전 엔티티 매니저를 만들기 위해 팩토리(공장)부터 지어둠
	@BeforeAll
	static void setUpBeforeClass() {
		JpaUtil.initFactory();
	}

	// 각 테스트 시작 전 엔티티 매니저를 생성
	@BeforeEach
	void setUp() {
		em = JpaUtil.getEntityManager();
		tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜잭션 내부에서 실행되어야 함
		tx.begin();
	}

	// 각 테스트 종료 후 엔티티 메니저를 닫아줌
	@AfterEach
	void tearDown() {
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}
		if (em != null && em.isOpen()) {
			em.close();
		}

	}

	// 전체 테스트 종료 후 엔티티 매니저 팩토리를 닫아줌
	@AfterAll
	static void tearDownAfterClass() {
		JpaUtil.closeFactory();
	}

	// 이제부터 테스트 진행
	@Test
	@DisplayName("기본 엔티티 저장 및 확인")
	void testEntityMapping() {
		// 새 엔티티 생성(엔티티 매니저가 관리하는 엔티티가 아님)
		Product product = new Product("P001", "노트북", 150000, "가볍고 높은 성능");

		// 엔티티 매니저가 엔티티를 관리함
		// 세부 핵심 특징: 관리되는 엔티티는 반드시 ID를 가지고 있어야 한다.
		// AUTO_INCREMENT전략을 사용하는 경우 persist() 호출 즉시 INSERT쿼리를 DB로 날려 ID를 받아온 뒤 영속성
		// 컨텍스트에 저장한다.
		em.persist(product);

		// ID확인, null 체크
		assertNotNull(product.getId());
		System.out.println("생성된 Product ID: " + product.getId());
	}

	@Test
	@DisplayName("PK의 테이블 생성 전략 확인")
	void entityTest() {
		User user = new User("홍길동", "ㄴㄷㄹㄴㄷㄹ", Role.ADMIN);
		em.persist(user);
		System.out.println("USERUSER" + user);
		tx.commit(); //INSERT로 DB로 날리기, 원래 persist는 날리는 코드가 아님
	}
}