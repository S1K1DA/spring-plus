# CH 5 플러스 Spring 과제

> # [Spring 2기] CH 5 플러스 Spring 과제

### 스파르타 내일배움캠프 Spring 백엔드 2기

<img width="105" height="141" alt="image" src="https://github.com/user-attachments/assets/9d536a94-7922-4fc2-8893-d2ae6602a8d7" />


### 2조 정은식
---

## 폴더 구조

<img width="398" height="1293" alt="image" src="https://github.com/user-attachments/assets/3f36ffd4-3c52-41c8-980f-765d9a514ee3" />


---

# ✅ 필수 기능

## [LV1-1. 코드 개선 퀴즈 - @Transactional의 이해]

- 할 일 저장 기능을 구현한 API(/todos)를 호출할 때, 아래와 같은 에러가 발생하고 있어요.
<img width="2048" height="137" alt="image" src="https://github.com/user-attachments/assets/66a1dfcd-774a-4309-993e-7935d55a0ef2" />

- 에러가 발생하지 않고 정상적으로 할 일을 저장 할 수 있도록 코드를 수정해주세요.

<img width="2285" height="1133" alt="image" src="https://github.com/user-attachments/assets/8be0d488-b398-47a4-969d-e8842ae0f89c" />


---

## [LV1-2. 코드 추가 퀴즈 - JWT의 이해]

<aside>
🚨 기획자의 긴급 요청이 왔어요!
아래의 요구사항에 맞춰 기획 요건에 대응할 수 있는 코드를 작성해주세요.

</aside>

- User의 정보에 nickname이 필요해졌어요.
    - User 테이블에 nickname 컬럼을 추가해주세요.
    - nickname은 중복 가능합니다.
- 프론트엔드 개발자가 JWT에서 유저의 닉네임을 꺼내 화면에 보여주길 원하고 있어요.

<img width="2399" height="1232" alt="image" src="https://github.com/user-attachments/assets/aa2f3c19-be7e-4281-84f5-f3d67e99664a" />
<img width="705" height="375" alt="image" src="https://github.com/user-attachments/assets/4b059d09-9a3a-4e8e-ad95-8f1c50dce819" />



---

## [LV1-3. 코드 개선 퀴즈 - JPA의 이해]

<aside>
🚨 기획자의 긴급 요청이 왔어요!
아래의 요구사항에 맞춰 기획 요건에 대응할 수 있는 코드를 작성해주세요.

</aside>

- 할 일 검색 시 `weather` 조건으로도 검색할 수 있어야해요.
    - `weather` 조건은 있을 수도 있고, 없을 수도 있어요!
- 할 일 검색 시 수정일 기준으로 기간 검색이 가능해야해요.
    - 기간의 시작과 끝 조건은 있을 수도 있고, 없을 수도 있어요!
- JPQL을 사용하고, 쿼리 메소드명은 자유롭게 지정하되 너무 길지 않게 해주세요.

- 데이터<img width="1483" height="260" alt="image" src="https://github.com/user-attachments/assets/e2138d05-839c-4d7c-a5db-c584c1d5148a" />

- 전체조회<img width="2432" height="1399" alt="image" src="https://github.com/user-attachments/assets/87738fca-5729-4c14-9938-b3f7117c8f4f" />

- 조건조회<img width="2391" height="1238" alt="image" src="https://github.com/user-attachments/assets/3f41a50f-dd9b-43f2-919a-d6627617cba5" />


---


## [LV1-4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해]

<img width="1836" height="248" alt="image" src="https://github.com/user-attachments/assets/c4fbfee9-8151-47ce-bd74-7516ad6d14a3" />

- 테스트 패키지 org.example.expert.domain.todo.controller의 todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다() 테스트가 실패하고 있어요.
- 테스트가 정상적으로 수행되어 통과할 수 있도록 테스트 코드를 수정해주세요.

<img width="2503" height="1442" alt="image" src="https://github.com/user-attachments/assets/a8537acd-fcdf-46f0-89c8-e3eab3a90e00" />

---


## [LV1-5. 코드 개선 퀴즈 - AOP의 이해]

- `UserAdminController` 클래스의 `changeUserRole()` 메소드가 실행 전 동작해야해요.
- `AdminAccessLoggingAspect` 클래스에 있는 AOP가 개발 의도에 맞도록 코드를 수정해주세요.
  
---


## [LV2-1. JPA Cascade]

<img width="1634" height="1732" alt="image" src="https://github.com/user-attachments/assets/04cf8565-a87c-4a67-a005-4392c254a14b" />


- 할 일을 새로 저장할 시, 할 일을 생성한 유저는 담당자로 자동 등록되어야 합니다.
- JPA의 `cascade` 기능을 활용해 할 일을 생성한 유저가 담당자로 등록될 수 있게 해주세요.

```java

@Getter
@Entity
@NoArgsConstructor
@Table(name = "todos")
public class Todo extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contents;
    private String weather;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // REMOVE -> 삭제할 떄 comment도 같이 삭제
    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    // PERSIST-> 저장할 때 manager도 같이 저장
    @OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST)
    private List<Manager> managers = new ArrayList<>();

    public Todo(String title, String contents, String weather, User user) {
        this.title = title;
        this.contents = contents;
        this.weather = weather;
        this.user = user;
        this.managers.add(new Manager(user, this));
    }
}

```


---


## [LV2-2. N+1]

- `CommentController` 클래스의 `getComments()` API를 호출할 때 N+1 문제가 발생하고 있어요. N+1 문제란, 데이터베이스 쿼리 성능 저하를 일으키는 대표적인 문제 중 하나로, 특히 연관된 엔티티를 조회할 때 발생해요.
- 해당 문제가 발생하지 않도록 코드를 수정해주세요.


- 댓글 3개 N+1 로그
<img width="490" height="700" alt="image" src="https://github.com/user-attachments/assets/e2145e60-cc1c-4334-ba77-56af230a9795" />

- 코드 수정후 N+1 해결 로그
<img width="495" height="722" alt="image" src="https://github.com/user-attachments/assets/34eb1a25-7ce2-469e-a157-8a0079eade36" />
 


---


## [LV2-3. QueryDSL]

- JPQL로 작성된 `findByIdWithUser` 를 QueryDSL로 변경합니다.
- 7번과 마찬가지로 N+1 문제가 발생하지 않도록 유의해 주세요!

코드 참고

---

## [LV2-4. Spring Security]
- 기존 `Filter`와 `Argument Resolver`를 사용하던 코드들을 Spring Security로 변경해주세요.
    - 접근 권한 및 유저 권한 기능은 그대로 유지해주세요.
    - 권한은 Spring Security의 기능을 사용해주세요.
- 토큰 기반 인증 방식은 유지할 거예요. JWT는 그대로 사용해주세요.

- 유저 회원가입<img width="1901" height="914" alt="image" src="https://github.com/user-attachments/assets/c175aa4f-5347-4e5f-ab3e-60690f3c258e" />

- 관리자 회원가입<img width="1909" height="893" alt="image" src="https://github.com/user-attachments/assets/b7b78549-4a61-4e8c-adba-d04ce2d4faf4" />

- 유저가 권한 변경 시<img width="1926" height="845" alt="image" src="https://github.com/user-attachments/assets/9b63ca58-8c56-4020-8f5c-d89f989a8bef" />

- 관리자가 권한 변경 시<img width="1951" height="813" alt="image" src="https://github.com/user-attachments/assets/bf90b95f-4604-4eae-a4ea-d753d429c5f7" />


---

# ✅ 도전 기능

## [LV3-1. QueryDSL을 사용하여 검색 기능 만들기]

- 새로운 API로 만들어주세요.
- 검색 조건은 다음과 같아요.
    - 검색 키워드로 일정의 제목을 검색할 수 있어요.
        - 제목은 부분적으로 일치해도 검색이 가능해요.
    - 일정의 생성일 범위로 검색할 수 있어요.
        - 일정을 생성일 최신순으로 정렬해주세요.
    - 담당자의 닉네임으로도 검색이 가능해요.
        - 닉네임은 부분적으로 일치해도 검색이 가능해요.
- 다음의 내용을 포함해서 검색 결과를 반환해주세요.
    - 일정에 대한 모든 정보가 아닌, 제목만 넣어주세요.
    - 해당 일정의 담당자 수를 넣어주세요.
    - 해당 일정의 총 댓글 개수를 넣어주세요.
- 검색 결과는 페이징 처리되어 반환되도록 합니다.


- 데이터<img width="1505" height="304" alt="image" src="https://github.com/user-attachments/assets/dded770c-93b9-4401-8859-4e4a0685f809" />

- 키워드 검색<img width="2388" height="1345" alt="image" src="https://github.com/user-attachments/assets/794428d5-99a8-4789-9ff8-0657a69e154a" />

- 닉네임 검색<img width="2042" height="1046" alt="image" src="https://github.com/user-attachments/assets/76212813-b94e-4ff9-9bcc-05f1c5fdf467" />


---

## [LV3-2. Transaction 심화]

- 매니저 등록 요청을 기록하는 로그 테이블을 만들어주세요.
    - DB 테이블명: `log`
- 매니저 등록과는 별개로 로그 테이블에는 항상 요청 로그가 남아야 해요.
    - 매니저 등록은 실패할 수 있지만, 로그는 반드시 저장되어야 합니다.
    - 로그 생성 시간은 반드시 필요합니다.
    - 그 외 로그에 들어가는 내용은 원하는 정보를 자유롭게 넣어주세요.
 

- 성공 케이스<img width="2445" height="1033" alt="image" src="https://github.com/user-attachments/assets/4b53c8d6-7369-458f-bb79-8c7058cbf148" />

- 성공 로그<img width="983" height="180" alt="image" src="https://github.com/user-attachments/assets/b575c4e1-1b37-47d7-b036-ff35cd72448b" />

- 실패 케이스<img width="2415" height="1032" alt="image" src="https://github.com/user-attachments/assets/559caa0c-b130-4ece-a7ce-93fc96c4830e" />

- 최종 로그<img width="1093" height="340" alt="image" src="https://github.com/user-attachments/assets/25b44663-3463-4944-bca6-4798e4aa3120" />







---


### 트러블 슈팅

> ####  

⚠️ 원인


💡 해결 방법


 
---

🔥 그럼 20000 🔥
       
