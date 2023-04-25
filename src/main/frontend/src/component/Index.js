 import React from 'react';
 import {BrowserRouter, Routes, Route} from "react-router-dom";
 import Login from "./member/Login"
 import Header from "./Header"
 import Main from "./Main"
 import Footer from "./Footer"
 import Signup from "./Signup"
 import Find from "./member/Find"
 import FindPw from "./member/FindPw"
 import List from "./board/List"
 import DashBoard from "./admin/DashBoard"
 import Write from "./board/write"

/*
    react-router-dom 다양한 라우터 컴포넌트 제공
    1.BrowserRouter : 가상 URL 관리 [URL 동기화]
    2.<Routes> : 가장 적합한 <Route> 컴포넌트를 검토하고 찾는다.
    3.<Route>  : 실제 URL 경로 지정해주는 컴포넌트
    <Route path="login" element = { <Login/> } />
        get요청시 Login 컴포넌트 반환
*/

 export default function Index(props){
     return (<>
     <BrowserRouter>
        <Header/>
        <Routes>
            <Route path="/" element = { <Main/> } />
            <Route path="/logins" element = { <Login/> } />
            <Route path="/signup" element = { <Signup/> } />
            <Route path="/member/find" element = { <Find/> } />
            <Route path="/member/findPw" element = { <FindPw/> } />
            <Route path="/board/list" element = { <List/> } />
            <Route path="/admin/dashBoard" element = { <DashBoard/> } />
            <Route path="/board/write" element = { <Write/> } />

        </Routes>
        <Footer/>
     </BrowserRouter>
     </>)
 }