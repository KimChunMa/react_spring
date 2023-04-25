import React, {useState, useEffect} from 'react';
import axios from 'axios';
import styles from '../../css/member/login.css';

export default function Login(props){

    const onLogin = () => {
        let loginForm = document.querySelectorAll('.loginForm')[0];
        let loginFormData = new FormData(loginForm);

        axios.post("/member/login", loginFormData)
        .then(r => {
            console.log(r);
            if(r.data == false){
                alert('실패')
            }else{
                alert('성공')
                // 브라우저 닫혀도 사라지지않지만 도메인이 달라지면 사라짐
                // 로컬 스토리지에 로그인 성공한 흔적 남기기
                //localStorage.setItem("key",value); // String 타입
                //value 에 객체 대입시 [Object] 객체처럼 사용불가
                // localStorage.setItem("login_token" , JSON.stringify( r.data ) );
                //JS 세션 스토리지 [브라우저 모두 닫히면 사라짐.]
                sessionStorage.setItem("login_token" , JSON.stringify( r.data ) );
                //window.localStorage.removeItem("login_token");

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
            <a href="/member/find"> 아이디 찾기 </a>
            <a href="/oauth2/authorization/google"> 구글 로그인 </a>
            <a href="/oauth2/authorization/kakao"> 카카오 로그인 </a>
            <a href="/oauth2/authorization/naver"> 네이버 로그인 </a>
         </form>
        </>)
}

