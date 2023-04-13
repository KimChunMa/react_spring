let memberInfo = null ;

//회원가입
function onSignup(){

    let info = {
        memail : document.querySelector('.memail').value,
        mpw : document.querySelector('.mpw').value,
        mphone : document.querySelector('.mphone').value,
        mname : document.querySelector('.mname').value
    }
    $.ajax({
        url:"/member/info",
        method:"post",
        data:JSON.stringify(info),
        contentType:"application/json",
        success: (r)=>{
            console.log(r);
              if( r == true  ){ alert('가입이 되셨습니다.');  location.href="/"}
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
                    document.querySelector('.nickname').innerHTML = r.mname +'님';
                    document.querySelector('.logout').innerHTML =
                    `<a href="/member/logout"><button type="button"> 로그아웃 </button></a>`

                }
            }
        })
}

/*

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
*/


//아이디 찾기
function findId(){

    let info = {
        "mname":document.querySelector('.mname').value ,
        "mphone":document.querySelector('.mphone').value
    }
        $.ajax({
            url:"/member/findId",
            method:"post",
            data:JSON.stringify(info),
            contentType:"application/json",
            success: (r)=>{
                alert('회원님의 아이디는 ' + r + ' 입니다.');
            }
        })
}

//비번 찾기
function findPw(){
    let info = {
        "memail":document.querySelector('.memail').value ,
        "mphone":document.querySelector('.mphone').value
    }
        $.ajax({
            url:"/member/findPw",
            method:"post",
            data:JSON.stringify(info),
            contentType:"application/json",
            success: (r)=>{
                alert('회원님의 임시비밀번호는 ' + r + ' 입니다.');
            }
        })
}


//회원탈퇴하기
function mdelete(){
       if(memberInfo.mno==null){
         alert('로그인한 사람만 가능합니다.');
         location.href="/";
         return;
         }


        $.ajax({
            url:"/member/mdelete",
            method:"delete",
            data:document.querySelector('.mpw').value ,
            success: (r)=>{
                if(r==true){alert('회원탈퇴 되었습니다.')}
            }
        })
}
