window.addEventListener("load", () => {
	let btnDelete = document.querySelector("#btn-delete");
	let btnUpdate = document.querySelector("#btn-update");
	let id = parseInt(document.querySelector("#id").value);
	
	if(btnDelete!=null){
	btnDelete.addEventListener("click", () => {
		del();
	})
	}
	if(btnUpdate!=null) {
	btnUpdate.addEventListener("click", () => {
		update();
	})
	}



	function del() {
		fetch('/api/board/'+id, {
			method: 'delete',
		})
			.then(res => {
				alert("삭제가 완료되었습니다");
				location.href = "/"  // redirect
				return res.json();
			}) // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면=> javascript오브젝트로 변경)
			.then(json => console.log(json))
			.catch(error => console.error('Error: ', error));
	}

function update() {
		let data = {
			title: title.value,
			content: content.value,
		}
		fetch('/api/board/'+id, {
			method: 'put',
			headers: {
				'Content-Type': 'application/json; charset=utf-8', // body데이터가 어떤 타입인지(MIME) 
			},
			body: JSON.stringify(data), // http body 데이터
		})
			.then(res => {
				alert("수정이 완료되었습니다");
				location.href = "/board/"+id  // redirect
				return res.json();
			}) // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면=> javascript오브젝트로 변경)
			.then(json => console.log(json))
			.catch(error => console.error('Error: ', error));
	}

})