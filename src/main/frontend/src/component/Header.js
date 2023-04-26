import React, {useState, useEffect} from 'react';
import axios from 'axios';

export default function Header(props){

    // let [ login , setLogin] = useState( JSON.parse( sessionStorage.getItem("login_token") ) );
    let [ login , setLogin] = useState( null );

    //로그아웃
    const logOut = () => {
        //js 세션 스토리지 초기화
        sessionStorage.setItem("login_token",null);

        //백엔드의 인증세션 지우기
        axios.get("member/logout")
            .then( r=>{ console.log(r); } );

        //window.location.href="/member/login";
        setLogin(null);
    }

    useEffect ( () => {
        axios.get("/member/info")
            .then( r => { console.log(r);
                if(r.data != '') { // 로그인되어 있으면 // 서비스에서 null이면 js에서 ''이다.
                    //JS 로컬 스토리에 저장
                    sessionStorage.setItem("login_token" , JSON.stringify(r.data) );
                    // 상태변수에 로컬 스토리지 데이터 저장 [렌더링 하기위해]
                    setLogin( JSON.parse(sessionStorage.getItem("login_token") ));
                }
            })
    },[])
    console.log(login)

    return (
    <div>
        <a href="/"> Home </a>
        <a href="/board/list"> 게시판 </a>
        <a href="/admin/DashBoard"> 관리자 </a>
        <a href="/admin/homework"> Apptodo </a>
        { login == null
            ?
            ( <>
                <a href="/logins"> 로그인 </a>
                <a href="/signup"> 회원가입 </a>
                <a href="/member/find"> 아이디 찾기 </a>
                <a href="/member/findPw"> 비번 찾기 </a>
            </> )
        : (<>
                <button onClick={logOut}> 로그아웃 </button>
          </>) }
    </div>)
    }