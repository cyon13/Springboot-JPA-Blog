window.addEventListener("load", () => {
	let btnUpdate = document.querySelector("#btn-update");
	let id = document.querySelector("#id");
	let username = document.querySelector("#username");
	let password = document.querySelector("#password");
	let email = document.querySelector("#email");
	btnUpdate.addEventListener("click", () => {
		if (username.value == "" || password.value == "" || email.value == "") {
			alert("양식을 다 채워주세요");
			return false;
			}
		update();
	})

	function update() {
		let data = {
			id : id.value,
			username : username.value,
			password: password.value,
			email: email.value
		}
		console.log(data);
		fetch('/user', {
			method: 'put',
			headers: {
				'Content-Type': 'application/json; charset=utf-8', // body데이터가 어떤 타입인지(MIME) 
			},
			body: JSON.stringify(data), // http body 데이터
		})
			.then(res => {
				alert("회원수정이 완료되었습니다.");
				location.href = "/"  // redirect
				return res.json();
			}) // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면=> javascript오브젝트로 변경)
			.then(json => console.log(json))
			.catch(error => console.error('Error: ', error));
	}

})