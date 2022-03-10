# studyJapanese

<h2>first</h2>

create github

<h2>BottomNavigationBar</h2>

<p>res에 android resource directory 선택 후 menu 폴더 생성
<p>NavigationBar 리소스 이름은 <b>bottom_menu</b>로 지정 

```java
//Navigation에 들어가는 item 지정, title이 보임, item 개수에 따라 개수가 정해짐
<item
        android:id="@+id/first_tab"
        app:showAsAction="always"
        android:enabled="true"
        android:icon="@drawable/write"
        android:title="Music" />
```

menu에서 설정한 NavigatiomBar 가져오기

```java
//상위 Layout이 RelativeLayout
//bottom_menu 
<com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_menu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:background="#345E86"
        app:itemIconTint="#FFFFFF"
        app:itemTextColor="#f6d170"
        app:labelVisibilityMode="labeled"
        app:menu="@menu/bottom_menu"/>
```
