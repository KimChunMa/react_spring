//1. 등록 [JSON.stringify() : json 타입에서 문자열 타입으로 변환,
//          JSON.parse: 문자열타입에서 json타입으로 변환 ]
function onwrite(){
    $.ajax({
        url: "/note/write", // 매핑 주소값
        method:"post", //매핑 HTTP 메소드
        //body 값에 JSON 형식의 문자열타입
        data: JSON.stringify ({"ncontents": document.querySelector(".ncontents").value}),
        contentType: "application/json",
        success : (r) => {
            console.log(r);
            if(r==true){alert("글쓰기 성공!")
                document.querySelector(".ncontents").value = '';
                onget();
            }else{alert("실패! ")}
        }
    })
}
//2. 등록된 글 목록 호출
onget();
function onget(){
    let html =`<tr>
                    <th> 번호 </th>
                    <th> 내용 </th>
                    <th> 비고 </th>
               </tr>`;
    $.ajax({ // ajax 이용한 @GetMapping 에게 요청 응답
        url: "/note/get",
        method: "get",
        success : (r) => {console.log(r)
            r.forEach((o,i) => {
                console.log(o);
                html += `<tr>
                            <td> ${o.nno} </td>
                            <td> ${o.ncontents} </td>
                            <td> <button onclick="del(${o.nno})"> 삭제 </button>
                                 <button onclick="edit(${o.nno})"> 수정 </button>
                            </td>
                        </tr>`
            })
                html += ` </table>`
            document.querySelector(".printTable").innerHTML = html;
        }
    })
}
//삭제
function del(nno){
     $.ajax({
         url: "/note/delete",
         method: "delete",
         data: {"nno":nno},
         success : (r) => {
            console.log(r)
            if(r==true) {alert('삭제!'); onget();}
            else{alert('삭제 실패!')}

         }
     })
}

//수정
function edit(nno){
    let ncontents = prompt("수정할 내용 : " ); console.log(ncontents);

    $.ajax({
             url: "/note/update",
             method: "put",
             data : JSON.stringify(  { "nno" : nno , "ncontents" : ncontents } ) ,
             contentType : "application/json" ,
             success : (r) => {
                console.log(r)
                if(r==true) {alert('수정!'); onget();}
                else{alert('수정 실패!')}

             }
         })
}


