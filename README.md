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

<h2>다른 xml의 View를 가져오는 inflater</h2>

<p>inflater는 액티비티 간의 전환이 아닌, 액티비티 내의 특정 LinearLayout이 있는 공간만 바꾸고 싶을 때 사용</p>

- inflater 기본 코드

```java
LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//after_login이라는 이름의 xml을 일단 디폴트로 설정
//before_after_login이라는 activity_main.xml내의 LinearLayout의 이름을 가져와서, before_login.xml을 
inflater.inflate(R.layout.after_login, before_after_login, true);
```

<p>inflater로 다른 xml의 View가져와 사용하기</p>
<p>위에 LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);가 설정되어 있으면 그대로 사용할 수 있다.</p>

```java
TextView name = (TextView)findViewById(R.id.afterLoginTextView);
name.setText(getName + "님 환영합니다.");
```

<p>로그인 전 before와 후인 after를 xml로 만들어 둔 뒤, class내에서 로그인 성공 시 after로 바꿈</p>

<h2>내부 Room을 이용한 DB</h2>

- gradle의 dependencies에 다음과 같은 ROOM이용을 위한 소스 추가

```java
implementation 'androidx.room:room-runtime:2.3.0'
```

<p>만약 빌드 시 에러가 난다면 다음 implementation 위치에 annotationProcessor 'androidx.room:room-compiler:2.3.0'추가</p>

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

<p>Dao는 삽입, 갱신 등을 말함</p>
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

- signUpActivity클래스에서 userDao를 이용해 삽입, 갱신, 삭제를 함

<p>signUPActivity클래스에서 만들어둔 User를 사용하기 위해서는 Dao 인스턴스를 만들어야함.(User.table에 정보를 건들 수 있도록) </p>

```java
public class SignUpActivity extends AppCompatActivity {
    //ROOM이용
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        //UserDatabase를 이용한 db생성인데 tmddmddnjs_db는 table이름.
        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "tmddmddnjs_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        //uUserDao는 UserDao 인터페이스를 이용해, 삽입 갱신등을 함. 예시는 아래 삽입, 수정, 삭제로 확인해보자.
        mUserDao = database.userDao();
    }
}
```

- 위 uUserDao를 이용해 삽입, 갱신, 삭제를 해보자

<p>삽입</p>

```java
User uInsert = new User();
uInsert.setName("정승원");
uInsert.setAge("25");
uInsert.setPhoneNumber("010-1111-2222");

mUserDao.setInsertUser(uInsert);
```

<p>수정</p>

```java
//데이터 수정
User uUpdate = new User();
uUpdate.setId(1); //바꾸고 싶은 id 지정
uUpdate.setName("정유설");
uUpdate.setAge("24");
uUpdate.setPhoneNumber("010-1111-0000");

mUserDao.setUpdateUser(uUpdate);
```

<p>삭제</p>

```java
//원하는 id 1개만 삭제
User uDelete = new User();
uDelete.setId(1);
mUserDao.setDeleteUser(uDelete);
```

<p>한번에 다 삭제</p>

<p>UserDao에 다음과 같은 소스 추가</p>

```java
@Query("DELETE FROM User ")
    void deleteAll();
```

<p>조회</p>

```java
//User에 있는 List를 가져오는 것
List<User> userList = mUserDao.getUserAll();
//i는 id의 값
for (int i = 0; i < userList.size(); i++){
        Log.d("Test", userList.get(i).getName() + " "
        +userList.get(i).getPhoneNumber());
}
Log.d("test", userList.get(1).getName()+"");
```

<h2>ROOM 회원가입 시 아이디 중복 확인</h2>

<p>checkId로 db의 모든 UserId값을 확인하면서 id와 동일한 값이 있는지 확인해본다.</p>

```java
List<User> userList = mUserDao.getUserAll();
for (int i = 0; i < userList.size(); i++){
        String checkId = userList.get(i).getUserId() + "";
        if(checkId.equals(id + "")){
                Toast.makeText(SignUpActivity.this,"아이디가 중복되었습니다.", Toast.LENGTH_SHORT).show();
                return;
        }
}
```

<h2>intent로 activity간 값 교환</h2>

- 넘겨줄 때

```java
Intent intent = new Intent(LoginActivity.this, MainActivity.class);
putName = userList.get(i).getUserName() + "";
intent.putExtra("이름", putName);
startActivity(intent);
```

- 받을 때

```java
Intent intent = getIntent();
String getName = intent.getExtras().getString("이름");
```

<h2>musicFragment에 DB에 있는 아티스트명, 음악명을 RecyclerView로 나타내기</h2>

- RecyclerView 생성 방법

<p>1. music_recyclerview_item-list.xml이라는 실제 화면에 보여질 xml을 만든다.</P>

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <TextView
        android:layout_marginTop="20dp"
        android:layout_marginLeft="20dp"
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:id="@+id/item_list_textView1"/>

    <TextView
        android:layout_marginBottom="20dp"
        android:layout_marginLeft="20dp"
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:id="@+id/item_list_textView2"/>

    <View
        android:layout_width="match_parent"
        android:layout_height="2dp"
        android:background="#000000"/>


</LinearLayout>
```

<p>2. musicFragment.xml에 RecyclerView생성</p>

<p>3. MusicRecyclerViewitem.java를 생성한다</p>
<p>1에서 생성한 textView 2개에 넣을 데이터를 만든다. get, set을 이용해 데이터 교환 </p>

```java
public class MusicRecyclerViewItem {
    private String main;
    private String sub;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
```

<p>4. musicAdapter.java를 생성한다</p>
<p>viewholder를 사용하여 1의 xml의 view를 가져와서 아이템 뷰를 저장한다</p> 

```java
public class musicAdapter extends RecyclerView.Adapter<musicAdapter.musicHolder> {
    ArrayList<MusicRecyclerViewItem> list;

    public musicAdapter(ArrayList<MusicRecyclerViewItem> data){
        list = data;
    }

    @NonNull
    @Override
    public musicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.music_recyclerview_item_list, parent, false);
        musicAdapter.musicHolder mh = new musicAdapter.musicHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull musicHolder holder, int position) {
        MusicRecyclerViewItem item = list.get(position);
        holder.item_list_textView1.setText(item.getMain());
        holder.item_list_textView2.setText(item.getSub());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //아이템 뷰를 저장하는 뷰홀더 클래스스
   class musicHolder extends RecyclerView.ViewHolder{
        TextView item_list_textView1;
        TextView item_list_textView2;
        public musicHolder(@NonNull View itemView) {
            super(itemView);
            item_list_textView1 = itemView.findViewById(R.id.item_list_textView1);
            item_list_textView2 = itemView.findViewById(R.id.item_list_textView2);
        }
    }
}
```

<p>5. musicFragment에서 데이터를 집어넣는다</p>
<p>music.db에 있는 데이터들을 각각 main과 sub에 집어 넣는다.</p>
 
```java
//리스트 뷰 사용하기
RecyclerView mRecyclerView;
musicAdapter musicAdapter;
ArrayList<MusicRecyclerViewItem> list;
String artistName, musicName;

public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);

        //RecyclerView 생성
        mRecyclerView = rootView.findViewById(R.id.music_recyclerView);
        list = new ArrayList<>();

        musicAdapter = new musicAdapter(list);
        mRecyclerView.setAdapter(musicAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        //musicDB사용
        musicDatabase database = Room.databaseBuilder(rootView.getContext(), musicDatabase.class, "music_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mmusicDao = database.musicDao();

        //musicdb의 내용 이름들을 가져와서
        List<music> musicList = mmusicDao.getmusicAll();
        for(int i = 0; i<musicList.size(); i++) {
            artistName = musicList.get(i).getArtistName() + "";
            musicName = musicList.get(i).getMusicName() + "";
            //munuItems에 넣은 뒤 list에 넣는다
            addItem(artistName, musicName);
        }

        musicAdapter.notifyDataSetChanged();

        return rootView;
    }
    private void addItem(String main, String sub){
        MusicRecyclerViewItem item = new MusicRecyclerViewItem();
        item.setMain(main);
        item.setSub(sub);

        list.add(item);
    }
```

- 리사이클 뷰에 보여지는 db 속 내용

![실제 db내용](https://user-images.githubusercontent.com/71477375/158541455-672c6337-36af-4128-9af4-c56c95fb427b.PNG)

- 실제 화면 상에 보이는 리사이클 뷰

<p>artistName이 main이고, 아래에 musicName이 sub이다.</p>

![노래](https://user-images.githubusercontent.com/71477375/158541453-a77e496d-67a4-4f3b-b3de-c4a65fad1794.PNG)

