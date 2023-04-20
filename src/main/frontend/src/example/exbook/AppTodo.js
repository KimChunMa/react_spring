//교재 App컴포넌트 --> AppTodo 컴포넌트
import styles from "../../App.css"
import React, {useState} from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import {Paper, List, Container} from '@mui/material';
export default function AppTodo(props){

    const [items, setItems] = useState(
        [] // array e
    ) //useState e

    //2. items 에 새로운 item 등록하는 함수
    const addItem1 = (item) =>{ //함수로부터 매개변수 전달은 item
        item.id = "ID-" + items.length //ID 구성 ??
        item.done = false; // 체크여부
        setItems([...items,item]); //기존 상태items에 item 추가

        //setItems([...기본배열, 값])
    }

    //3. 삭제 기능
    const deleteItem= (item) => {
        console.log(item.id)
        const newItems = items.filter( (e) => {return e.id !== item.id});
            //삭제할 id를 제외한 새로운 배열 선언
            setItems([...newItems]);
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




    //모든 Items 배열 순회
    let TodoItems =
        /* <Paper style="margin : 16px;">   HTML의 style 속성 방법 */
        <Paper style={{margin:16 }}>
            <List>
                {
                    items.map((i) =>
                        <Todo item = {i} key = {i.id} 삭제함수={deleteItem}/>
                    )
                }
            </List>
        </Paper>



    return (<>

         <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem2={addItem1}/>
                { TodoItems }
            </Container>
         </div>
    </>);
}