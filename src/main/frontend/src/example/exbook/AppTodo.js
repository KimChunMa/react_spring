//교재 App컴포넌트 --> AppTodo 컴포넌트
import styles from "../../App.css"
import React, {useState} from 'react';
import Todo from './Todo';
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
        items.map((i) =>
            <Todo item = {i} key = {i.id} />
    )

    return (<>

         <div className="App">
                    { TodoItems }
         </div>
    </>);
}