//교재 App컴포넌트 --> AppTodo 컴포넌트
import styles from "../../App.css"
import React, {useState} from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import {Paper, List, Container} from '@mui/material';
export default function AppTodo(props){

    const [items, setItems] = useState(
        [
        ] // array e
    ) //useState e

    //2. items 에 새로운 item 등록하는 함수
    const addItem = (item) =>{ //함수로부터 매개변수 전달은 item
        item.id = "ID-" + item.length //ID 구성
        item.done = false; // 체크여부
        setItems([...items,item]); //기존 상태items에 item 추가
        //setItems([...기본배열, 값])
    }

    let TodoItems =
        /* <Paper style="margin : 16px;">   HTML의 style 속성 방법 */
        <Paper style={{margin:16 }}>
            <List>
                {
                    items.map((i) =>
                        <Todo item = {i} key = {i.id} />
                    )
                }
            </List>
        </Paper>



    return (<>

         <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem={addItem}/>
                { TodoItems }
            </Container>
         </div>
    </>);
}