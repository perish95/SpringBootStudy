package com.example.jpa;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex07_jpql.Department;
import com.example.jpa.ex07_jpql.Employee;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@SpringBootTest
class Ex07JpqlTests {

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
	@DisplayName("반환 타입이 Query인 JPQL")
	void queryTest() {
		// 부서 및 사원 등록
		Department dept = new Department("Develop");
		Employee emp1 = new Employee("jessica", 5000);
		Employee emp2 = new Employee("tom", 6000);
		// 부서에 사원 정보 등록하기
		dept.getEmployees().add(emp1);
		dept.getEmployees().add(emp2);
		// 사원에 부서 정보 등록하기
		emp1.setDepartment(dept);
		emp2.setDepartment(dept);
		// 영속화
		em.persist(dept);
		em.persist(emp1);
		em.persist(emp2);
		// 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
		em.flush();
		// DB로부터 조회하기 위해서 영속화된 엔티티를 준영속 상태로 변경 (JPQL은 필요하지 않음)
		em.clear();

		Query query = em.createQuery("select e.name, e.salary from Employee e");
		List<Object[]> results = query.getResultList();
		results.stream().forEach(obj -> {
			Object[] row = (Object[]) obj;
			System.out.println("이름: " + row[0] + ", 급여: " + row[1]);
		});
	}

	@Test
	@DisplayName("반환 타입이 TypedQuery인 JPQL")
	void typedQueryTest() {
		TypedQuery<Employee> query = em.createQuery("select e from Employee e", Employee.class);
		List<Employee> employees = query.getResultList();
		employees.stream().forEach(emp -> System.out.println("Name: " + emp.getName()));
	}

	@Test
	@DisplayName("N + 1 문제 JPQL")
	void nPlusOneTest() {
		// 부서 및 사원 등록
		Department dept = new Department("Develop");
		Employee emp1 = new Employee("jessica", 5000);
		Employee emp2 = new Employee("tom", 6000);
		// 부서에 사원 정보 등록하기
		dept.getEmployees().add(emp1);
		dept.getEmployees().add(emp2);
		// 사원에 부서 정보 등록하기
		emp1.setDepartment(dept);
		emp2.setDepartment(dept);
		// 영속화
		em.persist(dept);
		em.persist(emp1);
		em.persist(emp2);
		// 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
		em.flush();
		// DB로부터 조회하기 위해서 영속화된 엔티티를 준영속 상태로 변경 (JPQL은 필요하지 않음)
		em.clear();

		// 사원 조회 쿼리(1)
		TypedQuery<Employee> query = em.createQuery("select e from Employee e", Employee.class);
		List<Employee> employees = query.getResultList();

		// 사원마다 부서를 조회하는 쿼리(N)
		for (Employee emp : employees) {
			System.out.println("Department Name: " + emp.getDepartment().getDeptName());
		}
	}

	@Test
	@DisplayName("N + 1 문제 해결 JPQL")
	void fetchJoinTest() {
		// 부서 및 사원 등록
		Department dept = new Department("Develop");
		Employee emp1 = new Employee("jessica", 5000);
		Employee emp2 = new Employee("tom", 6000);
		// 부서에 사원 정보 등록하기
		dept.getEmployees().add(emp1);
		dept.getEmployees().add(emp2);
		// 사원에 부서 정보 등록하기
		emp1.setDepartment(dept);
		emp2.setDepartment(dept);
		// 영속화
		em.persist(dept);
		em.persist(emp1);
		em.persist(emp2);
		// 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
		em.flush();
		// DB로부터 조회하기 위해서 영속화된 엔티티를 준영속 상태로 변경 (JPQL은 필요하지 않음)
		em.clear();

		// Fetch Join으로 한 번에 조회해 오기
		String jpql = "select e from Employee e join fetch e.department";

		List<Employee> employees = em.createQuery(jpql, Employee.class).getResultList();
		for (Employee emp : employees) {
			System.out.println("Department Name: " + emp.getDepartment().getDeptName());
		}
	}
}