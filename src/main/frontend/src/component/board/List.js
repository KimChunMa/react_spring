import React,{ useState , useEffect } from 'react';
import axios from 'axios';
/* ---------table mui -------- */
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
/* ---------------------------*/
import Container from '@mui/material/Container';
import CategoryList from './CategoryList'
/* ---------------- 페이지 ---------------------*/
import Pagination from '@mui/material/Pagination';

import View from './View';

export default function List(props){

      // 1. 요청한 게시물 정보를 가지고 있는 리스트 변수[ 상태 관리변수 ]
      let [ rows , setRows ] = useState( [] )
      //1-2. 카테고리+ 페이지수
      let [ pageInfo , setPageInfo ] = useState( { 'cno' : 0 , 'page' : 1 , 'key' : '' , 'keyword' : '' } )
      console.log(pageInfo)
      // 총페이지
      let [ totalPage, setTotalPage] = useState(1);
      //총 게시물
      let [ totalCount, setTotalCount ] = useState(1);


      // 2. 서버에게 요청하기 [ 컴포넌트가 처음 생성 되었을때 ] // useEffect( ()=>{} , [] )
      useEffect( ()=>{
          axios.get('/board',{ params : pageInfo })
              .then( r => {   console.log(r);
              setRows( r.data.boardDtoList ); //응답받은 게시물
              setTotalPage(r.data.totalPage); //페이지
              setTotalCount(r.data.totalCount)  //카운트
              })
              .catch( err => { console.log(err); })
      } , [pageInfo] ) // cno 변경 될때마다 해당 useEffect 실행된다.

      // useEffect ( ()=> {} ) : 생성, 업데이트
      // useEffect ( ()=> {},[] ) : 생성될때 1번
      // useEffect ( ()=> {}, [변수] ) : 생성, 업데이트 될때마다 새렌더링

    //3. 카테고리 변경
    const categoryChange = (cno) => { pageInfo.cno = cno ; setPageInfo({...pageInfo});}
    // [...배열명] vs {...객체명} : 기존 배열 / 객체의 새로운 메모리 할당

    //4. 페이징 변경
      const selectPage = ( event , value ) =>{
             console.log(value);

             pageInfo.page = value;
             setPageInfo( {...pageInfo } )
      }

     //5. 검색 했을때
     const onSearch = () => {
        pageInfo.key = document.querySelector('.key').value
        pageInfo.keyword = document.querySelector('.keyword').value
        pageInfo.page = 1; // 검색시 1페이지로
        pageInfo.cno = 0;
        setPageInfo({...pageInfo})
     }

    return (
      <Container>
        <div> 현재 페이지 : { pageInfo.page } 총 게시물 : {totalCount}</div>
        <div style={{display:'flex' , justifyContent:'space-between' , alignItems:'center' }}>
            <a href="/board/write"> 글쓰기 </a>
            <CategoryList categoryChange={categoryChange}/>
        </div>
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell align="center" style={{ width:'10%' }}>번호</TableCell>
                  <TableCell align="left" style={{ width:'60%' }}>제목</TableCell>
                  <TableCell align="center" style={{ width:'10%' }}>작성자</TableCell>
                  <TableCell align="center" style={{ width:'10%' }}>작성일</TableCell>
                  <TableCell align="center" style={{ width:'10%' }}>조회수</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {rows.map((row) => (
                  <TableRow  key={row.name}   sx={{ '&:last-child td, &:last-child th': { border: 0 } }}  >
                    <TableCell component="th" scope="row"> {row.bno} </TableCell>
                    <TableCell align="left"><a href={'/board/view/'+row.bno}>{row.btitle}</a></TableCell>
                    <TableCell align="center">{row.memail}</TableCell>
                    <TableCell align="center">{row.bdate}</TableCell>
                    <TableCell align="center">{row.bview}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>

          <div style={{ display:'flex' ,justifyContent : 'center' , margin: '40px 0px' }}>
            {/* 전체페이지수 필요*/}
            <Pagination count={totalPage} color="primary" onChange={selectPage} page ={pageInfo.page}/>
          </div>

            <div class="searchBox">
                <select className="key">
                    <option value="btitle"> 제목 </option>
                    <option value="bcontent"> 내용 </option>
                </select>
                <input type="text" className="keyword" />
                <button type="button" onClick={ onSearch } > 검색 </button>
            </div>

      </Container>
      );
}