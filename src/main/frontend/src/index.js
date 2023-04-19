import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'; //1. import이용해 App 컴포넌트[함수]를 불러온다.
import reportWebVitals from './reportWebVitals';
//------------------------------------------------------------------- //
//import 컴포넌트명 from 'index.js' 파일 기준으로 상대경로
import Book from './example/ex1component/Book';
import Product from './example/ex1component/Product';
import ProductList from './example/ex1component/ProductList';
import Clock from './example/ex1component/Clock';
//------------------ ex2css ------------------------------//
import Comment from './example/ex2css/Comment';
import CommentList from './example/ex2css/CommentList';

//2.ReactDom.createRoot(해당 div ) : 해당 div를 리액트 root로 사용하여 root 객체 생성
const root = ReactDOM.createRoot(document.getElementById('root'));


/*
//1. html에 존재하는 div 가져오기


//3. root.render() : 해당 root 객체(우리가 가져온 div)의 컴포넌트 렌더링
root.render(
  <React.StrictMode>
    // * <컴포넌트/>를 이용해 컴포넌트를 사용한다
    <App /> // 4. app 컴포넌트에 render 함수에 포함 [app 호출하는 방법 : 상단에 import]
  </React.StrictMode>
);
*/


/*

//1. 예제 1 개발자 정의 컴포넌트 렌더링
root.render(
    <React.StrictMode>
        <Book/>
    </React.StrictMode>
);

*/

//2. 예제2

/*

root.render(
    <React.StrictMode>
        <Product/>
    </React.StrictMode>
);
*/




/*
//3. 예제3 컴포넌트에 컴포넌트 포함하기
root.render(
    <React.StrictMode>
        <ProductList/>
    </React.StrictMode>
);
*/


/*
setInterval( ()=> {
    root.render(
        <React.StrictMode>
            <Clock/>
        </React.StrictMode>
    );
},1000);
*/

 root.render(
        <React.StrictMode>
            <CommentList/>
        </React.StrictMode>
    );


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
