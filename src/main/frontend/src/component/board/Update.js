import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import {useSearchParams} from 'react-router-dom';

export default function Reply(props){
   //URL 가져오기
   const [ searchParams , setSearchParams ] = useSearchParams();
   //댓글 가져오기
   const [reply, setReply] = useState({});

   useEffect( ()=>{
       axios.get('/board/s_board',{params : {bno: searchParams.get("bno")}})
                   .then( r => {
                       console.log(r.data.replyDtoList);
                       r.data.replyDtoList.forEach( (o)=>{
                            if(searchParams.get("rno") == o.rno){
                               setReply(o);
                            }
                       })//forEach e
                   }) // then
   },[])

   //내용 입력 이벤트
   const inputContent = (e) => {
        reply.rcontent = e.target.value
        setReply({...reply})
   }
   console.log(reply)

    //댓글 수정
   const onEdit = () => {
        let reply = {
            rno : searchParams.get("rno"),
            rcontent: document.querySelector('#text').value
        }
        console.log(reply)

        axios.put('/board/reply' ,reply )
            .then(r => {
                if(r.data ==true){
                    alert('수정 성공');
                    window.location.href="http://localhost:8080/board/view/"+searchParams.get("bno")
                }else{
                    alert('수정실패');
                }
            })
   }

    return(<>
        <h3> 수정 </h3>
        수정할 댓글 : <input type="text" value={reply.rcontent} id="text"
                     onChange={ inputContent } />
        <button onClick={onEdit}> 수정 </button>
    </>)
}