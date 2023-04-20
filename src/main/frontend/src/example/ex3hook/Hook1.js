import React, {useState, useEffect} from 'react';

export default function Hook1(){
   let [ count , setCount ] = useState(0);
/*
    // 1. 예제 훅[useState]을 사용 안했을때
    //return : 렌더링 될 HTML 반환 해주는 곳 [화면에 표시한 코드]
    // 컴포넌트 당 1번씩 실행

    let count = 0;

    const countHandler = () => {
        console.log(count)
        count++;
    }

    return (<>
        <div>
            <p> 총 {count} 번 클릭 했습니다 </p>
            <button onClick={countHandler}>
                증가
            </button>
        </div>
    </>)

*/
/*
   //useState
    console.log('컴포넌트 실행 ')
   let [ count , setCount ] = useState(0);

    const countHandler = () => {
        setCount(count+1);
        console.log(count)
    }

    return (<>
        <div>
            <p> 총 {count} 번 클릭 했습니다 </p>
            <button onClick={countHandler}>
                증가
            </button>
        </div>
    </>)

*/

     // 1. useEffect() 함수

/*
        컴포넌트의 생명주기 [ Life Cycle ]
        mount : 생성 , update: 업데이트 , unmount: 제거
        1.  useEffect( () => {} );
        2. mount , update 시 실행되는 함수
        3. 하나의 컴포넌트에서 여러번 사용 가능
        4. unmount 작동할 경우 return 사용
     */


     useEffect( () => {
        console.log("useEffect1 총 클릭수 : " + count)
     })

     useEffect( () => {
        console.log("useEffect2 총 클릭수 : " + count)
     })

    useEffect( () => {
        console.log("useEffect3 총 클릭수 : " + count)
        return () => { console.log('컴포넌트가 제거 되었을때')}//unmount
    })

    const countHandler = () => {
        setCount(count+1);
    }

        return (<>
            <div>
                <p> 총 {count} 번 클릭 했습니다 </p>
                <button onClick={countHandler}>
                    증가
                </button>
            </div>
        </>)



}