let memberInfo = null ;

//회원가입
function onSignup(){

    let info = {
        memail : document.querySelector('.memail').value,
        mpw : document.querySelector('.mpw').value,
        mphone : document.querySelector('.mphone').value,
        mname : document.querySelector('.mname').value
    }

    console.log(info)
    $.ajax({
        url:"/member/info",
        method:"post",
        data:JSON.stringify(info),
        contentType:"application/json",
        success: (r)=>{
            console.log(r);
            if(r==true){alert('성공')}
        }
    })
}

/*//로그인 시큐리티 사용으로 사용안함
function onLogin(){
    let info = {
        memail : document.querySelector('.memail').value,
        mpw : document.querySelector('.mpw').value,
    }

    $.ajax({
        url:"/member/login",
        method:"post",
        data:JSON.stringify(info),
        contentType:"application/json",
        success: (r)=>{

            if(r==true){alert('로그인 성공 '); location.href="/";}
            else{alert('실패 ')}
        }

    })
}*/

//로그인 정보 출력
getMember()
function getMember(){
       $.ajax({
            url:"/member/info",
            method:"get",
            success: (r)=>{

                memberInfo = r;
                console.log(memberInfo);
                if(memberInfo.mno !=null){
                    let html = `<table>
                                    <tr>
                                        <th> 번호 </th>
                                        <th> 이메일 </th>
                                        <th> 비번 </th>
                                        <th> 이름 </th>
                                        <th> 휴대번호 </th>
                                        <th> 생성일 </th>
                                        <th> 수정일 </th>
                                    </tr>
                                    <tr>
                                        <td> ${r.mno} </td>
                                        <td> ${r.memail} </td>
                                        <td> ${r.mpw} </td>
                                        <td> ${r.mname}</td>
                                        <td> ${r.mphone} </td>
                                        <td> ${r.cdate} </td>
                                        <td> ${r.udate} </td>
                                    </tr> `
                    document.querySelector('.info').innerHTML = html;
                    document.querySelector('.nickname').innerHTML = r.mname +'님';
                }
            }
        })
}

//로그아웃
function logout(){
        $.ajax({
            url:"/member/logout",
            method:"get",
            success: (r)=>{
                if(r==true){alert('로그아웃 되었습니다');
                location.href="/"}

            }
        })

}

//아이디 찾기
function findId(){
        $.ajax({
            url:"/member/findId",
            method:"get",
            data:{"mname":document.querySelector('.mname').value ,
                  "mphone":document.querySelector('.mphone').value
                  },
            success: (r)=>{
                if(r==true){alert('로그아웃 되었습니다');
                location.href="/"}

            }
        })

}