import React from 'react';
import axios from 'axios';

 export default function Find(props){

    const findId = () => {

        let info = {
               "mname":document.querySelector('.mname').value ,
               "mphone":document.querySelector('.mphone').value
           }

        axios.post("http://localhost:8080/member/findId", info )
            .then(r => {
                if(r.data == ''){
                    alert('찾을수 없습니다.');
                }else{
                    console.log(r)
                    alert("회원님의 아이디는 " + r.data)
                }
            })

    }



    return (<>
                <h3>회원 정보 찾기 [아이디/ 비밀번호]</h3>
                   <form className="findIdForm">
                        이름 : <input type="text" className="mname"/> <br/>
                        전화번호 :  <input type="text" className="mphone"/> <br/>
                        <button onClick={findId} type="button"> 아이디찾기 </button>
                    </form>
            </>)
 }