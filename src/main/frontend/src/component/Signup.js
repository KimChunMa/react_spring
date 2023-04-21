 import React from 'react';
 import axios from 'axios';

 export default function Signup(props){
     return (<>
      <h3> 회원가입 </h3>

      <div >
        아이디[이메일] : <input type="text" class="memail"/> <br/>
        비밀번호 :  <input type="text" class="mpw"/> <br/>
        전화번호 :  <input type="text" class="mphone"/> <br/>
        이름 :  <input type="text" class="mname"/> <br/>
        <button onclick="onSignup()" type="button"> 가입하기</button>
      </div>
     </>)
 }

