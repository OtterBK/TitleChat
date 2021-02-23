
<div align=center>
  
![header](https://capsule-render.vercel.app/api?type=soft&color=7200da&fontColor=f9c00c&height=130&section=header&text=%20Title　Chat%20&animation=scaleIn&fontSize=60&fontAlign=50&fontAlignY=50)

</div>
<br>

## 🌈 타이틀 챗 🕰
#### 가볍고 사용이 간편하며 원하는 주제에 대해서 대화가 가능한 공간을 제공하는 프리웨어 채팅 프로그램을 개발 📟
> 2학년 2학기 기초프로젝트 중간 과제📔
> JAVA 소켓 통신을 사용한 프로그램 개발과제

<br>

#### 📚 개요
> 사용자 자신이 원하는 주제를 선택하고 채팅방을 생성하거나 참여가 가능하여 사용자가 필요로 하는 정보에 대한 소통을 하기가 간단한 프로그램

``` 
+ 타이틀 챗은 GUI 기반 채팅 프로그램으로 데이터베이스를 사용하는 서버와 클라이언트로 나누어 개발이 필요함
+ 사용자에게 제공될 클라이언트는 직접적으로 데이터베이스에 접근하지 않아 프로그램이 가벼움
+ 패스워드를 찾기 위한 정보로서 이름, 연락처만을 사용자에게 요구하고 기능 또한 필수적인 부분만 인터페이스 보여주기에 사용이 간편함
+ 채팅 기록을 별도로 서버에 저장하지 않기에 정보 보호 가능
```

#### 🛠 동작 방식

<div align=center>
  
![동작방식](https://user-images.githubusercontent.com/28488288/108906810-a7442400-7664-11eb-9d78-65e015c02f8a.png)


` 타이틀 챗(Title Chat)은 주제별로 채팅을 나누어 사용자가 원하는 정보에 대한 소통을 쉽게 가능하도록 편리성에 중점을 둔 GUI 기반 채팅 프로그램임`

![구조1](https://user-images.githubusercontent.com/28488288/108909562-0c4d4900-7668-11eb-8f9d-704836969580.png) 
###### 클라이언트, 서버, 데이터베이스로  구성됨

</div>

---

#### 🔗 접근 방식

<div align=center>
  
![접근방식](https://user-images.githubusercontent.com/28488288/108909667-2d159e80-7668-11eb-816b-a415d7b85b9b.png)
##### 서버에서만 데이터베이스로 접근할 수 있음

` 보안성 향상 `

</div>

<br>

## 🛰 서버

#### 🖇 데이터베이스

<div align=center>
  
![서버1](https://user-images.githubusercontent.com/28488288/108912349-de6a0380-766b-11eb-89d7-f071ea284c13.png)

`서버는 데이터베이스와 JDBC를 이용하여 사용자의 정보를 관리,
데이터베이스로는 mySql을 사용함`

</div>

#### ⚙ 기능
<div align=center>
  
![서버2](https://user-images.githubusercontent.com/28488288/108912429-f6da1e00-766b-11eb-9d07-c5d053a646ed.png)

</div>

[서버의 주요기능]
```
- 접속 허가
- 로그인 확인
- PW 찾기
- 방 정보 전송
- 채팅 방 입장 요청
- 채팅 입력 처리
- 연결 종료 처리
- 로그표시용 GUI
```




## 🛰 클라이언트
> GUI 기반의 클라이언트 프로그램을 별도로 제작함
> 주 기능은 사용자에게 시각적 인터페이스를 제공하고 서버에 연결하여 요청, 처리 메시지를 보냄

#### :recycle: 처리 구성도

<div align=center>
  
![클라1](https://user-images.githubusercontent.com/28488288/108913557-79171200-766d-11eb-88ab-9a19bb10e8fc.png)

</div>

---

#### :school_satchel: 사용 API
```
* jdbc - 데이터베이스 사용
```
<br>

### :exclamation: 자세한 내용은 계획서를 확인해주세요. :exclamation:
#### :ledger: [타이틀 챗 계획서](https://github.com/OtterBK/TitleChat/tree/master/%EA%B3%84%ED%9A%8D%EC%84%9C)

<br>

#### 📷 스냅샷

![스냅샷](https://user-images.githubusercontent.com/28488288/108914496-c47df000-766e-11eb-964a-00dc52f358cb.gif)

