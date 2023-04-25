import React, {useState} from 'react';
import axios from 'axios';

export default function Header(props){

    let [ login , setLogin] = useState( JSON.parse( sessionStorage.getItem("login_token") ) );

    console.log(login);

    //로그아웃
    const logOut = () => {
        sessionStorage.setItem("login_token",null);

        //백엔드의 인증세션 지우기
        axios.get("member/logout")
            .then( r=>{ console.log(r); } );

        window.location.href="/login";
    }

    return (
    <div>
        <a href="/"> Home </a>
        <a href="/board/list"> 게시판 </a>
        <a href="/admin/DashBoard"> 관리자 </a>
        { login == null
            ?
            ( <>
                <a href="/login"> 로그인 </a>
                <a href="/signup"> 회원가입 </a>
                <a href="/member/find"> 아이디 찾기 </a>
                <a href="/member/findPw"> 비번 찾기 </a>
            </> )
        : (<>
                <button onClick={logOut}> 로그아웃 </button>
          </>) }
    </div>)
    }