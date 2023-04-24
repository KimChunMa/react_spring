import React from 'react';
import axios from 'axios';

export default function FindPw(props){
    const findPw = () => {
        let info = {
               "memail":document.querySelector('.memail').value ,
               "mphone":document.querySelector('.mphone').value
           }

        axios.post("http://localhost:8080/member/findPw", info )
            .then(r => {
                alert('임시 비밀번호 : '+ r.data)
            })

    }



    return (<>
                <h3>회원 정보 찾기 [ 비밀번호]</h3>
                   <form className="findIdForm">
                        아이디 : <input type="text" className="memail"/> <br/>
                        전화번호 :  <input type="text" className="mphone"/> <br/>
                        <button onClick={findPw} type="button"> 비밀번호찾기 </button>
                    </form>
            </>)
 }