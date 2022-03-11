# studyJapanese

<h2>first</h2>

create github

<h2>BottomNavigationBar</h2>

<p>res에 android resource directory 선택 후 menu 폴더 생성</p>
<p>NavigationBar 리소스 이름은 <b>bottom_menu</b>로 지정</p>

```java
//Navigation에 들어가는 item 지정, title이 보임, item 개수에 따라 개수가 정해짐
<item
        android:id="@+id/first_tab"
        app:showAsAction="always"
        android:enabled="true"
        android:icon="@drawable/write"
        android:title="Music" />
```

<p>menu에서 설정한 NavigatiomBar 가져오기</p>

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

- BottomNavigationBar적용 

![1111](https://user-images.githubusercontent.com/71477375/157703614-3f25027e-5863-49f0-b76c-c286557fde95.PNG)

<h2>BottomNavigationBar에 Fragment설정</h2>

<p>다음과 같이 BlankFragment생성</p>
<p>일반 class가 아닌 Fragment로 생성해야함</p>

![image](https://user-images.githubusercontent.com/71477375/157809868-413e3f40-9907-4925-971f-ee7805ae66a3.png)

<p>Fragment내의 기본 소스 설정</p>

```java
public class MusicFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //R.layout.X는 해당하는 Fragment의 xml의 이름을 가져오는 것
        return inflater.inflate(R.layout.fragment_music, container, false);
    }
}
```
        
<p>Main에서 설정한 Fragment가져오기</p>


```java
MusicFragment musicFragment;
musicFragment = new MusicFragment();

//어플 시작 시 초기 Fragment화면 설정
getSupportFragmentManager().beginTransaction().replace(R.id.container, musicFragment).commit();

//bottom_mune의 xml을 가져옴.
BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
     switch(item.getItemId()) {
     //bottom_menu의 item의 id를 가져온 것.
     case R.id.first_tab:
          getSupportFragmentManager().beginTransaction().replace(R.id.container, musicFragment).commit();
          return true;
     case R.id.second_tab:
          getSupportFragmentManager().beginTransaction().replace(R.id.container, lyricsFragment).commit();
          return true;
     }
     return false;
     }
});
```
