function setCategory(){
    $.ajax({
        url : "/board/category/write",
        method:"POST",
        data:JSON.stringify({
        cname: document.querySelector(".cname").value
        }),
        contentType:"application/json",
        success:(r)=>{
            console.log(r);
            if(r==true){ getCategory() }
        }
    })
}
getCategory();
function getCategory(){

   $.ajax({
        url : "/board/category/list",
        method:"get",
        success:(r)=>{
            console.log(r);

            let html = `<button onclick="selectorCno(0)" type="button"> 전체보기 </button>`;

            for(let cno in r){
                console.log("필드명/키 : " + cno);
                console.log("필드명/키에 저장된 값 : " + r[cno]);
                html += `<button onclick="selectorCno(${cno})" type="button"> ${r[cno]} </button>`;
            }//for end
            document.querySelector(".categorylistbox").innerHTML = html;
        }
    })
}

//3. 카테고리 선택
let selectCno = 0; // 선택한 카테고리 번호 [기본값 = 0]
function selectorCno(cno){
    console.log(cno + " 의 카테고리 선택");
    selectCno = cno;
     getBoard(cno)
}

//4. 게시물 쓰기
function setBoard(){
    if(selectCno == 0 ){
            alert('카테고리를 선택해주세요');
    }

    let info = {
        btitle : document.querySelector(".btitle").value ,
        bcontent : document.querySelector(".bcontent").value ,
        cno : selectCno
    }

    $.ajax({
        url:"/board/write", method : "post",
        data:JSON.stringify(info), contentType: "application/json",
        success: (r)=>{
            console.log(r);
            if(r == 4){alert('글쓰기 성공')
                document.querySelector(".btitle").value  = '';
                document.querySelector(".bcontent").value = '';
                getBoard(selectCno)
            }
        }
    })

}


//5. 게시물 출력
getBoard(0);
function getBoard(cno){
    selectCno = cno;

    $.ajax({
        url:"/board/list", method:"get", data:{"cno":selectCno},
        success:(r)=>{
            console.log(r);
            let html = `<tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회수</th>
                        </tr>`;
            r.forEach((o)=>{
                html += `<tr>
                            <td>${o.bno}</td>
                            <td onclick="s_board(${o.bno})" style="cursor:pointer;">${o.btitle}</td>
                            <td>${o.memail}</td>
                            <td>${o.bdate}</td>
                            <td>${o.bview}</td>
                        </tr>`;


            })
            document.querySelector(".boardlistbox").innerHTML = html;
        }
    })
}

//6. 내가 작성한 (로그인 되어있다는 가정) 게시물
myboards();
function myboards(){
    $.ajax({
        url:"/board/myboards",
        method:"get",
          success:(r)=>{
                    console.log(r);
                    let html = `<tr>
                                    <th>번호</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>작성일</th>
                                    <th>조회수</th>
                                </tr>`;
                    r.forEach((o)=>{
                        html += `<tr>
                                    <td>${o.bno}</td>
                                    <td>${o.btitle}</td>
                                    <td>${o.memail}</td>
                                    <td>${o.bdate}</td>
                                    <td>${o.bview}</td>
                                </tr>`;


                    })
                    document.querySelector(".boardlistbox").innerHTML = html;
                }
    })//ajax e
}//myboards e


function s_board(bno){
    $.ajax({
    url:"/board/s_board",
    method:"get",
    data:{"bno":bno},
    success:(r)=>{
        console.log(r);

        let html = `<table border="1">
                        <tr>
                            <th>게시판 번호</th>
                            <th>카테고리</th>
                            <th>제목</th>
                            <th>내용</th>
                            <th>작성자 이메일</th>
                            <th>작성일</th>
                            <th>조회수</th>
                            <th>삭제</th>
                        </tr>
                        <tr>
                            <td>${r.bno}</td>
                            <td>${r.cname}</td>
                            <td>${r.btitle}</td>
                            <td>${r.bcontent}</td>
                            <td>${r.memail}</td>
                            <td>${r.bdate}</td>
                            <td>${r.bview}</td>
                            <td> <button type="button" onclick="b_del(${r.bno})">
                                 ${r.bno} 삭제하기 </button>
                            </td>
                        </tr>
                    </table>`

                    document.querySelector(".boardbox").innerHTML = html;
    }

    })
}

function b_del(bno){
    $.ajax({
    url:"/board/b_del",
    method:"delete",
    data:{"bno":bno},
    success:(r)=>{
        if(r==true){alert('삭제완료 ! '); myboards();
        document.querySelector(".boardbox").innerHTML =""
        }
        else{alert('오류! ')}
        }
     })
}

/*
    해당 변수의 자료형 확인 Prototype
    Array: forEach() 가능
    {object, object, object}

    object : forEach() 불가능 -----> for(let cno in object){}
    {
        필드명:값, 필드명:값, 필드명:값
    }
        object[필드명] : 해당 필드의 값 호출
*/