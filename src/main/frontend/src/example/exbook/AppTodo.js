//교재 App컴포넌트 --> AppTodo 컴포넌트
import styles from "../../App.css"
import React, {useState} from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import {Paper, List, Container} from '@mui/material';
export default function AppTodo(props){

    const [items, setItems] = useState(
    [
        {
            id:"0",
            title:"hello 1",
            done : true
        },
        {
            id:"1",
            title:"hello 2",
            done : false
        }
    ] // array e
    ) //useState e

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
                <AddTodo/>
                { TodoItems }
            </Container>
         </div>
    </>);
}