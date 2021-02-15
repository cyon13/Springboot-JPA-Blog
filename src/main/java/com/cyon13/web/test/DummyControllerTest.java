package com.cyon13.web.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyon13.web.model.RoleType;
import com.cyon13.web.model.User;
import com.cyon13.web.repository.UserRepository;




// html파일이 아니라 data를 리턴해주는 컨트롤러 
@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;

	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable); // page에 관한 여러 정보들을 리턴
		List<User> users = pagingUser.getContent(); // 컨텐츠만 리턴
		
		//if(pagingUser.isFirst())
		
		return users;
	// /dummy/user?page=0; = 첫페이지
	// /dummy/user?page=1; = 두번째페이지
		
	}
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4를 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
		// 그럼 return null이 리턴이 되잖아.. 그럼 프로그램에 문제가 있지 않겠니?
		// Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!
		
		//User user = userRepository.findById(id).get(); // 절대 null일 경우가 없을 때 사용
		//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() { // 만약 null이면 밑에 로직을 실행해줘
		//
		//			@Override
		//			public User get() {
		//				// TODO Auto-generated method stub
		//				return new User();
		//			}
		//		});
		
		// 람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 없습니다. id:"+id);
//		});
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id:"+id);
			}
		});
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환(웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user;
		
		
		
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username:"+user.getUsername());
		System.out.println("username:"+user.getPassword());
		System.out.println("username:"+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}
		
}
