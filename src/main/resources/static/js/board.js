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

            let html = `<button onclick="selectCno(0)" type="button"> 전체보기 </button>`;

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
function getBoard(cno){
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