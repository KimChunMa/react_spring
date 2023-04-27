import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import {useSearchParams} from 'react-router-dom';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import CategoryList from './CategoryList'


export default function Modify(props){
    //게시판
    const [ board , setBoard ] = useState({});

    //카테고리 선택
    let [cno, setCno] = useState(0)

    //URL 가져오기
    const [ searchParams , setSearchParams ] = useSearchParams();

    //게시판 상세 정보 불러오기
    useEffect( () => {
            axios.get('/board/s_board',{params : {bno: searchParams.get("bno")}})
                .then( r => {
                    console.log(r.data);
                    setBoard(r.data); setCno(r.data.cno);
                })
        },[])

    //수정 버튼
    const onModify = () => {

       let m_board = {
        btitle: board.btitle ,
        bcontent: board.bcontent ,
        cno: board.cno,
        bno: board.bno
       }

       console.log(m_board);

       axios.put('/board/b_modify', m_board )
            .then( r => {
                console.log(r.data)
                if(r.data == true){
                alert('수정성공!');window.location.href="/board/view/"+board.bno }
                else{
                alert('오류!')}
            })
    }

     //카테고리 변경 함수
    const categoryChange = (cno) => { setCno(cno);  console.log(cno)}

    //제목 입력 이벤트
    const inputTitle = (e) => {
        board.btitle = e.target.value
        setBoard({...board})
    }

     //내용 입력 이벤트
     const inputContent = (e) => {
        board.bcontent = e.target.value
        setBoard({...board})
     }


        return( <>
            <Container>
              <CategoryList categoryChange={categoryChange} />
              <TextField  className="btitle" id="btitle" label="제목" variant="standard"
                            value={board.btitle} onChange= {inputTitle} />
               <TextField  className="bcontent" label="내용" id="bcontent"
                            multiline  rows={10}  defaultValue="" variant="standard"
                            onChange={inputContent }  value={board.bcontent} />
               <Button variant="outlined" onClick={ onModify }> 등록 </Button>
               <Button variant="outlined"> 취소 </Button>

            </Container>
        </>)
}