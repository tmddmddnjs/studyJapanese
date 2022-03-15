# studyJapanese

<h2>first</h2>

create github

<h2>BottomNavigationBar</h2>

<p>res에 android resource directory 선택 후 menu 폴더 생성</p>

- NavigationBar 리소스 이름은 <b>bottom_menu</b>로 지정

```java
//Navigation에 들어가는 item 지정, title이 보임, item 개수에 따라 개수가 정해짐
<item
        android:id="@+id/first_tab"
        app:showAsAction="always"
        android:enabled="true"
        android:icon="@drawable/write"
        android:title="Music" />
```

- menu에서 설정한 NavigatiomBar 가져오기

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

![image](https://user-images.githubusercontent.com/71477375/157809868-413e3f40-9907-4925-971f-ee7805ae66a3.png)

<p>일반 class가 아닌 Fragment로 생성해야함</p>

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

<p>inflater는 액티비티 간의 전환이 아닌, 액티비티 내의 특정 LinearLayout이 있는 공간만 바꾸고 싶을 때 사용</p>

- inflater 기본 코드

```java
LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//before_login이라는 이름의 xml을 일단 디폴트로 설정
//before_after_login이라는 activity_main.xml내의 LinearLayout의 이름을 가져와서, before_login.xml을 
inflater.inflate(R.layout.before_login, before_after_login, true);
```

<p>로그인 전 before와 후인 after를 xml로 만들어 둔 뒤, class내에서 로그인 성공 시 after로 바꿈</p>

<h2>내부 Room을 이용한 DB</h2>

- gradle의 dependencies에 다음과 같은 ROOM이용을 위한 소스 추가

```java
implementation 'androidx.room:room-runtime:2.3.0'
```

<p>만약 빌드 시 에러가 난다면 다음 implementation 위치에 annotationProcessor 'androidx.room:room-compiler:2.3.0' 추가</p>

- User에 대한 정보를 get, set하는 class

<p>다음과 같이 @Entity를 추가함으로 User를 DB로 사용하겠다는 뜻이고, 각각 get, set을 만들어 줘야함</p>
<p>여기에 들어가는 userxxx들은 전부 DB에 들어가는 객체 값들</p>
        
```java
@Entity
public class User {
    //id 값은 자동적으로 1씩 더해지도록 하는 것이 PrimaryKey이다.
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String userID;
    private String userPass;
    private String userName;
    private String userAge;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
```

- UserDao에서는 User데이터 베이스의 삽입, 갱신, 삭제, Query등을 만들어서 DB를 조작하는 인터페이스

<p>@Dao를 추가하고 @Insert등을 만들고 밑에 메소드를 만듬</p>
<p>class가 아닌 interface로 만들어야함</p>

```java
@Dao
public interface UserDao {
    //삽입
    @Insert
    void setInsertUser(User user);

    //수정
    @Update
    void setUpdateUser(User user);

    //삭제
    @Delete
    void setDeleteUser(User user);

    //DB 요청 명령문
    @Query("SELECT * from User")
    List<User> getUserAll();

}
```

- UserDatabase는 추상으로 만들어서, @Database에 아까 User.class에서 만든 entities를 가져온다. version = 1

<p>추상으로 만들고 RoomDatabase를 상속받는다.</p>
<p>갱신 삭제 등을 하는 UserDao를 가져와서 추상메소드로 만든다.</p>

```java
@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```
