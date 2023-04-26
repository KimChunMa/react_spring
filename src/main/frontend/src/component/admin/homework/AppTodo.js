//교재 App컴포넌트 --> AppTodo 컴포넌트
import styles from "./App.css"
import React, {useState, useEffect} from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import {Paper, List, Container} from '@mui/material';
import axios from 'axios'; // npm install axios
import Pagination from '@mui/material/Pagination';
export default function AppTodo(props){

    let [items, setItems] = useState(
        [] // array e
    ) //useState e

    //페이지
    let [ page, setPage ] = useState(1)
    console.log(page)

    let [ totalPage, setTotalPage] = useState(1);



    //컴포넌트가 실행될때 한번 이벤트 발생
    useEffect( () => {
          // ajax : jquery 설치 가 필요
          // fetch : 리액트 전송 비동기 통신 함수 [ 내장함수 - 설치 X ]
          // axios : 리액트 외부 라이브러리 [ 설치 필요 ] JSON통신 기본값
          axios.get( "/todo" , {params : {"page":page}})
                     .then( r => {
                         console.log(r );
                         setItems( r.data.tododtoList ); // 서버에게 응답받은 리스트를 재렌더링
                         setTotalPage(r.data.totalPage); //페이지
                     })
          // 해당 주소의 매핑되는 컨트롤/메소드 에 @CrossOrigin(origins = "http://localhost:3000") 추가
          //axios.post( "http://localhost:8080/todo" , {mname: "유재석"}).then( r => { console.log( r ); })
          //axios.put( "http://localhost:8080/todo" ).then( r => { console.log( r ); })
          //axios.delete( "http://localhost:8080/todo", {params: {id:1 } } ).then( r => { console.log( r ); })
       } , [page] );




    //2. items 에 새로운 item 등록하는 함수
    const addItem1 = (item) =>{ //함수로부터 매개변수 전달은 item
        item.id = "ID-" + items.length //ID 구성 ??
        item.done = false; // 체크여부
        setItems([...items,item]); //기존 상태items에 item 추가
        //setItems([...기본배열, 값])
        axios.post( "/todo", item )
                    .then( r => {console.log("넣은값 : "+item.id)})
    }

    //3. 삭제 기능
    const deleteItem= (item) => {
        console.log(item.id);
        const newItems = items.filter( (e) => {return e.id !== item.id});
            //삭제할 id를 제외한 새로운 배열 선언
            setItems([...newItems]);
            console.log(newItems);
        axios.delete( "/todo", {params: {id:item.id}} )
                                .then( r => {console.log("결과: " + r )})
    }
        //js 반복문 함수 제공
            // r = [1,2,3]
            // 배열 or 리스트.forEach( (o)=> {}) : 반복문 가능 [return 없음]
                //let array = r.forEach( (o) => {o+3});
                //반복문이 끝나면 array 에는 아무것도 들어있지 않다.

            // 배열 or 리스트 .map( (o)=> {})    : return 값들을 새로운 배열에 저장
                // let array = r.map( (o) => {return o+3});
                //array = [4,5,6]

            // 배열 or 리스트.filter( (o) => {return }) : +조건충족할 경우 객체 반환
                // let array = r.filter( (o) => { o>=3});
                //array = [3]

    //4. 수정함수
    const editItem = () => {
        setItems([...items])
    }


    //모든 Items 배열 순회
    let TodoItems =
        /* <Paper style="margin : 16px;">   HTML의 style 속성 방법 */
        <Paper style={{margin:16 }}>
            <List>
                {
                    items.map((i) =>
                        <Todo item = {i} key = {i.id}
                        삭제함수={deleteItem} 수정함수 ={editItem}/>
                    )
                }
            </List>
        </Paper>

    //페이지 이동
    const movePage = (e) => { page = e.target.outerText;  setPage(...page);  }


    return (<>

         <h3> 현재 페이지 : {page} </h3>
         <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem2={addItem1}/>
                { TodoItems }
            </Container>
             <div style={{ display:'flex' ,justifyContent : 'center' , margin: '40px 0px' }}>
                <Pagination count={totalPage} color="primary" onClick={movePage} />
             </div>
         </div>
    </>);
}