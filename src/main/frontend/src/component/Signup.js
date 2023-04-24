 import React, {useState} from 'react';
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

    //2. 아이디 중복 체크
    let [memailMsg, setMemailMsg] = useState('');
    const idCheck = (e) => {
        //1
        // console.log(document.querySelector('.memail').value);
        // console.log(e.target.value)

        axios.get("http://localhost:8080/member/idcheck", {params: {memail: e.target.value}})
        .then(res =>{
            if(res.data == true ) {setMemailMsg('불가능')}
            else{setMemailMsg('사용가능')}
            }
        ).catch(e =>  console.log('오류 : '+ e));
    }

        //2. 전화번호 중복 체크
        let [MphoneMsg, setMphone] = useState('');
        const mphoneCheck = (e) => {
            console.log(e.target.value)
            axios.get("http://localhost:8080/member/phoneCheck", {params: {mphone: e.target.value}})
            .then(r =>{
                console.log(r)
                if(r.data == true ) {setMphone('불가능')}
                else{setMphone('사용가능')}
                }
            ).catch(e =>  console.log('오류 : '+ e));
        }

     return (<div>
      <h3> 회원가입 </h3>

      <form >
        아이디[이메일] : <input type="text" class="memail" onChange={idCheck}/>
        <span>{memailMsg} </span>
        <br/>
        비밀번호 :  <input type="text" class="mpw"  />    <br/>
        전화번호 :  <input type="text" class="mphone" onChange={mphoneCheck}/>  <span>{MphoneMsg} </span> <br/>
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

