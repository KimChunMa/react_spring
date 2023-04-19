//Comment.js
    //class -> className
import React from 'react';
import styles from './Comment.css'
//img 가져오기
import logo from '../../logo.svg'
export default function Comment(props){
    return(<>
        <div className="wrapper">
            <div>
                <img src={logo} class="logoImg"/>
            </div>

            <div class="contentContainer">
                <div class="nameText"> {props.name} </div>
                <div class="CommentText"> {props.comment} </div>
            </div>




        </div>
    </>);
}