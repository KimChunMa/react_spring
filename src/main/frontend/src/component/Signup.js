 import React from 'react';
 import axios from 'axios';

 export default function Signup(props){

    const onSignup = () => {
        console.log('onsignup oppen')
        let info = {
            memail: document.querySelector('.memail').value,
            mpw: document.querySelector('.mpw').value,
            mphone: document.querySelector('.mphone').value,
            mname: document.querySelector('.mname').value,
        }
        console.log(info);

        axios.post("http://localhost:8080/member/info", info)
                .then(r => {
                    console.log( r );
                    if(r.data == true){
                        alert('가입');
                        window.location.href="/login";
                    }
                })
            .catch(err => {console.log(err)});
    }

     return (<div>
      <h3> 회원가입 </h3>

      <form >
        아이디[이메일] : <input type="text" class="memail"/> <br/>
        비밀번호 :  <input type="text" class="mpw"/> <br/>
        전화번호 :  <input type="text" class="mphone"/> <br/>
        이름 :  <input type="text" class="mname"/> <br/>
           <button onClick={ onSignup } type="button"> 가입 </button>
      </form>
     </div>)
 }

 /*
    HTML -----> JSX
        1. <> </>
        2. class -> className
        3. style -> style = {{}}
        4. 카멜표기법 :
            onclick -> onClick
            margin-top -> marginTop
        5.
 */

