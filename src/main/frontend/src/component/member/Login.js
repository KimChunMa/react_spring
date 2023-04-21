import React, {useState, useEffect} from 'react';
import axios from 'axios';
import styles from '../../css/member/login.css';

export default function Login(props){

    const onLogin = () => {
        let loginForm = document.querySelectorAll('.loginForm')[0];
        let loginFormData = new FormData(loginForm);

        axios.post("http://localhost:8080/member/login", loginFormData)
        .then(r => {
            if(r.data == false){
                alert('실패')
            }else{
                alert('성공')
                // 로컬 스토리지에 로그인 성공한 흔적 남기기
                window.location.href="/";
            }
         })
    }




    return (<>
        <h3> 로그인 페이지 </h3>
        <form className="loginForm">
            아이디 :  <input type="text" name="memail" /> <br/>
            비밀번호 :  <input type="text" name="mpw" /> <br/>
            <button onClick={onLogin} type="button"> 로그인 버튼 </button>
            <a href="/oauth2/authorization/google"> 구글 로그인 </a>
            <a href="/oauth2/authorization/kakao"> 카카오 로그인 </a>
            <a href="/oauth2/authorization/naver"> 네이버 로그인 </a>
         </form>
        </>)
}

