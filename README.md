# Fill-Android

필름 현상소, 필름 정보제공 서비스   
당신의 추억으로 FILL-IN</br></br>
<img width="90%" src="https://user-images.githubusercontent.com/56147398/148933940-1c02f148-6d3b-45c8-a3de-cfd9213ac6db.jpeg"/>

## 기술 스택 💻
- Architecture: MVC + MVVM
- Android Jetpack
    - Lifecycle
    - AAC
    - Dagger-Hilt
    - App Startup
    - DataBinding/ViewBinding
    - Security(EncryptedSharedPreference)
    - Paging3
    - SplashScreen Core
- Modern Kotlin
    - Coroutines + Flow
- CI/CD
    - Github Action
    - Slack
- Glide
- Retrofit/Okhttp
- Kakao SDK
- NaverMap SDK
- Timber
- Flipper

## 역할 📸
- 이강민 : 지도 뷰, 지도 검색, 지도 상세정보
- 윤현지 : 마이페이지, 사진업로드 및 스튜디오필름 선택
- 김수빈 : 메인 홈, 필름롤 , 사진상세보기(모달창)
- 이현우 : 소셜 로그인, 프로젝트 설정, 유틸, 그 외 작업

## 폴더링 구조 📂
<img width="50%" src="https://user-images.githubusercontent.com/54518925/150623426-7f574390-9858-4603-af58-37c52be1e943.png"/></br>
<img width="50%" src="https://user-images.githubusercontent.com/54518925/150623430-4f8a1b6d-3719-4bc4-a57c-76ea3245fc69.png"/></br>
### package name은 반드시 소문자로 작성
- presentation -> 뷰 관련 작업
- di -> 의존성 주입 관련 모듈
- data -> 서버, 데이터 관련 작업
- core -> util 확장함수 모듈   

## 컨벤션 🎞
- Coding Convention: <https://www.notion.so/66jxndoe/Coding-Convention-0c07e7e3d55740e291a490c84062e33f>
- Branch 전략: <https://www.notion.so/66jxndoe/Branch-4ac90596ba404905962ca2ba839fcbc2>
- Github Convention: <https://www.notion.so/66jxndoe/GitHub-Convention-7880943da7534f5e94a023ebfe043c57>
