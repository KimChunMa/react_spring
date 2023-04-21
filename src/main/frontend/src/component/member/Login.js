import React, {useState, useEffect} from 'react';
import axios from 'axios';
import styles from '../../css/member/login.css';

export default function Login(props){
    return (<>
        <h3> 로그인 페이지 </h3>
        <form action="http://localhost:8080/member/login" method="post">
            아이디 :  <input type="text" name="memail" /> <br/>

            비밀번호 :  <input type="text" name="mpw" /> <br/>
            <button type="submit"> 버튼 </button>
            <a href="/oauth2/authorization/google"> 구글 로그인 </a>
            <a href="/oauth2/authorization/kakao"> 카카오 로그인 </a>
            <a href="/oauth2/authorization/naver"> 네이버 로그인 </a>
         </form>
        </>)
}

