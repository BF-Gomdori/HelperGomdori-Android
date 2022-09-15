# 도움곰돌이

<img src="https://user-images.githubusercontent.com/50831854/188374855-01bd3ea7-285e-49b7-9896-9c43e51095d2.png" alt="도움곰돌이" width="100" height = "100" />

> 장애인을 위한 실시간 도움(봉사자) 매칭 서비스

- 장애인이 **자유롭고 안전하게** 외부 활동을 할 수 있도록
- 특정 동반자가 아니라 **사회 구성원 전체**로부터 도움을 받을 수 있도록
- **도움이 돌고 돌았으면** 하는 바람을 담아 만든 안드로이드 앱 서비스입니다.

## 앱 사용 예시
<img src = "https://user-images.githubusercontent.com/50831854/188384358-3d8ae430-2deb-4056-9d2d-0b1e218a0049.gif" alt="실시간영상" width = "300" />

## OverView
- **곰돌이** : 장애인(도움받는 사람) / **베프**: 비장애인(도움주는 사람)
- **지도 API**와 **GPS**를 사용하여 다수의 사람들과 위치 공유
- **WebSocket**을 사용하여 실시간으로 도움 요청을 받아오고 보여줍니다
- 도움 요청시 베프에게 실시간 **Push 알림**이 제공됩니다.
- 이를 통해 즉각적인 도움을 줄 수 있어 사고 예방 가능

### Packaging
```text
🎈com.bf.helpergomdori
 ┣ 📃HelperGomdoriApplication
 ┣ 📂base
 ┣ 📂data  
 ┃ ┣ 📃ApiService
 ┃ ┣ 📂dataSource 
 ┃ ┃ ┣ 📃RemoteDataSource
 ┃ ┃ ┗ 📃RemoteDataSourceImpl
 ┃ ┣ 📂repository 
 ┃ ┃ ┣ 📃LoginRepository
 ┃ ┃ ┗ 📃RemoteDataSource
 ┣ 📂di
 ┣ 📂model
 ┣ 📂service
 ┃ ┗ 📃BfFirebaseService
 ┣ 📂ui
 ┃ ┣ 📂askHelp
 ┃ ┣ 📂main  
 ┃ ┣ 📂mypage
 ┃ ┣ 📂request
 ┃ ┣ 📂response
 ┃ ┗ 📂signin  
 ┣ 📂util
 ┃ ┣ 📃LocationUtil
 ┃ ┣ 📃WebSocketUtil
 ┃ ┣ 📃NotificationUtil
 ┗ ┗ 📃HelperGomdoriConst
```

## 앱 사용 Flow
- 베프는 앱에 진입하면 바로 지도에 위치 띄워짐
- 곰돌이는 도움 요청하면 지도에 위치 띄워짐 & 베프에게 도와달라는 Push 알림 뜸
- 베프가 도움 응답하고, 곰돌이가 이를 수락하면 도움이 시작됨
- 두 사용자의 원활한 매칭을 위해 길찾기 기능 및 전화 기능이 제공됨
- 상황이 종료되면 앱 내에서 감사 인사 혹은 평가를 보낼 수 있음
- 감사 인사 및 평가는 추후에 사용자의 신뢰 확보를 위한 데이터로 사용됨
