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

- 다음과 같이 BlankFragment생성
<p>일반 class가 아닌 Fragment로 생성해야함</p>

![image](https://user-images.githubusercontent.com/71477375/157809868-413e3f40-9907-4925-971f-ee7805ae66a3.png)

- Fragment내의 기본 소스 설정

```java
public class MusicFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //R.layout.X는 해당하는 Fragment의 xml의 이름을 가져오는 것
        return inflater.inflate(R.layout.fragment_music, container, false);
    }
}
```
        
- Main에서 설정한 Fragment가져오기


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
                //item이름이 R.id.first_tab인 경우 musicFragment인 Framgent로 교체
                //R.id.container는 activity_main에 있는 FrameLayout의 id이름임.
                getSupportFragmentManager().beginTransaction().replace(R.id.container, musicFragment).commit();
                return true;
        case R.id.second_tab:
                //item이름이 R.id.second_tab인 경우 lyricsFragment인 Fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.container, lyricsFragment).commit();
                return true;
                }
        return false;
        }
});
```

<h2>login화면 바꾸기 inflater</h2>

<p>로그인 전 before와 후인 after를 xml로 만들어 둔 뒤, class내에서 로그인 성공 시 after로 바꿈</p>

- inflater 기본 코드

```java
LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//before_login이라는 이름의 xml을 일단 디폴트로 설정
//before_after_login이라는 activity_main.xml내의 LinearLayout의 이름을 가져와서, before_login.xml을 
inflater.inflate(R.layout.before_login, before_after_login, true);
```

